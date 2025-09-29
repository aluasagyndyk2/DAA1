package tests;

import mypackage.MergeSort;
import mypackage.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class MergeSortTest {

    private int[] generateRandomArray(int n) {
        Random rand = new Random();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = rand.nextInt(1000000);
        }
        return a;
    }

    @Test
    void testBasicSorting() {
        int[] input = {5, 2, 8, 1, 9, 4, 7, 3, 6};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Metrics metrics = new Metrics();

        MergeSort.sort(input, metrics);

        assertArrayEquals(expected, input, "Array should be correctly sorted.");
    }

    @Test
    void testCutoffLogic() {
        // Проверяем, что сортировка вставками на маленьких массивах работает (размер < CUTOFF=7)
        int[] input = {5, 2, 1, 4, 6, 3};
        int[] expected = {1, 2, 3, 4, 5, 6};
        Metrics metrics = new Metrics();

        MergeSort.sort(input, metrics);

        assertArrayEquals(expected, input, "Cutoff logic should correctly sort small arrays.");
    }

    @Test
    void testLargeRandomSetAndDepth() {
        final int N = 100000;
        int[] input = generateRandomArray(N);
        int[] expected = input.clone();
        Arrays.sort(expected);

        Metrics metrics = new Metrics();
        MergeSort.sort(input, metrics);

        assertArrayEquals(expected, input, "Large random set check failed.");

        // Проверка метрик: глубина рекурсии должна быть log2(N)
        double logN = Math.log(N) / Math.log(2);
        assertTrue(metrics.getMaxDepth() > logN - 5 && metrics.getMaxDepth() < logN + 5,
                "Max depth should be O(log n).");
    }
}