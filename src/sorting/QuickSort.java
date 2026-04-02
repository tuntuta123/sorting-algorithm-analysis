package sorting;

import util.SortingListener;
import java.util.*;

/**
 * QuickSort works by picking a pivot element and rearranging the list so everything    
 * smaller than the pivot is on the left and everything bigger is on the right. Then it recursively does
 * the same thing to both halves. On average it's very fast but in the worst case
 * (like an already sorted list with a bad pivot choice) it can slow down a lot.
 * We use the last element as the pivot here.
 */
public class QuickSort {

	/**
     * Entry point for the sort. Does a null and size check and then
     * kicks off the recursive quickSort on the full range of the list.
     *
     * @param list the list to sort
     */
    public static void sort(List<Integer> list) {
        if (list == null || list.size() <= 1) 
        	return;
        quickSort(list, 0, list.size() - 1);
    }

	/**
     * This is the recursive part. It picks a pivot via partition(), then recursively
     * sorts the left and right sublists around it.
     *
     * @param list the list being sorted
     * @param low  the start index of the sublist we're currently sorting
     * @param high the end index of the sublist we're currently sorting
     */
    static void quickSort(List<Integer> list, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(list, low, high);
            quickSort(list, low, pivotIndex - 1);
            quickSort(list, pivotIndex + 1, high);
        }
    }

	/**
     * Rearranges elements in the sublist so that everything less than the pivot
     * ends up to its left and everything greater ends up to its right.
     * The pivot is always the last element in the current sublist.
     * Returns the final index where the pivot ended up.
     *
     * @param list the list being sorted
     * @param low  the start of the sublist
     * @param high the end of the sublist 
     * @return the index where the pivot was placed after partitioning
     */
    static int partition(List<Integer> list, int low, int high) {
        int pivot = list.get(high); 
        SortingListener.notifyAccess(high, pivot);
        int i = low - 1;

        for (int j = low; j <= high - 1; j++) {
            int vj = list.get(j); 
            SortingListener.notifyAccess(j, vj);
            SortingListener.notifyComparison(j, high, vj, pivot);
            if (vj < pivot) {
                i++;
                int vi = list.get(i); 
                SortingListener.notifySwap(i, j, vi, vj);
                SortingListener.notifyAccess(i, vi);
                list.set(i, vj); 
                SortingListener.notifyAccess(i, vj);
                list.set(j, vi); 
                SortingListener.notifyAccess(j, vi);
            }
        }

        int vi1 = list.get(i + 1); 
        SortingListener.notifySwap(i + 1, high, vi1, pivot);
        SortingListener.notifyAccess(i + 1, vi1);
        list.set(i + 1, pivot); 
        SortingListener.notifyAccess(i + 1, pivot);
        list.set(high,  vi1);   
        SortingListener.notifyAccess(high,   vi1);
        return i + 1;
    }
}

