package QueueProblems;

import java.util.*;

public class QueueProblems {
    public String predictPartyVictory(String senate) {
        Queue<Character> turns = new LinkedList<>();
        int rTurns = 0;
        int dTurns = 0;


        for (char c : senate.toCharArray()) {
            turns.add(c);
        }

        while (!turns.isEmpty()) {
            Character c = turns.poll();
            if (c == 'D') {
                if (rTurns > 0) {
                    rTurns--;
                    turns.add('R');
                } else {
                    dTurns++;
                }
            } else {
                if (dTurns > 0) {
                    dTurns--;
                    turns.add('D');
                } else {
                    rTurns++;
                }
            }
        }
        return rTurns > 0 ? "Radiant" : "Dire";
    }

    /**
     * 2070. Most Beautiful Item for Each Query
     * You are given a 2D integer array items where items[i] = [pricei, beautyi] denotes the price and beauty of an item respectively.
     * <p>
     * You are also given a 0-indexed integer array queries. For each queries[j], you want to determine the maximum beauty of an item whose price is less than or equal to queries[j]. If no such item exists, then the answer to this query is 0.
     * <p>
     * Return an array answer of the same length as queries where answer[j] is the answer to the jth query.
     */
    public int[] maximumBeautyQueueApproach(int[][] items, int[] queries) {
        PriorityQueue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[0]));
        queue.addAll(Arrays.asList(items));

        int[][] queriesWithIndices = new int[queries.length][2];
        for (int i = 0; i < queries.length; i++) {
            queriesWithIndices[i][0] = queries[i];
            queriesWithIndices[i][1] = i;
        }

        Arrays.sort(queriesWithIndices, (a, b) -> a[0] - b[0]);

        int currMax = 0;
        for (int[] query : queriesWithIndices) {
            while (!queue.isEmpty() && queue.peek()[0] <= query[0]) {
                int[] item = queue.poll();
                currMax = Math.max(currMax, item[1]);
            }
            queries[query[1]] = currMax;
        }

        return queries;

    }

    public int[] maximumBeautyBinarySearch(int[][] items, int[] queries) {
        int[] ans = new int[queries.length];

        // Sort and store max beauty
        Arrays.sort(items, (a, b) -> a[0] - b[0]);
        int max = items[0][1];

        for (int i = 0; i < items.length; i++) {
            max = Math.max(max, items[i][1]);
            items[i][1] = max;
        }

        for (int i = 0; i < queries.length; i++) {
            // answer i-th query
            ans[i] = maximumBeautyBinarySearch(items, queries[i]);
        }

        return ans;

    }

    private int maximumBeautyBinarySearch(int[][] items, int query) {
        int left = 0;
        int right = items.length - 1;
        int max = 0;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (items[mid][0] > query) {
                right = mid - 1;
            } else {
                max = Math.max(items[mid][1], max);
                left = mid + 1;
            }
        }
        return max;
    }
}
