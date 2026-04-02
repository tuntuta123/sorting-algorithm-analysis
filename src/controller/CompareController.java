package controller;

import model.SortStats;
import util.SortingListener;
import view.window.CompareWindow;
import view.components.BarPanel;
import generator.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Controls the side-by-side comparison of two sorting algorithms.
 * Manages data generation, starting/pausing/resetting the sort,
 * and notifies the view when both sorts are done.
 */
public class CompareController {

    private final CompareWindow view;
    
    private final String algo1;
    private final String algo2;
    
    private final String genType;
    private final double entropy;

    private List<Integer> currentData1;
    private List<Integer> currentData2;
    private int arraySize;

    private boolean running = false;
    private boolean paused = false;
    private int doneCount = 0;

    private CompareSortRunner sortRunner1;
    private CompareSortRunner sortRunner2;
    private VisualizationListener visListener1;
    private VisualizationListener visListener2;
    private SortStats stats1;
    private SortStats stats2;

	/**
     * Creates a new CompareController object.
     *
     * @param view        the comparison window this controller manages
     * @param algo1       name of the first algorithm
     * @param algo2       name of the second algorithm
     * @param genType     type of input generator
     * @param entropy     entropy level for the input
     * @param initialSize initial size of the arrays
     */
    public CompareController(CompareWindow view, String algo1, String algo2,
                              String genType, double entropy, int initialSize) {
        this.view = view;
        this.algo1 = algo1;
        this.algo2 = algo2;
        this.genType = genType;
        this.entropy = entropy;
        this.stats1 = new SortStats(algo1, genLabel());
        this.stats2 = new SortStats(algo2, genLabel());
        this.arraySize = initialSize;
    }

	/**
     * Generates a fresh pair of identical lists and updates the view.
     * Both algorithms get the same data so the comparison is fair.
     */
    public void generateData() {
        List<Integer> original = buildGenerator().getList();
        currentData1 = new ArrayList<>(original);
        currentData2 = new ArrayList<>(original);
        view.updateBars(currentData1, currentData2);
    }

	/**
     * Starts the sort, or resumes it if it was paused.
     * Wires up both visualization listeners and kicks off both sort runners in parallel.
     */
    public void start() {
        if (paused) {
            paused = false;
            if (visListener1 != null) 
            	visListener1.resume();
            if (visListener2 != null) 
            	visListener2.resume();
            view.onResumed();
            return;
        }

        stats1 = new SortStats(algo1, genLabel());
        stats2 = new SortStats(algo2, genLabel());

        BarPanel bp1 = view.getBarPanel1();
        BarPanel bp2 = view.getBarPanel2();

        visListener1 = new VisualizationListener(bp1, view.getSpeedSlider());
        visListener2 = new VisualizationListener(bp2, view.getSpeedSlider());

        running = true;
        paused = false;
        doneCount = 0;
        view.onSortingStarted();
        view.startLiveStats(stats1, stats2);

        bp1.setLiveData(currentData1);
        bp2.setLiveData(currentData2);

        sortRunner1 = new CompareSortRunner(currentData1, algo1, bp1, visListener1, stats1, this);
        sortRunner2 = new CompareSortRunner(currentData2, algo2, bp2, visListener2, stats2, this);
        sortRunner1.execute();
        sortRunner2.execute();
    }

	/**
     * Pauses both sorts if they are currently running.
     * Does nothing if already paused or not running.
     */
    public void pause() {
        if (running && !paused) {
            paused = true;
            if (visListener1 != null) 
            	visListener1.pause();
            if (visListener2 != null) 
            	visListener2.pause();
            view.onPaused();
            
        }
    }

	/**
     * Cancels any running sorts, resets all state, and generates fresh data.
     * Also resumes listeners before cancelling to avoid them getting stuck.
     */
    public void reset() {
        if (sortRunner1 != null) { 
        	sortRunner1.cancel(true); 
        	sortRunner1 = null; 
        }
        if (sortRunner2 != null) { 
        	sortRunner2.cancel(true); 
        	sortRunner2 = null; 
        }
        if (paused) {
            if (visListener1 != null) 
            	visListener1.resume();
            if (visListener2 != null) 
            	visListener2.resume();
        }
        running = false;
        paused = false;
        doneCount = 0;
        SortingListener.clearListeners();
        view.onReset();
        generateData();
    }

	/**
     * Called by each sort runner when it finishes.
     * Once both have finished, it notifies the view to show the final results.
     * Synchronized because both runners call this from separate threads.
     */
    public synchronized void onOneSortDone() {
        doneCount++;
        if (doneCount >= 2) {
            running = false;
            view.onBothSortsDone(currentData1, currentData2, stats1, stats2);
        }
    }

	/**
     * Updates the array size and regenerates data if no sort is running.
     *
     * @param size the new array size
     */
    public void setArraySize(int size) {
        this.arraySize = size;
        if (!running) 
        	generateData();
    }

	/** @return the stats collected for the first algorithm */
    public SortStats getStats1() { 
    	return stats1; 
    }
    
    /** @return the stats collected for the second algorithm */
    public SortStats getStats2() { 
    	return stats2; 
    }

	/**
     * Builds the appropriate generator based on the genType field.
     *
     * @return a RandomGenerator, EntropyGenerator or ReverseEntropyGenerator depending on the config
     */
    private NumberGenerator buildGenerator() {
    	if("Random".equals(genType)){
    		return new RandomGenerator(arraySize);
    	}
        else if ("Entropy".equals(genType)){
                return new EntropyGenerator(entropy, arraySize);
        } else {
        	return new ReverseEntropyGenerator(entropy, arraySize);
        }
    }

	/**
     * Returns a label for the generator, used in stats.
     *
     * @return "Random", "Entropy X.X" or "Reverse Entropy X.X" depending on the configuration
     */
    private String genLabel() {
        if("Random".equals(genType)){
    		return "Random";
    	}
        else if ("Entropy".equals(genType)){
                return "Entropy " + entropy;
        } else {
        	return "Reverse Entropy " + entropy;
        }
    }
}
