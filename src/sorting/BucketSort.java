package sorting;
import java.util.*;

public class BucketSort {

    private static void insertionSort(List<Integer> bucket) {
        for (int i = 1; i < bucket.size(); i++) {
            int key = bucket.get(i);
            int j = i - 1;

            while (j >= 0) {
				SortingListener.notifyComparison(j, i, bucket.get(j), key);

                if (bucket.get(j) <= key){
                	break;
                }

                SortingListener.notifySwap(j + 1, j, bucket.get(j + 1), bucket.get(j));
                bucket.set(j + 1, bucket.get(j));
                j--;
            }

            SortingListener.notifySwap(j + 1, j + 1, bucket.get(j + 1), key);
            bucket.set(j + 1, key);
        }
    }

    public static void sort(List<Integer> list) {
        if (list.size()<= 1) {
			return;
		}
        int min =list.get(0);
        int max =list.get(0);
        for (int i = 1; i< list.size(); i++) {
            int v = list.get(i);
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
            int v = list.get(i);
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
                SortingListener.notifySwap(index, index, list.get(index), v);
                list.set(index, v);
                index++;
            }
        }
    }
}

