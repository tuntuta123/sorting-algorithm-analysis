package benchmark;

import export.*;
import generator.*;
import model.*;
import sorting.*;
import util.*;

import java.util.*;

/**
 * Main class responsible for running sorting benchmarks.
 * It generates different configurations, executes sorting algorithms,
 * collects statistics, and exports results to a CSV file.
 */

public class BenchmarkRunner {

	/**
	 * Entry point of the benchmark execution.
	 * Runs all configurations and exports results to results.csv.
	 *
	 * @param args command line arguments (not used)
	 */
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
        					buildGeneratorLabel(config, run),
        					config.getGeneratorType(),
        					config.getSize(),
        					config.getEntropy(),
        					run
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
	/**
	 * Builds the list of benchmark configurations to execute.
	 * Combines algorithms, input sizes, entropy values, and generator types.
	 *
	 * @return list of benchmark configurations
	 */
	private static List<BenchmarkConfig> buildConfigs() {
        	List<BenchmarkConfig> configs = new ArrayList<>();

        	int[] sizes = {100, 500, 1000};
        	double[] entropies = {0.0, 0.25, 0.50, 0.75};
        	int repetitions = 4;

        	String[] algorithms = {
		        //"BubbleSort",
		        //"CocktailShakerSort",
		        //"CombSort",
		        //"InsertionSorting",
		        //"MergeSorting",
		        //"QuickSort",
		        //"PancakeSort",
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

		/**
	 * Generates input data according to the given generator type.
	 *
	 * @param generatorType type of generator (random, entropy, reverse_entropy)
	 * @param size size of the list
	 * @param entropy entropy value (if applicable)
	 * @return generated list of integers
	 * @throws IllegalArgumentException if generator type is unknown
	 */

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

		/**
	 * Builds a readable label describing the generator configuration.
	 *
	 * @param config benchmark configuration
	 * @param run current repetition index
	 * @return formatted label string
	 */
    	private static String buildGeneratorLabel(BenchmarkConfig config, int run) {
    		String type = config.getGeneratorType();
    		int size = config.getSize();

    		if (type.equals("random")) {
        		return "random | n=" + size + " | run=" + run;
    		}
    		else if (type.equals("entropy")) {
        		return String.format("entropy | h=%.2f | n=%d | run=%d",
                	config.getEntropy(), size, run);
    		}
    		else if (type.equals("reverse_entropy")) {
        		return String.format("reverse entropy | h=%.2f | n=%d | run=%d",
                	config.getEntropy(), size, run);
    		}
    		else {
        		return "unknown | n=" + size + " | run=" + run;
    		}
	}

		/**
	 * Executes the selected sorting algorithm on the given data.
	 *
	 * @param algorithmName name of the sorting algorithm
	 * @param data list to sort
	 * @throws IllegalArgumentException if algorithm is unknown
	 */

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
