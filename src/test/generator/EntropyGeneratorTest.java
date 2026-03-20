package test.generator;

import generator.EntropyGenerator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EntropyGeneratorTest {

    	@Test
    	void list_hasCorrectSize() {
        	EntropyGenerator g = new EntropyGenerator(0.5, 100);
        	assertEquals(100, g.getList().size());
    	}

    	@Test
    	void list_sizeZero_isEmpty() {
        	EntropyGenerator g = new EntropyGenerator(0.5, 0);
        	assertTrue(g.getList().isEmpty());
    	}

    	@Test
    	void list_containsAllExpectedValues() {
        	int size = 20;
        	EntropyGenerator g = new EntropyGenerator(0.4, size);
        	List<Integer> list = g.getList();

        	for (int i = 0; i < size; i++) {
            		assertTrue(list.contains(i), "missing value: " + i);
        	}
    	}

    	@Test
    	void list_hasNoDuplicates() {
        	int size = 50;
        	EntropyGenerator g = new EntropyGenerator(0.7, size);
        	List<Integer> list = g.getList();

        	long distinct = list.stream().distinct().count();
        	assertEquals(size, distinct);
    	}

    	@Test
    	void targetEntropyZero_producesSortedList() {
        	int size = 20;
        	EntropyGenerator g = new EntropyGenerator(0.0, size);

        	assertTrue(g.isSorted(), "list isnt sorted for entropy=0");
        	assertEquals(0, g.getLastDisplaced());
    	}

    	@Test
    	void negativeEntropy_throwsIllegalArgumentException() {
        	assertThrows(IllegalArgumentException.class, () -> new EntropyGenerator(-0.1, 10));
    	}

    	@Test
    	void entropyGreaterThanOne_throwsIllegalArgumentException() {
        	assertThrows(IllegalArgumentException.class, () -> new EntropyGenerator(1.1, 10));
    	}


    	@Test
    	void actualNumberOfDisplacedElements_matchesLastDisplaced() {
        	int size = 50;
        	EntropyGenerator g = new EntropyGenerator(0.8, size);
        	List<Integer> list = g.getList();

        	int actualDisplaced = 0;
        	for (int i = 0; i < size; i++) {
            	if (list.get(i) != i) {
                	actualDisplaced++;
            	}
        	}

        	assertEquals(actualDisplaced, g.getLastDisplaced(), "lastDisplaced should match the number of displaced elems");
    	}


    	@Test
    	void lastEntropy_isCloseToTargetEntropy() {
	    	int size = 100;
	    	double target = 0.3;
	    	EntropyGenerator g = new EntropyGenerator(target, size);

	    	assertEquals(target, g.getLastEntropy(), 0.05);
	}
}
