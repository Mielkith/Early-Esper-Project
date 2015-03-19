/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EsperTest;


import com.espertech.esper.client.*;
import java.util.ArrayList;
import weka.core.*;
import weka.associations.Apriori;
/**
 *
 * @author gs023850
 */
public class CEPListener implements UpdateListener {
    
    private FastVector events = new FastVector();
    private FastVector eventValues = new FastVector();
    
 public void update(EventBean[] newData, EventBean[] oldData) {
         System.out.println("Event received: "
                            + newData[0].getUnderlying());
         Attribute EventType = new Attribute("EvenType");
         
         Instances set = new Instances("Events",events,0);
         
         for (int i = 0; i < newData.length; i++)
         {
         String temp = newData[i].get("eventType").toString();
         eventValues.addElement(temp);
         }
                 
          Instance inst = new Instance(1); 
         inst.setValue(EventType, eventValues);
        Apriori aprioriObj = new weka.associations.Apriori();
      

        Instances instances = new weka.core.Instances(set);
        try{
        aprioriObj.buildAssociations(instances);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
        
        FastVector rules[] = aprioriObj.getAllTheRules();
        
       
        

         
    }
 
 
 
}
