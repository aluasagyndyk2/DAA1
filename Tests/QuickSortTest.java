package tests;

import mypackage.QuickSort;
import mypackage.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class QuickSortTest {

    private int[] generateRandomArray(int n) {
        Random rand = new Random();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = rand.nextInt(1000000);
        }
        return a;
    }

    @Test
    void testCorrectnessOnRandomData() {
        final int N = 1000;
        int[] input = generateRandomArray(N);
        int[] expected = input.clone();
        Arrays.sort(expected);

        QuickSort.sort(input, new Metrics());

        assertArrayEquals(expected, input, "QuickSort failed on random data.");
    }

    @Test
    void testBoundedRecursionDepth() {
        // [ТРЕБОВАНИЕ] Проверка ограниченной глубины стека O(log n) даже в худших случаях.
        final int N = 100000;

        // Худший случай для обычного QS: уже отсортированный массив
        int[] sorted = new int[N];
        for(int i=0; i<N; i++) sorted[i] = i;
        Metrics worstCaseMetrics = new Metrics();

        QuickSort.sort(sorted, worstCaseMetrics);

        // Максимальная ожидаемая глубина для O(log n) стека (для N=100000 это ~17)
        double logN = Math.log(N) / Math.log(2);
        double maxExpectedDepth = 2 * logN; // Даем запас, т.к. пивот рандомизирован

        assertTrue(worstCaseMetrics.getMaxDepth() < maxExpectedDepth,
                "QuickSort stack depth is too high. Max depth: " + worstCaseMetrics.getMaxDepth() +
                        ", Expected Max: " + (int)maxExpectedDepth);

        assertArrayEquals(new int[]{0, N-1}, new int[]{sorted[0], sorted[N-1]}, "Sorted array integrity check failed.");
    }
}