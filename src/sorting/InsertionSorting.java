package sorting;
import java.util.*;

/**
 * This class implements the Insertion Sort algorithm.
 * It sorts a list of integers by repeatedly inserting the next element into its correct position.
 */
public class InsertionSorting {

	/**
     * Sorts the given list using the Insertion Sort algorithm.
     * 
     * @param input The list of integers to be sorted.
     */
    public static void sort(List<Integer> input) {
        int i, j;
        int length = input.size();
        for (i = 1; i < length; i++) {
            j = i;
            // Move the current element to its correct position
            while (j > 0) {
                int vj  = input.get(j);     SortingListener.notifyAccess(j,     vj);
                int vj1 = input.get(j - 1); SortingListener.notifyAccess(j - 1, vj1);
                if (vj >= vj1) break;
                SortingListener.notifyComparison(j, j - 1, vj, vj1);
                swap(input, j, j - 1);
                SortingListener.notifySwap(j, j - 1, vj, vj1);
                j--;
            }
        }
    }

	/**
     * Swaps two elements in the list.
     * 
     * @param list The list containing the elements to be swapped.
     * @param i The index of the first element.
     * @param j The index of the second element.
     */
    private static void swap(List<Integer> list, int i, int j) {
        int vi = list.get(i); SortingListener.notifyAccess(i, vi);
        int vj = list.get(j); SortingListener.notifyAccess(j, vj);
        list.set(i, vj);      SortingListener.notifyAccess(i, vj);
        list.set(j, vi);      SortingListener.notifyAccess(j, vi);
    }
}
