package sorting;
import java.util.*;

public class PancakeSort {
    public static void flip(List<Integer> list, int k) {
        for (int l = 0; l < k; l++) {
            SortingListener.notifySwap(l, k, list.get(l), list.get(k));
            swap(list, l, k);
            k--;
        }
    }

    public static int maxIndex(List<Integer> list, int k) {
        int res = 0;
        for (int i = 0; i < k; i++) {
            SortingListener.notifyComparison(res, i, list.get(res), list.get(i));
            if (list.get(res) < list.get(i))
                res = i;
        }
        return res;
    }

    public static void sort(List<Integer> list) {
        int maxI;
        for (int n = list.size(); n > 1; n--) {
            maxI = maxIndex(list, n);
            SortingListener.notifyComparison(maxI, n - 1, list.get(maxI), list.get(n - 1));
            if (maxI != n - 1) {
                flip(list, maxI);
                flip(list, n - 1);
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
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
