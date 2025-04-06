import Trie.Trie;
import Trie.WordDictionary;
import common.CommonUtils;

import java.util.*;

public class ArrayProblems {
    public int largestElementInArray(List<Integer> numbers) {
        int max = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
            max = Math.max(numbers.get(i), max);
        }
        return max;
    }

    public int secondLargest(List<Integer> numbers) {
        int max = numbers.get(0);
        int maxSecond = max;

        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) > max) {
                maxSecond = max;
                max = numbers.get(i);
            }
        }
        if (maxSecond == max)
            maxSecond = -1;
        return maxSecond;

    }

    public int secondSmallest(List<Integer> numbers) {
        int min = numbers.get(0);
        int minSecond = min;
        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) < min) {
                minSecond = min;
                min = numbers.get(i);
            }
        }
        if (minSecond == min)
            minSecond = -1;

        return minSecond;

    }

    public boolean checkArrayIsSorted(List<Integer> numbers) {
        for (int i = 1; i < numbers.size(); i++) {
            if (numbers.get(i) < numbers.get(i - 1))
                return false;
        }
        return true;
    }

    public void removeDuplicatesFromSortedArray(int[] numbers) {
        int i = 0;
        for (int j = 1; j < numbers.length; j++) {
            if (numbers[i] != numbers[j])
                numbers[++i] = numbers[j];
        }

        for (int j = 0; j <= i; j++) {
            System.out.println(numbers[j]);
        }
    }

    public void shiftArrayElementsByOne(int[] numbers) {
        int i = 0;
        for (int j = 1; j < numbers.length; j++) {
            int temp = numbers[i];
            numbers[i] = numbers[j];
            numbers[j] = temp;
            i++;
        }
    }

    public double findMaxAverage(int[] nums, int k) {
        double ans = 0;
        double average;
        int index;
        for (index = 0; index < k; index++) {
            ans += nums[index];
        }
        average = ans / index;
        for (int i = k; i < nums.length; i++) {
            ans += nums[i] - nums[i - k];
            average = Math.max(ans / k, average);
        }
        return average;

    }

    public int longestOnes(int[] nums, int k) {
        int ans = 0;
        int left = 0;
        int res = 0;
        int right;
        for (right = 0; right < nums.length; right++) {
            if (nums[right] == 0)
                ans += 1;
            while (ans > k) {
                if (nums[left] == 0)
                    ans--;
                left++;
            }
            res = Math.max((right - left) + 1, res);
        }
        return res;
    }

    public int minStartValue(int[] nums) {
        int min = 0;
        int current = 0;
        for (int num : nums) {
            current += num;
            min = Math.min(min, current);
        }
        return -min + 1;
    }

    public int[] getAverages(int[] nums, int k) {
        long[] arr = new long[nums.length];
        arr[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            arr[i] = arr[i - 1] + nums[i];
        }
        int[] averages = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            if (i >= k && i + k < nums.length) {
                long sub = i - k - 1 >= 0 ? arr[i - k - 1] : 0;
                averages[i] = (int) Math.floor((double) (arr[i + k] - sub) / ((2 * k) + 1));
            } else {
                averages[i] = -1;
            }
        }
        return averages;
    }

    public int[] runningSum(int[] nums) {
        int[] prefix = new int[nums.length];
        prefix[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            prefix[i] = prefix[i - 1] + nums[i];
        }
        return prefix;
    }

    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int min = target - nums[i];
            if (hashMap.containsKey(nums[i]))
                return new int[]{hashMap.get(nums[i]), i};
            hashMap.put(min, i);
        }
        return null;
    }

    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs.length == 0) return new ArrayList<>();
        Map<String, List> ans = new HashMap<String, List>();
        for (String s : strs) {
            char[] ca = s.toCharArray();
            Arrays.sort(ca);
            String key = String.valueOf(ca);
            if (!ans.containsKey(key)) ans.put(key, new ArrayList());
            ans.get(key).add(s);
        }
        return new ArrayList(ans.values());
    }

    public int[] productExceptSelf(int[] nums) {
        int sum = 1;
        int zeroCount = 0;
        int[] muls = new int[nums.length];
        for (int num : nums) {
            if (num == 0)
                zeroCount++;
            else {
                sum = sum * num;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            int s;
            if (zeroCount > 1) {
                s = 0;
            } else {
                if (zeroCount == 1) {
                    if (nums[i] == 0) {
                        s = sum;
                    } else
                        s = 0;
                } else
                    s = sum / nums[i];

            }
            muls[i] = s;
        }
        return muls;
    }

    public boolean isValidSudoku(char[][] board) {
        int rowsL = board.length;
        int columnsL = board[0].length;
        HashMap<Integer, Set<Character>> columns = new HashMap<>();
        HashMap<Integer, Set<Character>> rows = new HashMap<>();
        HashMap<Integer, Set<Character>> groups = new HashMap<>();
        for (int row = 0; row < rowsL; row++) {
            for (int column = 0; column < columnsL; column++) {
                rows.putIfAbsent(row, new HashSet<>());
                columns.putIfAbsent(column, new HashSet<>());
                char value = board[row][column];
                int group = row / 3 + (column / 3 * 3);
                groups.putIfAbsent(group, new HashSet<>());
                if (value != '.') {
                    if (rows.get(row).contains(value))
                        return false;
                    if (columns.get(column).contains(value))
                        return false;
                    if (groups.get(group).contains(value))
                        return false;
                    rows.get(row).add(value);
                    columns.get(column).add(value);
                    groups.get(group).add(value);
                }

            }
        }
        return true;
    }

    public int longestConsecutive(int[] nums) {
        if (nums.length == 0)
            return 0;
        Arrays.sort(nums);
        int max = 1;
        int currentStreak = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                if (nums[i] - nums[i - 1] == 1) {
                    currentStreak += 1;
                } else {
                    max = Math.max(max, currentStreak);
                    currentStreak = 1;
                }
            }
        }
        return Math.max(max, currentStreak);
    }

    public int[] twoSumSorted(int[] numbers, int target) {
        int low = 0;
        int high = numbers.length - 1;
        while (low < high) {
            int sum = numbers[low] + numbers[high];

            if (sum == target) {
                return new int[]{low + 1, high + 1};
            } else if (sum < target) {
                ++low;
            } else {
                --high;
            }
        }
        // In case there is no solution, return {-1, -1}.
        return new int[]{-1, -1};
    }

    /**
     * Largest Number At Least Twice of Others
     * You are given an integer array nums where the largest integer is unique.
     * <p>
     * Determine whether the largest element in the array is at least twice as much as every other number in the array. If it is, return the index of the largest element, or return -1 otherwise.
     */
    public int dominantIndex(int[] nums) {
        int largest = 0;
        int secondLargest = -1;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= nums[largest]) {
                largest = i;
            }
        }

        for (int i = 0; i < nums.length; i++) {
            if ((secondLargest == -1 || nums[i] >= nums[secondLargest]) && nums[i] != nums[largest]) {
                secondLargest = i;
            }
        }
        if (nums[largest] / 2 >= nums[secondLargest])
            return largest;
        return -1;
    }

    public int[] plusOne(int[] digits) {
        int carry = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            int digit = digits[i];
            int sum = digit + carry;
            if (sum > 9) {
                digits[i] = 0;
            } else {
                digits[i] = sum;
                carry = 0;
                break;
            }
        }
        if (carry == 1) {
            int[] ans = new int[digits.length + 1];
            ans[0] = 1;
            return ans;
        }
        return digits;
    }

    /**
     * 498. Diagonal Traverse
     * Given an m x n matrix mat, return an array of all the elements of the array in a diagonal order.
     */
    public int[] findDiagonalOrder(int[][] mat) {
        boolean up = true;

        int n = mat.length;
        int m = mat[0].length;

        int total = n * m;
        int[] ans = new int[total];

        int row = 0;
        int column = 0;
        int index = 0;
        while (row < n && column < m) {

            ans[index++] = mat[row][column];
            int newRow = row + (up ? -1 : 1);
            int newColumn = column + (up ? 1 : -1);

            if (!CommonUtils.validGrid(n, m, newRow, newColumn)) {
                if (up) {
                    row += (column == m - 1 ? 1 : 0);
                    column += (column < m - 1 ? 1 : 0);
                } else {
                    column += (row == n - 1 ? 1 : 0);
                    row += (row < n - 1 ? 1 : 0);
                }
                up = !up;
            } else {
                row = newRow;
                column = newColumn;
            }

        }
        return ans;

    }

    /**
     * 1213. Intersection of Three Sorted Arrays
     * Given three integer arrays arr1, arr2 and arr3 sorted in strictly increasing order, return a sorted array of only the integers that appeared in all three arrays.
     */
    public List<Integer> arraysIntersection(int[] arr1, int[] arr2, int[] arr3) {
        int[] res = new int[2000 + 1];
        for (int i : arr1) {
            if (res[i] == 0)
                res[i]++;
        }
        for (int i : arr2) {
            if (res[i] == 1)
                res[i]++;
        }

        List<Integer> ans = new ArrayList<>();
        for (int i : arr3) {
            if (res[i] == 2) {
                ans.add(i);
                res[i]++;
            }
        }
        return ans;

    }

    /**
     * 1975. Maximum Matrix Sum
     * You are given an n x n integer matrix. You can do the following operation any number of times:
     * <p>
     * Choose any two adjacent elements of matrix and multiply each of them by -1.
     * Two elements are considered adjacent if and only if they share a border.
     * <p>
     * Your goal is to maximize the summation of the matrix's elements. Return the maximum sum of the matrix's elements using the operation mentioned above.
     */
    public long maxMatrixSum(int[][] matrix) {
        int n = matrix.length; // Number of rows
        int m = matrix[0].length; // Number of columns

        long sum = 0; // To store the total absolute sum of all elements
        int negativeCount = 0; // To count the number of negative values
        int lowest = Integer.MAX_VALUE; // To track the smallest absolute value in the matrix

        for (int[] rows : matrix) {
            for (int column = 0; column < m; column++) {
                // Check if the current element is negative
                if (rows[column] < 0) {
                    negativeCount++;
                }
                // Update the smallest absolute value found so far
                lowest = Math.min(lowest, Math.abs(rows[column]));

                // Add the absolute value of the current element to the sum
                sum += Math.abs(rows[column]);
            }
        }

        // Check if the count of negatives is odd or even
        // If even, return the total sum
        // If odd, subtract 2 * lowest absolute value to balance the sum
        return negativeCount % 2 == 0 ? sum : (sum - (2L * lowest));
    }

    /**
     * 2371. Minimize Maximum Value in a Grid
     * You are given an m x n integer matrix grid containing distinct positive integers.
     * <p>
     * You have to replace each integer in the matrix with a positive integer satisfying the following conditions:
     * <p>
     * The relative order of every two elements that are in the same row or column should stay the same after the replacements.
     * The maximum number in the matrix after the replacements should be as small as possible.
     * The relative order stays the same if for all pairs of elements in the original matrix such that grid[r1][c1] > grid[r2][c2] where either r1 == r2 or c1 == c2, then it must be true that grid[r1][c1] > grid[r2][c2] after the replacements.
     * <p>
     * For example, if grid = [[2, 4, 5], [7, 3, 9]] then a good replacement could be either grid = [[1, 2, 3], [2, 1, 4]] or grid = [[1, 2, 3], [3, 1, 4]].
     * <p>
     * Return the resulting matrix. If there are multiple answers, return any of them.
     */
    public int[][] minScore(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        PriorityQueue<int[]> min_heap = new PriorityQueue<>(
                Comparator.comparingInt(a -> a[0])
        );

        int[] rows = new int[n];
        int[] cols = new int[m];
        Arrays.fill(rows, 1);
        Arrays.fill(cols, 1);

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                min_heap.offer(new int[]{grid[i][j], i, j});
            }
        }

        while (!min_heap.isEmpty()) {
            int[] element = min_heap.poll();
            int value = element[0];
            int row = element[1];
            int col = element[2];

            int val = Math.max(rows[row], cols[col]);
            grid[row][col] = val;

            rows[row] = val + 1;
            cols[col] = val + 1;
        }

        return grid;
    }

    /**
     * 2923. Find Champion I
     * There are n teams numbered from 0 to n - 1 in a tournament.
     * <p>
     * Given a 0-indexed 2D boolean matrix grid of size n * n. For all i, j that 0 <= i, j <= n - 1 and i != j team i is stronger than team j if grid[i][j] == 1, otherwise, team j is stronger than team i.
     * <p>
     * Team a will be the champion of the tournament if there is no team b that is stronger than team a.
     * <p>
     * Return the team that will be the champion of the tournament.
     */
    public int findChampion(int[][] grid) {
        int n = grid.length;
        Set<Integer> weakTeams = new HashSet<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1)
                    weakTeams.add(j);
            }
        }
        for (int i = 0; i < n; i++) {
            if (!weakTeams.contains(i))
                return i;
        }
        return -1;
    }

    /**
     * 1346. Check If N and Its Double Exist
     * Given an array arr of integers, check if there exist two indices i and j such that :
     * <p>
     * i != j
     * 0 <= i, j < arr.length
     * arr[i] == 2 * arr[j]
     */
    public boolean checkIfExist(int[] arr) {
        Set<Integer> seen = new HashSet<>();

        for (int num : arr) {
            // Check if 2 * num or num / 2 exists in the set
            if (
                    seen.contains(2 * num) ||
                            (num % 2 == 0 && seen.contains(num / 2))
            ) {
                return true;
            }
            // Add the current number to the set
            seen.add(num);
        }
        // No valid pair found
        return false;
    }

    /**
     * 2554. Maximum Number of Integers to Choose From a Range I
     * You are given an integer array banned and two integers n and maxSum. You are choosing some number of integers following the below rules:
     * <p>
     * The chosen integers have to be in the range [1, n].
     * Each integer can be chosen at most once.
     * The chosen integers should not be in the array banned.
     * The sum of the chosen integers should not exceed maxSum.
     * Return the maximum number of integers you can choose following the mentioned rules.
     */
    public int maxCount(int[] banned, int n, int maxSum) {
        int currSum = 0;
        int digits = 0;
        Arrays.sort(banned);

        int last = 0;
        for (int ban : banned) {
            if (ban > n) break;
            if (currSum >= maxSum) break;


            if (last + 1 <= ban - 1) {
                int[] result = maxCountSearch(maxSum - currSum, last + 1, ban - 1);
                currSum += result[0];
                digits += result[1];
            }
            last = ban;
        }
        // Handle the remaining range [last + 1, n]
        if (last + 1 <= n) {
            int[] result = maxCountSearch(maxSum - currSum, last + 1, n);
            currSum += result[0];
            digits += result[1];
        }
        return digits;
    }

    public int[] maxCountSearch(int targetSum, int left, int right) {
        int sum = 0, count = 0;
        int originalLeft = left;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int rangeSum = rangeSum(originalLeft, mid);

            if (rangeSum > targetSum) {
                right = mid - 1;
            } else {
                sum = rangeSum;
                count = mid - originalLeft + 1;
                left = mid + 1;
            }
        }
        return new int[]{sum, count};
    }

    private int rangeSum(int start, int end) {
        return sumUpTo(end) - sumUpTo(start - 1);
    }

    private int sumUpTo(int x) {
        return x * (x + 1) / 2;
    }

    /**
     * 2981. Find Longest Special Substring That Occurs Thrice I
     * You are given a string s that consists of lowercase English letters.
     * <p>
     * A string is called special if it is made up of only a single character. For example, the string "abc" is not special, whereas the strings "ddd", "zz", and "f" are special.
     * <p>
     * Return the length of the longest special substring of s which occurs at least thrice, or -1 if no special substring occurs at least thrice.
     * <p>
     * A substring is a contiguous non-empty sequence of characters within a string.
     */
    public int maximumLength(String s) {
        HashMap<Character, LinkedHashMap<String, Integer>> map = new LinkedHashMap<>();
        int left = 0;
        for (int right = 1; right < s.length(); right++) {
            char lc = s.charAt(left);
            char rc = s.charAt(right);
            if (lc != rc) {
                String c = s.substring(left, right);
                map.putIfAbsent(lc, new LinkedHashMap<>());
                map.get(lc).put(c, map.get(lc).getOrDefault(c, 0) + 1);
                left = right;
            }
        }

        if (left < s.length()) {
            char lc = s.charAt(left);
            String c = s.substring(left);
            map.putIfAbsent(lc, new LinkedHashMap<>());
            map.get(lc).put(c, map.get(lc).getOrDefault(c, 0) + 1);
        }

        Integer maxSubstring = -1;
        for (Character c : map.keySet()) {
            int[][] sorted = new int[map.get(c).size()][2];
            int index = 0;
            for (String string : map.get(c).keySet()) {
                sorted[index++] = new int[]{string.length(), map.get(c).get(string)};
            }
            Arrays.sort(sorted, (a, b) -> Integer.compare(b[0], a[0]));

            int max = -1;
            if (sorted[0][1] >= 3) {
                max = sorted[0][0];
            } else if (sorted.length > 1) {
                max = sorted[1][0];
            }
            if (sorted[0][0] > 2) {
                max = Math.max(max, sorted[0][0] - (3 - sorted[0][1]));
            }
            maxSubstring = Math.max(max, maxSubstring);
        }

        return maxSubstring;
    }

    public int maximumBeauty(int[] nums, int k) {
        // If there's only one element, the maximum beauty is 1
        if (nums.length == 1) return 1;

        int maxBeauty = 0;
        int maxValue = 0;

        // Find the maximum value in the array
        for (int num : nums) maxValue = Math.max(maxValue, num);

        // Create an array to keep track of the count changes
        int[] count = new int[maxValue + 1];

        // Update the count array for each value's range [val - k, val + k]
        for (int num : nums) {
            count[Math.max(num - k, 0)]++; // Increment at the start of the range
            count[Math.min(num + k + 1, maxValue)]--; // Decrement after the range
        }

        int currentSum = 0; // Tracks the running sum of counts
        // Calculate the prefix sum and find the maximum value
        for (int val : count) {
            currentSum += val;
            maxBeauty = Math.max(maxBeauty, currentSum);
        }

        return maxBeauty;
    }

    public long pickGifts(int[] gifts, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>((a, b) -> b - a);
        for (int gift : gifts) {
            queue.add(gift);
        }
        for (int i = 0; i < k; i++) {
            int highest = queue.poll();
            int remaining = (int) Math.floor(Math.sqrt(highest));
            queue.add(remaining);
        }
        long sum = 0;
        while (!queue.isEmpty())
            sum += queue.poll();
        return sum;
    }

    public int[] distinctNumbers(int[] nums, int k) {
        HashMap<Integer, Integer> size = new HashMap<>();
        int left = 0;

        int[] ans = new int[nums.length - k + 1];
        for (int right = 0; right < nums.length; right++) {
            if (right - left + 1 > k) {
                size.put(nums[left], size.get(nums[left]) - 1);
                if (size.get(nums[left]) == 0)
                    size.remove(nums[left]);
                left++;
            }

            size.put(nums[right], size.getOrDefault(nums[right], 0) + 1);

            if (right - left + 1 == k) {
                ans[right - k + 1] = size.size();
            }
        }
        return ans;
    }

    /**
     * 1752. Check if Array Is Sorted and Rotated
     * Given an array nums, return true if the array was originally sorted in non-decreasing order, then rotated some number of positions (including zero). Otherwise, return false.
     * <p>
     * There may be duplicates in the original array.
     * <p>
     * Note: An array A rotated by x positions results in an array B of the same length such that A[i] == B[(i+x) % A.length], where % is the modulo operation.
     */
    public boolean check(int[] nums) {
        boolean smallFound = false;
        for (int right = 1; right < nums.length; right++) {
            if (nums[right - 1] > nums[right]) {
                if (smallFound)
                    return false;
                smallFound = true;
            }
        }
        return !smallFound || nums[0] >= nums[nums.length - 1];
    }

    /**
     * 3105. Longest Strictly Increasing or Strictly Decreasing Subarray
     * You are given an array of integers nums. Return the length of the longest
     * subarray
     * of nums which is either
     * strictly increasing
     * or
     * strictly decreasing
     * .
     */
    public int longestMonotonicSubarray(int[] nums) {
        int max = 1;
        int left = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] >= nums[i]) {
                left = i;
            }
            max = Math.max(max, i - left + 1);
        }

        left = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] <= nums[i]) {
                left = i;
            }
            max = Math.max(max, i - left + 1);
        }
        return max;
    }

    /**
     * 1800. Maximum Ascending Subarray Sum
     * Given an array of positive integers nums, return the maximum possible sum of an ascending subarray in nums.
     * <p>
     * A subarray is defined as a contiguous sequence of numbers in an array.
     * <p>
     * A subarray [numsl, numsl+1, ..., numsr-1, numsr] is ascending if for all i where l <= i < r, numsi  < numsi+1. Note that a subarray of size 1 is ascending.
     */
    public int maxAscendingSum(int[] nums) {
        int sum = nums[0];
        int max = nums[0];
        for (int right = 1; right < nums.length; right++) {
            if (nums[right - 1] > nums[right]) {
                sum = 0;
            }
            sum += nums[right];
            max = Math.max(sum, max);
        }
        return max;
    }

    /**
     * 1726. Tuple with Same Product
     * Given an array nums of distinct positive integers, return the number of tuples (a, b, c, d) such that a * b = c * d where a, b, c, and d are elements of nums, and a != b != c != d.
     */
    public int tupleSameProduct(int[] nums) {
        int numsLength = nums.length;

        Map<Integer, Integer> pairProductsFrequency = new HashMap<>();

        int totalNumberOfTuples = 0;

        for (int firstIndex = 0; firstIndex < numsLength; firstIndex++) {
            for (int secondIndex = firstIndex + 1; secondIndex < numsLength; secondIndex++
            ) {
                pairProductsFrequency.put(nums[firstIndex] * nums[secondIndex], pairProductsFrequency.getOrDefault(
                        nums[firstIndex] * nums[secondIndex], 0) + 1);
            }
        }

        for (int productValue : pairProductsFrequency.keySet()) {
            int productFrequency = pairProductsFrequency.get(productValue);
            int pairsOfEqualProduct =
                    ((productFrequency - 1) * productFrequency) / 2;

            totalNumberOfTuples += 8 * pairsOfEqualProduct;
        }

        return totalNumberOfTuples;
    }

    /**
     * 3160. Find the Number of Distinct Colors Among the Balls
     * You are given an integer limit and a 2D array queries of size n x 2.
     * <p>
     * There are limit + 1 balls with distinct labels in the range [0, limit]. Initially, all balls are uncolored. For every query in queries that is of the form [x, y], you mark ball x with the color y. After each query, you need to find the number of distinct colors among the balls.
     * <p>
     * Return an array result of length n, where result[i] denotes the number of distinct colors after ith query.
     * <p>
     * Note that when answering a query, lack of a color will not be considered as a color.
     */
    public int[] queryResults(int limit, int[][] queries) {
        HashMap<Integer, Set<Integer>> assigns = new HashMap<>();
        int total = 0;
        HashMap<Integer, Integer> arr = new HashMap<>();
        int[] ans = new int[queries.length];
        int index = 0;
        for (int[] query : queries) {
            int a = query[0];
            int b = query[1];
            if (!arr.containsKey(a)) {
                arr.put(a, b);
                assigns.putIfAbsent(b, new HashSet<>());
                if (assigns.get(b).isEmpty()) total++;
                assigns.get(b).add(a);
            } else {
                int oldB = arr.get(a);
                if (oldB != b) { // Only update if it's a different value
                    assigns.get(oldB).remove(a);
                    if (assigns.get(oldB).isEmpty())
                        total--;

                    arr.put(a, b);
                    assigns.putIfAbsent(b, new HashSet<>());
                    assigns.get(b).add(a);
                    if (assigns.get(b).size() == 1) {
                        total++;
                    }
                }
            }
            ans[index++] = total;
        }
        return ans;
    }

    /**
     * 2342. Max Sum of a Pair With Equal Sum of Digits
     * You are given a 0-indexed array nums consisting of positive integers. You can choose two indices i and j, such that i != j, and the sum of digits of the number nums[i] is equal to that of nums[j].
     * <p>
     * Return the maximum value of nums[i] + nums[j] that you can obtain over all possible indices i and j that satisfy the conditions.
     */
    public int maximumSum(int[] nums) {
        Arrays.sort(nums);
        int[] digitMapping = new int[82];
        int max = -1;
        for (int num : nums) {
            int temp = num;
            int digitSum = 0;
            while (temp != 0) {
                int last = temp % 10;
                temp = temp / 10;
                digitSum += last;
            }
            if (digitMapping[digitSum] != 0)
                max = Math.max(digitMapping[digitSum] + num, max);
            digitMapping[digitSum] = num;
        }
        return max;

    }

    public int punishmentNumber(int n) {
        int punishmentNum = 0;

        // Iterate through numbers in range [1, n]
        for (int currentNum = 1; currentNum <= n; currentNum++) {
            int squareNum = currentNum * currentNum;

            // Check if valid partition can be found and add squared number if so
            if (canPartition(squareNum, currentNum)) {
                punishmentNum += squareNum;
            }
        }
        return punishmentNum;
    }

    public boolean canPartition(int num, int target) {
        // Invalid partition found
        if (target < 0 || num < target) {
            return false;
        }

        // Valid partition found
        if (num == target) {
            return true;
        }

        // Recursively check all partitions for a valid partition
        return (
                canPartition(num / 10, target - (num % 10)) ||
                        canPartition(num / 100, target - (num % 100)) ||
                        canPartition(num / 1000, target - (num % 1000))
        );
    }

    public String findDifferentBinaryString(String[] nums) {
        Set<Integer> integers = new HashSet();
        for (String num : nums) {
            integers.add(Integer.parseInt(num, 2));
        }

        int n = nums.length;
        for (int num = 0; num <= n; num++) {
            if (!integers.contains(num)) {
                String ans = Integer.toBinaryString(num);
                while (ans.length() < n) {
                    ans = "0" + ans;
                }

                return ans;
            }
        }

        return "";
    }

    public int[] pivotArray(int[] nums, int pivot) {
        int[] ans = new int[nums.length];
        int lessI = 0;
        int greaterI = nums.length - 1;
        for (int i = 0, j = nums.length - 1; i < nums.length; i++, j--) {
            if (nums[i] < pivot) {
                ans[lessI] = nums[i];
                lessI++;
            }
            if (nums[j] > pivot) {
                ans[greaterI] = nums[j];
                greaterI--;
            }

        }
        while (lessI <= greaterI) {
            ans[lessI] = pivot;
            lessI++;
        }
        return ans;
    }

    public int minimumIndex(List<Integer> nums) {
        int x = nums.get(0);
        int count = 0;
        int xCount = 0;
        int n = nums.size();

        for (Integer num : nums) {
            if(num == x)
                count++;
            else
                count--;
            if(count == 0) {
                count = 1;
                x = num;
            }
        }

        for (int num : nums) {
            if (num == x) {
                xCount++;
            }
        }

        count = 0;
        for (int index = 0; index < n; index++) {
            if (nums.get(index) == x) {
                count++;
            }
            int remainingCount = xCount - count;
            if (count * 2 > index + 1 && remainingCount * 2 > n - index - 1) {
                return index;
            }
        }

        return -1;
    }


}
