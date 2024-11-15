package Graphs;

import Graphs.DisjointSet.DisjointSet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class PrimsProblems {

    /**
     * 1584. Min Cost to Connect All Points
     * You are given an array points representing integer coordinates of some points on a 2D-plane, where points[i] = [xi, yi].
     * <p>
     * The cost of connecting two points [xi, yi] and [xj, yj] is the manhattan distance between them: |xi - xj| + |yi - yj|, where |val| denotes the absolute value of val.
     * <p>
     * Return the minimum cost to make all points connected. All points are connected if there is exactly one simple path between any two points.
     */
    public int minCostConnectPoints(int[][] points) {
        int n = points.length;

        // Min-heap to store minimum weight edge at top.
        PriorityQueue<Pair> heap = new PriorityQueue<>(Comparator.comparingInt(a -> a.steps));
        boolean[] inMST = new boolean[n];

        heap.add(new Pair(0, 0));
        int mstCost = 0;
        int edgesUsed = 0;

        while (!heap.isEmpty()) {
            Pair topElement = heap.poll();
            int weight = topElement.steps;
            int currNode = topElement.id;

            if (inMST[currNode]) {
                continue;
            }

            inMST[currNode] = true;
            mstCost += weight;
            edgesUsed++;

            for (int nextNode = 0; nextNode < n; ++nextNode) {
                // If next node is not in MST, then edge from curr node
                // to next node can be pushed in the priority queue.
                if (!inMST[nextNode]) {
                    int nextWeight = Math.abs(points[currNode][0] - points[nextNode][0]) +
                            Math.abs(points[currNode][1] - points[nextNode][1]);

                    heap.add(new Pair(nextNode, nextWeight));
                }
            }
        }
        return mstCost;
    }

    public int minCostConnectPointsOptimized(int[][] points) {
        int n = points.length;
        int mstCost = 0;
        int edgesUsed = 0;

        // Track nodes which are included in MST.
        boolean[] inMST = new boolean[n];

        int[] minDist = new int[n];
        minDist[0] = 0;

        for (int i = 1; i < n; ++i) {
            minDist[i] = Integer.MAX_VALUE;
        }

        while (edgesUsed < n) {
            int currMinEdge = Integer.MAX_VALUE;
            int currNode = -1;

            // Pick least weight node which is not in MST.
            for (int node = 0; node < n; ++node) {
                if (!inMST[node] && currMinEdge > minDist[node]) {
                    currMinEdge = minDist[node];
                    currNode = node;
                }
            }

            mstCost += currMinEdge;
            edgesUsed++;
            inMST[currNode] = true;

            for (int nextNode = 0; nextNode < n; nextNode++) {
                int weight = Math.abs(points[currNode][0] - points[nextNode][0]) +
                        Math.abs(points[currNode][1] - points[nextNode][1]);

                if (!inMST[nextNode] && minDist[nextNode] > weight) {
                    minDist[nextNode] = weight;
                }
            }
        }

        return mstCost;
    }
}
