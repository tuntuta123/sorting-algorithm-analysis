package sorting;

import java.util.HashMap;
import java.util.Map;

public class SortingAlgorithmFactory {

    private static final Map<String, SortingAlgorithm> registry = new HashMap<>();

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

    public static SortingAlgorithm get(String name) {
        SortingAlgorithm algo = registry.get(name);
        if (algo == null) {
            throw new IllegalArgumentException("Unknown algorithm: " + name);
        }
        return algo;
    }
}
