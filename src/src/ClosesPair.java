package kz.astana.algos;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {

    // Нүктені көрсету үшін ішкі класс
    static class Point {
        double x, y;
        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    // Екі нүкте арасындағы қашықтық
    private static double dist(Point p1, Point p2) {
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) +
                (p1.y - p2.y) * (p1.y - p2.y));
    }

    // Brute Force кіші массивке
    private static double bruteForce(Point[] pts, int left, int right) {
        double min = Double.MAX_VALUE;
        for (int i = left; i < right; i++) {
            for (int j = i + 1; j <= right; j++) {
                min = Math.min(min, dist(pts[i], pts[j]));
            }
        }
        return min;
    }

    private static double stripClosest(Point[] strip, int size, double d) {
        Arrays.sort(strip, 0, size, Comparator.comparingDouble(p -> p.y));
        double min = d;

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size && (strip[j].y - strip[i].y) < min; j++) {
                min = Math.min(min, dist(strip[i], strip[j]));
            }
        }
        return min;
    }

    private static double closestUtil(Point[] pts, int left, int right) {
        if (right - left <= 3) {
            return bruteForce(pts, left, right);
        }

        int mid = (left + right) / 2;
        Point midPoint = pts[mid];

        double dl = closestUtil(pts, left, mid);
        double dr = closestUtil(pts, mid + 1, right);

        double d = Math.min(dl, dr);

        Point[] strip = new Point[right - left + 1];
        int j = 0;
        for (int i = left; i <= right; i++) {
            if (Math.abs(pts[i].x - midPoint.x) < d) {
                strip[j++] = pts[i];
            }
        }

        return Math.min(d, stripClosest(strip, j, d));
    }

    public static double closest(Point[] pts) {
        Arrays.sort(pts, Comparator.comparingDouble(p -> p.x));
        return closestUtil(pts, 0, pts.length - 1);
    }

    public static void main(String[] args) {
        Point[] points = {
                new Point(2, 3),
                new Point(12, 30),
                new Point(40, 50),
                new Point(5, 1),
                new Point(12, 10),
                new Point(3, 4)
        };

        double minDist = closest(points);
        System.out.printf("The smallest distance is %.4f\n", minDist);
    }
}
