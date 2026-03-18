package sorting;

import java.util.*;

public class QuickSort{
	
	public static void sort(List<Integer> list) {
		if (list == null || list.size() <= 1) return;
		quickSort(list, 0, list.size() - 1);
    	}
	
	//items to the left are smaller
	//compare j with pivot, if j<pivot then i++ and switch with k
	static void quickSort(List<Integer> list, int low, int high){
		if (low < high){
			int pivotIndex = partition(list, low, high);
			quickSort(list,low,pivotIndex-1);
			quickSort(list,pivotIndex+1, high);
		}
	}
	
	static int partition(List<Integer> list, int low, int high){
		int pivot = list.get(high); SortingListener.notifyAccess(high, pivot);
		int i = low - 1;
		
		for (int j = low ; j <= high -1 ; j++){
			// comparison event: list[j] vs pivot
			int vj = list.get(j); SortingListener.notifyAccess(j, vj);
            		SortingListener.notifyComparison(j, high, vj, pivot);
			if (vj < pivot){
				i++;
				
				// swap event (swap öncesi!)
                		SortingListener.notifySwap(i, j, list.get(i), vj);
				swap(list,i,j);
			}
		}
		// final swap with pivot
        	SortingListener.notifySwap(i + 1, high, list.get(i + 1), list.get(high));
		swap(list, i + 1, high);
        	return i + 1;
	}
	
	static void swap(List<Integer> list, int first, int second) {
    		int vf = list.get(first);  SortingListener.notifyAccess(first,  vf);
    		int vs = list.get(second); SortingListener.notifyAccess(second, vs);
    		list.set(first,  vs);      SortingListener.notifyAccess(first,  vs);
    		list.set(second, vf);      SortingListener.notifyAccess(second, vf);
	}
}
