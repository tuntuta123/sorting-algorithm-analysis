package sorting;

import generator.*;
import java.util.*;

public class Demo{

	public static void main(String[] args){
		SwapSortGenerator veryRandom = new SwapSortGenerator(1.0,100);
		SwapSortGenerator veryRandom2 = new SwapSortGenerator(1.0,100);
		
		List<Integer> l1 = veryRandom.getList();
		System.out.println("Before sorting : " + l1);
		
		InsertionSorting.sort(l1);
		System.out.println("After sorting : " + l1);

		//BubbleSort
		BubbleSort bubble=new BubbleSort();
        List<Integer> list = new ArrayList<Integer>();
        list.add(5);
        list.add(6);
        list.add(1);
        list.add(3);
        bubble.sort(list);
        bubble.printList(list);
        
        List<Integer> l2 = veryRandom2.getList();
		System.out.println("Before sorting : " + l2);
		
		MergeSorting.sort(l2);
		System.out.println("After sorting : " + l2);

	}

}
