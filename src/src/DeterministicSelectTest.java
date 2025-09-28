import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Random;

public class DeterministicSelectTest {
    @Test
    void testSelectCorrectness() {
        Random rnd = new Random();
        int[] arr = rnd.ints(200, -1000, 1000).toArray();
        int[] copy = arr.clone();
        Arrays.sort(copy);

        for (int k = 0; k < arr.length; k++) {
            int val = DeterministicSelect.select(arr.clone(), k);
            assertEquals(copy[k], val);
        }
    }
}
