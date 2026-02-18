package sorting;

import java.util.*;

/**
 * This class is used to manage listeners for sorting events.
 * It allows listeners to be added and notifies them when comparisons or swaps happen.
 */
public class SortingListener {

    private static List<Listener> listeners = new ArrayList<>();

	/**
     * Adds a listener to the list of listeners.
     * 
     * @param listener The listener to be added.
     */
    public static void addListener(Listener listener) {
        listeners.add(listener);
    }

	/**
     * Notifies all listeners when two elements are compared.
     * 
     * @param i1 Index of the first element.
     * @param i2 Index of the second element.
     * @param val1 Value of the first element.
     * @param val2 Value of the second element.
     */
    public static void notifyComparison(int i1, int i2, int val1, int val2) {
        for (Listener listener : listeners) {
            listener.onComparison(i1, i2, val1, val2);
        }
    }

	/**
     * Notifies all listeners when two elements are swapped.
     * 
     * @param i Index of the first element.
     * @param j Index of the second element.
     * @param val1 Value of the first element before swap.
     * @param val2 Value of the second element before swap.
     */
    public static void notifySwap(int i, int j, int val1, int val2) {
        for (Listener listener : listeners) {
            listener.onSwap(i, j, val1, val2);
        }
    }
    
    public static void clearListeners() {
		listeners.clear();
	}

}

