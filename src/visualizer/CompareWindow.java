package visualizer;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import sorting.*;

public class CompareWindow extends AbstractVisualizer {

    private BarPanel barPanel1;
    private BarPanel barPanel2;

    private final String algo1;
    private final String algo2;

    private List<Integer> currentData1;
    private List<Integer> currentData2;

    private SwingWorker<Void, Void> sortRunner1;
    private SwingWorker<Void, Void> sortRunner2;
    private VisualizationListener visListener1;
    private VisualizationListener visListener2;
    private SortStats stats1;
    private SortStats stats2;
    private int doneCount = 0;

    public CompareWindow(String algo1, String algo2, String genType, double entropy) {
        super(algo1 + " vs " + algo2 + " — " + ("Random".equals(genType) ? "Random" : "Entropy " + entropy),
              genType, entropy, 1400, 650);
        this.algo1 = algo1;
        this.algo2 = algo2;
        stats1 = new SortStats(algo1, genLabel());
        stats2 = new SortStats(algo2, genLabel());
        buildUI();
        generateData();
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout(6, 6));

        JPanel titlePanel = new JPanel(new GridLayout(1, 2));
        JLabel label1 = new JLabel(algo1, SwingConstants.CENTER);
        JLabel label2 = new JLabel(algo2, SwingConstants.CENTER);
        label1.setFont(new Font("SansSerif", Font.BOLD, 15));
        label2.setFont(new Font("SansSerif", Font.BOLD, 15));
        label1.setBorder(BorderFactory.createEmptyBorder(8, 0, 4, 0));
        label2.setBorder(BorderFactory.createEmptyBorder(8, 0, 4, 0));
        titlePanel.add(label1);
        titlePanel.add(label2);
        add(titlePanel, BorderLayout.NORTH);

        JPanel barsPanel = new JPanel(new GridLayout(1, 2, 6, 0));
        barPanel1 = new BarPanel();
        barPanel2 = new BarPanel();
        barPanel1.setBackground(Color.WHITE);
        barPanel2.setBackground(Color.WHITE);
        barPanel1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        barPanel2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        barsPanel.add(barPanel1);
        barsPanel.add(barPanel2);
        add(barsPanel, BorderLayout.CENTER);

        add(buildControlPanel(), BorderLayout.SOUTH);
    }

    @Override
    public void generateData() {
        List<Integer> original = buildGenerator().getList();
        currentData1 = new ArrayList<>(original);
        currentData2 = new ArrayList<>(original);
        barPanel1.update(currentData1, -1, -1);
        barPanel2.update(currentData2, -1, -1);
    }

    @Override
    public void startSorting() {
        if (paused) {
            paused = false;
            if (visListener1 != null) visListener1.resume();
            if (visListener2 != null) visListener2.resume();
            startBtn.setText("Start");
            startBtn.setEnabled(false);
            pauseBtn.setEnabled(true);
            return;
        }

        stats1 = new SortStats(algo1, genLabel());
        stats2 = new SortStats(algo2, genLabel());

        visListener1 = new VisualizationListener(barPanel1, speedSlider);
        visListener2 = new VisualizationListener(barPanel2, speedSlider);

        running = true;
        paused = false;
        doneCount = 0;
        startBtn.setEnabled(false);
        pauseBtn.setEnabled(true);
        resetBtn.setEnabled(false);

        barPanel1.setLiveData(currentData1);
        barPanel2.setLiveData(currentData2);

        sortRunner1 = new CompareSortRunner(currentData1, algo1, barPanel1, visListener1, stats1, this);
        sortRunner2 = new CompareSortRunner(currentData2, algo2, barPanel2, visListener2, stats2, this);
        sortRunner1.execute();
        sortRunner2.execute();
    }

    @Override
    public void togglePause() {
        if (running && !paused) {
            paused = true;
            if (visListener1 != null) visListener1.pause();
            if (visListener2 != null) visListener2.pause();
            startBtn.setText("Resume");
            startBtn.setEnabled(true);
            pauseBtn.setEnabled(false);
        }
    }

    @Override
    public void reset() {
        if (sortRunner1 != null) { sortRunner1.cancel(true); sortRunner1 = null; }
        if (sortRunner2 != null) { sortRunner2.cancel(true); sortRunner2 = null; }
        if (paused) {
            if (visListener1 != null) visListener1.resume();
            if (visListener2 != null) visListener2.resume();
        }
        running = false;
        paused = false;
        doneCount = 0;
        SortingListener.clearListeners();
        resetButtons();
        generateData();
    }

    @Override
    protected void openStatsWindow() {
        new StatsWindow(stats1, stats2);
    }

    public synchronized void onOneSortDone() {
        doneCount++;
        if (doneCount >= 2) {
            running = false;
            SwingUtilities.invokeLater(() -> {
                doneButtons();
                barPanel1.update(currentData1, -1, -1);
                barPanel2.update(currentData2, -1, -1);
            });
        }
    }
}
