package benchmark;

/**
 * Represents a single benchmark configuration.
 * It defines which algorithm to run, the type of input data,
 * and the parameters controlling size, entropy, and repetitions.
 */

public class BenchmarkConfig {

	private final String algorithmName;
	private final String generatorType;
	private final int size;
	private final double entropy;
	private final int repetitions;

		/**
	 * Creates a benchmark configuration.
	 *
	 * @param algorithmName name of the sorting algorithm
	 * @param generatorType type of data generator (e.g. random, entropy)
	 * @param size size of the input list
	 * @param entropy entropy value used for generators (or -1 if not applicable)
	 * @param repetitions number of times this configuration should be executed
	 */

    	public BenchmarkConfig(String algorithmName, String generatorType, int size, double entropy, int repetitions) {
		this.algorithmName = algorithmName;
		this.generatorType = generatorType;
		this.size = size;
		this.entropy = entropy;
		this.repetitions = repetitions;
    	}

	/**
	 * @return the algorithm name
	 */
	public String getAlgorithmName() {
		return algorithmName;
	}

	/**
	 * @return the generator type
	 */
	public String getGeneratorType() {
		return generatorType;
	}

	/**
	 * @return the input size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @return the entropy value associated with this configuration
	 */
	public double getEntropy() {
		return entropy;
	}

	/**
	 * @return number of repetitions for this configuration
	 */
	public int getRepetitions() {
		return repetitions;
	}

	/**
	 * Returns a readable representation of the configuration.
	 *
	 * @return string describing this configuration
	 */
	@Override
	public String toString() {
		return "BenchmarkConfig{" +
		        "algorithmName='" + algorithmName + '\'' +
		        ", generatorType='" + generatorType + '\'' +
		        ", size=" + size +
		        ", entropy=" + entropy +
		        ", repetitions=" + repetitions +
		        '}';
	    }
}
