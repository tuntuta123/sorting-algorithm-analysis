package sorting;

import util.SortingListener;
import java.util.*;

/**
 * Comb Sort is an improved version of Bubble Sort. The main idea is that instead
 * of always comparing adjacent elements, it starts by comparing elements that are
 * far apart and gradually shrinks the gap between them. This gets rid of small values
 * near the end of the list much faster. The shrink factor of 1.3 is the generally
 * accepted best value for it.
 */
public class CombSort {

	/**
     * Sorts the list by comparing elements separated by a gap that shrinks each pass.
     * Once the gap reaches 1 it behaves like Bubble Sort, but by then the list
     * is nearly sorted so it finishes quickly. Stops when a full pass with gap 1
     * produces no swaps.
     *
     * @param list the list to sort
     */
    public static void sort(List<Integer> list) {
        int length = list.size();
        double shrink = 1.3;
        int gap = length;
        boolean sorted = false;

        while (!sorted) {
            gap = (int) Math.floor(gap / shrink);
            if (gap <= 1) {
                sorted = true;
                gap = 1;
            }

            for (int i = 0; i < length - gap; i++) {
                int sm  = gap + i;
                int vi  = list.get(i);  
                SortingListener.notifyAccess(i,  vi);
                int vsm = list.get(sm); 
                SortingListener.notifyAccess(sm, vsm);
                SortingListener.notifyComparison(i, sm, vi, vsm);

                if (vi > vsm) {
                    list.set(i,  vsm); 
                    SortingListener.notifyAccess(i,  vsm);
                    list.set(sm, vi);  
                    SortingListener.notifyAccess(sm, vi);
                    SortingListener.notifySwap(i, sm, vi, vsm);
                    sorted = false;
                }
            }
        }
    }
}
