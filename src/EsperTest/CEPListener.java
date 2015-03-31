/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EsperTest;


import com.espertech.esper.client.*;
import weka.core.*;
import weka.associations.Apriori;
import weka.filters.Filter;
/**
 *
 * @author gs023850
 */
public class CEPListener implements UpdateListener {
    
    private FastVector dataSet = new FastVector();
    private FastVector eventValues = new FastVector();
    
 public void update(EventBean[] newData, EventBean[] oldData) {
         
     System.out.println("Event received: "
                            + newData[0].getUnderlying()
         );
         if (newData.length > 10)
         {
        //create the column name and type, these are strings
        //http://weka.wikispaces.com/Creating+an+ARFF+file
             
         //Attribute eventName  = new Attribute("eventName", (FastVector)null);
       //  Attribute eventType  = new Attribute("eventType", (FastVector)null);
         Attribute eventSeverity  = new Attribute("eventSeverity");
      //  Attribute eventTime  = new Attribute("eventTime", (FastVector)null);
         Attribute active  = new Attribute("active");
      //   Attribute elementClassName  = new Attribute("elementClassName", (FastVector)null);
      //   Attribute category  = new Attribute("category", (FastVector)null);
         Attribute duration  = new Attribute("duration");

         
         
         //the FastVector forms a dataset, combine the columns into a table
     //    dataSet.addElement(eventName);
     //    dataSet.addElement(eventType);
         dataSet.addElement(eventSeverity);
   //      dataSet.addElement(eventTime);
         dataSet.addElement(active);
    //     dataSet.addElement(elementClassName);
     //    dataSet.addElement(category);
         dataSet.addElement(duration);




         
         
         Instances set = new Instances("Events",dataSet,0);
         Instances set2 = null; //this is to hold the instances after they have been converted to nominals, weka does not go for strings
          
         
         //weka stores all things ,even strings, as doubles
         //populate a weka data set by extracting the string values from the EventBean data object
         double[] row = new double[set.numAttributes()];
         for (int i = 0; i < newData.length; i++)
         {
       //  row[0] = set.attribute("eventName").addStringValue(newData[i].get("att1").toString());
         //row[1] = set.attribute("eventType").addStringValue(newData[i].get("att2").toString());
         //row[2] = set.attribute("eventSeverity").addStringValue(newData[i].get("att3").toString());
      //   row[3] = set.attribute("eventTime").addStringValue(newData[i].get("att4").toString());
         
         row[0] = set.attribute("eventSeverity").addStringValue(newData[i].get("att1").toString());
         row[1] = set.attribute("active").addStringValue(newData[i].get("att2").toString());
         row[2] = set.attribute("duration").addStringValue(newData[i].get("att3").toString());

       //  set.setValue(EventType, temp);
         set.add(new Instance(1.0, row));
         }
                 
      String[] options = new String[2];
       options[0] = "-R";                // "range"
       options[1] = "0-99999999";                 // first attribute

       //weka.filters.unsupervised.attribute.StringToNominal ff=new weka.filters.unsupervised.attribute.StringToNominal(); // new instance of filter
      weka.filters.unsupervised.attribute.NumericToNominal ff = new weka.filters.unsupervised.attribute.NumericToNominal(); // new instance of filter

       try{
               ff.setOptions(options); 
        // set options
               ff.setInputFormat(set);                          // inform filter about dataset **AFTER** setting options
               set2 = Filter.useFilter(set, ff);
               }
        catch(java.lang.Exception e)
        { 
            System.out.println(e);
        }
         //inst.setValue(EventType, eventValues);
        Apriori aprioriObj = new weka.associations.Apriori();
      
        try{
        aprioriObj.buildAssociations(set2);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        
        FastVector rules[] = aprioriObj.getAllTheRules();
        
       
         }

         
    }
 
 
 
}
