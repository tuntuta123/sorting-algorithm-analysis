package sorting;

import generator.*;
import java.util.*;

public class Demo{

	public static void main(String[] args){
		SwapSortGenerator veryRandom = new SwapSortGenerator(1.0,100);
		
		List<Integer> l1 = veryRandom.getList();
		System.out.println("Before sorting : " + l1);
		
		InsertionSorting.sort(l1);
		System.out.println("After sorting : " + l1);
	}

}
