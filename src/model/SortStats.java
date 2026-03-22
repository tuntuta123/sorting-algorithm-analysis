package model;
import java.util.*;

/**
 * Keeps track of stats for a sorting algorithm while it runs.
 * Stores things like how many comparisons and swaps happened,
 * and also records them over time so we can plot them later.
 * Was updated to also hold benchmark metadata like list size,
 * entropy, and run number for more detailed analysis.
 */
public class SortStats {

    private final String algorithmName;
    private final String generatorLabel;

	private List<Long> comparisonTimes = new ArrayList<>();
	private List<Long> comparisonValues = new ArrayList<>();
	private List<Long> swapTimes = new ArrayList<>();
	private List<Long> swapValues = new ArrayList<>();

    private volatile long comparisons = 0;
    private volatile long swaps = 0;
    private volatile long accesses = 0;

    private volatile long startTime = -1;
    private volatile long endTime = -1;
    	
    // benchmark data
    private final String generatorType;
    private final int size;
    private final double entropy;
    private final int run;

	/**
	 * Creates a SortStats with just an algorithm name and input label.
	 * The benchmark fields are set to default placeholder
	 * values since we don't have that info here. Used by the older parts of the code.
	 *
	 * @param algorithmName  the name of the sorting algorithm
	 * @param generatorLabel describes the kind of input used 
	 */
    public SortStats(String algorithmName, String generatorLabel) {
        this.algorithmName = algorithmName;
        this.generatorLabel = generatorLabel;
        	
        // default values so the old constructor still works
        this.generatorType = "unknown";
        this.size = -1;
        this.entropy = -1.0;
        this.run = -1;
    }
    	
    /**
	 * Creates a SortStats with full benchmark metadata attached.
	 * Use this one when running proper benchmarks where you want to
	 * track results across different sizes, entropies, and multiple runs.
	 *
	 * @param algorithmName  the name of the sorting algorithm
	 * @param generatorLabel describes the kind of input used
	 * @param generatorType  the type of generator used (e.g. "SwapSort")
	 * @param size           the size of the list that was sorted
	 * @param entropy        the entropy level of the input (0.0 = sorted, 1.0 = fully random)
	 * @param run            which run number this is in the benchmark
	 */
    public SortStats(String algorithmName, String generatorLabel, String generatorType, int size, double entropy, int run) {
        this.algorithmName = algorithmName;
        this.generatorLabel = generatorLabel;
        this.generatorType = generatorType;
        this.size = size;
        this.entropy = entropy;
        this.run = run;
    }
    	
    /**
     * Resets everything and starts the timer. Should be called right before
     * the sort begins.
     */
    public void start() {
	    comparisons = 0;
	    swaps = 0;
	    accesses = 0;
	    comparisonTimes.clear();
	    comparisonValues.clear();
	    swapTimes.clear();
	    swapValues.clear();
	    startTime = System.currentTimeMillis();
	    endTime = -1;
	}

    /**
     * Call this every time the algorithm does a comparison.
     * It increments the counter and saves the current time and total
     * so we can graph how comparisons grew over time.
     */
    public void incrementComparisons() { 
    	comparisons++; 
		long t = System.currentTimeMillis() - startTime;
		comparisonTimes.add(t);
		comparisonValues.add(comparisons);
    }
    
    /**
     * Call this every time the algorithm does a swap.
     * Same idea as incrementComparisons, it saves the timestamp too.
     */
    public void incrementSwaps(){ 
    	swaps++; 
		long t = System.currentTimeMillis() - startTime;
		swapTimes.add(t);
		swapValues.add(swaps);
    }
    
    /**
     * Call this every time the algorithm reads or writes to the array.
     * It just increments the counter.
     */
    public void incrementAccesses(){ 
    	accesses++; 
    }

    /**
     * Returns the list of timestamps (in ms) at which each comparison happened.
     * Used together with getComparisonValues() to draw a graph.
     *
     * @return list of timestamps for each comparison
     */
	public List<Long> getComparisonTimes() {
		return comparisonTimes;
	}

    /**
     * Returns the running total of comparisons at each recorded point in time.
     *
     * @return list of cumulative comparison counts
     */
	public List<Long> getComparisonValues() {
		return comparisonValues;
	}

    /**
     * Returns the list of timestamps (in ms) at which each swap happened.
     *
     * @return list of timestamps for each swap
     */
	public List<Long> getSwapTimes() {
		return swapTimes;
	}

    /**
     * Returns the running total of swaps at each recorded point in time.
     *
     * @return list of cumulative swap counts
     */
	public List<Long> getSwapValues() {
		return swapValues;
	}

    /**
     * Stops the timer. It should be called right after the sort finishes.
     */
    public void stop() {
        endTime = System.currentTimeMillis();
    }

    /**
     * @return total number of comparisons made during the sort
     */
    public long getComparisons(){ 
    	return comparisons; 
    }
    
    /**
     * @return total number of swaps made during the sort
     */
    public long getSwaps(){ 
    	return swaps; 
    }
    
    /**
     * @return total number of array accesses during the sort
     */
    public long getAccesses(){ 
    	return accesses; 
    }
    
    /**
     * @return the name of the sorting algorithm
     */
    public String getAlgorithmName(){ 
    	return algorithmName; 
    }
    
    /**
     * @return the label describing what kind of input was used
     */
    public String getGeneratorLabel(){ 
    	return generatorLabel; 
    }

    /**
     * Returns how long the sort took in milliseconds.
     * If the sort is still running, it returns the time elapsed so far.
     * Returns 0 if start() was never called.
     *
     * @return elapsed time in ms
     */
    public long getElapsedMs() {
        if (startTime < 0) 
        	return 0;
        long end = endTime >= 0 ? endTime : System.currentTimeMillis();
        return end - startTime;
    }

    /**
     * Checks whether the sort has finished.
     *
     * @return true if the sort is done, false if it's still running
     */
    public boolean isFinished() { 
    	return endTime >= 0; 
    }
    	
    /** @return the type of generator that produced the input list */
    public String getGeneratorType() {
    	return generatorType;
    }

	/** @return the size of the list that was sorted, or -1 if not set */
    public int getSize() {
        return size;
    }
    	
    	/** @return the entropy of the input (0.0 = fully sorted, 1.0 = fully random), or -1 if not set */
    public double getEntropy() {
        return entropy;
    }

	/** @return which run number this was in the benchmark, or -1 if not set */
    public int getRun() {
        return run;
    }
}
