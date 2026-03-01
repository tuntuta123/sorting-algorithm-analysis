package controller;

import model.SortStats;
import java.util.List;

public class SortRunner extends AbstractSortRunner {

    private final VisualizerController controller;

    public SortRunner(List<Integer> data, String algorithm,
                      VisualizerController controller,
                      VisualizationListener visListener,
                      SortStats stats) {
        super(data, algorithm, visListener, stats);
        this.controller = controller;
    }

    @Override
    protected void done() {
        if (!isCancelled()) {
            controller.onSortingDone();
        }
    }
}
