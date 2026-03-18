package benchmark;

import generator.EntropyGenerator;
import model.SortStats;
import sorting.*;
import util.*;
import export.CsvExporter;

import java.util.*;

public class BenchmarkRunner {

    	public static void main(String[] args) {

        	List<SortStats> results = new ArrayList<>();

        	int size = 5000;
        	double entropy = 0.5;

        	EntropyGenerator generator = new EntropyGenerator(entropy, size);
        	List<Integer> data = new ArrayList<>(generator.getList()); // safe copy

        	SortStats stats = new SortStats(
                	"CocktailShakerSort",
                	"Entropy_" + entropy + "_n=" + size
        	);

        	SortingListener.clearListeners();
        	SortingListener.addListener(new StatsListener(stats));

        	stats.start();

        	CocktailShakerSort.sort(data);

        	stats.stop();

        	results.add(stats);

        	CsvExporter.exportSummary("results.csv", results);

        	System.out.println("Done + exported!");
    	}
}
