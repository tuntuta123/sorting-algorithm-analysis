package sorting;

import java.util.List;

/**
 * Represents a sorting algorithm as a functional interface.
 * The sorting classes don't implement this directly, but instead the factory
 * wraps their static methods in lambdas and maps them to this type.
 */
public interface SortingAlgorithm {

	/**
     * Sorts the given list of integers in ascending order.
     *
     * @param list the list to sort
     */
    void sort(List<Integer> list);
}
