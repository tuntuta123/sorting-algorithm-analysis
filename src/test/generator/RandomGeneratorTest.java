package test.generator;

import generator.RandomGenerator;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class RandomGeneratorTest {

    @Test
    void list_hasCorrectSize() {
        RandomGenerator g = new RandomGenerator(100);
        assertEquals(100, g.getList().size());
    }

    @Test
    void list_sizeZero_isEmpty() {
        RandomGenerator g = new RandomGenerator(0);
        assertTrue(g.getList().isEmpty());
    }

    @Test
    void list_sizeOne_hasSingleElement() {
        RandomGenerator g = new RandomGenerator(1);
        assertEquals(1, g.getList().size());
    }
    
    @Test
    void list_containsAllExpectedValues() {
        int size = 10;
        RandomGenerator g = new RandomGenerator(size);
        List<Integer> list = g.getList();
        for (int i = 0; i < size; i++) {
            assertTrue(list.contains(i), "Missing value: " + i);
        }
    }

    @Test
    void list_hasNoDuplicates() {
        int size = 20;
        RandomGenerator g = new RandomGenerator(size);
        List<Integer> list = g.getList();
        long distinct = list.stream().distinct().count();
        assertEquals(size, distinct);
    }

    @Test
    void list_containsAllExpectedValues_largeSize() {
        int size = 100;
        RandomGenerator g = new RandomGenerator(size);
        List<Integer> list = g.getList();
        for (int i = 0; i < size; i++) {
            assertTrue(list.contains(i), "Missing value: " + i);
        }
    }

    @Test
    void list_isNotAlwaysSorted() {
        boolean foundUnsorted = false;
        for (int attempt = 0; attempt < 10; attempt++) {
            RandomGenerator g = new RandomGenerator(20);
            if (!g.isSorted()) {
                foundUnsorted = true;
                break;
            }
        }
        assertTrue(foundUnsorted, "RandomGenerator produced sorted list 10 times in a row");
    }

    @Test
    void list_producesDifferentResultsAcrossInstances() {
        boolean foundDifference = false;
        for (int attempt = 0; attempt < 10; attempt++) {
            RandomGenerator g1 = new RandomGenerator(20);
            RandomGenerator g2 = new RandomGenerator(20);
            if (!g1.getList().equals(g2.getList())) {
                foundDifference = true;
                break;
            }
        }
        assertTrue(foundDifference, "RandomGenerator produced identical lists 10 times in a row");
    }
    
    @Test
    void negativeSize_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new RandomGenerator(-1));
    }
}
