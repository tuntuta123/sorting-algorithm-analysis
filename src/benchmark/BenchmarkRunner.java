package benchmark;

import export.*;
import generator.*;
import model.*;
import sorting.*;
import util.*;

import java.util.*;

public class BenchmarkRunner {

    	public static void main(String[] args) {

        	List<BenchmarkConfig> configs = buildConfigs();
        	List<SortStats> results = new ArrayList<>();

        	for (BenchmarkConfig config : configs) {
            		System.out.println("Running: " + config);

		    	for (int run = 1; run <= config.getRepetitions(); run++) {

				List<Integer> data = buildData(
				        	config.getGeneratorType(),
				        	config.getSize(),
				        	config.getEntropy()
				);

				SortStats stats = new SortStats(
				        	config.getAlgorithmName(),
				        	buildGeneratorLabel(config, run)
				);

				SortingListener.clearListeners();
				SortingListener.addListener(new StatsListener(stats));

		        	stats.start();

		        	runAlgorithm(config.getAlgorithmName(), data);

		        	stats.stop();

		        	results.add(stats);

		        	System.out.println("Finished run " + run + " for " +
		                	config.getAlgorithmName() + " / " +
		                	config.getGeneratorType());
		    	}
        	}

        	CsvExporter.exportSummary("results.csv", results);
        	System.out.println("benchmarks completed.");
    }

    private static List<BenchmarkConfig> buildConfigs() {
        	List<BenchmarkConfig> configs = new ArrayList<>();

        	int[] sizes = {100, 500/*, 1000*/};
        	double[] entropies = {0.0, 0.25, 0.50, 0.75};
        	int repetitions = 1;

        	String[] algorithms = {
		        "BubbleSort",
		        /*"CocktailShakerSort",*/
		        /*"CombSort",*/
		        "InsertionSorting",
		        "MergeSorting",
		        "QuickSort",
		        /*"PancakeSort",*/
		        "BucketSort"
        	};

        	for (int i = 0; i < algorithms.length; i++) {
            		String algo = algorithms[i];

            		for (int j = 0; j < sizes.length; j++) {
                		int size = sizes[j];

                		configs.add(new BenchmarkConfig(algo, "random", size, -1.0, repetitions));

                		for (int k = 0; k < entropies.length; k++) {
                    			double entropy = entropies[k];
                    			configs.add(new BenchmarkConfig(algo, "entropy", size, entropy, repetitions));
                    			configs.add(new BenchmarkConfig(algo, "reverse_entropy", size, entropy, repetitions));
                		}
            		}		
        	}

        	return configs;
    	}

    	private static List<Integer> buildData(String generatorType, int size, double entropy) {
        	if (generatorType.equals("random")) {
            		return new ArrayList<>(new RandomGenerator(size).getList());
        	} 
        	else if (generatorType.equals("entropy")) {
            		return new ArrayList<>(new EntropyGenerator(entropy, size).getList());
        	} 
        	else if (generatorType.equals("reverse_entropy")) {
            		return new ArrayList<>(new ReverseEntropyGenerator(entropy, size).getList());
        	} 
        	else {
            		throw new IllegalArgumentException("Unknown generator type: " + generatorType);
        	}
    	}

    	private static String buildGeneratorLabel(BenchmarkConfig config, int run) {
		if (config.getGeneratorType().equals("random")) {
		    	return "Random_n=" + config.getSize() + "_run=" + run;
		} 
		else if (config.getGeneratorType().equals("entropy")) {
		    	return "Entropy_" + config.getEntropy() + "_n=" + config.getSize() + "_run=" + run;
		} 
		else if (config.getGeneratorType().equals("reverse_entropy")) {
		    	return "ReverseEntropy_" + config.getEntropy() + "_n=" + config.getSize() + "_run=" + run;
		} 
		else {
		    	return "Unknown_n=" + config.getSize() + "_run=" + run;
		}
    	}

    	private static void runAlgorithm(String algorithmName, List<Integer> data) {
        	switch (algorithmName) {
            		case "BubbleSort":
                		BubbleSort.sort(data);
                	break;
            		case "CocktailShakerSort":
                		CocktailShakerSort.sort(data);
                	break;
            		case "CombSort":
               		 	CombSort.sort(data);
                	break;
            		case "InsertionSorting":
                		InsertionSorting.sort(data);
                	break;
            		case "MergeSorting":
                		MergeSorting.sort(data);
                	break;
            		case "QuickSort":
                		QuickSort.sort(data);
                	break;
            		case "PurgeSort":
                		PurgeSort.sort(data);
                	break;
            		case "ModeratePurgeSort":
                		ModeratePurgeSort.sort(data);
                	break;
            		case "PancakeSort":
                		PancakeSort.sort(data);
                	break;
            		case "BucketSort":
                		BucketSort.sort(data);
                	break;
            		default:
                		throw new IllegalArgumentException("Unknown algorithm: " + algorithmName);
        	}
    	}
}
