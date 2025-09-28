package kz.astana.algos.util;

public class Metrics {
    public static long comparisons = 0;      // қанша рет салыстыру жасалды
    public static int currentDepth = 0;      // ағымдағы рекурсия тереңдігі
    public static int maxDepth = 0;          // максималды рекурсия тереңдігі

    public static void reset() {
        comparisons = 0;
        currentDepth = 0;
        maxDepth = 0;
    }

    public static void incComparison() {
        comparisons++;
    }

    public static void enterRecursion() {
        currentDepth++;
        if (currentDepth > maxDepth) {
            maxDepth = currentDepth;
        }
    }

    public static void exitRecursion() {
        currentDepth--;
    }
}
