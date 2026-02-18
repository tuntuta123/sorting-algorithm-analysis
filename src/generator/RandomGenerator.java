package generator;

import java.util.*;

public class RandomGenerator extends AbstractGenerator{
	public RandomGenerator(int size){
		super(size);
		init();
	}
	
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
