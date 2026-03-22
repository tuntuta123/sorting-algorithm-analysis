package export;

import model.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class CsvExporter {

	public static void exportSummary(String filePath, List<SortStats> statsList) {
       		try (FileWriter writer = new FileWriter(filePath)) {

            		writer.append("algorithm,generatorLabel,generatorType,entropy,size,run,comparisons,swaps,accesses,timeMs\n");

		    	for (SortStats s : statsList) {
		        	writer.append(String.format(Locale.US,"\"%s\",\"%s\",\"%s\",%.2f,%d,%d,%d,%d,%d,%d\n",
				        escapeCsv(s.getAlgorithmName()),
                        		escapeCsv(s.getGeneratorLabel()),
                        		escapeCsv(s.getGeneratorType()),
                        		s.getEntropy(),
                        		s.getSize(),
                        		s.getRun(),
                        		s.getComparisons(),
                        		s.getSwaps(),
                        		s.getAccesses(),
                        		s.getElapsedMs()
		        	));
		    	}

		} 
		catch (IOException e) {
		    	e.printStackTrace();
		}
	}
	
	private static String escapeCsv(String value) {
		if (value == null) {
		    	return "";
		}
		return value.replace("\"", "\"\"");
    	}
	    
}
