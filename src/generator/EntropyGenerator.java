package generator;

import java.util.*;

/**
 * Generator that creates a list with a target entropy level
 * based on the proportion of displaced elements.
*/

public class EntropyGenerator extends AbstractGenerator {

    	private final double targetEntropy;
    	private final Random rnd = new Random();

    	private int lastDisplaced = -1; //how many elems were changed in the last list
    	private double lastEntropy = -1; //entropy val of list - NOT EQUAL WITH TARGET ENTROPY !!!

		/**
     	 * Creates an entropy-based generator.
     	 *
     	 * @param targetEntropy desired entropy value in [0,1]
     	 * @param size size of the list to generate
     	 * @throws IllegalArgumentException if targetEntropy is outside [0,1]
     	*/

    	public EntropyGenerator(double targetEntropy, int size) {
        	super(size);

        	if (targetEntropy < 0.0 || targetEntropy > 1.0) {
            		throw new IllegalArgumentException("targetEntropy must be in [0,1]");
        	}

        	this.targetEntropy = targetEntropy;
        	init();
    	}	

		/**
     	 * Returns how many elements were displaced in the last generated list.
     	 *
     	 * @return number of displaced elements
     	*/

    	public int getLastDisplaced() {
        	return lastDisplaced;
    	}

		/**
     	 * Returns the actual entropy of the last generated list.
     	 *
     	 * @return entropy value of the generated list
     	*/

    	public double getLastEntropy() {
        	return lastEntropy;
    	}

		/**
     	 * Generates a list starting from sorted order, then applies
     	 * a cyclic displacement on randomly chosen indices.
     	 *
     	 * @return a list with a disorder level close to the target entropy
     	*/

    	@Override
    	protected List<Integer> generate() {

        	int n = getSize();

        	//ordered list
        	List<Integer> list = new ArrayList<>(n);
        	for (int i = 0; i < n; i++) {
            		list.add(i);
        	}

        	if (n <= 1) {
            		lastDisplaced = 0;
            		lastEntropy = 0.0;
            		return list;
        	}

        	int d = bestDisplacedCount(n, targetEntropy);

        	lastDisplaced = d;
        	lastEntropy = shannon((double) d / n); // ratio of displaced elements

        	if (d == 0) {
            		return list;
        	}

        	//indx list todecide randomly which indexes will get changed
        	
        	List<Integer> indices = new ArrayList<>();
        	for (int i = 0; i < n; i++) {
            		indices.add(i);
        	}

        	Collections.shuffle(indices, rnd);
		//select first d elemts to change their indexes
        	List<Integer> disp = indices.subList(0, d);
		
        	//cycle thorugh the index
        	int firstValue = list.get(disp.get(0));

        	for (int i = 0; i < d - 1; i++) {
            		int to = disp.get(i);
            		int from = disp.get(i + 1);
            		list.set(to, list.get(from));
        	}

        	list.set(disp.get(d - 1), firstValue); //to complete the cycle
        	/*e.g 
        		list = [0,1,2,3,4,5]
        		disp = [4, 1, 5]
        		list[4] = 1
			list[1] = 5
			
			last change : firstElem=4 with 5
			
			list[4] = 1
			list[1] = 5
			list[5] = 4
        	*/

        	return list;
    	}
		
		/**
     	 * Finds the number of displaced elements that best matches
     	 * the requested entropy.
     	 *
     	 * @param n size of the list
     	 * @param target target entropy
     	 * @return best displacement count for the given target
     	*/

    	private int bestDisplacedCount(int n, double target) {

        	int bestD = 0;
        	double bestDiff = Double.POSITIVE_INFINITY;

        	for (int d = 0; d <= n/2; d++) {

		    	if (d == 1) continue; // 1 displaced impossible - at least 2

		    	double p = (double) d / n; //again, ratio of displaced elements
		    	double h = shannon(p);

		    	double diff = Math.abs(h - target);

		    	if (diff < bestDiff) {
		        	bestDiff = diff;
		        	bestD = d;
		    	}
        	}
        	
        	if (target > 0.5) {
        		return n - bestD; //symmetry
    		}
        	return bestD;
    	}

		/**
     	 * Computes the binary Shannon entropy for a proportion p.
     	 *
     	 * @param p proportion of displaced elements
     	 * @return binary Shannon entropy
     	*/

    	protected double shannon(double p) {
        	double q = 1.0 - p;
        	double h = 0.0;

		if (p > 0.0) {
		    	h -= p * (Math.log(p) / Math.log(2));
		}
		if (q > 0.0) {
		    	h -= q * (Math.log(q) / Math.log(2));
		}
        	return h;
    	}
}

