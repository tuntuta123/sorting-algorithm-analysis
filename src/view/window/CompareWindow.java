package view.window;

import controller.CompareController;
import model.SortStats;
import view.components.BarPanel;
import view.components.LiveStatsPanel;
import view.components.StatsWindow;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * A class that has a visualizer window that runs two sorting algorithms side by side for comparison.
 * Each algorithm gets its own bar panel and live stats panel displayed in parallel.
 */
public class CompareWindow extends AbstractVisualizer {

    private BarPanel barPanel1;
    private BarPanel barPanel2;
    private LiveStatsPanel liveStatsPanel1;
    private LiveStatsPanel liveStatsPanel2;
    private CompareController controller;

    private final String algo1;
    private final String algo2;

    /**
     * Creates and displays the comparison window for two sorting algorithms.
     * Automatically generates the initial data and maximizes the window.
     *
     * @param algo1 The name of the first sorting algorithm.
     * @param algo2 The name of the second sorting algorithm.
     * @param genType The type of data generator.
     * @param entropy The entropy value used when genType is not "Random".
     * @param initialSize The initial number of elements to sort.
     */
    public CompareWindow(String algo1, String algo2, String genType, double entropy, int initialSize) {
        super(algo1 + " vs " + algo2 + " - " + ("Random".equals(genType) ? "Random" : "Entropy " + entropy), 1500, 800,
              initialSize);
        this.algo1 = algo1;
        this.algo2 = algo2;
        buildUI();
        this.controller = new CompareController(this, algo1, algo2, genType, entropy, initialSize);
        controller.generateData();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    /**
     * Builds and arranges the UI with two title labels, two bar panels,
     * two live stats panels, and the shared control panel at the bottom.
     */
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

        liveStatsPanel1 = new LiveStatsPanel();
        liveStatsPanel2 = new LiveStatsPanel();
        JPanel liveStatsRow = new JPanel(new GridLayout(1, 2, 6, 0));
        liveStatsRow.add(liveStatsPanel1);
        liveStatsRow.add(liveStatsPanel2);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(liveStatsRow, BorderLayout.NORTH);
        southPanel.add(buildControlPanel(), BorderLayout.SOUTH);
        add(southPanel, BorderLayout.SOUTH);
    }

    /**
     * Starts the live stats display for the algorithms at the same time.
     *
     * @param stats1 The SortStats object for the first algorithm.
     * @param stats2 The SortStats object for the second algorithm.
     */
    public void startLiveStats(SortStats stats1, SortStats stats2) {
        liveStatsPanel1.start(stats1);
        liveStatsPanel2.start(stats2);
    }

    /**
     * Resets both live stats panels and tells the controller to reset the sorting state.
     */
    @Override
    public void reset() {
        liveStatsPanel1.reset();
        liveStatsPanel2.reset();
        controller.reset();
    }

    /**
     * Tells the controller to generate new data for the algorithms.
     */
    @Override
    public void generateData(){ 
    	controller.generateData(); 
    }

    /**
     * Tells the controller to start sorting the algorithms.
     */
    @Override
    public void startSorting() { 
    	controller.start(); 
    	liveStatsPanel1.resume();
    	liveStatsPanel2.resume();
    }

    /**
     * Tells the controller to pause or resume the algorithms.
     */
    @Override
    public void togglePause(){ 
    	controller.pause(); 
    	liveStatsPanel1.pause();
    	liveStatsPanel2.pause();
    }

    /**
     * Opens the stats window showing the results of both algorithms side by side.
     */
    @Override
    protected void openStatsWindow() {
        new StatsWindow(controller.getStats1(), controller.getStats2());
    }

    /**
     * Tells the controller to update the array size when the user changes it.
     *
     * @param newSize The new number of elements to sort.
     */
    @Override
    protected void onSizeChanged(int newSize) { 
    	controller.setArraySize(newSize);
    }

    /**
     * Updates both bar panels with new data.
     *
     * @param data1 The current list of values for the first algorithm.
     * @param data2 The current list of values for the second algorithm.
     */
    public void updateBars(List<Integer> data1, List<Integer> data2) {
        barPanel1.update(data1, -1, -1);
        barPanel2.update(data2, -1, -1);
    }

    /**
     * Called when both sorting algorithms have finished.
     * Stops both live stats panels, enables the stats button, and does a final bar update.
     *
     * @param data1 The fully sorted list for the first algorithm.
     * @param data2 The fully sorted list for the second algorithm.
     * @param stats1 The final SortStats for the first algorithm.
     * @param stats2 The final SortStats for the second algorithm.
     */
    public void onBothSortsDone(List<Integer> data1, List<Integer> data2,
                                 SortStats stats1, SortStats stats2) {
        SwingUtilities.invokeLater(() -> {
            liveStatsPanel1.stop();
            liveStatsPanel2.stop();
            startBtn.setEnabled(false);
            pauseBtn.setEnabled(false);
            resetBtn.setEnabled(true);
            statsBtn.setEnabled(true);
            barPanel1.update(data1, -1, -1);
            barPanel2.update(data2, -1, -1);
        });
    }

    /**
     * Returns the bar panel for the first algorithm.
     *
     * @return The BarPanel used to display the first algorithm's data.
     */
    public BarPanel getBarPanel1() { 
    	return barPanel1; 
    }

    /**
     * Returns the bar panel for the second algorithm.
     *
     * @return The BarPanel used to display the second algorithm's data.
     */
    public BarPanel getBarPanel2() { 
    	return barPanel2; 
    }
}
