package mypackage;

import java.util.Comparator;

public class Point {
    public final double x, y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static final Comparator<Point> COMPARE_BY_X = new CompareX();
    public static final Comparator<Point> COMPARE_BY_Y = new CompareY();

    private static class CompareX implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            if (p1.x < p2.x) return -1;
            if (p1.x > p2.x) return +1;
            return 0;
        }
    }

    private static class CompareY implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            if (p1.y < p2.y) return -1;
            if (p1.y > p2.y) return +1;
            return 0;
        }
    }

    public double distanceTo(Point that) {
        double dx = this.x - that.x;
        double dy = this.y - that.y;
        return Math.sqrt(dx*dx + dy*dy);
    }
}