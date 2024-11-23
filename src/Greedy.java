import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Greedy {
    /**
     * 2126. Destroying Asteroids
     * You are given an integer mass, which represents the original mass of a planet. You are further given an integer array asteroids, where asteroids[i] is the mass of the ith asteroid.
     * <p>
     * You can arrange for the planet to collide with the asteroids in any arbitrary order. If the mass of the planet is greater than or equal to the mass of the asteroid, the asteroid is destroyed and the planet gains the mass of the asteroid. Otherwise, the planet is destroyed.
     * <p>
     * Return true if all asteroids can be destroyed. Otherwise, return false.
     */
    public boolean asteroidsDestroyed(int mass, int[] asteroids) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int asteroid : asteroids) {
            queue.add(asteroid);
        }
        long currMass = mass;
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            if (curr > currMass)
                return false;
            currMass += curr;
        }
        return true;
    }

    /**
     * 2294. Partition Array Such That Maximum Difference Is K
     * You are given an integer array nums and an integer k. You may partition nums into one or more subsequences such that each element in nums appears in exactly one of the subsequences.
     * <p>
     * Return the minimum number of subsequences needed such that the difference between the maximum and minimum values in each subsequence is at most k.
     * <p>
     * A subsequence is a sequence that can be derived from another sequence by deleting some or no elements without changing the order of the remaining elements.
     */
    public int partitionArray(int[] nums, int k) {
        Arrays.sort(nums);
        int ans = 1;
        int x = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] - x > k) {
                x = nums[i];
                ans++;
            }
        }
        return ans;
    }

    /**
     * 502. IPO
     * Suppose LeetCode will start its IPO soon. In order to sell a good price of its shares to Venture Capital, LeetCode would like to work on some projects to increase its capital before the IPO. Since it has limited resources, it can only finish at most k distinct projects before the IPO. Help LeetCode design the best way to maximize its total capital after finishing at most k distinct projects.
     * <p>
     * You are given n projects where the ith project has a pure profit profits[i] and a minimum capital of capital[i] is needed to start it.
     * <p>
     * Initially, you have w capital. When you finish a project, you will obtain its pure profit and the profit will be added to your total capital.
     * <p>
     * Pick a list of at most k distinct projects from given projects to maximize your final capital, and return the final maximized capital.
     * <p>
     * The answer is guaranteed to fit in a 32-bit signed integer.
     */
    public int findMaximizedCapital(int k, int w, int[] profits, int[] capital) {
        return 1;
    }

    /**
     * 1323. Maximum 69 Number
     * You are given a positive integer num consisting only of digits 6 and 9.
     * <p>
     * Return the maximum number you can get by changing at most one digit (6 becomes 9, and 9 becomes 6).
     */
    public int maximum69Number(int num) {
        String s = String.valueOf(num);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '6') {
                s = s.substring(0, i) + '9' + s.substring(i + 1);
                break;
            }
        }
        return Integer.valueOf(s);
    }

    /**
     * 1710. Maximum Units on a Truck
     * You are assigned to put some amount of boxes onto one truck. You are given a 2D array boxTypes, where boxTypes[i] = [numberOfBoxesi, numberOfUnitsPerBoxi]:
     * <p>
     * numberOfBoxesi is the number of boxes of type i.
     * numberOfUnitsPerBoxi is the number of units in each box of the type i.
     * You are also given an integer truckSize, which is the maximum number of boxes that can be put on the truck. You can choose any boxes to put on the truck as long as the number of boxes does not exceed truckSize.
     * <p>
     * Return the maximum total number of units that can be put on the truck.
     */
    public int maximumUnits(int[][] boxTypes, int truckSize) {
        Arrays.sort(boxTypes, (a, b) -> b[1] - a[1]);
        int ans = 0;
        int count = 0;
        for (int[] boxType : boxTypes) {
            int totalAllowed = Math.min(truckSize - count, boxType[0]);
            ans += totalAllowed * boxType[1];
            count += totalAllowed;
            if (count == truckSize)
                return ans;
        }
        return ans;
    }

    /**
     * 1196. How Many Apples Can You Put into the Basket
     * <p>
     * You have some apples and a basket that can carry up to 5000 units of weight.
     * <p>
     * Given an integer array weight where weight[i] is the weight of the ith apple, return the maximum number of apples you can put in the basket.
     */
    public int maxNumberOfApples(int[] weight) {
        Arrays.sort(weight);
        int ans = 0;
        for (int i = 0; i < weight.length; i++) {
            if (weight[i] + ans <= 5000) {
                ans += weight[i];
            } else {
                return i + 1;
            }
        }
        return weight.length;
    }

    /**
     * 1338. Reduce Array Size to The Half
     * <p>
     * You are given an integer array arr. You can choose a set of integers and remove all the occurrences of these integers in the array.
     * <p>
     * Return the minimum size of the set so that at least half of the integers of the array are removed.
     */
    public int minSetSize(int[] arr) {
        int halfsize = arr.length / 2;

        Map<Integer, Integer> map = new HashMap<>();
        for (int num : arr)
            map.put(num, map.getOrDefault(num, 0) + 1);

        int[] bucket = new int[arr.length + 1];
        for (int num : map.keySet()) {
            int frequency = map.get(num);
            bucket[frequency]++;
        }

        int result = 0;
        for (int frequency = bucket.length - 1; frequency > 0 && halfsize > 0; frequency--) {
            while (bucket[frequency] > 0 && halfsize > 0) {
                result++;
                halfsize -= frequency;
                bucket[frequency]--;
            }
        }

        return result;
    }

    /**
     * 1833. Maximum Ice Cream Bars
     * It is a sweltering summer day, and a boy wants to buy some ice cream bars.
     * <p>
     * At the store, there are n ice cream bars. You are given an array costs of length n, where costs[i] is the price of the ith ice cream bar in coins. The boy initially has coins coins to spend, and he wants to buy as many ice cream bars as possible.
     * <p>
     * Note: The boy can buy the ice cream bars in any order.
     * <p>
     * Return the maximum number of ice cream bars the boy can buy with coins coins.
     * <p>
     * You must solve the problem by counting sort.
     */
    public int maxIceCream(int[] costs, int coins) {
        Arrays.sort(costs);
        int ans = 0;
        int count = 0;
        for (int cost : costs) {
            if (ans + cost > coins)
                break;
            ans += cost;
            count++;
        }
        return count;
    }

    /**
     * 409. Longest Palindrome
     * Given a string s which consists of lowercase or uppercase letters, return the length of the longest
     * palindrome
     * that can be built with those letters.
     * <p>
     * Letters are case sensitive, for example, "Aa" is not considered a palindrome.
     */
    public int longestPalindrome(String s) {
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : s.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        int res = 0;
        boolean hasOddFrequency = false;
        for (int freq : frequencyMap.values()) {
            if ((freq % 2) == 0) {
                res += freq;
            } else {
                res += freq - 1;
                hasOddFrequency = true;
            }
        }
        if (hasOddFrequency) return res + 1;
        return res;
    }

    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g);
        Arrays.sort(s);
        int contentChildren = 0;
        int cookieIndex = 0;
        while (cookieIndex < s.length && contentChildren < g.length) {
            if (s[cookieIndex] >= g[contentChildren]) {
                contentChildren++;
            }
            cookieIndex++;
        }
        return contentChildren;
    }

    public int largestSumAfterKNegations(int[] nums, int k) {
        Arrays.sort(nums);

        int smallestIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < 0 && k > 0) {
                nums[i] = nums[i] * -1;
                k--;
            }
            if (nums[i] < nums[smallestIndex])
                smallestIndex = i;

        }
        if (k > 0 && k % 2 != 0) {
            nums[smallestIndex] = nums[smallestIndex] * -1;
        }
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        return sum;
    }

    public int matchPlayersAndTrainers(int[] players, int[] trainers) {
        Arrays.sort(players);
        Arrays.sort(trainers);

        int playerIndex = 0;
        int trainerIndex = 0;
        while (playerIndex < players.length && trainerIndex < trainers.length) {
            if (trainers[trainerIndex] >= players[playerIndex]) {
                playerIndex++;
            }
            trainerIndex++;
        }
        return playerIndex;
    }

    /**
     * 1663. Smallest String With A Given Numeric Value
     * <p>
     * The numeric value of a lowercase character is defined as its position (1-indexed) in the alphabet, so the numeric value of a is 1, the numeric value of b is 2, the numeric value of c is 3, and so on.
     * <p>
     * The numeric value of a string consisting of lowercase characters is defined as the sum of its characters' numeric values. For example, the numeric value of the string "abe" is equal to 1 + 2 + 5 = 8.
     * <p>
     * You are given two integers n and k. Return the lexicographically smallest string with length equal to n and numeric value equal to k.
     * <p>
     * Note that a string x is lexicographically smaller than string y if x comes before y in dictionary order, that is, either x is a prefix of y, or if i is the first position such that x[i] != y[i], then x[i] comes before y[i] in alphabetic order.
     */
    public String getSmallestString(int n, int k) {
        StringBuilder ans = new StringBuilder();
        while (k > 0) {
            int next = k - n + 1;
            if (next >= 26) {
                ans.append("z");
                k -= 26;
            } else {
                ans.append(Character.toChars(next - 1 + 'a'));
                k -= next;
            }
            n--;
        }
        return ans.reverse().toString();
    }

    /**
     * 2486. Append Characters to String to Make Subsequence
     * You are given two strings s and t consisting of only lowercase English letters.
     * <p>
     * Return the minimum number of characters that need to be appended to the end of s so that t becomes a subsequence of s.
     * <p>
     * A subsequence is a string that can be derived from another string by deleting some or no characters without changing the order of the remaining characters.
     */
    public int appendCharacters(String s, String t) {
        int tIndex = 0;
        int sIndex = 0;

        while (sIndex < s.length() && tIndex < t.length()) {
            if (t.charAt(tIndex) == s.charAt(sIndex)) {
                tIndex++;
            }
            sIndex++;
        }
        return t.length() - tIndex;
    }

    /**
     * 2405. Optimal Partition of String
     * Given a string s, partition the string into one or more substrings such that the characters in each substring are unique. That is, no letter appears in a single substring more than once.
     * <p>
     * Return the minimum number of substrings in such a partition.
     * <p>
     * Note that each character should belong to exactly one substring in a partition.
     */
    public int partitionString(String s) {
        int count = 1;
        int[] lastSeen = new int[26];
        int start = 0;
        Arrays.fill(lastSeen, -1);
        for (int i = 0; i < s.length(); i++) {
            if (lastSeen[s.charAt(i) - 'a'] >= start) {
                start = i;
                count++;
            }
            lastSeen[s.charAt(i) - 'a'] = i;
        }
        return count;
    }

    /**
     * 11. Container With Most Water
     * You are given an integer array height of length n. There are n vertical lines drawn such that the two endpoints of the ith line are (i, 0) and (i, height[i]).
     * <p>
     * Find two lines that together with the x-axis form a container, such that the container contains the most water.
     * <p>
     * Return the maximum amount of water a container can store.
     * <p>
     * Notice that you may not slant the container.
     */
    public int maxArea(int[] height) {
        int maxarea = 0;
        int left = 0;
        int right = height.length - 1;
        while (left < right) {
            int width = right - left;
            maxarea = Math.max(maxarea, Math.min(height[left], height[right]) * width);
            if (height[left] <= height[right]) {
                left++;
            } else {
                right--;
            }
        }
        return maxarea;
    }

    /**
     * 2384. Largest Palindromic Number
     * You are given a string num consisting of digits only.
     * <p>
     * Return the largest palindromic integer (in the form of a string) that can be formed using digits taken from num. It should not contain leading zeroes.
     * <p>
     * Notes:
     * <p>
     * You do not need to use all the digits of num, but you must use at least one digit.
     * The digits can be reordered.
     */
    public String largestPalindromic(String num) {
        char[] chars = num.toCharArray();
        HashMap<Character, Integer> counts = new HashMap<>();
        for (char c : chars) {
            counts.put(c, counts.getOrDefault(c, 0) + 1);
        }
        List<Character> nums = new ArrayList<>(counts.keySet());
        nums.sort(Comparator.reverseOrder());

        Character middle = null;
        StringBuilder stringBuilder = new StringBuilder();
        int left = 0;
        for (Character c : nums) {
            if (stringBuilder.isEmpty() && c == '0') {
                if (nums.size() == 1)
                    return "0";
                else
                    continue;
            }
            int count = counts.get(c);
            if (count % 2 != 0 && middle == null) {
                middle = c;
                count--;
            }
            while (count > 1) {
                stringBuilder.insert(left, c);
                stringBuilder.insert(stringBuilder.length() - left, c);
                left++;
                count -= 2;
            }
        }
        if (middle != null) {
            stringBuilder.insert(left, middle);
        }
        return stringBuilder.toString();
    }

    /**
     * 2178. Maximum Split of Positive Even Integers
     * You are given an integer finalSum. Split it into a sum of a maximum number of unique positive even integers.
     * <p>
     * For example, given finalSum = 12, the following splits are valid (unique positive even integers summing up to finalSum): (12), (2 + 10), (2 + 4 + 6), and (4 + 8). Among them, (2 + 4 + 6) contains the maximum number of integers. Note that finalSum cannot be split into (2 + 2 + 4 + 4) as all the numbers should be unique.
     * Return a list of integers that represent a valid split containing a maximum number of integers. If no valid split exists for finalSum, return an empty list. You may return the integers in any order.
     */
    public List<Long> maximumEvenSplit(long finalSum) {
        List<Long> res = new ArrayList<>();
        if (finalSum % 2 == 1)
            return res;
        long currSum = 0;
        long next = 2;
        while (currSum + next + next + 2 <= finalSum) {
            currSum += next;
            res.add(next);
            next += 2;
        }
        if (finalSum != currSum)
            res.add(finalSum - currSum);
        return res;
    }

    /**
     * 2457. Minimum Addition to Make Integer Beautiful
     * You are given two positive integers n and target.
     * <p>
     * An integer is considered beautiful if the sum of its digits is less than or equal to target.
     * <p>
     * Return the minimum non-negative integer x such that n + x is beautiful. The input will be generated such that it is always possible to make n beautiful.
     */
    public long makeIntegerBeautiful(long n, int target) {
        return 1;
    }

    public int earliestFullBloom(int[] plantTime, int[] growTime) {
        int n = growTime.length;
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            indices.add(i);
        }
        Collections.sort(indices, Comparator.comparingInt(i -> -growTime[i]));
        int result = 0;
        for (int i = 0, curPlantTime = 0; i < n; ++i) {
            int idx = indices.get(i);
            int time = curPlantTime + plantTime[idx] + growTime[idx];
            result = Math.max(result, time);
            curPlantTime += plantTime[idx];
        }
        return result;
    }

    /**
     * 53. Maximum Subarray
     * Given an integer array nums, find the
     * subarray
     * with the largest sum, and return its sum.
     */
    public int maxSubArray(int[] nums) {
        int max = nums[0];
        int sum = 0;
        for (int num : nums) {
            sum += num;
            max = Math.max(sum, max);
            if (sum < 0)
                sum = 0;

        }
        return max;
    }

    /**
     * 55. Jump Game
     * You are given an integer array nums. You are initially positioned at the array's first index, and each element in the array represents your maximum jump length at that position.
     * <p>
     * Return true if you can reach the last index, or false otherwise.
     */
    public boolean canJump(int[] nums) {
        int last = 0;
        for (int i = 0; i < nums.length; i++) {
            int current = nums[i];
            if (i == nums.length - 1 || current + i >= nums.length - 1)
                return true;
            if (current == 0) {
                last = last - 1;
                if (last < 0)
                    return false;
                continue;
            }
            last = Math.max(current - 1, last - 1);
        }
        return false;
    }

    public int jump(int[] nums) {
        // The starting range of the first jump is [0, 0]
        int answer = 0, n = nums.length;
        int curEnd = 0, curFar = 0;
        for (int i = 0; i < n - 1; i++) {
            curFar = Math.max(curFar, i + nums[i]);

            if (i == curEnd) {
                curEnd = curFar;
                answer++;
            }
        }
        return answer;
    }

    public int canCompleteCircuit(int[] gas, int[] cost) {
        int totalGas = 0, totalCost = 0;
        int currentGas = 0, start = 0;

        for (int i = 0; i < gas.length; i++) {
            totalGas += gas[i];
            totalCost += cost[i];
            currentGas += gas[i] - cost[i];

            // If currentGas goes negative, we cannot start from the current start
            if (currentGas < 0) {
                start = i + 1;  // Set next station as the starting point
                currentGas = 0;  // Reset the current gas balance
            }
        }

        // If total gas is greater than or equal to total cost, return start
        return (totalGas >= totalCost) ? start : -1;
    }

    public boolean isNStraightHand(int[] hand, int groupSize) {
        int handSize = hand.length;
        if (handSize % groupSize != 0) {
            return false;
        }
        Map<Integer, Integer> cardCount = new TreeMap<>();
        for (int j : hand) {
            cardCount.put(j, cardCount.getOrDefault(j, 0) + 1);
        }
        while (!cardCount.isEmpty()) {
            int curr = cardCount.entrySet().iterator().next().getKey();
            for (int i = 0; i < groupSize; i++) {
                int next = curr + i;
                if (!cardCount.containsKey(next)) return false;
                cardCount.put(curr + i, cardCount.get(next) - 1);
                if (cardCount.get(next) == 0) cardCount.remove(next);
            }
        }
        return true;

    }

    public List<Integer> partitionLabels(String s) {
        int[] lastIndex = new int[26];
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'a';
            lastIndex[c] = i;
        }
        List<Integer> res = new ArrayList<>();
        int cur = -1;
        int currMaxLast = 0;
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'a';
            currMaxLast = Math.max(currMaxLast, lastIndex[c]);
            if (lastIndex[c] == i && currMaxLast == i) {
                res.add(i - cur);
                cur = i;
            }
        }

        return res;
    }

    public boolean checkValidString(String s) {
        Stack<Integer> openBrackets = new Stack<>();
        Stack<Integer> asterisks = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(')
                openBrackets.push(i);
            else if (c == '*') {
                asterisks.add(i);
            }
            if (c == ')') {
                if (!openBrackets.isEmpty()) {
                    openBrackets.pop();
                } else if (!asterisks.isEmpty()) {
                    asterisks.pop();
                } else {
                    return false;
                }
            }
        }
        while (!openBrackets.isEmpty() && !asterisks.isEmpty()) {
            if (asterisks.pop() < openBrackets.pop())
                return false;
        }

        return openBrackets.isEmpty();
    }

    public boolean mergeTriplets(int[][] triplets, int[] target) {
        boolean aFound = false, bFound = false, cFound = false;
        for (int[] triplet : triplets) {
            int a = triplet[0] - target[0];
            int b = triplet[1] - target[1];
            int c = triplet[2] - target[2];

            // Skip if any element is greater than the target
            if (a > 0 || b > 0 || c > 0)
                continue;

            // Set flags if exact match is found
            if (a == 0)
                aFound = true;
            if (b == 0)
                bFound = true;
            if (c == 0)
                cFound = true;

            // If all flags are true, we can return early
            if (aFound && bFound && cFound)
                return true;
        }
        return false;
    }

    /**
     * 1861. Rotating the Box
     * You are given an m x n matrix of characters box representing a side-view of a box. Each cell of the box is one of the following:
     * <p>
     * A stone '#'
     * A stationary obstacle '*'
     * Empty '.'
     * The box is rotated 90 degrees clockwise, causing some of the stones to fall due to gravity. Each stone falls down until it lands on an obstacle, another stone, or the bottom of the box. Gravity does not affect the obstacles' positions, and the inertia from the box's rotation does not affect the stones' horizontal positions.
     * <p>
     * It is guaranteed that each stone in box rests on an obstacle, another stone, or the bottom of the box.
     * <p>
     * Return an n x m matrix representing the box after the rotation described above.
     */
    public char[][] rotateTheBox(char[][] box) {
        int rows = box.length;
        int cols = box[0].length;

        for (int row = 0; row < box.length; row++) {
            int emptySpot = cols - 1;
            for (int col = cols - 1; col >= 0; col--) {
                if (box[row][col] == '#') {
                    box[row][col] = '.';
                    box[row][emptySpot--] = '#';
                } else if (box[row][col] == '*') {
                    emptySpot = col - 1;
                }
            }
        }
        char[][] res = new char[cols][rows];
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < cols; column++) {
                res[column][row] = box[rows - row - 1][column];
            }
        }
        return res;
    }
}
