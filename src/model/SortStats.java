package model;
import java.util.*;

public class SortStats {

    private final String algorithmName;
    private final String generatorLabel;

	private List<Long> comparisonTimes = new ArrayList<>();
	private List<Long> comparisonValues = new ArrayList<>();
	private List<Long> swapTimes = new ArrayList<>();
	private List<Long> swapValues = new ArrayList<>();

    private volatile long comparisons = 0;
    private volatile long swaps = 0;
    private volatile long startTime = -1;
    private volatile long endTime = -1;

    public SortStats(String algorithmName, String generatorLabel) {
        this.algorithmName = algorithmName;
        this.generatorLabel = generatorLabel;
    }

    public void start() {
	    comparisons = 0;
	    swaps = 0;

	    comparisonTimes.clear();
	    comparisonValues.clear();
	    swapTimes.clear();
	    swapValues.clear();

	    startTime = System.currentTimeMillis();
	    endTime = -1;
	}

    public void incrementComparisons() { 
    	comparisons++; 
		long t = System.currentTimeMillis() - startTime;

		comparisonTimes.add(t);
		comparisonValues.add(comparisons);
    }
    public void incrementSwaps(){ 
    	swaps++; 
		long t = System.currentTimeMillis() - startTime;

		swapTimes.add(t);
		swapValues.add(swaps);
    }

	public List<Long> getComparisonTimes() {
		return comparisonTimes;
	}

	public List<Long> getComparisonValues() {
		return comparisonValues;
	}

	public List<Long> getSwapTimes() {
		return swapTimes;
	}

	public List<Long> getSwapValues() {
		return swapValues;
	}

    public void stop() {
        endTime = System.currentTimeMillis();
    }

    public long getComparisons(){ 
    	return comparisons; 
    }
    public long getSwaps(){ 
    	return swaps; 
    }
    public String getAlgorithmName(){ 
    	return algorithmName; 
    }
    public String getGeneratorLabel(){ 
    	return generatorLabel; 
    }

    public long getElapsedMs() {
        if (startTime < 0) 
        	return 0;
        long end = endTime >= 0 ? endTime : System.currentTimeMillis();
        return end - startTime;
    }

    public boolean isFinished() { 
    	return endTime >= 0; 
    }
}
