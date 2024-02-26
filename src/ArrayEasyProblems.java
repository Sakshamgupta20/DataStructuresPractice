import java.util.List;

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
}
