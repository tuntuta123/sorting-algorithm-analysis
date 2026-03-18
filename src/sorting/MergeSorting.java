package sorting;

import java.util.*;

/**
 * This class implements the Merge Sort algorithm.
 * It recursively splits the list into two halves and then merges them back together in sorted order.
 */
public class MergeSorting {

    /**
     * Merges two sorted sublists into one sorted list.
     *
     * @param input The list to be sorted.
     * @param i     The starting index of the left sublist.
     * @param m     The ending index of the left sublist.
     * @param j     The ending index of the right sublist.
     */
    public static void aux(List<Integer> input, int i, int m, int j) {
        List<Integer> left = new ArrayList<>(input.subList(i, m + 1));
        List<Integer> right = new ArrayList<>(input.subList(m + 1, j + 1));

        for (int k = 0; k < left.size();  k++) SortingListener.notifyAccess(i + k,     left.get(k));
        for (int k = 0; k < right.size(); k++) SortingListener.notifyAccess(m + 1 + k, right.get(k));

        int leftIn = 0;
        int rightIn = 0;
        int sortedIndex = i;

        while (leftIn < left.size() && rightIn < right.size()) {
            int leftVal = left.get(leftIn);
            int rightVal = right.get(rightIn);
            SortingListener.notifyComparison(i + leftIn, m + 1 + rightIn, leftVal, rightVal);

            if (leftVal <= rightVal) {
                input.set(sortedIndex, leftVal); SortingListener.notifyAccess(sortedIndex, leftVal);
                sortedIndex++;
                leftIn++;
            } else {
                input.set(sortedIndex, rightVal); SortingListener.notifyAccess(sortedIndex, rightVal);
                sortedIndex++;
                rightIn++;
            }
        }

        while (leftIn < left.size()) {
            int v = left.get(leftIn++);
            input.set(sortedIndex, v); SortingListener.notifyAccess(sortedIndex, v);
            sortedIndex++;
        }

        while (rightIn < right.size()) {
            int v = right.get(rightIn++);
            input.set(sortedIndex, v); SortingListener.notifyAccess(sortedIndex, v);
            sortedIndex++;
        }
    }

    /**
     * Recursively divides the list into sublists and merges them.
     *
     * @param input The list to be sorted.
     * @param i     The starting index of the current sublist.
     * @param j     The ending index of the current sublist.
     */
    public static void merge(List<Integer> input, int i, int j) {
        if (i >= j) {
            return;
        }
        int m = i + (j - i) / 2;
        merge(input, i, m);
        merge(input, m + 1, j);
        aux(input, i, m, j);
    }

    /**
     * Sorts the list using the Merge Sort algorithm.
     *
     * @param input The list of integers to be sorted.
     */
    public static void sort(List<Integer> input) {
        merge(input, 0, input.size() - 1);
    }
}
