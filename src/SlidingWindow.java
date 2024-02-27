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

        for(int i=0;i<s.length();i++){
            absArr[i] = Math.abs((int)s.charAt(i) - (int) t.charAt(i));
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
}
