package sorting;

import java.util.*;

public class BogoSort{
	
	public static void sort(List<Integer> list){
		while(!sorted(list)){
			shuffle(list);
		}
	}
	
	public static boolean sorted(List<Integer> list){
		for(int i = 0 ; i<list.size(); i++){
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
