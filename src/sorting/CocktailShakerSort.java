package sorting;
import java.util.*;
import util.SortingListener;

public class CocktailShakerSort {
    public static void sort(List<Integer> list) {
        boolean swapped = true;
        int i = 0;
        int j = list.size();
        while (swapped == true) {
            swapped = false;
            for (int k = i; k < j - 1; k++) {
                int vk  = list.get(k);     SortingListener.notifyAccess(k,     vk);
                int vk1 = list.get(k + 1); SortingListener.notifyAccess(k + 1, vk1);
                SortingListener.notifyComparison(k, k + 1, vk, vk1);
                if (vk > vk1) {
                    SortingListener.notifySwap(k, k + 1, vk, vk1);
                    swap(list, k, k + 1);
                    swapped = true;
                }
            }
            if (swapped == false)
                break;
            swapped = false;
            j--;
            for (int k = j - 1; k >= i; k--) {
                int vk  = list.get(k);     SortingListener.notifyAccess(k,     vk);
                int vk1 = list.get(k + 1); SortingListener.notifyAccess(k + 1, vk1);
                SortingListener.notifyComparison(k, k + 1, vk, vk1);
                if (vk > vk1) {
                    SortingListener.notifySwap(k, k + 1, vk, vk1);
                    swap(list, k, k + 1);
                    swapped = true;
                }
            }
            i++;
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
