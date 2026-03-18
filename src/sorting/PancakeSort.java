package sorting;
import java.util.*;
import util.SortingListener;

public class PancakeSort {

	public static void flip(List<Integer> list, int k){
		for(int l=0; l<k; l++){
			int vl = list.get(l); SortingListener.notifyAccess(l, vl);
			int vk = list.get(k); SortingListener.notifyAccess(k, vk);
			SortingListener.notifySwap(l, k, vl, vk);
			swap(list, l, k);
			k--;
		}
	}
	
	public static int maxIndex(List<Integer> list, int k){
		int res = 0;
		for(int i=0; i < k; i++){
			int vres = list.get(res); SortingListener.notifyAccess(res, vres);
			int vi   = list.get(i);   SortingListener.notifyAccess(i,   vi);
			SortingListener.notifyComparison(res, i, vres, vi);
			if(vres < vi)
				res = i;
		}
		return res;
	}
	
	public static void sort(List<Integer> list){
		int maxI;
		for(int n = list.size(); n > 1; n--){
			maxI = maxIndex(list, n);
			int vmaxI = list.get(maxI);  SortingListener.notifyAccess(maxI,  vmaxI);
			int vn1   = list.get(n - 1); SortingListener.notifyAccess(n - 1, vn1);
			SortingListener.notifyComparison(maxI, n - 1, vmaxI, vn1);
			if(maxI != n - 1){
				flip(list, maxI);
				flip(list, n-1);
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
        int vi = list.get(i); SortingListener.notifyAccess(i, vi);
        int vj = list.get(j); SortingListener.notifyAccess(j, vj);
        list.set(i, vj);      SortingListener.notifyAccess(i, vj);
        list.set(j, vi);      SortingListener.notifyAccess(j, vi);
    }
}
