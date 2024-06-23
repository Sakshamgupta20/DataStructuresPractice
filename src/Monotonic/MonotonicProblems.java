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
}