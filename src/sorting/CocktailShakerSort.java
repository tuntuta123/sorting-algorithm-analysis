package sorting;
import java.util.*;

public class CocktailShakerSort {

	public static void sort(List<Integer> list){
		boolean swapped = true;
		int i = 0;
		int j = list.size();
		while(swapped == true){
			swapped = false;
			for(int k = i; k < j - 1; k++){
				if(list.get(k) > list.get(k+1)){
					swap(list, k, k+1);
					swapped = true;
				}
			}
			if (swapped == false)
				break;
			swapped = false;
			j--;
			for(int k=j-1; k>=i;k--){
				if(list.get(k) > list.get(k+1)){
					swap(list, k, k+1);
					swapped = true;
				}
			}
			i++;
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
        int temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}

