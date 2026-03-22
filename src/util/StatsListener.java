package util;

import model.SortStats;

/**
 * A listener that tracks sorting statistics like comparisons, swaps, and accesses.
 * Every time the sorting algorithm does something, this class updates the stats.
 */
public class StatsListener implements Listener {

    private final SortStats stats;

    /**
     * Creates a StatsListener that will record events into the given stats object.
     *
     * @param stats the SortStats object where all the counts will be stored
     */
    public StatsListener(SortStats stats) {
        this.stats = stats;
    }

    /**
     * Called when two elements are compared during sorting.
     * Increments the comparison counter in stats.
     *
     * @param i1 index of the first element
     * @param i2 index of the second element
     * @param v1 value of the first element
     * @param v2 value of the second element
     */
    @Override
    public void onComparison(int i1, int i2, int v1, int v2) {
        stats.incrementComparisons();
    }

    /**
     * Called when two elements are swapped during sorting.
     * Increments the swap counter in stats.
     *
     * @param i1 index of the first element
     * @param i2 index of the second element
     * @param v1 value of the first element
     * @param v2 value of the second element
     */
    @Override
    public void onSwap(int i1, int i2, int v1, int v2) {
        stats.incrementSwaps();
    }

    /**
     * Called when an element is accessed (get or set) during sorting.
     * Increments the access counter in stats.
     *
     * @param index the position of the accessed element
     * @param value the value of the accessed element
     */
    @Override
    public void onAccess(int index, int value) {
        stats.incrementAccesses();
    }
}
