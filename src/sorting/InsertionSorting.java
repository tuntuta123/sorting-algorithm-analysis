package sorting;

import java.util.*;

public class InsertionSorting {
	
	public static void sort(List<Integer> input){
		int i, j;
		int length = input.size();
		for(i=1;i<length;i++){
			j = i;
			while(j > 0 && input.get(j) < input.get(j-1)){
				swap(input, j, j-1);
				j--;
			}
		}
	}
	
	public static void swap(List<Integer> list, int i, int j){
		int temp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, temp);
	}

}
