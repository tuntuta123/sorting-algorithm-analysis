package controller;

import model.SortStats;
import java.util.List;

/**
 * Sort runner used for the single algorithm visualizer.
 * It has the same idea as CompareSortRunner but it just notifies the
 * VisualizerController when the sort is done instead of managing two panels.
 */
public class SortRunner extends AbstractSortRunner {

	/** The controller to notify when the sorting is done */
    private final VisualizerController controller;

	/**
     * Creates a new SortRunner object.
     *
     * @param data        the list to sort
     * @param algorithm   the name of the algorithm to use
     * @param controller  the controller to notify when done
     * @param visListener listener that handles the live visualization
     * @param stats       the stats object to record into
     */
    public SortRunner(List<Integer> data, String algorithm,
                      VisualizerController controller,
                      VisualizationListener visListener,
                      SortStats stats) {
        super(data, algorithm, visListener, stats);
        this.controller = controller;
    }

	/**
     * Called automatically by SwingWorker when the sort finishes.
     * Just tells the controller it's done. It does nothing if cancelled.
     */
    @Override
    protected void done() {
        if (!isCancelled()) {
            controller.onSortingDone();
        }
    }
}
