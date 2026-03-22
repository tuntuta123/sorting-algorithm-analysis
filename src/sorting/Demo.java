package sorting;

import generator.*;
import java.util.*;

/**
 * Demo class to test that all sorting algorithms work correctly.
 * Uses the factory to get each algorithm and runs them on the same generated list
 * so the results are easy to compare.
 */
public class Demo {

    public static void main(String[] args) {

        List<String> algorithms = List.of(
            "Bubble Sort",
            "Insertion Sort",
            "Merge Sort",
            "Quick Sort",
            "Bucket Sort",
            "Comb Sort",
            "Pancake Sort",
            "Cocktail Shaker Sort",
            "Bogo Sort"
        );

        System.out.println("=== Sorting Algorithm Demo ===\n");

        for (String name : algorithms) {
            // Give each algorithm its own freshly generated list
            List<Integer> list = new EntropyGenerator(1, 10).getList();

            System.out.println("Algorithm : " + name);
            System.out.println("Before    : " + list);

            SortingAlgorithm algo = SortingAlgorithmFactory.get(name);
            algo.sort(list);

            System.out.println("After     : " + list);
            System.out.println("Sorted?   : " + isSorted(list));
            System.out.println();
        }
    }

    /**
     * Helper function to verify the list is actually sorted after the algorithm runs.
     *
     * @param list the list to check
     * @return true if sorted in ascending order, false otherwise
     */
    private static boolean isSorted(List<Integer> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i) > list.get(i + 1)) 
            	return false;
        }
        return true;
    }
}
