import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SlidingWindow {
    public int minSubArrayLen(int target, int[] nums) {
        int min = Integer.MAX_VALUE;
        boolean valueFound = false;
        int left = 0;
        int sum = 0;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum > target) {
                min = Math.min(right - left + 1, min);
                valueFound = true;
                sum -= nums[left++];
            }
        }
        return valueFound ? min : 0;
    }

    public int maxVowels(String s, int k) {
        char[] arr = s.toCharArray();
        Set<Character> vowels = new HashSet<>();
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');
        int max = 0;
        int left = 0;
        int curr = 0;
        for (int right = 0; right < arr.length; right++) {
            if (vowels.contains(arr[right]))
                curr++;
            while (right - left + 1 > k) {
                if (vowels.contains(arr[left++]))
                    curr--;
            }
            max = Math.max(max, curr);
        }
        return max;
    }

    public int equalSubstring(String s, String t, int maxCost) {
        int max = 0;
        int curr = 0;
        int left = 0;
        int[] absArr = new int[s.length()];
        int n = Math.min(s.length(), t.length());

        for (int i = 0; i < s.length(); i++) {
            absArr[i] = Math.abs((int) s.charAt(i) - (int) t.charAt(i));
        }
        for (int right = 0; right < n; right++) {
            curr += absArr[right];
            while (curr > maxCost) {
                curr -= absArr[right];
                left++;
            }
            max = Math.max(max, right - left + 1);
        }
        return max;
    }

    public int shareCandies(int[] candies, int k) {
        if (k == 0) return candies.length;

        int left = 0;
        HashMap<Integer, Integer> all = new HashMap<>();
        for (int candy : candies) {
            all.put(candy, all.getOrDefault(candy, 0) + 1);
        }

        int max = 0;
        for (int right = 0; right < candies.length; right++) {
            all.put(candies[right], all.get(candies[right]) - 1);
            if (all.get(candies[right]) == 0) {
                all.remove(candies[right]);
            }

            if (right - left + 1 > k) {
                all.put(candies[left], all.getOrDefault(candies[left], 0) + 1);
                left++;
            }

            // If the window size is k, calculate max.
            if (right - left + 1 == k) {
                max = Math.max(max, all.size());
            }

        }
        return max;
    }

    /**
     * 2461. Maximum Sum of Distinct Subarrays With Length K
     * You are given an integer array nums and an integer k. Find the maximum subarray sum of all the subarrays of nums that meet the following conditions:
     * <p>
     * The length of the subarray is k, and
     * All the elements of the subarray are distinct.
     * Return the maximum subarray sum of all the subarrays that meet the conditions. If no subarray meets the conditions, return 0.
     * <p>
     * A subarray is a contiguous non-empty sequence of elements within an array.
     */
    public long maximumSubarraySum(int[] nums, int k) {
        int left = 0;
        long currSum = 0;
        long max = 0;

        int maxNum = 0;
        for(int num:nums){
            maxNum = Math.max(maxNum,num);
        }
        int[] counts = new int[maxNum+1];
        Arrays.fill(counts,-1);

        for (int right = 0; right < nums.length; right++) {
            int num = nums[right];

            if (counts[num] != -1) {
                int last = counts[num];
                while (left <= last) {
                    currSum -= nums[left++];
                }
            }
            currSum += num;
            counts[num] = right;

            if (right - left == k - 1) {
                max = Math.max(currSum, max);
                currSum -= nums[left];
                counts[nums[left++]] = -1;
            }
        }
        return max;
    }

    /**
     * 2516. Take K of Each Character From Left and Right
     * You are given a string s consisting of the characters 'a', 'b', and 'c' and a non-negative integer k. Each minute, you may take either the leftmost character of s, or the rightmost character of s.
     * <p>
     * Return the minimum number of minutes needed for you to take at least k of each character, or return -1 if it is not possible to take k of each character.
     */
    public int takeCharacters(String s, int k) {
        int[] count = new int[3];
        int n = s.length();


        for (char c : s.toCharArray()) {
            count[c - 'a']++;
        }


        for (int i = 0; i < 3; i++) {
            if (count[i] < k) return -1;
        }

        int[] window = new int[3];
        int left = 0;
        int max = 0;

        for (int right = 0; right < s.length(); right++) {
            window[s.charAt(right) - 'a']++;
            while (left <= right && (count[0] - window[0] < k || count[1] - window[1] < k || count[2] - window[2] < k)) {
                window[s.charAt(left) - 'a']--;
                left++;
            }
            max = Math.max(max, right - left + 1);
        }
        return n - max;

    }
}
