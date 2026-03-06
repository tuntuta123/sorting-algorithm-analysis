package sorting;

import java.util.*;

public class PurgeSort{
	public static void sort(List<Integer> list){
		int max = list.get(0);
		for (int i = 0 ; i<list.size() ; i++){
			if (list.get(i)>=max){
				max = list.get(i);
			}
			else{
				list.remove(i);
				i--;
			}
		}
	}

}
