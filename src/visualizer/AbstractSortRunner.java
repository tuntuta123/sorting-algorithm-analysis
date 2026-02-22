package visualizer;

import javax.swing.SwingWorker;
import java.util.List;
import sorting.*;

public abstract class AbstractSortRunner extends SwingWorker<Void, Void> {

    protected final List<Integer> data;
    protected final String algorithm;
    protected final VisualizationListener listener;

    public AbstractSortRunner(List<Integer> data, String algorithm, VisualizationListener listener) {
        this.data = data;
        this.algorithm = algorithm;
        this.listener = listener;
    }

    @Override
    protected Void doInBackground() throws Exception {

        SortingListener.clearListeners();
        SortingListener.addListener(listener);

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
            default: 
            	throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        }
        return null;
    }
}
