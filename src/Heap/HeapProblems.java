package Heap;

import common.CommonUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HeapProblems {

    /**
     * 1046. Last Stone Weight
     * Easy
     * Topics
     * Companies
     * Hint
     * You are given an array of integers stones where stones[i] is the weight of the ith stone.
     * <p>
     * We are playing a game with the stones. On each turn, we choose the heaviest two stones and smash them together. Suppose the heaviest two stones have weights x and y with x <= y. The result of this smash is:
     * <p>
     * If x == y, both stones are destroyed, and
     * If x != y, the stone of weight x is destroyed, and the stone of weight y has new weight y - x.
     * At the end of the game, there is at most one stone left.
     * <p>
     * Return the weight of the last remaining stone. If there are no stones left, return 0.
     *
     * @param stones
     * @return
     */
    public int lastStoneWeight(int[] stones) {
        PriorityQueue<Integer> sto = new PriorityQueue<>((a, b) -> b - a);
        for (int stone : stones) {
            sto.add(stone);
        }
        while (sto.size() > 1) {
            int y = sto.poll();
            int x = sto.poll();
            if (x != y)
                sto.add(y - x);
        }
        return sto.isEmpty() ? 0 : sto.poll();
    }

    /**
     * 2208. Minimum Operations to Halve Array Sum
     * Medium
     * Topics
     * Companies
     * Hint
     * You are given an array nums of positive integers. In one operation, you can choose any number from nums and reduce it to exactly half the number. (Note that you may choose this reduced number in future operations.)
     * <p>
     * Return the minimum number of operations to reduce the sum of nums by at least half.
     *
     * @param nums
     * @return
     */
    public int halveArray(int[] nums) {
        PriorityQueue<Double> queue = new PriorityQueue<>(Comparator.reverseOrder());
        double sum = 0;
        for (int num : nums) {
            sum += num;
            queue.add((double) num);
        }
        double targetSum = sum / 2;
        double currSum = 0;
        int operations = 0;
        while (currSum < targetSum && !queue.isEmpty()) {
            operations += 1;
            double num = queue.poll() / 2;
            currSum += num;
            queue.add(num);
        }
        return operations;

    }

    /**
     * 1962. Remove Stones to Minimize the Total
     * Medium
     * Topics
     * Companies
     * Hint
     * You are given a 0-indexed integer array piles, where piles[i] represents the number of stones in the ith pile, and an integer k. You should apply the following operation exactly k times:
     * <p>
     * Choose any piles[i] and remove floor(piles[i] / 2) stones from it.
     * Notice that you can apply the operation on the same pile more than once.
     * <p>
     * Return the minimum possible total number of stones remaining after applying the k operations.
     * <p>
     * floor(x) is the greatest integer that is smaller than or equal to x (i.e., rounds x down).
     *
     * @param piles
     * @param k
     * @return
     */
    public int minStoneSum(int[] piles, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(Comparator.reverseOrder());
        int sum = 0;
        for (int num : piles) {
            sum += num;
            queue.add(num);
        }
        for (int i = 0; i < k; i++) {
            int num = queue.poll();
            int remove = (int) (double) (num / 2);
            sum -= remove;
            queue.add(num - remove);
        }
        return sum;
    }

    /**
     * 1167. Minimum Cost to Connect Sticks
     * Medium
     * Topics
     * Companies
     * Hint
     * You have some number of sticks with positive integer lengths. These lengths are given as an array sticks, where sticks[i] is the length of the ith stick.
     * <p>
     * You can connect any two sticks of lengths x and y into one stick by paying a cost of x + y. You must connect all the sticks until there is only one stick remaining.
     * <p>
     * Return the minimum cost of connecting all the given sticks into one stick in this way.
     */
    public int connectSticks(int[] sticks) {
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int num : sticks) {
            queue.add(num);
        }
        int sum = 0;
        while (queue.size() > 1) {
            int one = queue.poll();
            int two = queue.poll();
            int s = one + two;
            sum += s;
            queue.add(s);
        }
        return sum;
    }

    /**
     * 215. Kth Largest Element in an Array
     * Medium
     * Topics
     * Companies
     * Given an integer array nums and an integer k, return the kth largest element in the array.
     * <p>
     * Note that it is the kth largest element in the sorted order, not the kth distinct element.
     * <p>
     * Can you solve it without sorting?
     */
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        for (int num : nums) {
            heap.add(num);
            if (heap.size() > k) {
                heap.remove();
            }
        }
        return heap.peek();
    }

    /**
     * 973. K Closest Points to Origin
     * <p>
     * Given an array of points where points[i] = [xi, yi] represents a point on the X-Y plane and an integer k, return the k closest points to the origin (0, 0).
     * <p>
     * The distance between two points on the X-Y plane is the Euclidean distance (i.e., √(x1 - x2)2 + (y1 - y2)2).
     * <p>
     * You may return the answer in any order. The answer is guaranteed to be unique (except for the order that it is in).
     */
    public int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> heap = new PriorityQueue<>((a, b) -> b[0] - a[0]);
        int[][] ans = new int[k][];
        for (int i = 0; i < points.length; i++) {
            int[] entry = {(int) (Math.pow(points[i][0], 2) + Math.pow(points[i][1], 2)), i};
            heap.add(entry);
            if (heap.size() > k) {
                heap.poll();
            }
        }
        for (int i = 0; i < k; i++) {
            ans[i] = points[heap.poll()[1]];
        }
        return ans;
    }

    /**
     * 692. Top K Frequent Words
     * Given an array of strings words and an integer k, return the k most frequent strings.
     * <p>
     * Return the answer sorted by the frequency from highest to lowest. Sort the words with the same frequency by their lexicographical order.
     */
    public List<String> topKFrequent(String[] words, int k) {
        HashMap<String, Integer> counts = new HashMap<>();
        for (String word : words) {
            counts.put(word, counts.getOrDefault(word, 0) + 1);
        }
        PriorityQueue<String> heap = new PriorityQueue<>((a, b) -> {
            if ((counts.get(b) - counts.get(a)) == 0) {
                return b.compareTo(a);
            } else return counts.get(a) - counts.get(b);
        });
        for (String num : counts.keySet()) {
            heap.add(num);
            if (heap.size() > k) {
                heap.poll();
            }
        }

        List<String> ans = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            ans.add(heap.poll());
        }
        Collections.reverse(ans);
        return ans;
    }

    /**
     * 2182. Construct String With Repeat Limit
     * You are given a string s and an integer repeatLimit. Construct a new string repeatLimitedString using the characters of s such that no letter appears more than repeatLimit times in a row. You do not have to use all characters from s.
     * <p>
     * Return the lexicographically largest repeatLimitedString possible.
     * <p>
     * A string a is lexicographically larger than a string b if in the first position where a and b differ, string a has a letter that appears later in the alphabet than the corresponding letter in b. If the first min(a.length, b.length) characters do not differ, then the longer string is the lexicographically larger one.
     */
    public String repeatLimitedString(String s, int repeatLimit) {
        PriorityQueue<Character> pq = new PriorityQueue<Character>((a, b) -> b - a);
        for (char ch : s.toCharArray()) {
            pq.add(ch);
        }
        StringBuffer res = new StringBuffer();
        ArrayList<Character> list = new ArrayList<Character>();
        Stack<Character> stk = new Stack<Character>();
        int count = 0;
        char previouschar = pq.peek();
        while (!pq.isEmpty()) {
            char curr = pq.poll();
            if (curr == previouschar) {
                if (count < repeatLimit) {
                    res.append(curr);
                } else {
                    stk.add(curr);
                }
                count++;
            } else {
                if (stk.isEmpty()) {
                    count = 0;
                    res.append(curr);
                    previouschar = curr;
                    count++;
                } else {
                    res.append(curr);
                    count = 0;
                    while (!stk.isEmpty() && count < repeatLimit) {
                        res.append(stk.pop());
                        count++;
                    }
                }
            }
        }
        return res.toString();
    }

    /**
     * 2462. Total Cost to Hire K Workers
     * You are given a 0-indexed integer array costs where costs[i] is the cost of hiring the ith worker.
     * <p>
     * You are also given two integers k and candidates. We want to hire exactly k workers according to the following rules:
     * <p>
     * You will run k sessions and hire exactly one worker in each session.
     * In each hiring session, choose the worker with the lowest cost from either the first candidates workers or the last candidates workers. Break the tie by the smallest index.
     * For example, if costs = [3,2,7,7,1,2] and candidates = 2, then in the first hiring session, we will choose the 4th worker because they have the lowest cost [3,2,7,7,1,2].
     * In the second hiring session, we will choose 1st worker because they have the same lowest cost as 4th worker but they have the smallest index [3,2,7,7,2]. Please note that the indexing may be changed in the process.
     * If there are fewer than candidates workers remaining, choose the worker with the lowest cost among them. Break the tie by the smallest index.
     * A worker can only be chosen once.
     * Return the total cost to hire exactly k workers.
     */
    public long totalCost(int[] costs, int k, int candidates) {
        PriorityQueue<Integer> left = new PriorityQueue<>();
        PriorityQueue<Integer> right = new PriorityQueue<>();

        int leftIndex = 0;
        int rightIndex = costs.length - 1;
        long cost = 0;
        boolean middleAdded = false;
        for (int i = 0; i < k; i++) {
            while (left.size() < candidates && leftIndex <= rightIndex && !middleAdded) {
                if (leftIndex == rightIndex)
                    middleAdded = true;
                left.add(costs[leftIndex++]);
            }
            while (right.size() < candidates && leftIndex <= rightIndex && !middleAdded) {
                if (leftIndex == rightIndex)
                    middleAdded = true;
                right.add(costs[rightIndex--]);
            }
            if (!left.isEmpty() && !right.isEmpty()) {
                if (left.peek() <= right.peek()) {
                    cost += left.poll();
                } else {
                    cost += right.poll();
                }
            } else if (!left.isEmpty()) {
                cost += left.poll();
            } else if (!right.isEmpty())
                cost += right.poll();
        }
        return cost;
    }

    /**
     * 373. Find K Pairs with Smallest Sums
     * You are given two integer arrays nums1 and nums2 sorted in non-decreasing order and an integer k.
     * <p>
     * Define a pair (u, v) which consists of one element from the first array and one element from the second array.
     * <p>
     * Return the k pairs (u1, v1), (u2, v2), ..., (uk, vk) with the smallest sums.
     */
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        int m = nums1.length;
        int n = nums2.length;

        List<List<Integer>> ans = new ArrayList<>();

        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        Set<String> visited = new HashSet<>();
        queue.offer(new int[]{nums1[0] + nums2[0], 0, 0});
        visited.add(CommonUtils.convertToHash(0, 0));
        while (k-- > 0 && !queue.isEmpty()) {
            int[] top = queue.poll();
            int i = top[1];
            int j = top[2];
            ans.add(List.of(nums1[i], nums2[j]));

            if (i + 1 < m && !visited.contains(CommonUtils.convertToHash(i + 1, j))) {
                queue.offer(new int[]{nums1[i + 1] + nums2[j], i + 1, j});
                visited.add(CommonUtils.convertToHash(i + 1, j));
            }

            if (j + 1 < n && !visited.contains(CommonUtils.convertToHash(i, j + 1))) {
                queue.offer(new int[]{nums1[i] + nums2[j + 1], i, j + 1});
                visited.add(CommonUtils.convertToHash(i, j + 1));
            }
        }
        return ans;
    }

    /**
     * 2402. Meeting Rooms III
     * You are given an integer n. There are n rooms numbered from 0 to n - 1.
     * <p>
     * You are given a 2D integer array meetings where meetings[i] = [starti, endi] means that a meeting will be held during the half-closed time interval [starti, endi). All the values of starti are unique.
     * <p>
     * Meetings are allocated to rooms in the following manner:
     * <p>
     * Each meeting will take place in the unused room with the lowest number.
     * If there are no available rooms, the meeting will be delayed until a room becomes free. The delayed meeting should have the same duration as the original meeting.
     * When a room becomes unused, meetings that have an earlier original start time should be given the room.
     * Return the number of the room that held the most meetings. If there are multiple rooms, return the room with the lowest number.
     * <p>
     * A half-closed interval [a, b) is the interval between a and b including a and not including b.
     * <p>
     * <p>
     * <p>
     * Example 1:
     * <p>
     * Input: n = 2, meetings = [[0,10],[1,5],[2,7],[3,4]]
     * Output: 0
     * Explanation:
     * - At time 0, both rooms are not being used. The first meeting starts in room 0.
     * - At time 1, only room 1 is not being used. The second meeting starts in room 1.
     * - At time 2, both rooms are being used. The third meeting is delayed.
     * - At time 3, both rooms are being used. The fourth meeting is delayed.
     * - At time 5, the meeting in room 1 finishes. The third meeting starts in room 1 for the time period [5,10).
     * - At time 10, the meetings in both rooms finish. The fourth meeting starts in room 0 for the time period [10,11).
     * Both rooms 0 and 1 held 2 meetings, so we return 0.
     * Example 2:
     * <p>
     * Input: n = 3, meetings = [[1,20],[2,10],[3,5],[4,9],[6,8]]
     * Output: 1
     * Explanation:
     * - At time 1, all three rooms are not being used. The first meeting starts in room 0.
     * - At time 2, rooms 1 and 2 are not being used. The second meeting starts in room 1.
     * - At time 3, only room 2 is not being used. The third meeting starts in room 2.
     * - At time 4, all three rooms are being used. The fourth meeting is delayed.
     * - At time 5, the meeting in room 2 finishes. The fourth meeting starts in room 2 for the time period [5,10).
     * - At time 6, all three rooms are being used. The fifth meeting is delayed.
     * - At time 10, the meetings in rooms 1 and 2 finish. The fifth meeting starts in room 1 for the time period [10,12).
     * Room 0 held 1 meeting while rooms 1 and 2 each held 2 meetings, so we return 1.
     */
    public int mostBooked(int n, int[][] meetings) {

        int[] meetingCount = new int[n];
        PriorityQueue<long[]> endTimes = new PriorityQueue<long[]>((a, b) -> a[0] != b[0] ? Long.compare(a[0], b[0]) : Long.compare(a[1], b[1]));
        PriorityQueue<Integer> unusedRooms = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            unusedRooms.offer(i);
        }
        Arrays.sort(meetings, (a, b) -> a[0] != b[0] ? Integer.compare(a[0], b[0]) : Integer.compare(a[1], b[1]));

        for (int[] meeting : meetings) {
            int start = meeting[0], end = meeting[1];

            while (!endTimes.isEmpty() && endTimes.peek()[0] <= start) {
                int room = (int) endTimes.poll()[1];
                unusedRooms.offer(room);
            }
            if (!unusedRooms.isEmpty()) {
                int room = unusedRooms.poll();
                endTimes.offer(new long[]{end, room});
                meetingCount[room]++;
            } else {
                long[] oldMeeting = endTimes.poll();
                int room = (int) oldMeeting[1];
                long endTime = oldMeeting[0];
                long newMeetingEndTime = endTime + (end - start);
                meetingCount[room]++;
                endTimes.add(new long[]{newMeetingEndTime, room});
            }
        }
        int maxMeetingCount = 0, maxMeetingCountRoom = 0;
        for (int i = 0; i < n; i++) {
            if (meetingCount[i] > maxMeetingCount) {
                maxMeetingCount = meetingCount[i];
                maxMeetingCountRoom = i;
            }
        }

        return maxMeetingCountRoom;

    }

    /**
     * 621. Task Scheduler
     * You are given an array of CPU tasks, each labeled with a letter from A to Z, and a number n. Each CPU interval can be idle or allow the completion of one task. Tasks can be completed in any order, but there's a constraint: there has to be a gap of at least n intervals between two tasks with the same label.
     * <p>
     * Return the minimum number of CPU intervals required to complete all tasks.
     *
     * @param tasks
     * @param n
     * @return
     */
    public int leastInterval(char[] tasks, int n) {
        Map<Character, Integer> counts = new HashMap<>();
        for (char task : tasks) {
            counts.put(task, counts.getOrDefault(task, 0) + 1);
        }

        // Max-heap to always pick the most frequent task
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
        maxHeap.addAll(counts.values());

        // A list to keep track of tasks that are cooling down
        Queue<int[]> coolDown = new LinkedList<>();
        int totalTime = 0;

        while (!maxHeap.isEmpty() || !coolDown.isEmpty()) {
            totalTime++;
            if (!maxHeap.isEmpty()) {
                int currentTaskCount = maxHeap.poll() - 1;
                if (currentTaskCount > 0) {
                    coolDown.offer(new int[]{currentTaskCount, totalTime + n});
                }
            }

            if (!coolDown.isEmpty() && coolDown.peek()[1] == totalTime) {
                maxHeap.offer(coolDown.poll()[0]);
            }
        }
        return totalTime;
    }
}
