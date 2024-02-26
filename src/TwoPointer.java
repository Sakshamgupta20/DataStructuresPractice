import java.util.Arrays;

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
        for (int i = 0; i <= index /2; i++) {
            swap(arr, i, index - i);
        }
        return new String(arr);
    }

    private void swap(char[] arr, int i, int j) {
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
