package Monotonic;

import java.util.*;

public class MonotonicProblems {
    public int[] dailyTemperatures(int[] temperatures) {
        int[] answers = new int[temperatures.length];
        Stack<Integer> numbers = new Stack<>();
        for (int i = 0; i < temperatures.length; i++) {
            while (!numbers.isEmpty() && temperatures[i] > temperatures[numbers.peek()]) {
                int j = numbers.pop();
                answers[j] = i - j;
            }
            numbers.push(i);
        }
        return answers;
    }

    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> deque = new LinkedList<>();
        int[] answers = new int[nums.length - k + 1];
        int res = 0;

        for (int i = 0; i < nums.length; i++) {
            while (!deque.isEmpty() && nums[i] >= nums[deque.getLast()]) {
                deque.removeLast();
            }

            deque.addLast(i);

            if (i - deque.getFirst() >= k)
                deque.removeFirst();

            if (i + 1 >= k)
                answers[res++] = nums[deque.getFirst()];

        }
        return answers;
    }

    public int longestSubarray(int[] nums, int limit) {
        int max = 0, left = 0;
        Deque<Integer> decreasing = new ArrayDeque<>();
        Deque<Integer> increasing = new ArrayDeque<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            while (!decreasing.isEmpty() && num > decreasing.getLast()) {
                decreasing.removeLast();
            }

            while (!increasing.isEmpty() && num < increasing.getLast()) {
                increasing.removeLast();
            }

            increasing.addLast(num);
            decreasing.addLast(num);

            while (decreasing.getFirst() - increasing.getFirst() > limit) {
                if (nums[left] == increasing.getFirst())
                    increasing.removeFirst();
                if (nums[left] == decreasing.getFirst())
                    decreasing.removeFirst();
                left++;
            }
            max = Math.max(max, i - left + 1);
        }
        return max;
    }

    public int[] finalPrices(int[] prices) {
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < prices.length; i++) {
            while (!stack.isEmpty() && prices[stack.peek()] >= prices[i]) {
                prices[stack.pop()] -= prices[i];
            }
            stack.push(i);
        }
        return prices;
    }

    public int[] canSeePersonsCount(int[] heights) {
        Stack<Integer> stack = new Stack<>();
        int[] ans = new int[heights.length];

        for (int i = 0; i < heights.length; i++) {
            if (heights.length - 1 != i)
                ans[i] += 1;

            while (!stack.isEmpty() && heights[stack.peek()] <= heights[i]) {
                stack.pop();
                if (!stack.isEmpty()) {
                    ans[stack.peek()] += 1;
                }
            }
            stack.push(i);
        }
        return ans;
    }

    public int[] mostCompetitive(int[] nums, int k) {
        Stack<Integer> stack = new Stack<>();
        int additional = nums.length - k;

        for (int num : nums) {
            while (!stack.isEmpty() && stack.peek() > num && additional > 0) {
                stack.pop();
                additional--;
            }
            stack.add(num);
        }

        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = stack.get(i);
        }
        return result;
    }

    public int sumSubarrayMins(int[] arr) {
        int MOD = 1000000007;

        Stack<Integer> stack = new Stack<>();
        long sumOfMinimums = 0;

        for (int i = 0; i <= arr.length; i++) {
            while (!stack.isEmpty() && (i == arr.length || arr[stack.peek()] >= arr[i])) {
                int popped = stack.pop();
                int min = stack.isEmpty() ? -1 : stack.peek();
                int max = i;

                long count = ((max - popped) * (popped - min)) % MOD;
                sumOfMinimums += (arr[popped] * count) % MOD;
                sumOfMinimums %= MOD;
            }
            stack.push(i);
        }
        return (int) sumOfMinimums;
    }

    public long subArrayRanges(int[] nums) {

        Stack<Integer> stack = new Stack<>();
        long answer = 0;

        for (int i = 0; i <= nums.length; i++) {
            while (!stack.isEmpty() && (i == nums.length || nums[stack.peek()] >= nums[i])) {
                int popped = stack.pop();
                int min = stack.isEmpty() ? -1 : stack.peek();
                int max = i;

                long count = ((long) (max - popped) * (popped - min));
                answer -= (nums[popped] * count);
            }
            stack.push(i);
        }
        stack.clear();

        for (int i = 0; i <= nums.length; i++) {
            while (!stack.isEmpty() && (i == nums.length || nums[stack.peek()] <= nums[i])) {
                int popped = stack.pop();
                int min = stack.isEmpty() ? -1 : stack.peek();
                int max = i;

                long count = ((long) (max - popped) * (popped - min));
                answer += (nums[popped] * count);
            }
            stack.push(i);
        }
        return answer;
    }

    public int validSubarrays(int[] nums) {
        Stack<Integer> stack = new Stack<>();
        int answer = 0;
        for (int i = 0; i <= nums.length; i++) {
            while (!stack.isEmpty() && (nums.length == i || nums[i] < nums[stack.peek()])) {
                int upperBound = stack.isEmpty() ? 0 : stack.pop();
                answer += (i - upperBound);
            }
            stack.add(i);
        }
        return answer;
    }

    public int maximumRobots(int[] chargeTimes, int[] runningCosts, long budget) {
        long sum = 0;
        int left = 0;
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < chargeTimes.length; i++) {
            sum += runningCosts[i];
            while (!deque.isEmpty() && chargeTimes[i] >= chargeTimes[deque.peekLast()])
                deque.pollLast();
            deque.addLast(i);
            if (chargeTimes[deque.getFirst()] + (i - left + 1) * sum > budget) {
                if (deque.getFirst() == i)
                    deque.pollFirst();
                sum -= runningCosts[i++];

            }
        }
        return chargeTimes.length - left;
    }

    public int carFleet(int target, int[] position, int[] speed) {
        Stack<Double> stack = new Stack<>();
        int[][] cars = new int[position.length][2];
        for (int i = 0; i < position.length; i++) {
            cars[i] = new int[]{position[i], speed[i]};
        }
        Arrays.sort(cars, Comparator.comparingInt(a -> a[0]));
        for (int i = cars.length - 1; i >= 0; i--) {
            double time = (double) (target - cars[i][0]) / cars[i][1];
            while (!stack.isEmpty() && time <= stack.peek())
                stack.pop();
            stack.push(time);
        }
        return stack.size();
    }

    public int largestRectangleArea(int[] heights) {
        int max = 0;
        Stack<Integer> stack = new Stack<>();
        stack.add(-1);
        int n = heights.length;
        for (int i = 0; i < heights.length; i++) {
            while (stack.peek() != -1 && heights[stack.peek()] > heights[i]) {
                int ind = stack.pop();
                max = Math.max(max, heights[ind] * (i - stack.peek() - 1));
            }
            stack.push(i);
        }
        while (stack.peek() != -1) {
            int ind = stack.pop();
            max = Math.max(max, heights[ind] * (n - stack.peek() - 1));
        }
        return max;

    }

    /**
     * 1574. Shortest Subarray to be Removed to Make Array Sorted
     * Given an integer array arr, remove a subarray (can be empty) from arr such that the remaining elements in arr are non-decreasing.
     * <p>
     * Return the length of the shortest subarray to remove.
     * <p>
     * A subarray is a contiguous subsequence of the array.
     */
    public int findLengthOfShortestSubarray(int[] arr) {
        int right = arr.length - 1;
        while (right > 0 && arr[right] >= arr[right - 1]) {
            right--;
        }

        int ans = right;
        int left = 0;
        while (left < right && (left == 0 || arr[left - 1] <= arr[left])) {
            while (right < arr.length && arr[right] < arr[left])
                right++;
            ans = Math.min(ans, right - left - 1);
            left++;
        }
        return ans;
    }

    /**
     * 3254. Find the Power of K-Size Subarrays I
     * You are given an array of integers nums of length n and a positive integer k.
     * <p>
     * The power of an array is defined as:
     * <p>
     * Its maximum element if all of its elements are consecutive and sorted in ascending order.
     * -1 otherwise.
     * You need to find the power of all
     * subarrays
     * of nums of size k.
     * <p>
     * Return an integer array results of size n - k + 1, where results[i] is the power of nums[i..(i + k - 1)].
     */
    public int[] resultsArray(int[] nums, int k) {
        int[] ans = new int[nums.length - k + 1];
        int index = 0;
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            if (!deque.isEmpty() && i - deque.getFirst() >= k)
                deque.removeFirst();

            if(!deque.isEmpty() && nums[deque.getLast()] + 1 != nums[i])
                deque.clear();

            deque.add(i);


            if (i >= k - 1) {
                ans[index++] = deque.size() == k ? nums[deque.getLast()] : -1;
            }

        }
        return ans;
    }
}
