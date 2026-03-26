package export;

import model.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Utility class used to export benchmark results into a CSV file.
 * Each row corresponds to a single run of a sorting algorithm.
 */

public class CsvExporter {

	/**
	 * Writes a summary of sorting statistics to a CSV file.
	 *
	 * @param filePath path of the output CSV file
	 * @param statsList list of SortStats objects to export
	 */

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
	
	/**
	 * Escapes a string for safe inclusion in a CSV file.
	 * Double quotes are duplicated to preserve formatting.
	 *
	 * @param value input string
	 * @return escaped string safe for CSV output
	 */

	private static String escapeCsv(String value) {
		if (value == null) {
		    	return "";
		}
		return value.replace("\"", "\"\"");
    	}
	    
}
