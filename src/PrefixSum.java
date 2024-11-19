import java.util.*;

public class PrefixSum {
    public int largestAltitude(int[] gain) {
        int max = 0;
        int sum = 0;
        for (int i = 0; i < gain.length; i++) {
            sum += gain[i];
            max = Math.max(sum, max);
        }
        return max;
    }

    public int pivotIndex(int[] nums) {
        int sum = 0;
        int leftSum = 0;
        for (int num : nums) {
            sum += num;
        }
        for (int i = 0; i < nums.length; i++) {
            if (leftSum == sum - leftSum - nums[i])
                return i;
            leftSum += nums[i];
        }
        return -1;
    }

    /**
     * 862. Shortest Subarray with Sum at Least K
     * Given an integer array nums and an integer k, return the length of the shortest non-empty subarray of nums with a sum of at least k. If there is no such subarray, return -1.
     * <p>
     * A subarray is a contiguous part of an array.
     */
    public int shortestSubarray(int[] nums, int k) {

        int shortestSubarrayLength = Integer.MAX_VALUE;

        long sum = 0;
        PriorityQueue<long[]> prefixSumHeap = new PriorityQueue<>(
                Comparator.comparingLong(a -> a[0])
        );
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum >= k) {
                shortestSubarrayLength = Math.min(shortestSubarrayLength, i + 1);
            }

            while (!prefixSumHeap.isEmpty() && sum - prefixSumHeap.peek()[0] >= k) {
                shortestSubarrayLength = (int) Math.min(shortestSubarrayLength, i - prefixSumHeap.poll()[1]);
            }
            prefixSumHeap.offer(new long[]{sum, i});
        }
        return shortestSubarrayLength == Integer.MAX_VALUE ? -1 : shortestSubarrayLength;
    }

    public int shortestSubarrayDeque(int[] nums, int k) {

        int shortestSubarrayLength = Integer.MAX_VALUE;

        int n = nums.length;

        long[] prefixSums = new long[n + 1];

        for (int i = 1; i <= n; i++) {
            prefixSums[i] = prefixSums[i - 1] + nums[i - 1];
        }
        Deque<Integer> candidateIndices = new ArrayDeque<>();

        for (int i = 0; i <= n; i++) {
            while (!candidateIndices.isEmpty() && prefixSums[i] - prefixSums[candidateIndices.getFirst()] >= k) {
                shortestSubarrayLength = Math.min(
                        shortestSubarrayLength,
                        i - candidateIndices.pollFirst()
                );
            }

            while (!candidateIndices.isEmpty() && prefixSums[i] <= prefixSums[candidateIndices.peekLast()]) {
                candidateIndices.pollLast();
            }

            candidateIndices.offerLast(i);
        }
        // Return -1 if no valid subarray found
        return shortestSubarrayLength == Integer.MAX_VALUE
                ? -1
                : shortestSubarrayLength;
    }

    /**
     * 1652. Defuse the Bomb
     * You have a bomb to defuse, and your time is running out! Your informer will provide you with a circular array code of length of n and a key k.
     * <p>
     * To decrypt the code, you must replace every number. All the numbers are replaced simultaneously.
     * <p>
     * If k > 0, replace the ith number with the sum of the next k numbers.
     * If k < 0, replace the ith number with the sum of the previous k numbers.
     * If k == 0, replace the ith number with 0.
     * As code is circular, the next element of code[n-1] is code[0], and the previous element of code[0] is code[n-1].
     * <p>
     * Given the circular array code and an integer key k, return the decrypted code to defuse the bomb!
     */
    public int[] decrypt(int[] code, int k) {
        int n = code.length;
        int[] prefixSums = new int[n];
        prefixSums[0] = code[0];
        for (int i = 1; i < n; i++) {
            prefixSums[i] = prefixSums[i - 1] + code[i];
        }

        int[] ans = new int[n];


        for (int i = 0; i < n; i++) {
            if(k > 0) {
                int sum = 0;
                int next = i + k;
                if(next >= n) {
                    sum += prefixSums[n - 1];
                    int remaining = next - n;
                    sum += prefixSums[remaining];
                } else {
                    sum += prefixSums[next];
                }
                sum -= prefixSums[i];
                ans[i] = sum;
            }

            if(k < 0) {
                int sum = 0;
                int next = i + k;
                sum += prefixSums[i] - code[i];
                if(next <= 0) {
                    sum += prefixSums[n - 1] - prefixSums[n + next - 1];
                } else  {
                    sum -= prefixSums[next - 1];
                }
                ans[i] = sum;
            }
        }
        return ans;

    }
}
