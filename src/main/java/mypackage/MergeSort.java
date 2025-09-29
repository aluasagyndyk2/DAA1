package mypackage;

import java.util.Arrays;

public class MergeSort {

    private static final int CUTOFF = 7; // Cutoff для Insertion Sort

    private static void insertionSort(int[] a, int low, int high, Metrics metrics) {
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
    }

    private static void merge(int[] a, int[] aux, int low, int mid, int high, Metrics metrics) {
        // Копирование в вспомогательный массив
        for (int k = low; k <= high; k++) {
            aux[k] = a[k];
        }

        int i = low;
        int j = mid + 1;

        // Слияние обратно в a[low..high]
        for (int k = low; k <= high; k++) {
            if (i > mid) { // Левая сторона исчерпана
                a[k] = aux[j++];
            } else if (j > high) { // Правая сторона исчерпана
                a[k] = aux[i++];
            } else {
                metrics.incrementComparison(); // Сравнение
                if (aux[i] <= aux[j]) {
                    a[k] = aux[i++];
                } else {
                    a[k] = aux[j++];
                }
            }
        }
    }

    public static void sort(int[] a, Metrics metrics) {
        metrics.startTimer();
        // [ТРЕБОВАНИЕ] Выделение буфера ОДИН РАЗ
        int[] aux = new int[a.length];
        metrics.incrementAllocation();
        sortRecursive(a, aux, 0, a.length - 1, metrics);
        metrics.stopTimer();
    }

    private static void sortRecursive(int[] a, int[] aux, int low, int high, Metrics metrics) {
        metrics.startRecursiveCall();

        if (high - low + 1 <= CUTOFF) {
            insertionSort(a, low, high, metrics);
            metrics.endRecursiveCall();
            return;
        }

        if (low < high) {
            int mid = low + (high - low) / 2;

            sortRecursive(a, aux, low, mid, metrics);
            sortRecursive(a, aux, mid + 1, high, metrics);

            // Если массив уже отсортирован, можно пропустить слияние (оптимизация)
            metrics.incrementComparison();
            if (a[mid] > a[mid + 1]) {
                merge(a, aux, low, mid, high, metrics);
            }
        }

        metrics.endRecursiveCall();
    }
}