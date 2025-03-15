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
            if (k > 0) {
                int sum = 0;
                int next = i + k;
                if (next >= n) {
                    sum += prefixSums[n - 1];
                    int remaining = next - n;
                    sum += prefixSums[remaining];
                } else {
                    sum += prefixSums[next];
                }
                sum -= prefixSums[i];
                ans[i] = sum;
            }

            if (k < 0) {
                int sum = 0;
                int next = i + k;
                sum += prefixSums[i] - code[i];
                if (next <= 0) {
                    sum += prefixSums[n - 1] - prefixSums[n + next - 1];
                } else {
                    sum -= prefixSums[next - 1];
                }
                ans[i] = sum;
            }
        }
        return ans;

    }

    /**
     * 2599. Make the Prefix Sum Non-negative
     * You are given a 0-indexed integer array nums. You can apply the following operation any number of times:
     * <p>
     * Pick any element from nums and put it at the end of nums.
     * The prefix sum array of nums is an array prefix of the same length as nums such that prefix[i] is the sum of all the integers nums[j] where j is in the inclusive range [0, i].
     * <p>
     * Return the minimum number of operations such that the prefix sum array does not contain negative integers. The test cases are generated such that it is always possible to make the prefix sum array non-negative.
     */
    public int makePrefSumNonNegative(int[] nums) {
        int operations = 0;
        long prefixSum = 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int num : nums) {
            if (num < 0) {
                pq.offer(num);
            }
            prefixSum += num;
            if (prefixSum < 0) {
                prefixSum -= pq.poll();
                operations++;
            }
        }

        return operations;

    }

    /**
     * 2364. Count Number of Bad Pairs
     * You are given a 0-indexed integer array nums. A pair of indices (i, j) is a bad pair if i < j and j - i != nums[j] - nums[i].
     * <p>
     * Return the total number of bad pairs in nums.
     */
    public long countBadPairs(int[] nums) {
        long badPairs = 0;
        Map<Integer, Integer> diffCount = new HashMap<>();
        for (int pos = 0; pos < nums.length; pos++) {
            int diff = pos - nums[pos];
            int goodPairsCount = diffCount.getOrDefault(diff, 0);
            badPairs += pos - goodPairsCount;
            diffCount.put(diff, goodPairsCount + 1);
        }
        return badPairs;
    }

    /**
     * 1524. Number of Sub-arrays With Odd Sum
     * Medium
     * Given an array of integers arr, return the number of subarrays with an odd sum.
     * <p>
     * Since the answer can be very large, return it modulo 109 + 7.
     */
    public int numOfSubarrays(int[] arr) {
        HashMap<Integer, Integer> odds = new HashMap<>();
        HashMap<Integer, Integer> oddsRes = new HashMap<>();

        odds.put(0, -1);  // Base case: Before array starts
        oddsRes.put(0, 0);
        oddsRes.put(-1, 0); // Fix: Prevent null access for `oddCount - 2`

        int n = arr.length;
        int oddCount = 0;
        long ans = 0;
        int MOD = 1_000_000_007;

        for (int i = 0; i < n; i++) {
            int num = arr[i];

            if (num % 2 != 0) { // If current number is odd
                int res = 0;

                // Get last occurrence of this odd count
                int lastIndex = odds.getOrDefault(oddCount, -1);
                res += (i - lastIndex);

                oddCount++;

                // Include previous odd subarray results if applicable
                res += oddsRes.getOrDefault(oddCount - 2, 0);

                ans = (ans + res) % MOD;
                odds.put(oddCount, i);
                oddsRes.put(oddCount, res);
            } else { // If current number is even
                ans = (ans + oddsRes.getOrDefault(oddCount, 0)) % MOD;
            }
        }

        return (int) ans;
    }

    /**
     * 1749. Maximum Absolute Sum of Any Subarray
     * Medium
     * Topics
     * Companies
     * Hint
     * You are given an integer array nums. The absolute sum of a subarray [numsl, numsl+1, ..., numsr-1, numsr] is abs(numsl + numsl+1 + ... + numsr-1 + numsr).
     * <p>
     * Return the maximum absolute sum of any (possibly empty) subarray of nums.
     * <p>
     * Note that abs(x) is defined as follows:
     * <p>
     * If x is a negative integer, then abs(x) = -x.
     * If x is a non-negative integer, then abs(x) = x.
     *
     * @param nums
     * @return
     */
    public int maxAbsoluteSum(int[] nums) {
        int negative = 0;
        int positive = 0;
        int max = 0;

        for (int right = 0; right < nums.length; right++) {
            int num = nums[right];
            positive += num;
            if(positive < 0)
                positive = 0;

            negative += num;
            if(negative > 0)
                negative = 0;
            max = Math.max(max,Math.abs(negative));
            max = Math.max(max,Math.abs(positive));
        }
        return max;
    }
}
