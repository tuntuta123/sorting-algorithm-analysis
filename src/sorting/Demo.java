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

		System.out.println("Before Bogosort : " + list2);
		BogoSort.sort(list2);
		System.out.println("After Bogosort : " + list2);
		
		SwapSortGenerator random2 = new SwapSortGenerator(1.0, 10);
		List<Integer> list3 = random2.getList();
		
		System.out.println("Before Pancake sort : " + list3);
		PancakeSort.sort(list3);
		System.out.println("After Pancake sort : " + list3);
		
		SwapSortGenerator random3 = new SwapSortGenerator(1.0, 20);
		List<Integer> list4 = random3.getList();
		
		System.out.println("Before CocktailShakerSort : " + list4);
		CocktailShakerSort.sort(list4);
		System.out.println("After CocktailShakerSort : " + list4);

	}

}
