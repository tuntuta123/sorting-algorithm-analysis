package sorting;

import util.SortingListener;
import java.util.*;

public class CocktailShakerSort {

    public static void sort(List<Integer> list) {
        boolean swapped = true;
        int i = 0;
        int j = list.size();
        while (swapped == true) {
            swapped = false;
            for (int k = i; k < j - 1; k++) {
                int vk  = list.get(k);     
                SortingListener.notifyAccess(k,     vk);
                int vk1 = list.get(k + 1); 
                SortingListener.notifyAccess(k + 1, vk1);
                SortingListener.notifyComparison(k, k + 1, vk, vk1);
                if (vk > vk1) {
                    SortingListener.notifySwap(k, k + 1, vk, vk1);
                    list.set(k,     vk1); 
                    SortingListener.notifyAccess(k,     vk1);
                    list.set(k + 1, vk);  
                    SortingListener.notifyAccess(k + 1, vk);
                    swapped = true;
                }
            }
            if (swapped == false)
                break;
            swapped = false;
            j--;
            for (int k = j - 1; k >= i; k--) {
                int vk  = list.get(k);     
                SortingListener.notifyAccess(k,     vk);
                int vk1 = list.get(k + 1); 
                SortingListener.notifyAccess(k + 1, vk1);
                SortingListener.notifyComparison(k, k + 1, vk, vk1);
                if (vk > vk1) {
                    SortingListener.notifySwap(k, k + 1, vk, vk1);
                    list.set(k,     vk1); 
                    SortingListener.notifyAccess(k,     vk1);
                    list.set(k + 1, vk);  
                    SortingListener.notifyAccess(k + 1, vk);
                    swapped = true;
                }
            }
            i++;
        }
    }
}

