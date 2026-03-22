package view.window;

import java.util.List;

/**
 * Defines the basic actions that every sorting visualizer must support.
 * Any class that implements this interface can generate data, start, pause, and reset sorting.
 */
public interface VisualizerInterface {

    /**
     * Generates a new set of data to be sorted and displays it on the visualizer.
     */
    void generateData();

    /**
     * Starts the sorting algorithm on the current data.
     */
    void startSorting();

    /**
     * Pauses the sorting if it is running, or resumes it if it is already paused.
     */
    void togglePause();
    
    /**
     * Stops the sorting and resets the visualizer back to its initial state.
     */
    void reset();
}
