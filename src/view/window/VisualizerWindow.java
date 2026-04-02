package view.window;

import controller.VisualizerController;
import model.SortStats;
import view.components.BarPanel;
import view.components.LiveStatsPanel;
import view.components.StatsWindow;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class shows the main visualizer window for a single sorting algorithm.
 * Shows a bar chart of the data being sorted, live stats while sorting runs,
 * and a control panel to start, pause, reset, and view performance results.
 */
public class VisualizerWindow extends AbstractVisualizer {

    private BarPanel barPanel;
    private LiveStatsPanel liveStatsPanel;
    private VisualizerController controller;
    private final String algorithmName;

    /**
     * Creates and displays the visualizer window for the given algorithm and data settings.
     * Automatically generates the initial data and maximizes the window.
     *
     * @param algorithmName The name of the sorting algorithm to visualize.
     * @param genType The type of data generator.
     * @param entropy The entropy value used when genType is not "Random".
     * @param initialSize The initial number of elements to sort.
     */
    public VisualizerWindow(String algorithmName, String genType, double entropy, int initialSize) {
        super(algorithmName + " - " + ("Random".equals(genType) ? "Random" : "Entropy " + entropy),
              1500, 800, initialSize);
        this.algorithmName = algorithmName;
        buildUI();
        this.controller = new VisualizerController(this, algorithmName, genType, entropy, initialSize);
        controller.generateData();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    /**
     * Builds and arranges the UI components, like title label, bar panel, live stats, and control panel.
     */
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

        liveStatsPanel = new LiveStatsPanel();

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(liveStatsPanel, BorderLayout.NORTH);
        southPanel.add(buildControlPanel(), BorderLayout.SOUTH);
        add(southPanel, BorderLayout.SOUTH);
    }

    /**
     * Starts the live stats display using the given stats object.
     *
     * @param stats The SortStats object to read live values from.
     */
    public void startLiveStats(SortStats stats) {
        liveStatsPanel.start(stats);
    }


    /**
     * Resets the live stats panel and tells the controller to reset the sorting state.
     */
    @Override
    public void reset() {
        liveStatsPanel.reset();
        controller.reset();
    }

    /**
     * Tells the controller to generate a new set of data.
     */
    @Override
    public void generateData(){ 
    	controller.generateData(); 
    }


    /**
     * Tells the controller to start the sorting algorithm.
     */
    @Override
    public void startSorting(){ 
    	controller.start(); 
    	liveStatsPanel.resume();
    }

    /**
     * Tells the controller to pause or resume sorting.
     */
    @Override
    public void togglePause(){ 
    	controller.pause(); 
    	liveStatsPanel.pause();
    }
    
    /**
     * Opens the stats window showing the results of the last sorting run.
     */
    @Override
    protected void openStatsWindow(){ 
    	new StatsWindow(controller.getStats()); 
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
     * Sets the live data reference on the bar panel.
     *
     * @param data The list of values to display in real time.
     */
    public void setLiveData(List<Integer> data) {
        barPanel.setLiveData(data);
    }

    /**
     * Updates the bar panel with new data and highlights two bars being compared or swapped.
     *
     * @param data The current list of values to display.
     * @param i1 Index of the first bar to highlight.
     * @param i2 Index of the second bar to highlight.
     */
    public void updateBars(List<Integer> data, int i1, int i2) {
        barPanel.setLiveData(data);
        barPanel.highlight(i1, i2);
    }

    /**
     * Highlights two bars on the bar panel without changing the data.
     *
     * @param i1 Index of the first bar to highlight.
     * @param i2 Index of the second bar to highlight.
     */
    public void highlightBars(int i1, int i2) {
        barPanel.highlight(i1, i2);
    }

    /**
     * Called when sorting finishes. Stops the live stats, enables the stats button,
     * and does a final update of the bar panel with no highlights.
     *
     * @param data The fully sorted list of values.
     * @param stats The final SortStats object with the complete results.
     */
    public void onSortingDone(List<Integer> data, SortStats stats) {
        liveStatsPanel.stop();
        doneButtons();
        barPanel.update(data, -1, -1);
    }

    /**
     * Updates the control panel buttons to their post-sorting state.
     * Disables start and pause, and enables reset and stats.
     */
    private void doneButtons() {
        startBtn.setEnabled(false);
        pauseBtn.setEnabled(false);
        resetBtn.setEnabled(true);
        statsBtn.setEnabled(true);
    }
    
    public BarPanel getBarPanel(){
    	return this.barPanel;
    }
}
