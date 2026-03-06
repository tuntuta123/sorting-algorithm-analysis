package sorting;

import generator.*;
import java.util.*;

public class Demo{

	public static void main(String[] args){
		SwapSortGenerator veryRandom = new SwapSortGenerator(1.0,10);
		SwapSortGenerator veryRandom2 = new SwapSortGenerator(1.0,10);
		
		List<Integer> l1 = veryRandom.getList();
		System.out.println("Before sorting : " + l1);
		
		InsertionSorting.sort(l1);
		System.out.println("After sorting : " + l1);
        
        List<Integer> l2 = veryRandom2.getList();
		System.out.println("Before sorting : " + l2);
		
		MergeSorting.sort(l2);
		System.out.println("After sorting : " + l2);

		//BucketSort
		SwapSortGenerator random = new SwapSortGenerator(1.0, 10);
		List<Integer> list1 = random.getList();

		System.out.println("Before bucket sort : " + list1);
		BucketSort.sort(list1);
		System.out.println("After bucket sort  : " + list1);

		//BubbleSort
		SwapSortGenerator random1 = new SwapSortGenerator(1.0, 10);
		List<Integer> list2 = random1.getList();

		System.out.println("Before Bogosrorot : " + list2);
		BogoSort.sort(list2);
		System.out.println("After Bogosrorot : " + list2);

	}

}
