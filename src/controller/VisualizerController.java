package controller;

import model.SortStats;
import util.SortingListener;
import view.window.VisualizerWindow;
import generator.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class that controls the single algorithm visualizer.
 * Handles data generation, starting/pausing/resetting the sort,
 * and keeps the view in sync with what's happening during the sort.
 */
public class VisualizerController {

    private final VisualizerWindow view;
    private final String algorithmName;
    private final String genType;
    private final double entropy;

    private List<Integer> currentData;
    private int arraySize;

    private boolean running = false;
    private boolean paused = false;

    private SortRunner sortRunner;
    private VisualizationListener visListener;
    private SortStats stats;

	/**
     * Creates a new VisualizerController object.
     *
     * @param view          the visualizer window this controller manages
     * @param algorithmName the name of the algorithm to visualize
     * @param genType       type of input generator 
     * @param entropy       entropy level for the input
     * @param initialSize   initial size of the array
     */
    public VisualizerController(VisualizerWindow view, String algorithmName,
                                 String genType, double entropy, int initialSize) {
        this.view = view;
        this.algorithmName = algorithmName;
        this.genType = genType;
        this.entropy = entropy;
        this.stats = new SortStats(algorithmName, genLabel());
        this.arraySize = initialSize;
    }

	/**
     * Generates a new list and updates the view with the new data.
     */
    public void generateData() {
        NumberGenerator gen = buildGenerator();
        currentData = new ArrayList<>(gen.getList());
        view.setLiveData(currentData);
        view.updateBars(currentData, -1, -1);
    }

	/**
     * Starts the sort, or resumes it if it was paused.
     * Sets up the visualization listener and kicks off the sort runner.
     */
    public void start() {
        if (paused) {
            paused = false;
            visListener.resume();
            view.onResumed();
            return;
        }

        stats = new SortStats(algorithmName, genLabel());
        SortingListener.clearListeners();
        visListener = new VisualizationListener(view, view.getSpeedSlider());

        running = true;
        paused = false;
        view.onSortingStarted();
        view.startLiveStats(stats);

        sortRunner = new SortRunner(currentData, algorithmName, this, visListener, stats);
        sortRunner.execute();
    }

	/**
     * Pauses the sort if it's currently running.
     * Does nothing if already paused or not running.
     */
    public void pause() {
        if (running && !paused) {
            paused = true;
            visListener.pause();
            view.onPaused();
        }
    }

	/**
     * Cancels any running sort, resets all state, and generates new data.
     * Also resumes the listener before cancelling to avoid it getting stuck.
     */
    public void reset() {
        if (sortRunner != null) { 
        	sortRunner.cancel(true); 
        	sortRunner = null; 
        }
        if (visListener != null && paused) 
        	visListener.resume();
        running = false;
        paused = false;
        SortingListener.clearListeners();
        view.onReset();
        generateData();
    }

	/**
     * Called by SortRunner when the sort finishes.
     * Updates the running/paused state and notifies the view.
     */
    public void onSortingDone() {
        running = false;
        paused = false;
        view.onSortingDone(currentData, stats);
    }

	/**
     * Updates the array size and regenerates data if no sort is running.
     *
     * @param size the new array size
     */
    public void setArraySize(int size) {
        this.arraySize = size;
        if (!running) generateData();
    }

	/**
     * Highlights two bars in the view during the sort animation.
     *
     * @param i1 index of the first bar to highlight
     * @param i2 index of the second bar to highlight
     */
    public void highlightBars(int i1, int i2) {
        view.updateBars(currentData, i1, i2);
    }

	/** @return the stats collected for the current sort */
    public SortStats getStats() { 
    	return stats; 
    }

	/** @return true if a sort is currently running */
    public boolean isRunning() { 	
    	return running; 
    }

	/**
     * Builds the appropriate generator based on the genType field.
     *
     * @return a RandomGenerator or EntropyGenerator depending on the config
     */
    private NumberGenerator buildGenerator() {
        return "Random".equals(genType)
                ? new RandomGenerator(arraySize)
                : new EntropyGenerator(entropy, arraySize);
    }

	/**
     * Returns a label for the generator, used in stats.
     *
     * @return "Random" or "Entropy X.X" depending on the configuration
     */
    private String genLabel() {
        return "Random".equals(genType) ? "Random" : "Entropy " + entropy;
    }
}
