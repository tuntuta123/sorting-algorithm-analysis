package generator;

import java.util.*;

/**
 * Generator that creates a randomly shuffled list of integers
 * from 0 to size - 1.
 */

public class RandomGenerator extends AbstractGenerator{
	/**
	 * Creates a random generator for the given size.
	 *
	 * @param size size of the list to generate
	 */
	public RandomGenerator(int size){
		super(size);
		init();
	}
	/**
	 * Generates a shuffled list of integers.
	 *
	 * @return a random permutation of values from 0 to size - 1
	 */
	@Override
	protected List<Integer> generate() {

        	int n = getSize();

        	//ordered list
        	List<Integer> list = new ArrayList<>(n);
        	for (int i = 0; i < n; i++) {
            		list.add(i);
        	}
        	Collections.shuffle(list);
        	return list;
	}
}
