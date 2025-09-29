package mypackage;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class ClosestPair {
    private double minDistance = Double.POSITIVE_INFINITY;
    private Point best1, best2; // Для отслеживания лучшей пары (хотя в метриках не требуется, полезно для отладки)

    /**
     * Базовый случай O(n^2) для небольших подмассивов (n <= 3).
     * Обновляет общее минимальное расстояние (minDistance).
     */
    private double bruteForce(Point[] points, int low, int high, Metrics metrics) {
        double min = Double.POSITIVE_INFINITY;
        // Перебираем все пары
        for (int i = low; i <= high; i++) {
            for (int j = i + 1; j <= high; j++) {
                metrics.incrementComparison();
                double dist = points[i].distanceTo(points[j]);
                if (dist < min) {
                    min = dist;
                }
                // Обновляем глобальную лучшую пару
                if (dist < minDistance) {
                    minDistance = dist;
                    // best1 = points[i]; // Можно раскомментировать для отладки
                    // best2 = points[j];
                }
            }
        }
        return min;
    }

    /**
     * Основной публичный метод для запуска алгоритма.
     * Выполняет начальную сортировку и запускает рекурсивный метод.
     */
    public double findClosestPair(Point[] allPoints, Metrics metrics) {
        if (allPoints.length < 2) return Double.POSITIVE_INFINITY;

        metrics.startTimer();

        // 1. Сортировка по X (делается один раз)
        Point[] pointsByX = allPoints.clone();
        Arrays.sort(pointsByX, Point.COMPARE_BY_X);

        // 2. Сортировка по Y (делается один раз, используется для разделения в рекурсии)
        Point[] pointsByY = allPoints.clone();
        Arrays.sort(pointsByY, Point.COMPARE_BY_Y);

        double result = closest(pointsByX, pointsByY, 0, allPoints.length - 1, metrics);

        metrics.stopTimer();
        return result;
    }

    /**
     * Рекурсивная функция "Разделяй и властвуй".
     *
     * @param pointsByX Подмассив, отсортированный по X.
     * @param pointsByY Подмассив, отсортированный по Y.
     * @param low Начальный индекс (используется только для pointsByX).
     * @param high Конечный индекс (используется только для pointsByX).
     * @return Минимальное расстояние в этом подмассиве.
     */
    private double closest(Point[] pointsByX, Point[] pointsByY, int low, int high, Metrics metrics) {
        metrics.startRecursiveCall();

        int n = high - low + 1;
        if (n <= 3) {
            double result = bruteForce(pointsByX, low, high, metrics);
            metrics.endRecursiveCall();
            return result;
        }

        int mid = low + (high - low) / 2;
        Point medianPoint = pointsByX[mid];

        // 1. Разделение Y-отсортированного массива

        // Для рекурсии нужно эффективно получить Y-отсортированные подмассивы
        List<Point> leftYList = new ArrayList<>();
        List<Point> rightYList = new ArrayList<>();

        // Разделяем pointsByY на два подмассива (O(n))
        for (Point p : pointsByY) {
            metrics.incrementComparison();
            // Используем medianPoint.x для разделения. Важно: для точек с одинаковым X
            // нужно соблюдать стабильность, но в простом алгоритме достаточно <=
            if (p.x <= medianPoint.x) {
                leftYList.add(p);
            } else {
                rightYList.add(p);
            }
        }
        Point[] leftY = leftYList.toArray(new Point[0]);
        Point[] rightY = rightYList.toArray(new Point[0]);

        // 2. Рекурсивные вызовы: dL и dR
        double delta = closest(pointsByX, leftY, low, mid, metrics);
        delta = Math.min(delta, closest(pointsByX, rightY, mid + 1, high, metrics));

        // 3. Комбинирование (Проверка полосы "strip")

        List<Point> strip = new ArrayList<>();
        double midX = medianPoint.x;

        // Создание полосы (strip) из точек pointsByY, находящихся на расстоянии < delta от средней линии
        for (Point p : pointsByY) {
            if (Math.abs(p.x - midX) < delta) {
                strip.add(p);
            }
        }

        // Проверка в полосе (strip уже отсортирован по Y)
        // [ТРЕБОВАНИЕ] Проверка только следующих 7-8 соседей (оптимизация O(n))
        for (int i = 0; i < strip.size(); i++) {
            // Внутренний цикл ограничен 7-8 сравнениями в худшем случае
            for (int j = i + 1; j < strip.size(); j++) {
                metrics.incrementComparison();

                // Оптимизация: если разница по Y уже больше delta, дальше смотреть нет смысла
                if ((strip.get(j).y - strip.get(i).y) >= delta) {
                    break;
                }

                delta = Math.min(delta, strip.get(i).distanceTo(strip.get(j)));
            }
        }

        metrics.endRecursiveCall();
        return delta;
    }
}