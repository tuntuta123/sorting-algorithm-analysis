package sorting;
import java.util.*;
import util.SortingListener;

/**
 * This algorithm distributes elements into several buckets, sorts each bucket 
 * individually, and then merges them back together.
 */

public class BucketSort {

    /**
     * Helper method to sort individual buckets.
     * @param bucket The list of integers.
     */
    private static void insertionSort(List<Integer> bucket) {
        for (int i = 1; i < bucket.size(); i++) {
            int key = bucket.get(i); SortingListener.notifyAccess(i, key);
            int j = i - 1;

            while (j >= 0) {
				int vj = bucket.get(j); SortingListener.notifyAccess(j, vj);
				SortingListener.notifyComparison(j, i, vj, key);

                if (vj <= key){
                	break;
                }

                SortingListener.notifySwap(j + 1, j, bucket.get(j + 1), vj);
                bucket.set(j + 1, vj); SortingListener.notifyAccess(j + 1, vj);
                j--;
            }

            SortingListener.notifySwap(j + 1, j + 1, bucket.get(j + 1), key);
            bucket.set(j + 1, key); SortingListener.notifyAccess(j + 1, key);
        }
    }


    /**
     * Sort method that executes the Bucket Sort logic.
     * @param list The unsorted list of integers that will be sorted.
     */
    public static void sort(List<Integer> list) {
        if (list.size()<= 1) {
			return;
		}
        int min = list.get(0); SortingListener.notifyAccess(0, min);
        int max = list.get(0); SortingListener.notifyAccess(0, max);
        for (int i = 1; i< list.size(); i++) {
            int v = list.get(i); SortingListener.notifyAccess(i, v);
            if (v < min){ 
            	min = v;
            }
            if (v > max){
		 		max = v;
			}
        }
        if (min == max){
        	return;
        }

        List<List<Integer>> buckets = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            buckets.add(new ArrayList<>());
        }

        double range = (double)(max - min + 1);

        for (int i = 0; i < list.size(); i++) {
            int v = list.get(i); SortingListener.notifyAccess(i, v);
            int bi = (int) (list.size() * (v - min) / range);
            if (bi == list.size()){
				bi = list.size() - 1;
			}
            buckets.get(bi).add(v);

            SortingListener.notifySwap(i, bi, v, v);
        }

        for (int i = 0; i < list.size(); i++) {
            insertionSort(buckets.get(i));
        }

        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int v : buckets.get(i)) {
                int old = list.get(index); SortingListener.notifyAccess(index, old);
                SortingListener.notifySwap(index, index, old, v);
                list.set(index, v);        SortingListener.notifyAccess(index, v);
                index++;
            }
        }
    }
}
