package sorting;

import java.util.*;

public class BogoSort{
	
	public static void sort(List<Integer> list){
		while(!sorted(list)){
			shuffle(list);
			SortingListener.notifySwap(0, list.size() - 1, list.get(0), list.get(list.size() - 1));
		}
	}
	
	public static boolean sorted(List<Integer> list){
		for(int i = 0 ; i<list.size(); i++){
			SortingListener.notifyComparison(i, i + 1, list.get(i), list.get(i + 1));
			if(i != list.get(i)){
				return false;
			}
		}
		return true;
	}
	
	public static List<Integer> shuffle(List<Integer> list){
		Collections.shuffle(list);
		return list;
	}
}
