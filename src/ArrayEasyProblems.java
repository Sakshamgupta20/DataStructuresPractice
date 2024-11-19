import common.CommonUtils;

import java.util.*;

public class ArrayEasyProblems {
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
            if(res[i] == 0)
                res[i]++;
        }
        for (int i : arr2) {
            if(res[i] == 1)
                res[i]++;
        }

        List<Integer> ans = new ArrayList<>();
        for (int i : arr3) {
            if(res[i] == 2) {
                ans.add(i);
                res[i]++;
            }
        }
        return ans;

    }


}
