package model;

public class SortStats {

    private final String algorithmName;
    private final String generatorLabel;

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
        startTime = System.currentTimeMillis();
        endTime = -1;
    }

    public void incrementComparisons() { 
    	comparisons++; 
    }
    public void incrementSwaps(){ 
    	swaps++; 
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
