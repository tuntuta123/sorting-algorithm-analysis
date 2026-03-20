package sorting;

import util.SortingListener;
import java.util.*;

public class QuickSort {

    public static void sort(List<Integer> list) {
        if (list == null || list.size() <= 1) return;
        quickSort(list, 0, list.size() - 1);
    }

    static void quickSort(List<Integer> list, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(list, low, high);
            quickSort(list, low, pivotIndex - 1);
            quickSort(list, pivotIndex + 1, high);
        }
    }

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
                SortingListener.notifyAccess(i, vi);
                SortingListener.notifySwap(i, j, vi, vj);
                list.set(i, vj); 
                SortingListener.notifyAccess(i, vj);
                list.set(j, vi); 
                SortingListener.notifyAccess(j, vi);
            }
        }

        int vi1 = list.get(i + 1); 
        SortingListener.notifyAccess(i + 1, vi1);
        SortingListener.notifySwap(i + 1, high, vi1, pivot);
        list.set(i + 1, pivot); 
        SortingListener.notifyAccess(i + 1, pivot);
        list.set(high,  vi1);   
        SortingListener.notifyAccess(high,   vi1);
        return i + 1;
    }
}

