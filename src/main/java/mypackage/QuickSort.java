package mypackage;

import java.util.Random;

public class QuickSort {

    private static void swap(int[] a, int i, int j) {
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static int partition(int[] a, int low, int high, Metrics metrics) {
        // [ТРЕБОВАНИЕ] Рандомизированный пивот
        Random rand = new Random();
        int pivotIndex = low + rand.nextInt(high - low + 1);
        swap(a, low, pivotIndex);

        int pivot = a[low];
        int i = low;
        for (int j = low + 1; j <= high; j++) {
            metrics.incrementComparison(); // Сравнение
            if (a[j] < pivot) {
                i++;
                swap(a, i, j);
            }
        }
        swap(a, low, i);
        return i; // Возвращаем индекс пивота
    }

    public static void sort(int[] a, Metrics metrics) {
        metrics.startTimer();
        sortIterative(a, 0, a.length - 1, metrics);
        metrics.stopTimer();
    }

    // [ТРЕБОВАНИЕ] Рекурсия на меньшей части, итерация на большей (Bounded Stack)
    private static void sortIterative(int[] a, int low, int high, Metrics metrics) {

        while (low < high) {
            metrics.startRecursiveCall();

            int p = partition(a, low, high, metrics);

            int leftSize = p - low;
            int rightSize = high - p;

            if (leftSize < rightSize) {
                // Рекурсия на меньшей (левой) части
                sortIterative(a, low, p - 1, metrics);
                // Итерация (продолжение цикла while) на большей (правой) части
                low = p + 1;
            } else {
                // Рекурсия на меньшей (правой) части
                sortIterative(a, p + 1, high, metrics);
                // Итерация (продолжение цикла while) на большей (левой) части
                high = p - 1;
            }
            metrics.endRecursiveCall();
        }
    }
}