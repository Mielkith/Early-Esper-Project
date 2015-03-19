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

    /**
     * @param args the command line arguments
     */

        public static void main(String[] args) {
  
        SimpleLayout layout = new SimpleLayout();
        ConsoleAppender appender = new ConsoleAppender(new SimpleLayout());
        Logger.getRootLogger().addAppender(appender);
        Logger.getRootLogger().setLevel((Level) Level.WARN);
      
      
        //The Configuration is meant only as an initialization-time object.
        Configuration cepConfig = new Configuration();
        // We register Ticks as objects the engine will have to handle
        cepConfig.addEventType("Tick",Tick.class.getName());

        // We setup the engine
        EPServiceProvider cep = EPServiceProviderManager.getProvider("myCEPEngine",cepConfig);
        
        EPRuntime cepRT = cep.getEPRuntime();

        // We register an EPL statement
        EPAdministrator cepAdm = cep.getEPAdministrator();
        EPStatement cepStatement = cepAdm.createEPL("select attribute2 as eventType from Tick.win:time(30)"); 
        //+ "having avg(price) > 6.0");
         cepStatement.addListener(new CEPListener());
         
         Thread t = new Thread(new GenerateStream(cepRT));
         t.run();
       
        
           
        }

    
    
}


