package Graphs;

import Trees.TreeNode;
import common.CommonUtils;

import java.util.*;

public class BfsGraphProblems {

    /**
     * Shortest Path in Binary Matrix
     * Given an n x n binary matrix grid, return the length of the shortest clear path in the matrix. If there is no clear path, return -1.
     * <p>
     * A clear path in a binary matrix is a path from the top-left cell (i.e., (0, 0)) to the bottom-right cell (i.e., (n - 1, n - 1)) such that:
     * <p>
     * All the visited cells of the path are 0.
     * All the adjacent cells of the path are 8-directionally connected (i.e., they are different and they share an edge or a corner).
     * The length of a clear path is the number of visited cells of this path.
     */
    public int shortestPathBinaryMatrix(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        if (grid[0][0] == 1)
            return -1;

        // more convenient to use a 2d array instead of a set for seen
        boolean[][] seen = new boolean[n][n];
        seen[0][0] = true;

        int[][] directions = CommonUtils.getAllDirections();
        Queue<Step> queue = new LinkedList<>();

        queue.add(new Step(0, 0, 1));

        while (!queue.isEmpty()) {
            Step step = queue.remove();
            int row = step.row;
            int column = step.column;
            int steps = step.steps;

            if (n - 1 == row && m - 1 == column)
                return steps;

            for (int[] direction : directions) {
                int newRow = row + direction[0];
                int newColumn = column + direction[1];
                if (CommonUtils.validGrid(n, m, newRow, newColumn) && grid[newRow][newColumn] == 0 && !seen[newRow][newColumn]) {
                    seen[newRow][newColumn] = true;
                    queue.add(new Step(newRow, newColumn, steps + 1));
                }
            }
        }
        return -1;
    }

    /**
     * All Nodes Distance K in Binary Tree
     * Given the root of a binary tree, the value of a target node target, and an integer k, return an array of the values of all nodes that have a distance k from the target node.
     * <p>
     * You can return the answer in any order.
     * <p>
     * NOTE: It can be solved with DFS also
     */
    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        Map<TreeNode, TreeNode> parents = new HashMap<>();

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(target);

        Set<TreeNode> seen = new HashSet<>();
        seen.add(target);

        mapGraphParent(root, null, parents);
        int distance = 0;
        while (!queue.isEmpty() && distance < k) {
            int currentLength = queue.size();
            for (int i = 0; i < currentLength; i++) {
                TreeNode node = queue.remove();
                for (TreeNode neighbor : new TreeNode[]{node.left, node.right, parents.get(node)}) {
                    if (neighbor != null && !seen.contains(neighbor)) {
                        seen.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            }
            distance++;
        }
        List<Integer> answers = new ArrayList<>();
        while (!queue.isEmpty()) {
            answers.add(queue.remove().val);
        }
        return answers;
    }

    private void mapGraphParent(TreeNode node, TreeNode parent, Map<TreeNode, TreeNode> parents) {
        if (node == null)
            return;
        parents.put(node, parent);
        mapGraphParent(node.left, node, parents);
        mapGraphParent(node.right, node, parents);
    }

    /**
     * 01 Matrix
     * Given an m x n binary matrix mat, return the distance of the nearest 0 for each cell.
     * <p>
     * The distance between two adjacent cells is 1.
     */
    public int[][] updateMatrix(int[][] mat) {
        int n = mat.length;
        int m = mat[0].length;

        Queue<Step> queue = new LinkedList<>();
        boolean[][] seen = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (mat[i][j] == 0) {
                    seen[i][j] = true;
                    queue.add(new Step(i, j, 1));
                }
            }
        }

        int[][] directions = CommonUtils.getVerticalHorizontalDirections();

        while (!queue.isEmpty()) {
            Step node = queue.remove();
            int row = node.row;
            int column = node.column;
            int steps = node.steps;

            for (int[] direction : directions) {
                int newRow = row + direction[0];
                int newColumn = column + direction[1];
                if (CommonUtils.validGrid(n, m, newRow, newColumn) && !seen[newRow][newColumn] && mat[newRow][newColumn] == 1) {
                    seen[newRow][newColumn] = true;
                    mat[newRow][newColumn] = steps;
                    queue.add(new Step(newRow, newColumn, steps + 1));
                }
            }
        }

        return mat;

    }


    /**
     * 1293. Shortest Path in a Grid with Obstacles Elimination
     * You are given an m x n integer matrix grid where each cell is either 0 (empty) or 1 (obstacle). You can move up, down, left, or right from and to an empty cell in one step.
     * <p>
     * Return the minimum number of steps to walk from the upper left corner (0, 0) to the lower right corner (m - 1, n - 1) given that you can eliminate at most k obstacles. If it is not possible to find such walk return -1.
     */
    public int shortestPath(int[][] grid, int k) {
        int n = grid.length;
        int m = grid[0].length;
        boolean[][][] seen = new boolean[n][m][k + 1];

        Queue<Step> queue = new LinkedList<>();
        queue.add(new Step(0, 0, 0, k));
        seen[0][0][k] = true;
        int[][] directions = CommonUtils.getVerticalHorizontalDirections();

        while (!queue.isEmpty()) {
            Step node = queue.remove();
            int row = node.row;
            int column = node.column;
            int steps = node.steps;
            int remains = node.remains;

            if (row == n - 1 && column == m - 1)
                return steps;

            for (int[] direction : directions) {
                int newRow = row + direction[0];
                int newColumn = column + direction[1];
                if (CommonUtils.validGrid(n, m, newRow, newColumn)) {
                    if (grid[newRow][newColumn] == 0 && !seen[newRow][newColumn][remains]) {
                        seen[newRow][newColumn][remains] = true;
                        queue.add(new Step(newRow, newColumn, steps + 1, remains));
                    }
                    if (grid[newRow][newColumn] == 1 && remains > 0 && !seen[newRow][newColumn][remains - 1]) {
                        seen[newRow][newColumn][remains - 1] = true;
                        queue.add(new Step(newRow, newColumn, steps + 1, remains - 1));
                    }
                }
            }
        }

        return -1;

    }

    /**
     * 1129. Shortest Path with Alternating Colors
     * You are given an integer n, the number of nodes in a directed graph where the nodes are labeled from 0 to n - 1. Each edge is red or blue in this graph, and there could be self-edges and parallel edges.
     * <p>
     * You are given two arrays redEdges and blueEdges where:
     * <p>
     * redEdges[i] = [ai, bi] indicates that there is a directed red edge from node ai to node bi in the graph, and
     * blueEdges[j] = [uj, vj] indicates that there is a directed blue edge from node uj to node vj in the graph.
     * Return an array answer of length n, where each answer[x] is the length of the shortest path from node 0 to node x such that the edge colors alternate along the path, or -1 if such a path does not exist.
     */
    public int[] shortestAlternatingPaths(int n, int[][] redEdges, int[][] blueEdges) {
        int RED = 0;
        int BLUE = 1;

        HashMap<Integer, HashMap<Integer, List<Integer>>> graph = new HashMap<>();
        addEdgeToGraph(n, RED, redEdges, graph);
        addEdgeToGraph(n, BLUE, blueEdges, graph);

        Queue<StateColor> queue = new LinkedList<>();
        queue.add(new StateColor(0, RED, 0));
        queue.add(new StateColor(0, BLUE, 0));

        int[] ans = new int[n];
        Arrays.fill(ans, Integer.MAX_VALUE);

        boolean[][] seen = new boolean[n][2];
        seen[0][RED] = true;
        seen[0][BLUE] = true;

        while (!queue.isEmpty()) {
            StateColor state = queue.remove();
            int node = state.node;
            int color = state.color;
            int steps = state.steps;
            ans[node] = Math.min(steps, ans[node]);

            for (int neighbor : graph.get(color).get(node)) {
                if (!seen[neighbor][1 - color]) {
                    seen[neighbor][1 - color] = true;
                    queue.add(new StateColor(neighbor, 1 - color, steps + 1));
                }
            }
        }

        for (int i = 0; i < n; i++) {
            if (ans[i] == Integer.MAX_VALUE) {
                ans[i] = -1;
            }
        }


        return ans;
    }

    private void addEdgeToGraph(int n, int color, int[][] edges, HashMap<Integer, HashMap<Integer, List<Integer>>> graph) {
        HashMap<Integer, List<Integer>> colorGraph = new HashMap<>();
        graph.put(color, colorGraph);

        for (int i = 0; i < n; i++)
            colorGraph.put(i, new ArrayList<>());

        for (int[] edge : edges) {
            colorGraph.get(edge[0]).add(edge[1]);
        }
    }
}
