package visualizer;

import java.util.List;

public class SortRunner extends AbstractSortRunner {

    private final VisualizerWindow window;

    public SortRunner(List<Integer> data, String algorithm,
                      VisualizerWindow window, VisualizationListener visListener,
                      SortStats stats) {
        super(data, algorithm, visListener, stats);
        this.window = window;
        window.setCurrentData(data);
    }

    @Override
    protected void done() {
        if (!isCancelled()) {
            window.onSortingDone();
        }
    }
}
