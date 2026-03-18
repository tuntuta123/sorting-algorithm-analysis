package sorting;

import model.SortStats;

public class StatsListener implements Listener {

    	private final SortStats stats;

    	public StatsListener(SortStats stats) {
        	this.stats = stats;
    	}

    	@Override
    	public void onComparison(int i1, int i2, int val1, int val2) {
        	stats.incrementComparisons();
    	}

    	@Override
    	public void onSwap(int i, int j, int val1, int val2) {
        	stats.incrementSwaps();
    	}
    	
    	@Override
    	public void onAccess(int index, int value) {
        	stats.incrementAccesses();
    	}
}
