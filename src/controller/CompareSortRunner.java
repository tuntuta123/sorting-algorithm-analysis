package controller;

import model.SortStats;
import view.components.BarPanel;
import java.util.List;

public class CompareSortRunner extends AbstractSortRunner {

    private final BarPanel barPanel;
    private final CompareController controller;

    public CompareSortRunner(List<Integer> data, String algorithm,
                             BarPanel barPanel, VisualizationListener visListener,
                             SortStats stats, CompareController controller) {
        super(data, algorithm, visListener, stats);
        this.barPanel = barPanel;
        this.controller = controller;
    }

    @Override
    protected void done() {
        if (!isCancelled()) {
            barPanel.update(data, -1, -1);
            controller.onOneSortDone();
        }
    }
}
