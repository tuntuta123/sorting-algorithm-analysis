package sorting;

import java.util.*;

public class MergeSorting {

    public static void aux(List<Integer> input, int i, int m, int j) {
        List<Integer> left = new ArrayList<>(input.subList(i, m + 1));
        List<Integer> right = new ArrayList<>(input.subList(m + 1, j + 1));

        int leftIndex = 0;
        int rightIndex = 0;
        int sortedIndex = i;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex) <= right.get(rightIndex)) {
                input.set(sortedIndex++, left.get(leftIndex++));
            } else {
                input.set(sortedIndex++, right.get(rightIndex++));
            }
        }

        while (leftIndex < left.size()) {
            input.set(sortedIndex++, left.get(leftIndex++));
        }

        while (rightIndex < right.size()) {
            input.set(sortedIndex++, right.get(rightIndex++));
        }
    }

    public static void merge(List<Integer> input, int i, int j) {
        if (i >= j) {
        	return;
		}
        int m = i + (j - i) / 2;
        merge(input, i, m);
        merge(input, m + 1, j);
        aux(input, i, m, j);
    }

    public static void sort(List<Integer> input) {
        merge(input, 0, input.size() - 1);
    }
}

