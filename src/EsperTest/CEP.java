/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EsperTest;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.SimpleLayout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import com.espertech.esper.client.*;


/**
 *Create CEP engine and rules
 * @author gs023850
 */
public  class CEP {

         static int[] colNumbers = {10,28,0,16,13,30,25,22};
    /** 
     * @param args the command line arguments
     */

        public static void main(String[] args) throws Exception{
  
        SimpleLayout layout = new SimpleLayout();
        ConsoleAppender appender = new ConsoleAppender(new SimpleLayout());
        Logger.getRootLogger().addAppender(appender);
        Logger.getRootLogger().setLevel((Level) Level.WARN);
        
        NominalLabels labels = new NominalLabels();
      
       
        //The Configuration is meant only as an initialization-time object.
        Configuration cepConfig = new Configuration();
        // We register Ticks as objects the engine will have to handle
        cepConfig.addEventType("Tick",Tick.class.getName());

        // We setup the engine
        EPServiceProvider cep = EPServiceProviderManager.getProvider("myCEPEngine",cepConfig);
        
        EPRuntime cepRT = cep.getEPRuntime();

        // We register an EPL statement
        int i = 0;
        EPAdministrator cepAdm = cep.getEPAdministrator();
        
        String  selectStatement =  
           "select attribute1 as "  + labels.GetHeader(colNumbers[i++]) + ", "
                + "attribute2 as "  + labels.GetHeader(colNumbers[i++]) + ", "
                + "attribute3 as "  + labels.GetHeader(colNumbers[i++]) + ", "
                + "attribute4 as "  + labels.GetHeader(colNumbers[i++]) + ", "
                + "attribute5 as "  + labels.GetHeader(colNumbers[i++]) + ", "
                + "attribute6 as "  + labels.GetHeader(colNumbers[i++]) + ", "
                + "attribute7 as "  + labels.GetHeader(colNumbers[i++]) + ", "
                + "attribute8 as " + labels.GetHeader(colNumbers[i++])
                + " from Tick.win:time_batch(20 sec)"; 
        
        EPStatement cepStatement = cepAdm.createEPL(
               selectStatement); 
        CEPListener listener = new CEPListener();
         cepStatement.addListener(listener);
         //set the labels for the nominal attributes
         listener.SetLabels(labels);
         listener.SetColNumber(colNumbers);
         Thread t = new Thread(new GenerateStream(cepRT, colNumbers));
         t.run();
       
        
           
        }
        
        

    
    
}


