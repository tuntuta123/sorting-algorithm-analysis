package sorting;

import util.SortingListener;
import java.util.*;

/**
 * BogoSort is an algorithm which sorts a list by
 * shuffling it randomly until it happens to be sorted. It works
 * eventually but could theoretically run forever. We implemented it
 * mostly for comparison purposes.
 */
public class BogoSort{

	/**
     * Sorts the list by repeatedly shuffling it until it's in order.
     * Notifies the listener on each shuffle so the visualizer can show what's happening.
     *
     * @param list the list to sort
     */
    public static void sort(List<Integer> list) {
        while (!sorted(list)) {
            shuffle(list);
            int v0  = list.get(0);              
            SortingListener.notifyAccess(0, v0);
            int vn1 = list.get(list.size() - 1);
            SortingListener.notifyAccess(list.size() - 1, vn1);
            SortingListener.notifySwap(0, list.size() - 1, v0, vn1);
        }
    }

	/**
     * Checks whether the list is already sorted in ascending order.
     * Goes through each pair of adjacent elements and compares them.
     *
     * @param list the list to check
     * @return true if the list is sorted, false otherwise
     */
    public static boolean sorted(List<Integer> list) {
		for (int i = 0; i < list.size() - 1; i++) {
		    int vi  = list.get(i);
		    SortingListener.notifyAccess(i,     vi);
		    int vi1 = list.get(i + 1);
		    SortingListener.notifyAccess(i + 1, vi1);
		    SortingListener.notifyComparison(i, i + 1, vi, vi1);
		    if (vi > vi1) {
		        return false;
		    }
		}
		return true;
	}

	/**
     * Randomly shuffles the list using Java's built-in Collections.shuffle.
     *
     * @param list the list to shuffle
     * @return the same list after shuffling 
     */
    public static List<Integer> shuffle(List<Integer> list) {
        Collections.shuffle(list);
        return list;
    }
}

