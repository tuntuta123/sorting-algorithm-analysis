package sorting;

import java.util.*;

public class BubbleSort{

	public static void sort(List<Integer> list){
		boolean bool;
		int var;
		for (int i = 0; i < list.size()-1; i++) {
			bool=false;
			for (int j = 0; j < list.size()-i-1; j++) {
				// comparison event
                		SortingListener.notifyComparison(j,j + 1,list.get(j),list.get(j + 1));
				if (list.get(j)>list.get(j+1)){
					// swap event (swap öncesi!)
                    			SortingListener.notifySwap(j,j + 1,list.get(j),list.get(j + 1));
					var=list.get(j);
					list.set(j,list.get(j+1));
					list.set(j+1, var);
					bool=true;
				}
			}
			
			if(bool==false){
				break;
			}
        	}
	}

}
