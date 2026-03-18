package sorting;
import java.util.*;
import util.SortingListener;

public class CombSort {

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
                int vi  = list.get(i);  SortingListener.notifyAccess(i,  vi);
                int vsm = list.get(sm); SortingListener.notifyAccess(sm, vsm);
                SortingListener.notifyComparison(i, sm, vi, vsm);

                if (vi > vsm) {
                    list.set(i,  vsm); SortingListener.notifyAccess(i,  vsm);
                    list.set(sm, vi);  SortingListener.notifyAccess(sm, vi);
                    SortingListener.notifySwap(i, sm, vi, vsm);
                    sorted = false;
                }
            }
        }
    }
}
