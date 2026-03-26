package generator;

import java.util.*;

/**
 * Base class for number generators.
 * Stores the target size and the generated list, and provides
 * common utility methods shared by all generators.
*/

public abstract class AbstractGenerator implements NumberGenerator{
	protected final int size;
	protected List<Integer> list;
	
	/**
	 * Creates a generator with the given list size.
	 *
	 * @param size size of the list to generate
	 * @throws IllegalArgumentException if size is negative
	*/
	
	public AbstractGenerator(int size){
		if(size<0){
			throw new IllegalArgumentException("Size can't be smaller than 0.");	
		}
		this.size = size;
	}
	
	/**
	 * Returns the size configured for this generator.
	 *
	 * @return the expected size of the generated list
	*/

	@Override
	public int getSize(){
		return this.size;
	}
	
	/**
	 * Returns the generated list.
	 *
	 * @return the current generated list
	*/

	@Override
	public List<Integer> getList(){
		return this.list;
	}
	
	/**
	 * Initializes the generator by calling generate() and storing the result.
	 *
	 * @throws IllegalStateException if generate() returns null
	*/

	public final void init(){
		List<Integer> generated = generate();
		if (generated == null) {
            		throw new IllegalStateException("generate() returned null");
       		}
       		this.list = generated;
	}
	
	/**
	 * Checks whether the current list is sorted.
	 *
	 * @return true if the list is sorted, false otherwise
	*/

	@Override
	public boolean isSorted(){
		List<Integer> l = getList();
		for (int i = 1 ; i<l.size() ; i++){
			if (l.get(i-1) > l.get(i)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Generates a new list according to the subclass strategy.
	 *
	 * @return a newly generated list
	 */

	protected abstract List<Integer> generate();
}
