package controller;

import model.SortStats;
import util.SortingListener;
import view.window.VisualizerWindow;
import generator.*;
import java.util.ArrayList;
import java.util.List;

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

    public VisualizerController(VisualizerWindow view, String algorithmName,
                                 String genType, double entropy, int initialSize) {
        this.view = view;
        this.algorithmName = algorithmName;
        this.genType = genType;
        this.entropy = entropy;
        this.stats = new SortStats(algorithmName, genLabel());
        this.arraySize = initialSize;
    }

    public void generateData() {
        NumberGenerator gen = buildGenerator();
        currentData = new ArrayList<>(gen.getList());
        view.setLiveData(currentData);
        view.updateBars(currentData, -1, -1);
    }

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

        sortRunner = new SortRunner(currentData, algorithmName, this, visListener, stats);
        sortRunner.execute();
    }

    public void pause() {
        if (running && !paused) {
            paused = true;
            visListener.pause();
            view.onPaused();
        }
    }

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

    public void onSortingDone() {
        running = false;
        paused = false;
        view.onSortingDone(currentData, stats);
    }

    public void setArraySize(int size) {
        this.arraySize = size;
        if (!running) generateData();
    }

    public void highlightBars(int i1, int i2) {
        view.updateBars(currentData, i1, i2);
    }

    public SortStats getStats() { 
    	return stats; 
    }

    public boolean isRunning() { 	
    	return running; 
    }

    private NumberGenerator buildGenerator() {
        switch (genType) {
            case "Reverse Entropy": return new ReverseEntropyGenerator(entropy, arraySize);
            case "Entropy":         return new EntropyGenerator(entropy, arraySize);
            default:                return new RandomGenerator(arraySize);
        }
    }

    private String genLabel() {
        switch (genType) {
            case "Reverse Entropy": return "Reverse Entropy " + entropy;
            case "Entropy":         return "Entropy " + entropy;
            default:                return "Random";
        }
    }
}
