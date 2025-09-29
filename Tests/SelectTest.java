package tests;

import mypackage.Select;
import mypackage.Metrics;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;

public class SelectTest {

    private int[] generateRandomArray(int n) {
        Random rand = new Random();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = rand.nextInt(1000000);
        }
        return a;
    }

    @Test
    void testSelectAgainstArraysSort() {
        final int N = 1000;
        final int trials = 100; // [ТРЕБОВАНИЕ] 100 случайных испытаний

        for (int t = 0; t < trials; t++) {
            int[] input = generateRandomArray(N);
            Random rand = new Random();
            int k = rand.nextInt(N) + 1; // k-й наименьший элемент, k от 1 до N

            int[] expectedArray = input.clone();
            Arrays.sort(expectedArray);
            int expectedValue = expectedArray[k - 1]; // k-й наименьший элемент (индекс k-1)

            Metrics metrics = new Metrics();
            int selectResult = Select.deterministicSelect(input.clone(), k, metrics);

            assertEquals(expectedValue, selectResult, "Deterministic Select failed for k=" + k + " on trial " + t);
        }
    }

    @Test
    void testEdgeCases() {
        final int N = 10;
        int[] input = {5, 2, 8, 1, 9, 4, 7, 3, 6, 0};

        // k=1 (минимум)
        assertEquals(0, Select.deterministicSelect(input.clone(), 1, new Metrics()));

        // k=10 (максимум)
        assertEquals(9, Select.deterministicSelect(input.clone(), 10, new Metrics()));

        // k=5 (медиана)
        assertEquals(4, Select.deterministicSelect(input.clone(), 5, new Metrics()));
    }
}