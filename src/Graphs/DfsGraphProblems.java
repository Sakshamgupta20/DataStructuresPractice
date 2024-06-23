package Graphs;

import common.CommonUtils;

import java.util.*;

import static common.CommonUtils.getVerticalHorizontalDirections;
import static common.CommonUtils.validGrid;

public class DfsGraphProblems {

    /**
     * There are n cities. Some of them are connected, while some are not. If city a is connected directly with city b, and city b is connected directly with city c, then city a is connected indirectly with city c.
     * <p>
     * A province is a group of directly or indirectly connected cities and no other cities outside of the group.
     * <p>
     * You are given an n x n matrix isConnected where isConnected[i][j] = 1 if the ith city and the jth city are directly connected, and isConnected[i][j] = 0 otherwise.
     * <p>
     * Return the total number of provinces.
     */
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        HashMap<Integer, List<Integer>> graph = new HashMap<>();

        for (int i = 0; i < n; i++) {
            graph.putIfAbsent(i, new ArrayList<>());
            for (int j = i + 1; j < n; j++) {
                if (isConnected[i][j] == 1) {
                    graph.putIfAbsent(j, new ArrayList<>());
                    graph.get(i).add(j);
                    graph.get(j).add(i);
                }
            }
        }

        boolean[] seen = new boolean[n];
        int ans = 0;

        for (int i = 0; i < n; i++) {
            if (!seen[i]) {
                ans++;
                seen[i] = true;
                findCircleNumDfs(graph, seen, i);
            }
        }
        return ans;

    }

    private void findCircleNumDfs(HashMap<Integer, List<Integer>> graph, boolean[] seen, int node) {
        for (Integer neighbor : graph.get(node)) {
            if (!seen[neighbor]) {
                seen[neighbor] = true;
                findCircleNumDfs(graph, seen, neighbor);
            }
        }
    }

    private void findCircleNumIterative(HashMap<Integer, List<Integer>> graph, boolean[] seen, int start) {
        Stack<Integer> stack = new Stack<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            int node = stack.pop();
            for (Integer neighbor : graph.get(node)) {
                if (!seen[neighbor]) {
                    seen[neighbor] = true;
                    stack.add(neighbor);
                }
            }
        }
    }

    /**
     * Given an m x n 2D binary grid grid which represents a map of '1's (land) and '0's (water), return the number of islands.
     * <p>
     * An island is surrounded by water and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid are all surrounded by water.
     */
    public int numIslands(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] directions = getVerticalHorizontalDirections();
        boolean[][] seen = new boolean[n][m];
        int ans = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                if (!seen[row][col] && grid[row][col] == '1') {
                    ans++;
                    seen[row][col] = true;
                    numIslands(grid, seen, directions, row, col);
                }
            }
        }
        return ans;
    }

    public void numIslands(char[][] grid, boolean[][] seen, int[][] directions, int row, int col) {
        int n = grid.length;
        int m = grid[0].length;
        for (int[] direction : directions) {
            int nextRow = row + direction[0];
            int nextCol = col + direction[1];
            if (validGrid(n, m, nextRow, nextCol) && grid[nextRow][nextCol] == '1' && !seen[nextRow][nextCol]) {
                seen[nextRow][nextCol] = true;
                numIslands(grid, seen, directions, nextRow, nextCol);
            }
        }
    }


    /**
     * Reorder Routes to Make All Paths Lead to the City Zero
     * There are n cities numbered from 0 to n - 1 and n - 1 roads such that there is only one way to travel between two different cities (this network form a tree). Last year, The ministry of transport decided to orient the roads in one direction because they are too narrow.
     * <p>
     * Roads are represented by connections where connections[i] = [ai, bi] represents a road from city ai to city bi.
     * <p>
     * This year, there will be a big event in the capital (city 0), and many people want to travel to this city.
     * <p>
     * Your task consists of reorienting some roads such that each city can visit the city 0. Return the minimum number of edges changed.
     * <p>
     * It's guaranteed that each city can reach city 0 after reorder.
     */
    public int minReorder(int n, int[][] connections) {
        Set<String> roads = new HashSet<>();
        Set<Integer> seen = new HashSet<>();
        ;
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            graph.put(i, new ArrayList<>());
        }

        for (int[] connection : connections) {
            int x = connection[0], y = connection[1];
            graph.get(x).add(y);
            graph.get(y).add(x);
            roads.add(CommonUtils.convertToHash(x, y));
        }

        return minReorder(0, graph, seen, roads);
    }

    public int minReorder(int node, HashMap<Integer, List<Integer>> graph, Set<Integer> seen, Set<String> roads) {
        int ans = 0;
        for (Integer neighbor : graph.get(node)) {
            if (!seen.contains(neighbor)) {
                if (roads.contains(CommonUtils.convertToHash(node, neighbor))) {
                    ans++;
                }

                seen.add(neighbor);
                ans += minReorder(neighbor, graph, seen, roads);
            }
        }
        return ans;
    }

    /**
     * There are n rooms labeled from 0 to n - 1 and all the rooms are locked except for room 0. Your goal is to visit all the rooms. However, you cannot enter a locked room without having its key.
     * <p>
     * When you visit a room, you may find a set of distinct keys in it. Each key has a number on it, denoting which room it unlocks, and you can take all of them with you to unlock the other rooms.
     * <p>
     * Given an array rooms where rooms[i] is the set of keys that you can obtain if you visited room i, return true if you can visit all the rooms, or false otherwise.
     */
    public boolean canVisitAllRooms(List<List<Integer>> rooms) {
        int n = rooms.size();
        Set<Integer> seen = new HashSet<>();
        seen.add(0);
        canVisitAllRooms(rooms, seen, 0);
        return seen.size() == n;
    }

    public void canVisitAllRooms(List<List<Integer>> rooms, Set<Integer> seen, int node) {
        for (Integer key : rooms.get(node)) {
            if (!seen.contains(key)) {
                seen.add(key);
                canVisitAllRooms(rooms, seen, key);
            }
        }
    }

    /**
     * Minimum Number of Vertices to Reach All Nodes
     * Given a directed acyclic graph, with n vertices numbered from 0 to n-1, and an array edges where edges[i] = [fromi, toi] represents a directed edge from node fromi to node toi.
     * <p>
     * Find the smallest set of vertices from which all nodes in the graph are reachable. It's guaranteed that a unique solution exists.
     * <p>
     * Notice that you can return the vertices in any order.
     */
    public List<Integer> findSmallestSetOfVertices(int n, List<List<Integer>> edges) {
        int[] indegree = new int[n];

        for (List<Integer> edge : edges) {
            indegree[edge.get(1)]++;
        }
        List<Integer> ans = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (indegree[i] == 0) {
                ans.add(i);
            }
        }

        return ans;
    }

    /**
     * Find if Path Exists in Graph
     * There is a bi-directional graph with n vertices, where each vertex is labeled from 0 to n - 1 (inclusive). The edges in the graph are represented as a 2D integer array edges, where each edges[i] = [ui, vi] denotes a bi-directional edge between vertex ui and vertex vi. Every vertex pair is connected by at most one edge, and no vertex has an edge to itself.
     * <p>
     * You want to determine if there is a valid path that exists from vertex source to vertex destination.
     * <p>
     * Given edges and the integers n, source, and destination, return true if there is a valid path from source to destination, or false otherwise.
     */
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        if (n == 1)
            return true;
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        boolean[] seen = new boolean[n];
        for (int i = 0; i < n; i++)
            graph.put(i, new ArrayList<>());
        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }
        return validPathDfs(graph, seen, source, destination);
    }

    public boolean validPathDfs(HashMap<Integer, List<Integer>> graph, boolean[] seen, int source, int destination) {
        if (source == destination)
            return true;

        if (!seen[source]) {
            seen[source] = true;
            for (Integer neighbor : graph.get(source)) {
                if (validPathDfs(graph, seen, neighbor, destination))
                    return true;
            }
        }
        return false;
    }


    /**
     * You have a graph of n nodes. You are given an integer n and an array edges where edges[i] = [ai, bi] indicates that there is an edge between ai and bi in the graph.
     * <p>
     * Return the number of connected components in the graph.
     */
    public int countComponents(int n, int[][] edges) {
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        boolean[] seen = new boolean[n];

        for (int i = 0; i < n; i++)
            graph.put(i, new ArrayList<>());

        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        int ans = 0;
        for (int i = 0; i < n; i++) {
            if (!seen[i]) {
                ans++;
                seen[i] = true;
                countComponents(i, graph, seen);
            }
        }
        return ans;

    }

    private void countComponents(int node, HashMap<Integer, List<Integer>> graph, boolean[] seen) {
        for (Integer neighbor : graph.get(node)) {
            if (!seen[neighbor]) {
                seen[neighbor] = true;
                countComponents(neighbor, graph, seen);
            }
        }
    }

    public int maxAreaOfIsland(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] directions = getVerticalHorizontalDirections();
        boolean[][] seen = new boolean[n][m];

        int max = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (!seen[i][j] && grid[i][j] == 1) {
                    seen[i][j] = true;
                    max = Math.max(maxAreaOfIsland(grid, directions, seen, n, m, i, j) + 1, max);
                }
            }
        }
        return max;
    }

    public int maxAreaOfIsland(int[][] grid, int[][] directions, boolean[][] seen, int n, int m, int row, int column) {
        int ans = 0;
        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newColumn = column + direction[1];
            if (validGrid(n, m, newRow, newColumn) && grid[newRow][newColumn] == 1 && !seen[newRow][newColumn]) {
                seen[newRow][newColumn] = true;
                ans += 1;
                ans += maxAreaOfIsland(grid, directions, seen, n, m, newRow, newColumn);
            }
        }
        return ans;
    }

    /**
     * Reachable Nodes With Restrictions
     * There is an undirected tree with n nodes labeled from 0 to n - 1 and n - 1 edges.
     * <p>
     * You are given a 2D integer array edges of length n - 1 where edges[i] = [ai, bi] indicates that there is an edge between nodes ai and bi in the tree. You are also given an integer array restricted which represents restricted nodes.
     * <p>
     * Return the maximum number of nodes you can reach from node 0 without visiting a restricted node.
     * <p>
     * Note that node 0 will not be a restricted node.
     */
    public int reachableNodes(int n, int[][] edges, int[] restricted) {
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        boolean[] seen = new boolean[n];

        for (int i : restricted) {
            seen[i] = true;
        }

        for (int i = 0; i < n; i++)
            graph.put(i, new ArrayList<>());

        for (int[] edge : edges) {
            graph.get(edge[0]).add(edge[1]);
            graph.get(edge[1]).add(edge[0]);
        }

        return reachableNodes(0, graph, seen) + 1;
    }

    private int reachableNodes(int node, HashMap<Integer, List<Integer>> graph, boolean[] seen) {
        seen[node] = true;
        int ans = 0;
        for (Integer neighbor : graph.get(node)) {
            if (!seen[neighbor]) {
                ans += 1;
                ans += reachableNodes(neighbor, graph, seen);
            }
        }
        return ans;
    }
}
