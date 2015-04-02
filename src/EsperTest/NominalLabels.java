/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EsperTest;

import com.univocity.parsers.common.processor.ColumnProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author gs023850
 */
public  class NominalLabels {
    
    private  Map<Integer, List<String>> labels;
    
    public NominalLabels() throws Exception 
    {
    String filePath = "C:\\Users\\gs023850\\Documents\\w4ndata\\distinctValues.csv";
      //https://github.com/uniVocity/univocity-parsers/#reading-csv
     CsvParserSettings settings = new CsvParserSettings();
     settings.setHeaderExtractionEnabled(true);
     ColumnProcessor  colProcessor = new ColumnProcessor ();
     settings.setRowProcessor(colProcessor);
    // settings.selectIndexes(0,11,16,20,22,27,28,30);
     CsvParser parser = new CsvParser(settings);
     parser.parse(new FileReader(filePath));
     //     labels =  colProcessor.getColumnValuesAsMapOfNames();

     labels =  colProcessor.getColumnValuesAsMapOfIndexes();
    
     /*Iterator<Map.Entry<Integer, List<String>>> it = labels.entrySet().iterator();
      
     while (it.hasNext()) {
          
            Map.Entry<Integer, List<String>> e = it.next();
            Integer key = e.getKey();
            List<String> value = e.getValue();
            Iterator<String> its = value.iterator();
            while(its.hasNext())
            {
                if (its.toString() == null || its.toString() == "null") {
                    its.remove();
                }
            }
        }*/
     

    
    };
    
   public String GetHeader(int j)
   {
       return labels.get(j).get(0);
   }
    
   public int NominalCount(int j)
   {
       return labels.get(j).size();
   }
   
    public String GetLabel(int i, int j)
    {
         return labels.get(i).get(j);
    }
    public  String GetLabels(int j)
      {
          StringBuilder sb = new StringBuilder();
          for (String label: labels.get(j))
          {
              if (label.isEmpty()) {break;}
              sb.append(label);
              sb.append(",");

          }
          return sb.toString();
      }
 
    
  
}
