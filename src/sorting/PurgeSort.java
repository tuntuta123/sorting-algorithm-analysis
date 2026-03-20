package sorting;

import util.SortingListener;
import java.util.*;

public class PurgeSort {

    public static void sort(List<Integer> list) {
        int max = list.get(0); 
        SortingListener.notifyAccess(0, max);
        for (int i = 0; i < list.size(); i++) {
            int vi = list.get(i); 
            SortingListener.notifyAccess(i, vi);
            SortingListener.notifyComparison(i, i, vi, max);
            if (vi >= max) {
                max = vi;
            } else {
                SortingListener.notifyAccess(i, vi); // should it stay, i dont know if removing elements shoudl count as access?? 
                list.remove(i);
                i--;
            }
        }
    }
}

