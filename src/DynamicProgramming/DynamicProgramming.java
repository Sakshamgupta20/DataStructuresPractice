package DynamicProgramming;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class DynamicProgramming {
    /**
     * 198. House Robber
     * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security systems connected and it will automatically contact the police if two adjacent houses were broken into on the same night.
     * <p>
     * Given an integer array nums representing the amount of money of each house, return the maximum amount of money you can rob tonight without alerting the police.
     *
     * @param nums
     * @return
     */
    public int rob(int[] nums) {
        HashMap<Integer, Integer> results = new HashMap<>();
        return rob(nums.length - 1, nums, results);
    }

    private int rob(int index, int[] nums, HashMap<Integer, Integer> results) {
        if (index < 0)
            return 0;

        if (results.containsKey(index))
            return results.get(index);

        results.put(index, Math.max(rob(index - 2, nums, results) + nums[index], rob(index - 1, nums, results)));

        return results.get(index);
    }

    public int robIterative(int[] nums) {
        if (nums.length == 1) {
            return nums[0];
        }
        int lastToLast = nums[0];
        int last = Math.max(nums[0], nums[1]);
        for (int i = 2; i < nums.length; i++) {
            int temp = last;
            last = Math.max(lastToLast + nums[i], last);
            lastToLast = temp;
        }
        return last;
    }

    public int lengthOfLISIterative(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1);
        int ans = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                    ans = Math.max(ans, dp[i]);
                }
            }
        }
        return ans;

    }

    public int lengthOfLIS(int[] nums) {
        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        int ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, lengthOfLISDp(nums, i, map));
        }
        return ans;

    }

    private int lengthOfLISDp(int[] nums, int i, Map<Integer, Integer> map) {
        if (map.containsKey(i))
            return map.get(i);

        int ans = 1;
        for (int j = 0; j < i; j++) {
            if (nums[i] > nums[j]) {
                ans = Math.max(ans, lengthOfLISDp(nums, j, map) + 1);
            }
        }

        map.put(i, ans);
        return ans;
    }

    /**
     * 2140. Solving Questions With Brainpower
     * You are given a 0-indexed 2D integer array questions where questions[i] = [pointsi, brainpoweri].
     * <p>
     * The array describes the questions of an exam, where you have to process the questions in order (i.e., starting from question 0) and make a decision whether to solve or skip each question. Solving question i will earn you pointsi points but you will be unable to solve each of the next brainpoweri questions. If you skip question i, you get to make the decision on the next question.
     * <p>
     * For example, given questions = [[3, 2], [4, 3], [4, 4], [2, 5]]:
     * If question 0 is solved, you will earn 3 points but you will be unable to solve questions 1 and 2.
     * If instead, question 0 is skipped and question 1 is solved, you will earn 4 points but you will be unable to solve questions 2 and 3.
     * Return the maximum points you can earn for the exam.
     */
    public long mostPoints(int[][] questions) {
        int n = questions.length;
        Map<Integer, Long> map = new HashMap<>();
        long ans = 0;
        for (int i = 0; i < n; i++) {
            ans = Math.max(ans, mostPointsDp(questions, i, map));
        }
        return ans;
    }

    private long mostPointsDp(int[][] questions, int i, Map<Integer, Long> map) {
        if (i >= questions.length)
            return 0;

        if (map.containsKey(i))
            return map.get(i);

        int j = i + questions[i][1] + 1;

        map.put(i, Math.max(questions[i][0] + mostPointsDp(questions, j, map), mostPointsDp(questions, i + 1, map)));

        return map.get(i);
    }

    /**
     * 70. Climbing Stairs
     * You are climbing a staircase. It takes n steps to reach the top.
     * <p>
     * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
     */
    public int climbStairs(int n) {
        Map<Integer, Integer> map = new HashMap<>();
        return climbStairs(n, map);
    }

    public int climbStairs(int n, Map<Integer, Integer> map) {
        if (n < 0)
            return 0;
        if (n == 0)
            return 1;
        if (map.containsKey(n))
            return map.get(n);
        map.put(n, climbStairs(n - 1, map) + climbStairs(n - 2, map));
        return map.get(n);
    }

    /**
     * 746. Min Cost Climbing Stairs
     * You are given an integer array cost where cost[i] is the cost of ith step on a staircase. Once you pay the cost, you can either climb one or two steps.
     * <p>
     * You can either start from the step with index 0, or the step with index 1.
     * <p>
     * Return the minimum cost to reach the top of the floor.
     */
    public int minCostClimbingStairs(int[] cost) {
        Map<Integer, Integer> map = new HashMap<>();
        return minCostClimbingStairs(cost, map, cost.length);
    }

    private int minCostClimbingStairs(int[] cost, Map<Integer, Integer> map, int index) {
        if (index <= 1) {
            return 0;
        }

        // Check if we have already calculated minimumCost(i)
        if (map.containsKey(index)) {
            return map.get(index);
        }

        int downOne = cost[index - 1] + minCostClimbingStairs(cost, map, index - 1);
        int downTwo = cost[index - 2] + minCostClimbingStairs(cost, map, index - 2);
        map.put(index, Math.min(downTwo, downOne));
        return map.get(index);
    }

    private int minCostClimbingStairsBottomUp(int[] cost) {
        int downOne = 0;
        int downTwo = 0;
        for (int i = 2; i < cost.length; i++) {
            int temp = downOne;
            downOne = Math.max(downOne + cost[i - 1], downTwo + cost[i - 2]);
            downTwo = temp;
        }
        return downOne;
    }

    /**
     * 322. Coin Change
     * You are given an integer array coins representing coins of different denominations and an integer amount representing a total amount of money.
     * <p>
     * Return the fewest number of coins that you need to make up that amount. If that amount of money cannot be made up by any combination of the coins, return -1.
     * <p>
     * You may assume that you have an infinite number of each kind of coin.
     */
    public int coinChange(int[] coins, int amount) {
        int ans = coinChange(coins, amount, new HashMap<>());
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public int coinChange(int[] coins, int amount, Map<Integer, Integer> memo) {
        if (amount == 0)
            return 0;
        if (amount < 0)
            return -1;
        if (memo.containsKey(amount))
            return memo.get(amount);

        int min = Integer.MAX_VALUE;
        for (int coin : coins) {
            int possible = coinChange(coins, amount - coin, memo);
            if (possible != -1)
                min = Math.min(min, possible + 1);
        }
        memo.put(amount, min == Integer.MAX_VALUE ? -1 : min);
        return memo.get(amount);
    }

    /**
     * 213. House Robber II
     * <p>
     * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed. All houses at this place are arranged in a circle. That means the first house is the neighbor of the last one. Meanwhile, adjacent houses have a security system connected, and it will automatically contact the police if two adjacent houses were broken into on the same night.
     * <p>
     * Given an integer array nums representing the amount of money of each house, return the maximum amount of money you can rob tonight without alerting the police.
     */
    public int rob2(int[] nums) {
        // Edge case for small arrays
        if (nums.length == 1) return nums[0];
        if (nums.length == 2) return Math.max(nums[0], nums[1]);

        Map<Integer, Integer> results = new HashMap<>();


        int maxRobFromFirst = rob2(nums.length - 2, nums, results, true);


        results.clear();

        int maxRobFromSecond = rob2(nums.length - 1, nums, results, false);


        return Math.max(maxRobFromFirst, maxRobFromSecond);
    }

    private int rob2(int index, int[] nums, Map<Integer, Integer> results, boolean isFirstIncluded) {
        if (index < 0) return 0;
        if (index == 0) return isFirstIncluded ? nums[0] : 0;
        if (results.containsKey(index)) return results.get(index);


        // Robbing the current house
        int robCurrent = nums[index] + rob2(index - 2, nums, results, isFirstIncluded);

        // Skipping the current house
        int skipCurrent = rob2(index - 1, nums, results, isFirstIncluded);

        // Take the maximum
        int maxRob = Math.max(robCurrent, skipCurrent);
        results.put(index, maxRob);
        return maxRob;
    }

    
}
