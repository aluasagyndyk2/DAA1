package mypackage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Arrays;

public class App {

    // ================== ВСПОМОГАТЕЛЬНЫЕ ФУНКЦИИ ==================

    private static int[] generateRandomArray(int n) {
        Random rand = new Random();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = rand.nextInt(1000000);
        }
        return a;
    }

    private static Point[] generateRandomPoints(int n) {
        Random rand = new Random();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
        }
        return points;
    }

    // ================== ЗАПУСК АЛГОРИТМОВ И ЗАПИСЬ ==================

    public static void main(String[] args) {
        System.out.println("Starting algorithm benchmarking...");
        String csvFileName = "measurements.csv";

        try (PrintWriter pw = new PrintWriter(new FileWriter(csvFileName))) {
            // Заголовок CSV-файла: соответствует требованиям метрик
            pw.println("Algorithm,N,Time_ns,Comparisons,MaxDepth,Allocations");

            // Задайте размеры N для тестирования
            int[] sizes = {100, 500, 1000, 5000, 10000, 50000, 100000, 500000, 1000000};

            for (int N : sizes) {
                System.out.println("Running N = " + N);

                runMergeSort(N, pw);
                runQuickSort(N, pw);
                runDeterministicSelect(N, pw);

                if (N <= 100000) { // Ограничим Closest Pair для скорости
                    runClosestPair(N, pw);
                }
            }

        } catch (IOException e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }

        System.out.println("Benchmarking complete. Results written to " + csvFileName);
    }

    // ================== МЕТОДЫ ЗАПУСКА ДЛЯ КАЖДОГО АЛГОРИТМА ==================

    private static void runMergeSort(int N, PrintWriter pw) {
        int[] data = generateRandomArray(N);
        Metrics metrics = new Metrics();
        MergeSort.sort(data.clone(), metrics);
        pw.printf("MergeSort,%d,%d,%d,%d,%d%n", N, metrics.getTimeNano(),
                metrics.getComparisons(), metrics.getMaxDepth(), metrics.getAllocations());
    }

    private static void runQuickSort(int N, PrintWriter pw) {
        int[] data = generateRandomArray(N);
        Metrics metrics = new Metrics();
        QuickSort.sort(data.clone(), metrics);
        pw.printf("QuickSort,%d,%d,%d,%d,%d%n", N, metrics.getTimeNano(),
                metrics.getComparisons(), metrics.getMaxDepth(), metrics.getAllocations());
    }

    private static void runDeterministicSelect(int N, PrintWriter pw) {
        int[] data = generateRandomArray(N);
        Metrics metrics = new Metrics();
        int k = N / 2;
        Select.deterministicSelect(data.clone(), k, metrics);
        pw.printf("Select(MoM),%d,%d,%d,%d,%d%n", N, metrics.getTimeNano(),
                metrics.getComparisons(), metrics.getMaxDepth(), metrics.getAllocations());
    }

    private static void runClosestPair(int N, PrintWriter pw) {
        Point[] data = generateRandomPoints(N);
        Metrics metrics = new Metrics();
        ClosestPair cp = new ClosestPair();
        cp.findClosestPair(data, metrics);
        pw.printf("ClosestPair,%d,%d,%d,%d,%d%n", N, metrics.getTimeNano(),
                metrics.getComparisons(), metrics.getMaxDepth(), metrics.getAllocations());
    }
}