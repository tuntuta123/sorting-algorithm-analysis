package util;

/**
 * This interface is for listening to events during the sorting process.
 * It provides methods that are called when two elements are compared, swapped, and when an element is accessed.
 */
public interface Listener {

    /**
     * Called when two elements are compared.
     *
     * @param i1 Index of the first element.
     * @param i2 Index of the second element.
     * @param v1 Value of the first element.
     * @param v2 Value of the second element.
     */
    void onComparison(int i1, int i2, int v1, int v2);

    /**
     * Called when two elements are swapped.
     *
     * @param i1 Index of the first element.
     * @param i2 Index of the second element.
     * @param v1 Value of the first element.
     * @param v2 Value of the second element.
     */
    void onSwap(int i1, int i2, int v1, int v2);

    /**
     * Called when a single element is accessed (get or set).
     *
     * @param index Index of the accessed element.
     * @param value Value at that index.
     */
    void onAccess(int index, int value);
}
