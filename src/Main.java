import java.lang.reflect.Array;
import java.util.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        PrefixSum prefixSum = new PrefixSum();
        int[] arr = new int[]{-4,-3,-2,-1,4,3,2};
        System.out.println(prefixSum.largestAltitude(arr));

    }
}