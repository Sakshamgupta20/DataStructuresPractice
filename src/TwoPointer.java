import java.lang.reflect.Array;
import java.util.*;

public class TwoPointer {
    public String reverseWords(String s) {
        int left = 0;
        int right = 0;
        StringBuilder result = new StringBuilder();
        while (left < s.length()) {
            if (s.charAt(left) != ' ')
                result.insert(right, s.charAt(left));
            else {
                result.append(" ");
                right = left + 1;
            }
            left++;
        }
        return result.toString();
    }

    public String reverseOnlyLetters(String s) {
        char[] arr = s.toCharArray();
        int left = 0;
        int right = arr.length - 1;
        while (right > left) {
            if (Character.isAlphabetic(arr[right]) && Character.isAlphabetic(arr[left]))
                swap(arr, left++, right--);
            else if (!Character.isAlphabetic(arr[right]))
                right--;
            else
                left++;
        }
        return new String(arr);
    }

    public int getCommon(int[] nums1, int[] nums2) {
        int min = Integer.MAX_VALUE;
        int left = 0;
        int right = 0;
        boolean val = false;
        while (left < nums1.length && right < nums2.length)
            if (nums1[left] == nums2[right]) {
                val = true;
                min = Math.min(nums1[left], min);
                left++;
                right++;
            } else if (nums1[left] < nums2[right]) {
                left++;
            } else {
                right++;
            }
        return val ? min : -1;
    }

    public void moveZeroes(int[] nums) {
        int left = 0;
        int right = 0;
        while (right < nums.length && left < nums.length) {
            if (nums[right] != 0)
                nums[left++] = nums[right];
            right++;
        }
        for (int i = left; i < nums.length; i++) {
            nums[i] = 0;
        }
    }

    public String reversePrefix(String word, char ch) {
        char[] arr = word.toCharArray();
        int index = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == ch) {
                index = i;
                break;
            }
        }
        if (index == -1)
            return word;
        for (int i = 0; i <= index / 2; i++) {
            swap(arr, i, index - i);
        }
        return new String(arr);
    }

    public int trap(int[] height) {
        int maxLeft = 0;
        int maxRight = 0;
        int left = 0, right = height.length - 1;
        int ans = 0;
        while (left < right) {
            if (height[left] < height[right]) {
                maxLeft = Math.max(maxRight, height[left]);
                ans += maxLeft - height[left];
                ++left;
            } else {
                maxRight = Math.max(maxRight, height[right]);
                ans += maxRight - height[right];
                --right;
            }
        }
        return ans;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        int n = nums.length;
        Arrays.sort(nums);
        List<List<Integer>> ans = new ArrayList<>();
        for (int i = 0; i < n && nums[i] <= 0; i++) {
            if (i==0 || nums[i]!=nums[i-1]) {
                int start = i+1, end  = n-1;
                int target = -nums[i];
                while (start < end) {
                    int sum = nums[start] + nums[end];
                    if (sum < target) {
                        start++;
                    } else if (sum > target) {
                        end--;
                    } else {
                        ans.add(Arrays.asList(nums[i], nums[start], nums[end]));
                        start++;
                        end--;
                        while(start < end && nums[start] == nums[start-1]) {
                            start++;
                        }
                    }
                }
            }
        }
        return ans;
    }



    private void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
