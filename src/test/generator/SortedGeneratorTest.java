package test.generator;

import generator.SortedGenerator;
import org.junit.jupiter.api.*;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class SortedGeneratorTest {

    @Test
    void list_hasCorrectSize() {
        SortedGenerator g = new SortedGenerator(100);
        assertEquals(100, g.getList().size());
    }

    @Test
    void list_sizeZero_isEmpty() {
        SortedGenerator g = new SortedGenerator(0);
        assertTrue(g.getList().isEmpty());
    }

    @Test
    void list_sizeOne_hasSingleElement() {
        SortedGenerator g = new SortedGenerator(1);
        assertEquals(1, g.getList().size());
    }

    @Test
    void list_firstElementIsZero() {
        SortedGenerator g = new SortedGenerator(10);
        assertEquals(0, g.getList().get(0));
    }

    @Test
    void list_lastElementIsSizeMinusOne() {
        SortedGenerator g = new SortedGenerator(100);
        assertEquals(99, g.getList().get(99));
    }

    @Test
    void list_containsAllExpectedValues() {
        int size = 10;
        SortedGenerator g = new SortedGenerator(size);
        List<Integer> list = g.getList();
        for (int i = 0; i < size; i++) {
            assertTrue(list.contains(i), "Missing value: " + i);
        }
    }

    @Test
    void list_isSorted() {
        SortedGenerator g = new SortedGenerator(10);
        assertTrue(g.isSorted());
    }

    @Test
    void list_isSorted_largeSize() {
        SortedGenerator g = new SortedGenerator(100);
        assertTrue(g.isSorted());
    }
    @Test
    void negativeSize_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new SortedGenerator(-1));
    }
}
