package sorting;

import util.SortingListener;
import java.util.*;

public class ModeratePurgeSort {
    public static void sort(List<Integer> list) {
        int max = list.get(0); SortingListener.notifyAccess(0, max);
        for (int i = 1; i < list.size(); i++) {
            int vi = list.get(i); SortingListener.notifyAccess(i, vi);
            SortingListener.notifyComparison(i, i, vi, max);
            if (vi >= max) {
                max = vi;
            } else {
                if (i + 1 < list.size()) {
                    int vi1 = list.get(i + 1); SortingListener.notifyAccess(i + 1, vi1);
                    SortingListener.notifyComparison(i + 1, i, vi1, max);
                }
                SortingListener.notifyAccess(i, vi);
                list.remove(i);
                i--;
            }
        }
    }
}
