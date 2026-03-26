
package generator;

import java.util.*;
/**
 * Common interface for all number generators.
 */
public interface NumberGenerator{
	/**
	 * Returns the generated list.
	 *
	 * @return generated list
	 */
	List<Integer> getList();
	/**
	 * Checks whether the generated list is sorted.
	 *
	 * @return true if sorted, false otherwise
	 */
    boolean isSorted();
	/**
    * Returns the size of the generated list.
    *
    * @return list size
    */
    int getSize();
}
