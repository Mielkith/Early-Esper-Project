/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EsperTest;


import com.espertech.esper.client.*;
import java.util.List;
import java.util.Map;
import weka.core.*;
import weka.associations.Apriori;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Add;
/**
 *
 * @author gs023850
 */
public class CEPListener implements UpdateListener {
    
    private FastVector dataSet = new FastVector();
    private FastVector eventValues = new FastVector();
    NominalLabels labels;
    int columnNumbers[];
    
    
    public void SetLabels(NominalLabels labels){
        this.labels = labels;
    }   
    
    public void SetColNumber(int colNumbers[])
    {
        columnNumbers = colNumbers;
    }
    
 public void update(EventBean[] newData, EventBean[] oldData)  {
         
     System.out.println("Event received: "
                            + newData[0].getUnderlying()
         );
         if (newData.length > 10)
         {
        //create the column name and type, these are strings
        //http://weka.wikispaces.com/Creating+an+ARFF+file
         Instances data;
          FastVector atts = new FastVector();
           
             
           for (int j = 0; j < columnNumbers.length; j++)
           {
                FastVector values = new FastVector();
                for (int i = 0; i < labels.NominalCount(j) ; i++)
                {
                    values.addElement(labels.GetLabel(j, i));
                }
                atts.addElement(new Attribute(labels.GetHeader(j), values));
           }

           data = new Instances("Title", atts, 0);
           
         
           
           for (int i = 0; i < newData.length; i++) {
                Instance inst = new Instance(columnNumbers.length);
                for (int j =0; j < columnNumbers.length; j++)
                {
                  inst.setValue(j,newData[i].get("eventType").toString());
                }
               data.add(inst);
           }
           
           

//string attributes, though one is a time
        Attribute eventName  = new Attribute("eventName", (FastVector)null);
        Attribute eventType  = new Attribute("eventType", (FastVector)null);
        Attribute eventTime  = new Attribute("eventTime", (FastVector)null);  
        Attribute elementClassName  = new Attribute("elementClassName", (FastVector)null);
        Attribute category  = new Attribute("category", (FastVector)null);
        //numerical attributes, though some of these may be booleans
        Attribute duration  = new Attribute("duration");
        Attribute active  = new Attribute("active");
        Attribute eventSeverity  = new Attribute("eventSeverity");
         
         
       //the FastVector forms a dataset, combine the columns into a table
        dataSet.addElement(eventName);
          dataSet.addElement(eventType);
          dataSet.addElement(eventSeverity);
          dataSet.addElement(eventTime);
          dataSet.addElement(active);
          dataSet.addElement(elementClassName);
          dataSet.addElement(category);
          dataSet.addElement(duration);




         
         
         //Instances data = new Instances("Events",dataSet,0);
         
         Instances numericalFilteredSet = null; //this is to hold the instances after they have been converted to nominals, weka does not go for strings
         Instances stringFilteredSet = null; //this is to hold the instances after they have been converted to nominals, weka does not go for strings

         
         //weka stores all things ,even strings, as doubles
         //populate a weka data data by extracting the string values from the EventBean data object
         //double[] row = new double[data.numAttributes()];
        


         
         for (int i = 0; i < newData.length; i++)
         {
         double[] rowS = new double[5];  //https://weka.wikispaces.com/Creating+an+ARFF+file suggests it needs a new array each time
         double[] rowN = new double[3];
         rowS[0] = data.attribute("eventName").addStringValue(newData[i].get("eventName").toString());
         rowS[1] = data.attribute("eventType").addStringValue(newData[i].get("eventType").toString());
         rowS[2] = data.attribute("eventTime").addStringValue(newData[i].get("eventTime").toString());       
         rowS[3] = data.attribute("elementClassName").addStringValue(newData[i].get("elementClassName").toString());
         rowS[4] = data.attribute("category").addStringValue(newData[i].get("category").toString());
         rowN[0] = data.attribute("eventSeverity").addStringValue(newData[i].get("eventSeverity").toString());
         rowN[2] = data.attribute("active").addStringValue(newData[i].get("active").toString());
         rowN[3] = data.attribute("duration").addStringValue(newData[i].get("duration").toString());

       //  data.setValue(EventType, temp);
         data.add(new Instance(1.0, rowS));
         }
                 
 
       
       //weka.filters.unsupervised.attribute.StringToNominal ff=new weka.filters.unsupervised.attribute.StringToNominal(); // new instance of filter
      weka.filters.unsupervised.attribute.NumericToNominal numericFilter = new weka.filters.unsupervised.attribute.NumericToNominal(); // new instance of filter
      weka.filters.unsupervised.attribute.StringToNominal stringFilter = new weka.filters.unsupervised.attribute.StringToNominal(); // new instance of filter

       try{
               String[] numericalOptions = new String[2];
               numericalOptions[0] = "-R";                // "range"
               numericalOptions[1] = "6-8";  
               numericFilter.setOptions(numericalOptions); 
               numericFilter.setInputFormat(data);                  
               numericalFilteredSet = Filter.useFilter(data, numericFilter);
               }
        catch(java.lang.Exception e)
        { 
            System.out.println(e);
        }
       try{
               String[] stringOptions = new String[2];
               stringOptions[0] = "-R";                
               stringOptions[1] = "1-5";  
               stringFilter.setOptions(stringOptions); 
               stringFilter.setInputFormat(numericalFilteredSet);                  
               stringFilteredSet = Filter.useFilter(numericalFilteredSet, stringFilter);
               }
        catch(java.lang.Exception e)
        { 
            System.out.println(e);
        }
         //inst.setValue(EventType, eventValues);
        Apriori aprioriObj = new weka.associations.Apriori();
      
        try{
        aprioriObj.buildAssociations(data);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        
        FastVector rules[] = aprioriObj.getAllTheRules();
        
       
         }

         
    }
     
 
}
