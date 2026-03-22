package controller;

import model.SortStats;
import view.components.BarPanel;
import java.util.List;

/**
 * Sort runner used for the side-by-side comparison view.
 * Extends AbstractSortRunner to get all the sorting/listener setup,
 * and adds the logic to update the bar panel and notify the controller when done.
 */
public class CompareSortRunner extends AbstractSortRunner {

	/** The bar panel this runner is responsible for updating. */
    private final BarPanel barPanel;
    /** The controller to notify when this sort finishes. */
    private final CompareController controller;

	/**
     * Creates a new CompareSortRunner object.
     *
     * @param data        the list to sort
     * @param algorithm   the name of the algorithm to use
     * @param barPanel    the bar panel to update when the sort finishes
     * @param visListener listener that handles the live visualization
     * @param stats       the stats object to record into
     * @param controller  the controller to notify when this sort is done
     */
    public CompareSortRunner(List<Integer> data, String algorithm,
                             BarPanel barPanel, VisualizationListener visListener,
                             SortStats stats, CompareController controller) {
        super(data, algorithm, visListener, stats);
        this.barPanel = barPanel;
        this.controller = controller;
    }

	/**
     * Called automatically by SwingWorker when the sort finishes.
     * Does a final update on the bar panel to show the fully sorted state,
     * then tells the controller this sort is done. It does nothing if cancelled.
     */
    @Override
    protected void done() {
        if (!isCancelled()) {
            barPanel.update(data, -1, -1);
            controller.onOneSortDone();
        }
    }
}
