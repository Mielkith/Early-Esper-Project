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
import java.util.List;
import java.util.Map;

/**
 *
 * @author gs023850
 */
public class GetNominals {
    
    
    public static  Map<String, List<String>> main() throws Exception
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
     return colProcessor.getColumnValuesAsMapOfNames();

    }
    
  
}
