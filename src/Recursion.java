import linkedList.ListNode;

import java.util.List;

public class Recursion {

    public void reverseString(char[] s) {
        reverseString(s, 0, s.length - 1);
    }

    public void reverseString(char[] s, int i, int j) {
        if (i >= j)
            return;
        char temp = s[i];
        s[i] = s[j];
        s[j] = temp;
        reverseString(s, i + 1, j - 1);
    }

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

    public int[] constructDistancedSequence(int n) {
        // Initialize the result sequence with size 2*n - 1 filled with 0s
        int[] resultSequence = new int[n * 2 - 1];

        // Keep track of which numbers are already placed in the sequence
        boolean[] isNumberUsed = new boolean[n + 1];

        // Start recursive backtracking to construct the sequence
        findLexicographicallyLargestSequence(
                0,
                resultSequence,
                isNumberUsed,
                n
        );

        return resultSequence;

    }

    private boolean findLexicographicallyLargestSequence(int currentIndex, int[] resultSequence, boolean[] isNumberUsed, int n) {

        if (currentIndex == resultSequence.length) {
            return true;
        }

        if (resultSequence[currentIndex] != 0) {
            return findLexicographicallyLargestSequence(
                    currentIndex + 1,
                    resultSequence,
                    isNumberUsed,
                    n
            );
        }

        for (int numberToPlace = n; numberToPlace >= 1; numberToPlace--) {
            if (isNumberUsed[numberToPlace]) continue;
            isNumberUsed[numberToPlace] = true;
            resultSequence[currentIndex] = numberToPlace;
            if (numberToPlace == 1) {
                if (findLexicographicallyLargestSequence(currentIndex + 1, resultSequence, isNumberUsed, n)
                ) {
                    return true;
                }
            }else if (
                    currentIndex + numberToPlace < resultSequence.length &&
                            resultSequence[currentIndex + numberToPlace] == 0
            ) {
                resultSequence[currentIndex + numberToPlace] = numberToPlace;

                if (
                        findLexicographicallyLargestSequence(
                                currentIndex + 1,
                                resultSequence,
                                isNumberUsed,
                                n
                        )
                ) {
                    return true;
                }

                // Undo the placement for backtracking
                resultSequence[currentIndex + numberToPlace] = 0;
                isNumberUsed[numberToPlace] = false;
            }
        }

        return false;
    }
}
