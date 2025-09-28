public class Main {
    public static void main(String[] args) {
        int[] arr = {5, 3, 8, 4, 2};

        // MergeSort
        MergeSort.sort(arr.clone());

        // QuickSort
        QuickSort.sort(arr.clone());

        // Select (k-th element)
        int val = DeterministicSelect.select(arr.clone(), 2);

        // ClosestPair
        double[][] points = {{0,0}, {1,1}, {2,2}, {5,5}};
        double d = ClosestPair.closestPair(points);

        System.out.println("Select result = " + val);
        System.out.println("Closest Pair distance = " + d);
    }
}
