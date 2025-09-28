import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {
    public static double closestPair(double[][] points) {
        double[][] px = points.clone();
        double[][] py = points.clone();
        Arrays.sort(px, Comparator.comparingDouble(a -> a[0]));
        Arrays.sort(py, Comparator.comparingDouble(a -> a[1]));
        return closestPairRec(px, py);
    }

    private static double closestPairRec(double[][] px, double[][] py) {
        int n = px.length;
        if (n <= 3) return bruteForce(px);

        int mid = n / 2;
        double[][] Qx = Arrays.copyOfRange(px, 0, mid);
        double[][] Rx = Arrays.copyOfRange(px, mid, n);

        double midX = px[mid][0];

        double[][] Qy = Arrays.stream(py).filter(p -> p[0] <= midX).toArray(double[][]::new);
        double[][] Ry = Arrays.stream(py).filter(p -> p[0] > midX).toArray(double[][]::new);

        double d1 = closestPairRec(Qx, Qy);
        double d2 = closestPairRec(Rx, Ry);
        double d = Math.min(d1, d2);

        return Math.min(d, stripClosest(py, midX, d));
    }

    private static double stripClosest(double[][] py, double midX, double d) {
        double min = d;
        int n = py.length;
        for (int i = 0; i < n; i++) {
            if (Math.abs(py[i][0] - midX) < d) {
                for (int j = i + 1; j < n && (py[j][1] - py[i][1]) < d; j++) {
                    double dist = distance(py[i], py[j]);
                    min = Math.min(min, dist);
                }
            }
        }
        return min;
    }

    private static double bruteForce(double[][] points) {
        double min = Double.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                min = Math.min(min, distance(points[i], points[j]));
            }
        }
        return min;
    }

    private static double distance(double[] p1, double[] p2) {
        double dx = p1[0] - p2[0];
        double dy = p1[1] - p2[1];
        return Math.sqrt(dx * dx + dy * dy);
    }
}
