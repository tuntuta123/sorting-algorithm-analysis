package sorting;

import util.SortingListener;
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
            while (j > 0) {
                int vj  = input.get(j);     
                SortingListener.notifyAccess(j,     vj);
                int vj1 = input.get(j - 1); 
                SortingListener.notifyAccess(j - 1, vj1);
                SortingListener.notifyComparison(j, j - 1, vj, vj1);
                if (vj >= vj1)
                    break;
                SortingListener.notifySwap(j, j - 1, vj, vj1);
                input.set(j,     vj1); 
                SortingListener.notifyAccess(j,     vj1);
                input.set(j - 1, vj);  
                SortingListener.notifyAccess(j - 1, vj);
                j--;
            }
        }
    }
}

