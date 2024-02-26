import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BasicMaths {

    public int countDigits(int number) {
        int count = 0;
        while (number != 0) {
            count += 1;
            number = number / 10;
        }
        return count;
    }

    public int reverse(int x) {
        long result = 0;
        while (x != 0) {
            int last = x % 10;
            result = result * 10 + last;
            x = x / 10;
        }
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            return 0;
        }
        return (int) result;
    }

    public int hcf(int x, int y) {
        if (x == 0)
            return y;
        return hcf(y % x, x);
    }

    public boolean armstrongNumber(int x) {
        if (x < 0)
            return true;
        int numDigits = 0;
        int temp = x;
        while (temp != 0) {
            temp = temp / 10;
            numDigits++;
        }
        int power = 0;
        temp = x;
        while (temp != 0) {
            int last = temp % 10;
            power += Math.pow(last, numDigits);
            temp = temp / 10;
        }
        return power == x;
    }

    public List<Integer> printDivisors(int num) {
        List<Integer> res = new ArrayList<>();
        for (int i = 1; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                res.add(i);
                if (num / i != i) {
                    res.add(num / i);
                }
            }
        }
        return res.stream().sorted().collect(Collectors.toList());
    }
}
