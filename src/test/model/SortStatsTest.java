package test.model;

import model.SortStats;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class SortStatsTest {

    private SortStats stats;

    @BeforeEach
    void setup() {
        stats = new SortStats("TestAlgorithm", "TestGenerator");
        stats.start();
    }

    @Test
    void start_resetsComparisons() {
        stats.incrementComparisons();
        stats.start();
        assertEquals(0, stats.getComparisons());
    }

    @Test
    void start_resetsSwaps() {
        stats.incrementSwaps();
        stats.start();
        assertEquals(0, stats.getSwaps());
    }

    @Test
    void start_resetsAccesses() {
        stats.incrementAccesses();
        stats.start();
        assertEquals(0, stats.getAccesses());
    }

    @Test
    void start_resetsComparisonTimeSeries() {
        stats.incrementComparisons();
        stats.start();
        assertTrue(stats.getComparisonTimes().isEmpty());
        assertTrue(stats.getComparisonValues().isEmpty());
    }

    @Test
    void start_resetsSwapTimeSeries() {
        stats.incrementSwaps();
        stats.start();
        assertTrue(stats.getSwapTimes().isEmpty());
        assertTrue(stats.getSwapValues().isEmpty());
    }

    @Test
    void incrementComparisons_increasesCount() {
        stats.incrementComparisons();
        stats.incrementComparisons();
        assertEquals(2, stats.getComparisons());
    }

    @Test
    void incrementSwaps_increasesCount() {
        stats.incrementSwaps();
        stats.incrementSwaps();
        stats.incrementSwaps();
        assertEquals(3, stats.getSwaps());
    }

    @Test
    void incrementAccesses_increasesCount() {
        stats.incrementAccesses();
        assertEquals(1, stats.getAccesses());
    }

    @Test
    void incrementComparisons_recordsTimeSeries() {
        stats.incrementComparisons();
        stats.incrementComparisons();
        assertEquals(2, stats.getComparisonTimes().size());
        assertEquals(2, stats.getComparisonValues().size());
    }

    @Test
    void incrementSwaps_recordsTimeSeries() {
        stats.incrementSwaps();
        stats.incrementSwaps();
        assertEquals(2, stats.getSwapTimes().size());
        assertEquals(2, stats.getSwapValues().size());
    }

    @Test
    void comparisonValues_areMonotonicallyIncreasing() {
        stats.incrementComparisons();
        stats.incrementComparisons();
        stats.incrementComparisons();
        var values = stats.getComparisonValues();
        for (int i = 1; i < values.size(); i++) {
            assertTrue(values.get(i) >= values.get(i - 1));
        }
    }

    @Test
    void swapValues_areMonotonicallyIncreasing() {
        stats.incrementSwaps();
        stats.incrementSwaps();
        stats.incrementSwaps();
        var values = stats.getSwapValues();
        for (int i = 1; i < values.size(); i++) {
            assertTrue(values.get(i) >= values.get(i - 1));
        }
    }

    @Test
    void elapsedMs_isPositiveAfterStop() throws InterruptedException {
        Thread.sleep(10);
        stats.stop();
        assertTrue(stats.getElapsedMs() > 0);
    }

    @Test
    void elapsedMs_isZeroBeforeStart() {
        SortStats fresh = new SortStats("A", "B");
        assertEquals(0, fresh.getElapsedMs());
    }

    @Test
    void isFinished_falseBeforeStop() {
        assertFalse(stats.isFinished());
    }

    @Test
    void isFinished_trueAfterStop() {
        stats.stop();
        assertTrue(stats.isFinished());
    }

    @Test
    void getAlgorithmName_returnsCorrectName() {
        assertEquals("TestAlgorithm", stats.getAlgorithmName());
    }

    @Test
    void getGeneratorLabel_returnsCorrectLabel() {
        assertEquals("TestGenerator", stats.getGeneratorLabel());
    }
}
