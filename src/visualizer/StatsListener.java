package visualizer;

import sorting.Listener;

public class StatsListener implements Listener {

    private final SortStats stats;

    public StatsListener(SortStats stats) {
        this.stats = stats;
    }

    @Override
    public void onComparison(int i1, int i2, int v1, int v2) {
        stats.incrementComparisons();
    }

    @Override
    public void onSwap(int i1, int i2, int v1, int v2) {
        stats.incrementSwaps();
    }
}
