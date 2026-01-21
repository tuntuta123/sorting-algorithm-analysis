package sorting;

import java.util.*;

public abstract class AbstractSorter implements SortingInterface{
	public List<Integer> list;
	
	public AbstractSorter(List<Integer> l){
		this.list = l;
	}
	@Override
	public String toString(){
		return "Hey gais =)";
	}
}
