package generator;

import java.util.*;

public abstract class AbstractGenerator implements NumberGenerator{
	protected final int size;
	protected List<Integer> list;
	
	public AbstractGenerator(int size){
		if(size<0){
			throw new IllegalArgumentException("Size can't be smaller than 0.");	
		}
		this.size = size;
	}
	
	@Override
	public int getSize(){
		return this.size;
	}
	
	@Override
	public List<Integer> getList(){
		return this.list;
	}
	
	public final void init(){
		List<Integer> generated = generate();
		if (generated == null) {
            		throw new IllegalStateException("generate() returned null");
       		}
       		this.list = generated;
	}
	
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
	
	protected abstract List<Integer> generate();
}
