package sorting;

import util.SortingListener;
import java.util.*;

/**
 * Pancake Sort sorts a list using only "flips", which means reversing a prefix of the list
 * up to some index. The idea is like sorting a stack of pancakes by size using
 * only a spatula: find the biggest, flip it to the top, then flip it to its
 * correct position at the bottom. Repeat for the rest of the list.
 */
public class PancakeSort {

	/**
     * Reverses the portion of the list from index 0 up to index k (inclusive).
     *
     * @param list the list to flip
     * @param k    the index to flip up to
     */
    public static void flip(List<Integer> list, int k) {
        for (int l = 0; l < k; l++) {
            int vl = list.get(l); 
            SortingListener.notifyAccess(l, vl);
            int vk = list.get(k); 
            SortingListener.notifyAccess(k, vk);
            SortingListener.notifySwap(l, k, vl, vk);
            list.set(l, vk); 
            SortingListener.notifyAccess(l, vk);
            list.set(k, vl); 
            SortingListener.notifyAccess(k, vl);
            k--;
        }
    }

	/**
     * Finds the index of the largest element in the list from index 0 up to k (exclusive).
     * Used to know where to flip before placing the max element in its correct spot.
     *
     * @param list the list to search
     * @param k    how far into the list to look
     * @return the index of the maximum element in that range
     */
    public static int maxIndex(List<Integer> list, int k) {
        int res = 0;
        for (int i = 0; i < k; i++) {
            int vres = list.get(res); 
            SortingListener.notifyAccess(res, vres);
            int vi   = list.get(i);   
            SortingListener.notifyAccess(i,   vi);
            SortingListener.notifyComparison(res, i, vres, vi);
            if (vres < vi)
                res = i;
        }
        return res;
    }

	/**
     * Sorts the list using pancake flips. Each iteration finds the largest unsorted
     * element, flips it to the front, then flips it to its correct position at the
     * end of the unsorted region. The sorted region grows from the back.
     *
     * @param list the list to sort
     */
    public static void sort(List<Integer> list) {
        int maxI;
        for (int n = list.size(); n > 1; n--) {
            maxI = maxIndex(list, n);
            int vmaxI = list.get(maxI);  
            SortingListener.notifyAccess(maxI,  vmaxI);
            int vn1   = list.get(n - 1); 
            SortingListener.notifyAccess(n - 1, vn1);
            SortingListener.notifyComparison(maxI, n - 1, vmaxI, vn1);
            if (maxI != n - 1) {
                flip(list, maxI);
                flip(list, n - 1);
            }
        }
    }
}

