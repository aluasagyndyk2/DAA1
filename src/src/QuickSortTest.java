import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.Random;

public class QuickSortTest {
    @Test
    void testQuickSortCorrectness() {
        int[] arr = new Random().ints(1000, -1000, 1000).toArray();
        int[] copy = arr.clone();

        QuickSort.sort(arr);
        Arrays.sort(copy);

        assertArrayEquals(copy, arr);
    }
}
