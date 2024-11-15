package Graphs;

import Graphs.DisjointSet.DisjointSet;
import Graphs.DisjointSet.DisjointSetPractice;

import java.util.*;

public class KruskalProblems {

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
        List<int[]> allEdges = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                int weight = Math.abs(points[i][0] - points[j][0]) +
                        Math.abs(points[i][1] - points[j][1]);

                int[] currEdge = {weight, i, j};
                allEdges.add(currEdge);
            }
        }
        allEdges.sort(Comparator.comparingInt(a -> a[0]));

        DisjointSetPractice uf = new DisjointSetPractice(n);
        int mstCost = 0;
        int edgesUsed = 0;
        for (int i = 0; i < allEdges.size(); i++) {
            int node1 = allEdges.get(i)[1];
            int node2 = allEdges.get(i)[2];
            int weight = allEdges.get(i)[0];
            if(!uf.connected(node1, node2)) {
                uf.union(node1, node2);
                mstCost += weight;
                edgesUsed++;
            }
        }
        return mstCost;
    }
}
