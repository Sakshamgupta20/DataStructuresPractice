import java.util.List;

public class Recursion {
    public void printNTimes(int i, int n) {
        if (n < i)
            return;
        printNTimes(i + 1, n);
        System.out.println(i);
    }

    public int sumNNumbers(int i) {
        if (i <= 0)
            return 0;
        return i + sumNNumbers(i - 1);
    }

    public int factorial(int i) {
        if (i <= 1)
            return 1;
        return i * factorial(i - 1);
    }

    public void reverse(int[] arr, int index) {
        if (arr.length == index)
            return;
        int item = arr[index];
        reverse(arr, index + 1);
        arr[arr.length - 1 - index] = item;
    }

    public boolean palindrome(String value, int i) {
        if (i >= value.length() / 2)
            return true;
        if (value.charAt(i) != value.charAt(value.length() - i - 1))
            return false;
        return palindrome(value, i + 1);
    }

    public int fibonacci(int num) {
        if (num <= 1)
            return num;

        return fibonacci(num - 1) + fibonacci(num - 2);
    }
}
