package generator;

import java.util.*;

public class Demo {

    private static void printEntropyList(String title, double target, EntropyGenerator g) {
        System.out.println("==============================================");
        System.out.println(title);
        System.out.println("----------------------------------------------");
        System.out.println("target entropy : " + target);
        System.out.println("actual entropy : " + g.getLastEntropy());
        System.out.println("displaced      : " + g.getLastDisplaced());
        System.out.println("displaced ratio: " + ((double) g.getLastDisplaced() / g.getSize()));
        System.out.println("----------------------------------------------");
        System.out.println(g.getList());
        System.out.println("==============================================");
        System.out.println();
    }

    public static void main(String[] args) {

        int n = 100;

        EntropyGenerator first  = new EntropyGenerator(0.75, n);
        /*EntropyGenerator second  = new EntropyGenerator(0.25, n);
        EntropyGenerator third = new EntropyGenerator(0.75, n);
        EntropyGenerator fourth = new EntropyGenerator(0.5, n);

        SortedGenerator sorted = new SortedGenerator(10);*/

        printEntropyList("1 ENTROPY LIST", 0.75, first);
        /*printEntropyList("ENTROPY LIST", 0.25, second);
        printEntropyList("ENTROPY LIST", 0.75, third);
        printEntropyList("ENTROPY LIST", 0.5, fourth);
	System.out.println("H(0.3) = " + first.shannon(0.3));
	System.out.println("H(0.7) = " + first.shannon(0.7));*/
	
        /*System.out.println("==============================================");
        System.out.println("SORTED LIST");
        System.out.println("----------------------------------------------");
        System.out.println(sorted.getList());
        System.out.println("==============================================");*/
    }
}

