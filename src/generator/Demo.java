package generator;

import java.util.*;

public class Demo{
	public static void main(String[] args){
		SwapSortGenerator veryRandom = new SwapSortGenerator(1.0,100);
		SwapSortGenerator lessRandom = new SwapSortGenerator(0.5,100);
		SwapSortGenerator kindaRandom = new SwapSortGenerator(0.1,100);
		SortedGenerator sorted = new SortedGenerator(10);
		
		System.out.println("=========================================");
		System.out.println("Very random list with %100 randomness : ");
		System.out.println("=========================================");
		System.out.println(veryRandom.getList());
		System.out.println("=========================================");
		System.out.println("A lesser random list with %50 randomness : ");
		System.out.println("=========================================");
		System.out.println(lessRandom.getList());
		System.out.println("=========================================");
		System.out.println("Not very random list with %10 randomness : ");
		System.out.println("=========================================");
		System.out.println(kindaRandom.getList());
		System.out.println("=========================================");
		System.out.println("Sorted List : ");
		System.out.println("=========================================");
		System.out.println(sorted.getList());
		System.out.println("=========================================");
	}
}
