package controller;

import model.SortStats;
import sorting.*;
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

        switch (algorithm) {
            case "Bubble Sort":    
            	BubbleSort.sort(data);       
            	break;
            case "Insertion Sort": 
            	InsertionSorting.sort(data); 
            	break;
            case "Merge Sort":     
            	MergeSorting.sort(data);     
            	break;
            case "Quick Sort":     
            	QuickSort.sort(data);        
            	break;
            case "Bucket Sort":    
            	BucketSort.sort(data);       
            	break;
            case "Pancake Sort":    
            	PancakeSort.sort(data);       
            	break;
            case "Cocktail Shaker Sort":    
            	CocktailShakerSort.sort(data);       
            	break;
            case "Purge Sort":    
            	PurgeSort.sort(data);       
            	break;
            case "Bogo Sort":    
            	BogoSort.sort(data);       
            	break;
            default: 
            	throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        }

        stats.stop();
        return null;
    }
}
