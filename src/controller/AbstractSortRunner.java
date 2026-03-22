package controller;

import model.SortStats;
import sorting.SortingAlgorithmFactory;
import util.*;
import javax.swing.SwingWorker;
import java.util.List;

/**
 * Abstract class for running a sorting algorithm in the background.
 * Extends SwingWorker so the sort runs on a separate thread and
 * doesn't freeze the UI while it's working.
 * Both listeners (visualization and stats) are wired up here.
 */
public abstract class AbstractSortRunner extends SwingWorker<Void, Void> {

    protected final List<Integer> data;
    protected final String algorithm;
    /** Listener that updates the visualization as the sort runs. */
    protected final VisualizationListener visListener;
    /** Listener that records stats as the sort runs. */
    protected final StatsListener statsListener;
    protected final SortStats stats;

	/**
     * Sets up everything needed to run the sort.
     * Automatically creates a StatsListener from the given SortStats.
     *
     * @param data        the list to sort
     * @param algorithm   the name of the algorithm to use
     * @param visListener listener for the visualization
     * @param stats       the stats object to record into
     */
    public AbstractSortRunner(List<Integer> data, String algorithm,
                              VisualizationListener visListener,
                              SortStats stats) {
        this.data = data;
        this.algorithm = algorithm;
        this.visListener = visListener;
        this.stats = stats;
        this.statsListener = new StatsListener(stats);
    }

	/**
     * Runs the sort on the background thread.
     * Clears any old listeners, registers the visualization and stats listeners,
     * then starts the sort and records the time.
     */
    @Override
    protected Void doInBackground() throws Exception {
        SortingListener.clearListeners();
        SortingListener.addListener(visListener);
        SortingListener.addListener(statsListener);
        stats.start();
        SortingAlgorithmFactory.get(algorithm).sort(data);
        stats.stop();
        return null;
    }
}
