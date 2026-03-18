package controller;

import model.SortStats;
import util.*;
import view.window.CompareWindow;
import view.components.BarPanel;
import generator.*;
import java.util.ArrayList;
import java.util.List;

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

    public void generateData() {
        List<Integer> original = buildGenerator().getList();
        currentData1 = new ArrayList<>(original);
        currentData2 = new ArrayList<>(original);
        view.updateBars(currentData1, currentData2);
    }

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

        bp1.setLiveData(currentData1);
        bp2.setLiveData(currentData2);

        sortRunner1 = new CompareSortRunner(currentData1, algo1, bp1, visListener1, stats1, this);
        sortRunner2 = new CompareSortRunner(currentData2, algo2, bp2, visListener2, stats2, this);
        sortRunner1.execute();
        sortRunner2.execute();
    }

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

    public synchronized void onOneSortDone() {
        doneCount++;
        if (doneCount >= 2) {
            running = false;
            view.onBothSortsDone(currentData1, currentData2, stats1, stats2);
        }
    }

    public void setArraySize(int size) {
        this.arraySize = size;
        if (!running) 
        	generateData();
    }

    public SortStats getStats1() { 
    	return stats1; 
    }
    public SortStats getStats2() { 
    	return stats2; 
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
