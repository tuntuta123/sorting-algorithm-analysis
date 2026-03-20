package test.generator;

import generator.ReverseEntropyGenerator;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class ReverseEntropyGeneratorTest {

    @Test
    void list_hasCorrectSize() {
        ReverseEntropyGenerator g = new ReverseEntropyGenerator(0.5, 100);
        assertEquals(100, g.getList().size());
    }

    @Test
    void list_sizeZero_isEmpty() {
        ReverseEntropyGenerator g = new ReverseEntropyGenerator(0.5, 0);
        assertTrue(g.getList().isEmpty());
    }

    @Test
    void list_containsAllExpectedValues() {
        int size = 20;
        ReverseEntropyGenerator g = new ReverseEntropyGenerator(0.4, size);
        List<Integer> list = g.getList();
        for (int i = 0; i < size; i++) {
            assertTrue(list.contains(i), "missing value: " + i);
        }
    }

    @Test
    void list_hasNoDuplicates() {
        int size = 50;
        ReverseEntropyGenerator g = new ReverseEntropyGenerator(0.7, size);
        List<Integer> list = g.getList();
        long distinct = list.stream().distinct().count();
        assertEquals(size, distinct);
    }

    @Test
    void targetEntropyZero_producesReverseSortedList() {
        int size = 20;
        ReverseEntropyGenerator g = new ReverseEntropyGenerator(0.0, size);
        List<Integer> list = g.getList();
        for (int i = 0; i < size; i++) {
            assertEquals(size - 1 - i, list.get(i), "list should be reverse sorted for entropy=0");
        }
        assertEquals(0, g.getLastDisplaced());
    }

    @Test
    void negativeEntropy_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new ReverseEntropyGenerator(-0.1, 10));
    }

    @Test
    void entropyGreaterThanOne_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new ReverseEntropyGenerator(1.1, 10));
    }

    @Test
    void actualNumberOfDisplacedElements_matchesLastDisplaced() {
        int size = 50;
        ReverseEntropyGenerator g = new ReverseEntropyGenerator(0.8, size);
        List<Integer> list = g.getList();
        int actualDisplaced = 0;
        for (int i = 0; i < size; i++) {
            if (list.get(i) != size - 1 - i) {
                actualDisplaced++;
            }
        }
        assertEquals(actualDisplaced, g.getLastDisplaced(), "lastDisplaced should match the number of displaced elems");
    }

    @Test
    void lastEntropy_isCloseToTargetEntropy() {
        int size = 100;
        double target = 0.3;
        ReverseEntropyGenerator g = new ReverseEntropyGenerator(target, size);
        assertEquals(target, g.getLastEntropy(), 0.05);
    }
}

