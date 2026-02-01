package sorting;

import java.util.*;

public class QuickSort{
	
	
	//items to the left are smaller
	//compare j with pivot, if j<pivot then i++ and switch with k
	
	static void sort(List<Integer> list, int low, int high){
		if (low < high){
			int pivotIndex = partition(list, low, high);
			sort(list,low,pivotIndex-1);
			sort(list,pivotIndex+1, high);
		}
	}
	
	static int partition(List<Integer> list, int low, int high){
		int pivot = list.get(high);
		int i = low - 1;
		
		for (int j = low ; j <= high -1 ; j++){
			// comparison event: list[j] vs pivot
            		SortingListener.notifyComparison(j, high, list.get(j), pivot);
			if (list.get(j) < pivot){
				i++;
				
				// swap event (swap öncesi!)
                		SortingListener.notifySwap(i, j, list.get(i), list.get(j));
				swap(list,i,j);
			}
		}
		// final swap with pivot
        	SortingListener.notifySwap(i + 1, high, list.get(i + 1), list.get(high));
		swap(list, i + 1, high);
        	return i + 1;
	}
	
	static void swap(List<Integer> list, int first, int second) {
    		int temp = list.get(first);
    		list.set(first, list.get(second));
    		list.set(second, temp);
	}
}
