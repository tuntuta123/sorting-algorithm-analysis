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

        EntropyGenerator first  = new EntropyGenerator(1.0, n);
        EntropyGenerator second  = new EntropyGenerator(0.51, n);
        EntropyGenerator third = new EntropyGenerator(0.1, n);
        EntropyGenerator fourth = new EntropyGenerator(0, n);

        SortedGenerator sorted = new SortedGenerator(10);

        printEntropyList("1 ENTROPY LIST", 1.0, first);
        printEntropyList("0.5 ENTROPY LIST", 0.51, second);
        printEntropyList("0.1 ENTROPY LIST", 0.1, third);
        printEntropyList("0 ENTROPY LIST", 0, fourth);

        System.out.println("==============================================");
        System.out.println("SORTED LIST");
        System.out.println("----------------------------------------------");
        System.out.println(sorted.getList());
        System.out.println("==============================================");
    }
}

