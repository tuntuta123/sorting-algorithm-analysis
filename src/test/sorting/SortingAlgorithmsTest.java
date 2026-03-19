package test.sorting;

import sorting.*;
import org.junit.jupiter.api.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class SortingAlgorithmsTest {

    private List<Integer> sorted(){ 
    	return new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)); 
    }
    private List<Integer> reversed() { 
    	return new ArrayList<>(Arrays.asList(9, 8, 7, 6, 5, 4, 3, 2, 1, 0)); 
    }
    private List<Integer> shuffled() { 
    	return new ArrayList<>(Arrays.asList(3, 7, 1, 9, 0, 5, 2, 8, 4, 6)); 
    }
    private List<Integer> single(){ 
    	return new ArrayList<>(Arrays.asList(42)); 
    }
    private List<Integer> empty(){ 
    	return new ArrayList<>(); 
    }

    private boolean isSorted(List<Integer> list) {
        for (int i = 1; i < list.size(); i++) {
            if (list.get(i - 1) > list.get(i)) 
            	return false;
        }
        return true;
    }

    @Test void bubbleSort_alreadySorted(){ 
    	List<Integer> l = sorted();   
    	BubbleSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void bubbleSort_reverseSorted(){ 
	    List<Integer> l = reversed(); 
	    BubbleSort.sort(l); 
	    assertTrue(isSorted(l)); 
    }
    @Test void bubbleSort_shuffled(){ 
    	List<Integer> l = shuffled(); 
    	BubbleSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void bubbleSort_singleElement(){ 
    	List<Integer> l = single();   
    	BubbleSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void bubbleSort_emptyList(){ 
    	List<Integer> l = empty();    
    	BubbleSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }

    @Test void insertionSort_alreadySorted(){ 
    	List<Integer> l = sorted();   
    	InsertionSorting.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void insertionSort_reverseSorted(){ 
    	List<Integer> l = reversed(); 
    	InsertionSorting.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void insertionSort_shuffled(){ 
    	List<Integer> l = shuffled(); 
    	InsertionSorting.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void insertionSort_singleElement() { 
    	List<Integer> l = single();   
    	InsertionSorting.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void insertionSort_emptyList(){ 
    	List<Integer> l = empty();    
    	InsertionSorting.sort(l); 
    	assertTrue(isSorted(l)); 
    }

    @Test void mergeSort_alreadySorted() { 
    	List<Integer> l = sorted();   
    	MergeSorting.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void mergeSort_reverseSorted() { 
    	List<Integer> l = reversed(); 
    	MergeSorting.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void mergeSort_shuffled(){ 
    	List<Integer> l = shuffled(); 
    	MergeSorting.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void mergeSort_singleElement() { 
    	List<Integer> l = single();   
    	MergeSorting.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void mergeSort_emptyList(){ 
    	List<Integer> l = empty();    
    	MergeSorting.sort(l); 
    	assertTrue(isSorted(l)); 
    }

    @Test void quickSort_alreadySorted() { 
    	List<Integer> l = sorted();   
    	QuickSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void quickSort_reverseSorted() { 
    	List<Integer> l = reversed(); 
    	QuickSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void quickSort_shuffled(){ 
    	List<Integer> l = shuffled(); 
    	QuickSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void quickSort_singleElement() { 
    	List<Integer> l = single();   
    	QuickSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void quickSort_emptyList(){ 
    	List<Integer> l = empty();    
    	QuickSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }

    @Test void bucketSort_alreadySorted() { 
    	List<Integer> l = sorted();   
    	BucketSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void bucketSort_reverseSorted() { 
    	List<Integer> l = reversed(); 
    	BucketSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void bucketSort_shuffled(){ 
    	List<Integer> l = shuffled(); 
    	BucketSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void bucketSort_singleElement() { 
    	List<Integer> l = single();   
    	BucketSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void bucketSort_emptyList(){ 
    	List<Integer> l = empty();    
    	BucketSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }

    @Test void combSort_alreadySorted() { 
    	List<Integer> l = sorted();   
    	CombSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void combSort_reverseSorted() { 
    	List<Integer> l = reversed(); 
    	CombSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void combSort_shuffled(){ 
    	List<Integer> l = shuffled(); 
    	CombSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void combSort_singleElement() { 
    	List<Integer> l = single();   
    	CombSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void combSort_emptyList(){ 
    	List<Integer> l = empty();    
    	CombSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }

    @Test void cocktailShakerSort_alreadySorted() { 
    	List<Integer> l = sorted();   
    	CocktailShakerSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void cocktailShakerSort_reverseSorted() { 
    	List<Integer> l = reversed(); 
    	CocktailShakerSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void cocktailShakerSort_shuffled(){ 
    	List<Integer> l = shuffled(); 
    	CocktailShakerSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void cocktailShakerSort_singleElement() { 
    	List<Integer> l = single();   
    	CocktailShakerSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void cocktailShakerSort_emptyList(){ 
    	List<Integer> l = empty();    
    	CocktailShakerSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }

    @Test void pancakeSort_alreadySorted() { 
    	List<Integer> l = sorted();   
    	PancakeSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void pancakeSort_reverseSorted() { 
    	List<Integer> l = reversed(); 
    	PancakeSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void pancakeSort_shuffled(){ 
    	List<Integer> l = shuffled(); 
    	PancakeSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void pancakeSort_singleElement() { 
    	List<Integer> l = single();   
    	PancakeSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void pancakeSort_emptyList(){ 
    	List<Integer> l = empty();    
    	PancakeSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }

    @Test void purgeSort_alreadySorted() { 
    	List<Integer> l = sorted();   
    	PurgeSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void purgeSort_reverseSorted() { 
    	List<Integer> l = reversed(); 
    	PurgeSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void purgeSort_shuffled(){ 
    	List<Integer> l = shuffled(); 
    	PurgeSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void purgeSort_singleElement() { 
    	List<Integer> l = single();   
    	PurgeSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }
    @Test void purgeSort_emptyList(){ 
    	List<Integer> l = empty();    
    	PurgeSort.sort(l); 
    	assertTrue(isSorted(l)); 
    }

}
