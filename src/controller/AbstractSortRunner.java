package controller;

import model.SortStats;
import sorting.SortingAlgorithmFactory;
import util.*;
import javax.swing.SwingWorker;
import java.util.List;

public abstract class AbstractSortRunner extends SwingWorker<Void, Void> {

    protected final List<Integer> data;
    protected final String algorithm;
    protected final VisualizationListener visListener;
    protected final StatsListener statsListener;
    protected final SortStats stats;

    public AbstractSortRunner(List<Integer> data, String algorithm,
                              VisualizationListener visListener,
                              SortStats stats) {
        this.data = data;
        this.algorithm = algorithm;
        this.visListener = visListener;
        this.stats = stats;
        this.statsListener = new StatsListener(stats);
    }

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
