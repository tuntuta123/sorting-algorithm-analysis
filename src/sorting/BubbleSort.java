package sorting;
import java.util.*;

/**
 * Goes through the list, compares adjacent elements, 
 * and swaps them if they are in the wrong order.
 */

public class BubbleSort{

    /**
     * Sorts the provided list using the Bubble Sort technique.
     * Includes an optimization to stop early if the list is already sorted.
     * @param list The list of integers that will be sorted.
     */
	public static void sort(List<Integer> list){
		boolean bool;
		int var;
		for (int i = 0; i < list.size()-1; i++) {
			bool=false;
			for (int j = 0; j < list.size()-i-1; j++) {
                		SortingListener.notifyComparison(j,j + 1,list.get(j),list.get(j + 1));
				if (list.get(j)>list.get(j+1)){
                    			SortingListener.notifySwap(j,j + 1,list.get(j),list.get(j + 1));
					var=list.get(j);
					list.set(j,list.get(j+1));
					list.set(j+1, var);
					bool=true;
				}
			}
			
			if(bool==false){
				break;
			}
        	}
	}

}
