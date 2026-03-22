package sorting;

import java.util.HashMap;
import java.util.Map;

/**
 * Factory class that gives a sorting algorithm by name.
 * All algorithms are registered in a static map at startup,
 * so adding a new one is just one extra line in the static block.
 */
public class SortingAlgorithmFactory {

	/**
     * Maps algorithm names to their implementations.
     * All the sorting classes are registered here as lambdas.
     */
    private static final Map<String, SortingAlgorithm> registry = new HashMap<>();

	/**
     * Registers all available sorting algorithms when the class is first loaded.
     * The keys are the display names used in the UI.
     */
    static {
        registry.put("Bubble Sort", list -> BubbleSort.sort(list));
        registry.put("Insertion Sort",list -> InsertionSorting.sort(list));
        registry.put("Merge Sort",list -> MergeSorting.sort(list));
        registry.put("Quick Sort", list -> QuickSort.sort(list));
        registry.put("Bucket Sort",list -> BucketSort.sort(list));
        registry.put("Comb Sort", list -> CombSort.sort(list));
        registry.put("Pancake Sort",list -> PancakeSort.sort(list));
        registry.put("Cocktail Shaker Sort",list -> CocktailShakerSort.sort(list));
        registry.put("Bogo Sort",list -> BogoSort.sort(list));
    }

	/**
     * Returns the sorting algorithm matching the given name.
     * Throws an exception if the name isn't in the registry.
     *
     * @param name the display name of the algorithm 
     * @return the corresponding SortingAlgorithm implementation
     * @throws IllegalArgumentException if no algorithm with that name exists
     */
    public static SortingAlgorithm get(String name) {
        SortingAlgorithm algo = registry.get(name);
        if (algo == null) {
            throw new IllegalArgumentException("Unknown algorithm: " + name);
        }
        return algo;
    }
}
