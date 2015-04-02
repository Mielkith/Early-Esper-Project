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
         if (newData.length > 2)
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
                    values.addElement(labels.GetLabel(columnNumbers[j], i));
                }
                atts.addElement(new Attribute(labels.GetHeader(columnNumbers[j]), values));
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
