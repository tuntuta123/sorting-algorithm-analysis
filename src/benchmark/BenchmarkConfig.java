package benchmark;

public class BenchmarkConfig {

	private final String algorithmName;
	private final String generatorType;
	private final int size;
	private final double entropy;
	private final int repetitions;

    	public BenchmarkConfig(String algorithmName, String generatorType, int size, double entropy, int repetitions) {
		this.algorithmName = algorithmName;
		this.generatorType = generatorType;
		this.size = size;
		this.entropy = entropy;
		this.repetitions = repetitions;
    	}

	public String getAlgorithmName() {
		return algorithmName;
	}

	public String getGeneratorType() {
		return generatorType;
	}

	public int getSize() {
		return size;
	}

	public double getEntropy() {
		return entropy;
	}

	public int getRepetitions() {
		return repetitions;
	}

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
