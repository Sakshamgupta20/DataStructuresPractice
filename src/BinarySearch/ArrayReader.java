package BinarySearch;

public class ArrayReader {
    private int[] arr; // Array to be used

    // Constructor to initialize the array
    public ArrayReader(int[] arr) {
        this.arr = arr;
    }


    public int compareSub(int l, int r, int x, int y) {
        int sum1 = 0, sum2 = 0;

        // Calculate the sum of arr[l..r]
        for (int i = l; i <= r; i++) {
            sum1 += arr[i];
        }

        // Calculate the sum of arr[x..y]
        for (int i = x; i <= y; i++) {
            sum2 += arr[i];
        }

        // Compare sums
        return Integer.compare(sum1, sum2);
    }

    public int length() {
        return arr.length;
    }
}