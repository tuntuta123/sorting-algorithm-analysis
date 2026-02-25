package visualizer;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import sorting.*;

public class VisualizerWindow extends AbstractVisualizer {

    private BarPanel barPanel;
    private final String algorithmName;
    private List<Integer> currentData;

    private SortRunner sortRunner;
    private VisualizationListener visListener;
    private SortStats stats;

    public VisualizerWindow(String algorithmName, String genType, double entropy) {
        super(algorithmName + " — " + ("Random".equals(genType) ? "Random" : "Entropy " + entropy),
              genType, entropy, 1000, 650);
        this.algorithmName = algorithmName;
        stats = new SortStats(algorithmName, genLabel());
        buildUI();
        generateData();
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout(6, 6));

        JLabel titleLabel = new JLabel("Visualizer of " + algorithmName, SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 4, 0));
        add(titleLabel, BorderLayout.NORTH);

        barPanel = new BarPanel();
        barPanel.setBackground(Color.WHITE);
        barPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(barPanel, BorderLayout.CENTER);

        add(buildControlPanel(), BorderLayout.SOUTH);
    }

    @Override
    public void generateData() {
        currentData = new ArrayList<>(buildGenerator().getList());
        barPanel.update(currentData, -1, -1);
    }

    @Override
    public void startSorting() {
        if (paused) {
            paused = false;
            visListener.resume();
            startBtn.setText("Start");
            startBtn.setEnabled(false);
            pauseBtn.setEnabled(true);
            return;
        }

        stats = new SortStats(algorithmName, genLabel());
        SortingListener.clearListeners();
        visListener = new VisualizationListener(this, speedSlider);

        running = true;
        paused = false;
        startBtn.setEnabled(false);
        pauseBtn.setEnabled(true);
        resetBtn.setEnabled(false);

        sortRunner = new SortRunner(currentData, algorithmName, this, visListener, stats);
        sortRunner.execute();
    }

    @Override
    public void togglePause() {
        if (running && !paused) {
            paused = true;
            visListener.pause();
            startBtn.setText("Resume");
            startBtn.setEnabled(true);
            pauseBtn.setEnabled(false);
        }
    }

    @Override
    public void reset() {
        if (sortRunner != null) { sortRunner.cancel(true); sortRunner = null; }
        if (visListener != null && paused) visListener.resume();
        running = false;
        paused = false;
        SortingListener.clearListeners();
        resetButtons();
        generateData();
    }

    @Override
    protected void openStatsWindow() {
        new StatsWindow(stats);
    }

    public void onSortingDone() {
        running = false;
        paused = false;
        doneButtons();
        barPanel.update(currentData, -1, -1);
    }

    public void setCurrentData(List<Integer> data) {
        this.currentData = data;
        barPanel.update(data, -1, -1);
    }

    public void highlightBars(int i1, int i2) {
        barPanel.update(currentData, i1, i2);
    }
}
