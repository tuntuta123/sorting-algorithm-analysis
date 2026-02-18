package visualizer;

import javax.swing.SwingWorker;
import java.util.List;
import sorting.*;

public class SortRunner extends SwingWorker<Void, Void> {

    private List<Integer> data;
    private String algorithm;
    private VisualizerWindow window;

    public SortRunner(List<Integer> data, String algorithm, VisualizerWindow window) {
        this.data = data;
        this.algorithm = algorithm;
        this.window = window;
        window.setCurrentData(data);
    }

    @Override
    protected Void doInBackground() throws Exception {
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
                //QuickSort.sort(data, 0, data.size() - 1);
                break;
            case "Bucket Sort":
                BucketSort.sort(data);
                break;
            default:
                throw new IllegalArgumentException("Unknown algorithm: " + algorithm);
        }
        return null;
    }

    @Override
    protected void done() {
        if (!isCancelled()) {
            window.onSortingDone();
        }
    }
}
