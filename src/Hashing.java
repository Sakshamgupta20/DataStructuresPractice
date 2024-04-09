import java.util.*;
import java.util.stream.Collectors;

public class Hashing {
    public boolean checkIfPangram(String sentence) {
        Set chars = new HashSet();
        for (int i = 0; i < sentence.length(); i++) {
            chars.add(sentence.charAt(i));
        }
        return chars.size() == 26;
    }

    public int missingNumber(int[] nums) {
        int total = 0;
        for (int num : nums) {
            total += num;
        }
        int n = nums.length;
        return (n * (n + 1) / 2) - total;
    }

    public int countElements(int[] arr) {
        Set<Integer> counts = new HashSet<>();
        int res = 0;
        for (int num : arr)
            counts.add(num);

        for (int num : arr) {
            int exp = num + 1;
            if (counts.contains(exp))
                res += 1;
        }
        return res;
    }

    public List<List<Integer>> findWinners(int[][] matches) {
        Set<Integer> winners = new HashSet<>();
        HashMap<Integer, Integer> loosers = new HashMap<>();
        for (int[] match : matches) {
            if (!loosers.containsKey(match[0]))
                winners.add(match[0]);
            winners.remove(match[1]);
            loosers.put(match[1], loosers.getOrDefault(match[1], 0) + 1);
        }
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> onlyOneMatchLoose = new ArrayList<>();
        loosers.forEach((player, times) -> {
            if (times == 1)
                onlyOneMatchLoose.add(player);
        });
        List<Integer> winne = new ArrayList<>(winners);
        Collections.sort(winne);
        Collections.sort(onlyOneMatchLoose);
        res.add(winne);
        res.add(onlyOneMatchLoose);
        return res;
    }

    public int largestUniqueNumber(int[] nums) {
        int[] arr = new int[1001];
        Arrays.fill(arr, 0);
        for (int num : nums) {
            arr[num] += 1;
        }
        for (int i = arr.length - 1; i > 0; i--) {
            if (arr[i] == 1)
                return i;
        }
        return -1;
    }

    public int maxNumberOfBalloons(String text) {
        HashMap<Character, Integer> balloon = new HashMap<>();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == 'b' || c == 'a' || c == 'l' || c == 'o' || c == 'n') {
                balloon.put(c, balloon.getOrDefault(c, 0) + 1);
            }
        }

        int max = Integer.MAX_VALUE;
        if (balloon.keySet().size() != 5)
            return 0;
        for (Character c : balloon.keySet()) {
            if (c == 'o' || c == 'l')
                max = (int) Math.min(max, Math.floor((double) balloon.get(c) / 2));
            else
                max = Math.min(max, balloon.get(c));
        }
        return max;
    }

    /**
     * Given a binary array nums, return the maximum length of a contiguous subarray with an equal number of 0 and 1.
     * pair of 0,1 should come together
     */
    public int findMaxLength(int[] nums) {
        int left = 0;
        int[] sum = new int[2];
        Arrays.fill(sum, 0);
        int max = 0;
        for (int right = 0; right < nums.length; right++) {
            if (nums[right] == 0)
                sum[0]++;
            else
                sum[1]++;

            int diff = Math.abs(sum[0] - sum[1]);
            if (diff > 1) {
                sum[nums[left++]]--;
            } else if (diff == 0) {
                max = Math.max(max, sum[0] + sum[1]);
            }
        }
        return max;
    }

    /**
     * TODO: DO AGAIN
     * Given a binary array nums, return the maximum length of a contiguous subarray with an equal number of 0 and 1.
     */
    public int findMaxLengthOfSubStringWithEqualZeroOne(int[] nums) {
        int prefixSum = 0;
        int max = 0;
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            prefixSum += nums[i] == 0 ? -1 : 1;
            if (prefixSum == 0)
                max = i + 1;
            else if (map.containsKey(prefixSum)) {
                max = Math.max(max, i - map.get(prefixSum));
            } else
                map.put(prefixSum, i);
        }
        return max;
    }

    public boolean canConstruct(String ransomNote, String magazine) {
        HashMap<Character, Integer> counts = new HashMap<>();
        for (int i = 0; i < magazine.length(); i++) {
            char c = magazine.charAt(i);
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }

        for (int i = 0; i < ransomNote.length(); i++) {
            char c = ransomNote.charAt(i);
            if (!counts.containsKey(c) || counts.get(c) == 0)
                return false;
            counts.put(c, counts.get(c) - 1);
        }
        return true;
    }

    public int numJewelsInStones(String jewels, String stones) {
        HashMap<Character, Integer> counts = new HashMap<>();
        for (int i = 0; i < stones.length(); i++) {
            char c = stones.charAt(i);
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }

        int res = 0;
        for (int i = 0; i < jewels.length(); i++) {
            char c = jewels.charAt(i);
            if (!counts.containsKey(c))
                continue;
            if (counts.get(c) > 0) {
                res += counts.get(c);
                counts.put(c, 0);
            }
        }
        return res;
    }

    /**
     * Given a string s, find the length of the longest substring without repeating characters.
     */
    public int lengthOfLongestSubstring(String s) {
        HashMap<Character, Integer> chars = new HashMap<>();
        char[] arr = s.toCharArray();
        int left = 0;
        int max = 0;
        for (int right = 0; right < s.length(); right++) {
            if (chars.containsKey(arr[right]) && chars.get(arr[right]) >= left) {
                //Update left index
                left = chars.get(arr[right]) + 1;
            }
            chars.put(arr[right], right);
            max = Math.max(max, right - left + 1);
        }
        return max;
    }

    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> dups = new HashSet<>();
        for (int num : nums) {
            if (dups.contains(num))
                return true;
            dups.add(num);
        }
        return false;
    }

    /**
     * Given a string path, where path[i] = 'N', 'S', 'E' or 'W', each representing moving one unit north, south, east, or west, respectively. You start at the origin (0, 0) on a 2D plane and walk on the path specified by path.
     * Return true if the path crosses itself at any point, that is, if at any time you are on a location you have previously visited. Return false otherwise.
     * TODO: Again
     */
    public boolean isPathCrossing(String path) {
        HashSet<String> route = new HashSet<>();
        int x = 0;
        int y = 0;
        route.add(x + "#" + y);
        for (int i = 0; i < path.length(); i++) {
            char c = path.charAt(i);
            if (c == 'N') y++;
            if (c == 'S') y--;
            if (c == 'E') x++;
            if (c == 'W') x--;
            String str = x + "#" + y;
            if (route.contains(str)) return true;
            else route.add(str);
        }
        return false;
    }

    public int sumOfUnique(int[] nums) {
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (int num : nums) {
            counts.put(num, counts.getOrDefault(num, 0) + 1);
        }
        int sum = 0;
        for (Integer key : counts.keySet()) {
            if (counts.get(key) == 1)
                sum += key;
        }
        return sum;
    }

    public int maxFrequencyElements(int[] nums) {
        int[] arr = new int[101];
        int max = 0;
        int total = 0;
        for (int num : nums) {
            arr[num]++;
            int frequency = arr[num];
            if (max == frequency) {
                total += frequency;
            } else if (frequency > max) {
                max = frequency;
                total = frequency;
            }
        }

        return total;
    }

    public int findLucky(int[] arr) {
        int[] dups = new int[501];
        for (int num : arr) {
            dups[num]++;
        }
        int max = -1;
        for (int i = 0; i < dups.length; i++) {
            if (i == dups[i] && dups[i] != 0)
                max = Math.max(i, max);
        }
        return max;
    }

    /**
     * Given an array of integers arr, return true if the number of occurrences of each value in the array is unique or false otherwise.
     */
    public boolean uniqueOccurrences(int[] arr) {
        HashMap<Integer, Integer> counts = new HashMap<>();
        HashMap<Integer, Integer> occurrences = new HashMap<>();
        for (int i : arr) {
            counts.put(i, counts.getOrDefault(i, 0) + 1);
            int occurrence = counts.get(i);
            occurrences.put(occurrence, occurrences.getOrDefault(occurrence, 0) + 1);
            occurrences.put(occurrence - 1, occurrences.getOrDefault(occurrence - 1, 0) - 1);
        }
        for (Integer key : occurrences.keySet()) {
            if (occurrences.get(key) > 1)
                return false;
        }
        return true;
    }

    public String frequencySort(String s) {
        HashMap<Character, Integer> counts = new HashMap<>();
        List<List<Character>> buckets = new ArrayList<>();
        for (char c : s.toCharArray()) {
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }
        int maxFreq = Collections.max(counts.values());
        for (int i = 0; i <= maxFreq; i++) {
            buckets.add(new ArrayList<>());
        }

        for (Character key : counts.keySet()) {
            int freq = counts.get(key);
            buckets.get(freq).add(key);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = buckets.size() - 1; i > 0; i--) {
            for (Character c : buckets.get(i)) {
                sb.append(String.valueOf(c).repeat(i));
            }
        }
        return sb.toString();
    }

    /**
     * You are given an integer array nums and an integer k.
     * <p>
     * The frequency of an element x is the number of times it occurs in an array.
     * <p>
     * An array is called good if the frequency of each element in this array is less than or equal to k.
     * <p>
     * Return the length of the longest good subarray of nums.
     * <p>
     * A subarray is a contiguous non-empty sequence of elements within an array.
     */
    public int maxSubarrayLength(int[] nums, int k) {
        int left = 0;
        int max = -1;
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (int right = 0; right < nums.length; right++) {
            int num = nums[right];
            counts.put(num, counts.getOrDefault(num, 0) + 1);
            while (counts.get(num) > k) {
                counts.put(nums[left], counts.get(nums[left]) - 1);
                left++;
            }

            max = Math.max(max, right - left + 1);
        }
        return max;
    }

    public int numIdenticalPairs(int[] nums) {
        HashMap<Integer, Integer> counts = new HashMap<>();
        for (int right = 0; right < nums.length; right++) {
            int num = nums[right];
            counts.put(num, counts.getOrDefault(num, 0) + 1);
        }
        int sum = 0;
        for (Integer key : counts.keySet()) {
            int count = counts.get(key);
            sum += ((count * (count - 1)) / 2);
        }
        return sum;
    }

    /**
     * Given an array of integers nums and an integer k, return the total number of subarrays whose sum equals to k.
     * A subarray is a contiguous non-empty sequence of elements within an array.
     */
    public int subarraySum(int[] nums, int k) {
        HashMap<Integer, Integer> preifx = new HashMap<>();
        preifx.put(0, 1);
        int sum = 0;
        int res = 0;
        for (int num : nums) {
            res += num;
            if (preifx.containsKey(res - k))
                sum += preifx.get(res - k);
            preifx.put(res, preifx.getOrDefault(res, 0) + 1);
        }
        return sum;
    }

    /**
     * Given an array of integers nums and an integer k. A continuous subarray is called nice if there are k odd numbers on it.
     * <p>
     * Return the number of nice sub-arrays.
     **/
    public int numberOfSubarrays(int[] nums, int k) {
        HashMap<Integer, Integer> preifx = new HashMap<>();
        preifx.put(0, 1);
        int sum = 0;
        int res = 0;
        for (int num : nums) {
            if (num % 2 != 0)
                res += 1;
            if (preifx.containsKey(res - k))
                sum += preifx.get(res - k);
            preifx.put(res, preifx.getOrDefault(res, 0) + 1);
        }
        return sum;
    }

    /**
     * Given a binary array nums and an integer goal, return the number of non-empty subarrays with a sum goal.
     * <p>
     * A subarray is a contiguous part of the array.
     */
    public int numSubarraysWithSum(int[] nums, int goal) {
        HashMap<Integer, Integer> prefix = new HashMap<>();
        prefix.put(0, 1);
        int res = 0;
        int prefixSum = 0;
        for (int num : nums) {
            prefixSum += num;
            if (prefix.containsKey(prefixSum - goal))
                res += prefix.get(prefixSum - goal);
            prefix.put(prefixSum, prefix.getOrDefault(prefixSum, 0) + 1);
        }
        return res;
    }

    /**
     * Given a binary array nums and an integer goal, return the number of non-empty subarrays with a sum goal.
     * <p>
     * A subarray is a contiguous part of the array.
     * <p>
     * <p>
     * Here idea is to subtract numSubarraysWithSumSlidingWindowApproach(nums,goal) and numSubarraysWithSumSlidingWindowApproach(nums,goal - 1)
     * https://leetcode.com/problems/binary-subarrays-with-sum/editorial/
     */
    public int numSubarraysWithSumSlidingWindowApproach(int[] nums, int goal) {
        int sum = 0;
        int left = 0;
        int res = 0;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (sum > goal && left <= right) {
                sum -= nums[left++];
            }
            res += right - left + 1;
        }
        return res;
    }

    /**
     * Given a binary array nums and an integer goal, return the number of non-empty subarrays with a sum goal.
     * <p>
     * A subarray is a contiguous part of the array.
     * <p>
     * <p>
     * Here idea is to keep track of zeros
     */
    public int numSubarraysWithSumSingleSlidingWindowApproach(int[] nums, int goal) {
        int sum = 0;
        int left = 0;
        int zeros = 0;
        int res = 0;
        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];
            while (left < right && (nums[left] == 0 || sum > goal)) {
                if (nums[left] == 1) {
                    zeros = 0;
                } else
                    zeros += 1;

                sum -= nums[left++];
            }

            if (sum == goal)
                res += 1 + zeros;
        }
        return res;
    }

    /**
     * You are given an array of positive integers nums and want to erase a subarray containing unique elements. The score you get by erasing the subarray is equal to the sum of its elements.
     * <p>
     * Return the maximum score you can get by erasing exactly one subarray.
     * <p>
     * An array b is called to be a subarray of a if it forms a contiguous subsequence of a, that is, if it is equal to a[l],a[l+1],...,a[r] for some (l,r).
     */
    public int maximumUniqueSubarray(int[] nums) {
        int max = 0;
        int left = 0;
        int[] sum = new int[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }

        Map<Integer, Integer> seen = new HashMap<>();
        for (int right = 0; right < nums.length; right++) {
            if (seen.containsKey(nums[right]) && left <= seen.get(nums[right])) {
                left = seen.get(nums[right]) + 1;
            }
            seen.put(nums[right], right);

            max = Math.max(max, sum[right] - sum[left] + nums[left]);
        }
        return max;

    }

    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length())
            return false;

        HashMap<Character, Character> sMap = new HashMap<>();
        HashSet<Character> assigned = new HashSet<>();
        for (int i = 0; i < s.length(); i++) {
            if (sMap.containsKey(s.charAt(i)) && sMap.get((s.charAt(i))) != t.charAt(i))
                return false;
            if (!sMap.containsKey(s.charAt(i)) && assigned.contains(t.charAt(i)))
                return false;
            sMap.put(s.charAt(i), t.charAt(i));
            assigned.add(t.charAt(i));
        }
        return true;
    }

    public boolean wordPattern(String pattern, String s) {
        String[] tArr = s.split(" ");
        if (pattern.length() != tArr.length)
            return false;

        HashMap<Character, String> sMap = new HashMap<>();
        HashSet<String> assigned = new HashSet<>();
        for (int i = 0; i < pattern.length(); i++) {
            if (sMap.containsKey(pattern.charAt(i)) && !Objects.equals(sMap.get((pattern.charAt(i))), tArr[i]))
                return false;
            if (!sMap.containsKey(pattern.charAt(i)) && assigned.contains(tArr[i]))
                return false;
            sMap.put(pattern.charAt(i), tArr[i]);
            assigned.add(tArr[i]);
        }
        return true;
    }

    public String customSortString(String order, String s) {
        HashMap<Character, Integer> counts = new HashMap<>();
        for (char c : s.toCharArray()) {
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }

        StringBuilder assigned = new StringBuilder();
        for (char c : order.toCharArray()) {
            if (counts.containsKey(c)) {
                assigned.append(String.valueOf(c).repeat(counts.get(c)));
            }
            counts.remove(c);
        }

        counts.keySet().forEach(c -> {
            assigned.append(String.valueOf(c).repeat(counts.get(c)));
        });


        return assigned.toString();
    }

    /**
     * Two strings are considered close if you can attain one from the other using the following operations:
     * <p>
     * Operation 1: Swap any two existing characters.
     * For example, abcde -> aecdb
     * Operation 2: Transform every occurrence of one existing character into another existing character, and do the same with the other character.
     * For example, aacabb -> bbcbaa (all a's turn into b's, and all b's turn into a's)
     * You can use the operations on either string as many times as necessary.
     * <p>
     * Given two strings, word1 and word2, return true if word1 and word2 are close, and false otherwise.
     */
    public boolean closeStrings(String word1, String word2) {
        if (word1.length() != word2.length())
            return false;

        int[] word1Map = new int[26];
        int[] word2Map = new int[26];

        for (char c : word1.toCharArray()) {
            word1Map[c - 'a']++;
        }

        for (char c : word2.toCharArray()) {
            word2Map[c - 'a']++;
        }

        for (int i = 0; i < 26; i++) {
            if ((word1Map[i] == 0 && word2Map[i] != 0)
                    || (word2Map[i] == 0 && word1Map[i] != 0))
                return false;
        }

        Arrays.sort(word1Map);
        Arrays.sort(word2Map);
        return Arrays.equals(word1Map, word2Map);

    }
}
