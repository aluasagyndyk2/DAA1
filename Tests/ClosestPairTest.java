package tests;

import mypackage.ClosestPair;
import mypackage.Metrics;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClosestPairTest {

    @Test
    void testSimpleCase() {
        // Ближайшая пара: (1, 1) и (2, 1) -> расстояние 1.0
        Point[] input = {
                new Point(10, 10),
                new Point(1, 1),
                new Point(2, 1),
                new Point(5, 5)
        };

        ClosestPair cp = new ClosestPair();
        double minDistance = cp.findClosestPair(input, new Metrics());

        assertEquals(1.0, minDistance, 1e-9, "The closest distance should be 1.0");
    }

    @Test
    void testDiagonalCase() {
        // Ближайшая пара: (0, 0) и (1, 1) -> расстояние sqrt(2) ≈ 1.414
        Point[] input = {
                new Point(100, 100),
                new Point(0, 0),
                new Point(1, 1),
                new Point(5, 0)
        };

        ClosestPair cp = new ClosestPair();
        double minDistance = cp.findClosestPair(input, new Metrics());

        assertEquals(Math.sqrt(2.0), minDistance, 1e-9, "The closest distance should be sqrt(2)");
    }

    @Test
    void testThreePoints() {
        // Тест для проверки базового случая (brute force)
        Point[] input = {
                new Point(0, 0),
                new Point(3, 4), // dist 5.0
                new Point(1, 0)  // dist 1.0
        };

        ClosestPair cp = new ClosestPair();
        double minDistance = cp.findClosestPair(input, new Metrics());

        assertEquals(1.0, minDistance, 1e-9, "Brute force case failed. Distance should be 1.0");
    }
}