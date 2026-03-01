package controller;

import sorting.Listener;
import model.SortStats;

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
