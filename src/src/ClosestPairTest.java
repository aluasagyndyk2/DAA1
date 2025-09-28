import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

public class ClosestPairTest {
    @Test
    void testClosestPairSmallN() {
        Random rnd = new Random();
        int n = 200;
        double[][] points = new double[n][2];
        for (int i = 0; i < n; i++) {
            points[i][0] = rnd.nextDouble() * 1000;
            points[i][1] = rnd.nextDouble() * 1000;
        }

        double fast = ClosestPair.closestPair(points);
        double brute = ClosestPair.bruteForce(points);

        assertEquals(brute, fast, 1e-9);
    }
}
