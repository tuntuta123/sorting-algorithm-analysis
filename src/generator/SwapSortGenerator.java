package generator;

import java.util.*;

public class SwapSortGenerator extends AbstractGenerator{
	
	public int swaps;
	
	public SwapSortGenerator(double swapRatio, int size){
		super(size);
		this.swaps = (int)(swapRatio * size);
	}
	
	public int getSwaps(){
		return this.swaps;
	}
	
	@Override
	protected List<Integer> generate(){
		List<Integer> list = new ArrayList<Integer>(getSize());
		
		for(int i = 0 ; i<getSize() ; i++){
			list.add(i);
		}
		
		for(int i = 0 ; i<getSwaps() ; i++){
			int first = (int) (Math.random() * getSize());
        		int second = (int) (Math.random() * getSize());

        		Collections.swap(list, first, second);
		}
		
		return list;
	}

}
