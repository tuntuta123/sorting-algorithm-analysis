package generator;

import java.util.*;

public class SortedGenerator extends AbstractGenerator{
	public SortedGenerator(int size){
		super(size);
		init();
	}
	
	@Override
	protected List<Integer> generate(){
		List<Integer> list = new ArrayList<Integer>(getSize());
		
		for(int i = 0 ; i<getSize() ; i++){
			list.add(i);
		}
		return list;
	}
}
