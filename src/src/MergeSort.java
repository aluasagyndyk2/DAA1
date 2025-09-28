package org.yourname.algos.mergesort;

import java.util.Arrays;

public class MergeSort {
    private static final int CUTOFF = 32; // кіші массивке insertion sort

    public static void sort(int[] a) {
        int[] buffer = new int[a.length];
        sort(a, 0, a.length - 1, buffer);
    }

    private static void sort(int[] a, int l, int r, int[] buffer) {
        if (r - l + 1 <= CUTOFF) {
            insertionSort(a, l, r);
            return;
        }
        int mid = (l + r) >>> 1;
        sort(a, l, mid, buffer);
        sort(a, mid + 1, r, buffer);
        merge(a, l, mid, r, buffer);
    }

    private static void insertionSort(int[] a, int l, int r) {
        for (int i = l + 1; i <= r; i++) {
            int key = a[i];
            int j = i - 1;
            while (j >= l && a[j] > key) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = key;
        }
    }

    private static void merge(int[] a, int l, int mid, int r, int[] buffer) {
        int i = l, j = mid + 1, k = l;
        while (i <= mid && j <= r) {
            if (a[i] <= a[j]) buffer[k++] = a[i++];
            else buffer[k++] = a[j++];
        }
        while (i <= mid) buffer[k++] = a[i++];
        while (j <= r) buffer[k++] = a[j++];
        System.arraycopy(buffer, l, a, l, r - l + 1);
    }

    public static void main(String[] args) {
        int[] arr = {5, 2, 9, 1, 7, 3, 8, 6, 4};
        System.out.println("Before: " + Arrays.toString(arr));
        MergeSort.sort(arr);
        System.out.println("After:  " + Arrays.toString(arr));
    }
}
