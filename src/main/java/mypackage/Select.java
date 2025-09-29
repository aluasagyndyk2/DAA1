package mypackage;

import java.util.Arrays;
import java.util.Random;

public class Select {

    // ИСПРАВЛЕННЫЙ: Добавлен static
    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // ИСПРАВЛЕННЫЙ: Добавлен static
    private static int findMedianOfFive(int[] a, int low, int high, Metrics metrics) {
        // Простой Insertion Sort для 5 элементов
        for (int i = low + 1; i <= high; i++) {
            int current = a[i];
            int j = i - 1;
            while (j >= low) {
                metrics.incrementComparison();
                if (a[j] > current) {
                    a[j + 1] = a[j];
                    j--;
                } else {
                    break;
                }
            }
            a[j + 1] = current;
        }
        return a[low + (high - low) / 2]; // Возвращаем медиану
    }

    // ИСПРАВЛЕННЫЙ: Добавлен static
    private static int partitionAround(int[] a, int low, int high, int pivotValue, Metrics metrics) {
        // 1. Находим пивот в массиве и перемещаем его в конец
        int pivotIndex = -1;
        for (int i = low; i <= high; i++) {
            if (a[i] == pivotValue) {
                pivotIndex = i;
                break;
            }
        }
        swap(a, pivotIndex, high);

        // 2. Стандартное разбиение (Hoare's или Lomuto's)
        int i = low;
        for (int j = low; j < high; j++) {
            metrics.incrementComparison();
            if (a[j] <= pivotValue) {
                swap(a, i, j);
                i++;
            }
        }
        swap(a, i, high); // Возвращаем пивот на место
        return i;
    }

    public static int deterministicSelect(int[] a, int k, Metrics metrics) {
        if (k < 1 || k > a.length) throw new IllegalArgumentException("k is out of range");
        metrics.startTimer();
        int result = selectRecursive(a, 0, a.length - 1, k - 1, metrics);
        metrics.stopTimer();
        return result;
    }

    private static int selectRecursive(int[] a, int low, int high, int kIndex, Metrics metrics) {
        metrics.startRecursiveCall();

        if (low == high) {
            metrics.endRecursiveCall();
            return a[low];
        }

        // 1. Группировка по 5 и нахождение медиан
        int n = high - low + 1;
        int numGroups = (n + 4) / 5;
        int[] medians = new int[numGroups];

        for (int i = 0; i < numGroups; i++) {
            int groupLow = low + i * 5;
            int groupHigh = Math.min(groupLow + 4, high);
            medians[i] = findMedianOfFive(a, groupLow, groupHigh, metrics);
        }

        // 2. Рекурсивное нахождение Медианы Медиан (MoM)
        int mom;
        if (numGroups == 1) {
            mom = medians[0];
        } else {
            // Рекурсия на массиве медиан
            mom = selectRecursive(medians, 0, numGroups - 1, numGroups / 2, metrics);
        }

        // 3. Разбиение массива с помощью MoM
        int p = partitionAround(a, low, high, mom, metrics);

        // 4. [ТРЕБОВАНИЕ] Рекурсия только на нужной стороне
        if (kIndex == p) {
            metrics.endRecursiveCall();
            return a[p];
        } else if (kIndex < p) {
            int result = selectRecursive(a, low, p - 1, kIndex, metrics);
            metrics.endRecursiveCall();
            return result;
        } else {
            int result = selectRecursive(a, p + 1, high, kIndex, metrics);
            metrics.endRecursiveCall();
            return result;
        }
    }
}