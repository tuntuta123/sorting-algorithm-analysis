package sorting;

import util.SortingListener;
import java.util.*;

public class BogoSort{

    public static void sort(List<Integer> list) {
        while (!sorted(list)) {
            shuffle(list);
            int v0  = list.get(0);              
            SortingListener.notifyAccess(0,               v0);
            int vn1 = list.get(list.size() - 1);
            SortingListener.notifyAccess(list.size() - 1, vn1);
            SortingListener.notifySwap(0, list.size() - 1, v0, vn1);
        }
    }

    public static boolean sorted(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            int vi  = list.get(i);     
            SortingListener.notifyAccess(i,     vi);
            int vi1 = list.get(i + 1); 
            SortingListener.notifyAccess(i + 1, vi1);
            SortingListener.notifyComparison(i, i + 1, vi, vi1);
            if (i != list.get(i)) {
                return false;
            }
        }
        return true;
    }

    public static List<Integer> shuffle(List<Integer> list) {
        Collections.shuffle(list);
        return list;
    }
}

