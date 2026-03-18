package export;

import model.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvExporter {

    public static void exportSummary(String filePath, List<SortStats> statsList) {
        	try (FileWriter writer = new FileWriter(filePath)) {

            		writer.append("algorithm,generator,comparisons,swaps,timeMs\n");

		    	for (SortStats s : statsList) {
		        	writer.append(String.format("%s,%s,%d,%d,%d\n",
				        s.getAlgorithmName(),
				        s.getGeneratorLabel(),
				        s.getComparisons(),
				        s.getSwaps(),
				        s.getElapsedMs()
		        	));
		    	}

		} 
		catch (IOException e) {
		    	e.printStackTrace();
		}
	    }
}
