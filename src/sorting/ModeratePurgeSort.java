package sorting;

import java.util.*;

public class ModeratePurgeSort {

    	public static void sort(List<Integer> list) {
		int max = list.get(0);

        	for (int i = 1; i < list.size(); i++) {
            		if (list.get(i) >= max) {
                	max = list.get(i);
            		} 
		    	else {
		        	if (i + 1 < list.size() && list.get(i + 1) >= max) {
		            		list.remove(i);   // remove only the bad element
		            		i--;              // adjust index after removal
		        	} 		
		        	else {
				    	list.remove(i); 
				    	i--;
		        	}

		    	}
        	}
    	}
}
