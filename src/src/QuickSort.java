package kz.astana.algos;

import java.util.Random;

public class QuickSort {
    private static final Random rand = new Random();

    public static void sort(int[] arr) {
        quicksort(arr, 0, arr.length - 1);
    }

    private static void quicksort(int[] arr, int left, int right) {
        while (left < right) {
            // random pivot таңдау
            int pivotIndex = left + rand.nextInt(right - left + 1);
            int pivot = arr[pivotIndex];
            int i = left, j = right;

            // partition
            while (i <= j) {
                while (arr[i] < pivot) i++;
                while (arr[j] > pivot) j--;
                if (i <= j) {
                    swap(arr, i, j);
                    i++;
                    j--;
                }
            }

            if ((j - left) < (right - i)) {
                if (left < j) {
                    quicksort(arr, left, j);
                }
                left = i;
            } else {
                if (i < right) {
                    quicksort(arr, i, right);
                }
                right = j;
            }
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {9, 4, 7, 3, 1, 8, 2, 6, 5};
        QuickSort.sort(arr);
        for (int x : arr) {
            System.out.print(x + " ");
        }
    }
}
