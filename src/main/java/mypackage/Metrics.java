package mypackage;

/**
 * Класс для сбора метрик: сравнения, аллокации и глубина рекурсии.
 */
public class Metrics {
    private long comparisons = 0;
    private long allocations = 0;
    private int maxRecursionDepth = 0;
    private int currentDepth = 0;
    private long startTimeNano = 0;
    private long endTimeNano = 0;

    public void startTimer() {
        startTimeNano = System.nanoTime();
    }

    public void stopTimer() {
        endTimeNano = System.nanoTime();
    }

    public long getTimeNano() {
        if (endTimeNano == 0) return 0;
        return endTimeNano - startTimeNano;
    }

    public void incrementComparison() {
        comparisons++;
    }

    public void incrementAllocation() {
        allocations++; // Используется для MergeSort буфера
    }

    public void startRecursiveCall() {
        currentDepth++;
        if (currentDepth > maxRecursionDepth) {
            maxRecursionDepth = currentDepth;
        }
    }

    public void endRecursiveCall() {
        currentDepth--;
    }

    public long getComparisons() {
        return comparisons;
    }

    public int getMaxDepth() {
        return maxRecursionDepth;
    }

    public long getAllocations() {
        return allocations;
    }

    @Override
    public String toString() {
        return String.format("Time(ns): %d, Comparisons: %d, MaxDepth: %d, Allocations: %d",
                getTimeNano(), comparisons, maxRecursionDepth, allocations);
    }
}