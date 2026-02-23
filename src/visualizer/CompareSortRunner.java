package visualizer;

import java.util.List;

public class CompareSortRunner extends AbstractSortRunner {

    private final BarPanel barPanel;
    private final CompareWindow window;

    public CompareSortRunner(List<Integer> data, String algorithm,
                             BarPanel barPanel, VisualizationListener visListener,
                             SortStats stats, CompareWindow window) {
        super(data, algorithm, visListener, stats);
        this.barPanel = barPanel;
        this.window = window;
    }

    @Override
    protected void done() {
        if (!isCancelled()) {
            barPanel.update(data, -1, -1);
            window.onOneSortDone();
        }
    }
}
