package Graphs;

import Graphs.DisjointSet.DisjointSet;
import common.CommonUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

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
            for (int column = 0; column < m; column++) {
                if (!seen[row][column] && grid[row][column] == '1') {
                    numIslands(grid, seen, directions, row, column);
                    ans++;
                }
            }
        }
        return ans;
    }

    private void numIslands(char[][] grid, boolean[][] seen, int[][] directions, int row, int column) {
        seen[row][column] = true;
        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newColumn = column + direction[1];
            if (validGrid(grid.length, grid[0].length, newRow, newColumn) && !seen[newRow][newColumn] && grid[newRow][newColumn] == '1') {
                numIslands(grid, seen, directions, newRow, newColumn);
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

    /**
     * 797. All Paths From Source to Target
     * Given a directed acyclic graph (DAG) of n nodes labeled from 0 to n - 1, find all possible paths from node 0 to node n - 1 and return them in any order.
     * <p>
     * The graph is given as follows: graph[i] is a list of all nodes you can visit from node i (i.e., there is a directed edge from node i to node graph[i][j]).
     */
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        int n = graph.length;
        int target = graph.length - 1;
        List<List<Integer>> answers = new ArrayList<>();

        LinkedList<Integer> path = new LinkedList<Integer>();
        path.addLast(0);
        allPathsSourceTargetDfs(answers, graph, path, target, 0);
        return answers;
    }

    private void allPathsSourceTargetDfs(List<List<Integer>> answers, int[][] graph, LinkedList<Integer> path, int target, int node) {
        if (target == node) {
            answers.add(new ArrayList<>(path));
            return;
        }
        for (int neighbor : graph[node]) {
            path.addLast(neighbor);
            allPathsSourceTargetDfs(answers, graph, path, target, neighbor);
            path.removeLast();
        }
    }

    /**
     * 332. Reconstruct Itinerary
     * You are given a list of airline tickets where tickets[i] = [fromi, toi] represent the departure and the arrival airports of one flight. Reconstruct the itinerary in order and return it.
     * <p>
     * All of the tickets belong to a man who departs from "JFK", thus, the itinerary must begin with "JFK". If there are multiple valid itineraries, you should return the itinerary that has the smallest lexical order when read as a single string.
     * <p>
     * For example, the itinerary ["JFK", "LGA"] has a smaller lexical order than ["JFK", "LGB"].
     * You may assume all tickets form at least one valid itinerary. You must use all the tickets once and only once.
     */
    public List<String> findItinerary(List<List<String>> tickets) {
        HashMap<String, LinkedList<String>> ticketsMap = new HashMap<>();
        for (List<String> ticket : tickets) {
            String from = ticket.get(0);
            String to = ticket.get(1);
            ticketsMap.putIfAbsent(from, new LinkedList<>());
            ticketsMap.get(from).add(to);
        }
        for (List<String> value : ticketsMap.values()) {
            Collections.sort(value);
        }

        LinkedList<String> answers = new LinkedList<>();
        findItinerary("JFK", ticketsMap, answers);
        return answers;
    }

    private void findItinerary(String from, HashMap<String, LinkedList<String>> ticketsMap, LinkedList<String> answers) {
        if (ticketsMap.containsKey(from)) {
            LinkedList<String> destList = ticketsMap.get(from);
            while (!destList.isEmpty()) {
                String dest = destList.pollFirst();
                findItinerary(dest, ticketsMap, answers);
            }
        }
        answers.offerFirst(from);
    }

    /**
     * 1059. All Paths from Source Lead to Destination
     * Given the edges of a directed graph where edges[i] = [ai, bi] indicates there is an edge between nodes ai and bi, and two nodes source and destination of this graph, determine whether or not all paths starting from source eventually, end at destination, that is:
     * <p>
     * At least one path exists from the source node to the destination node
     * If a path exists from the source node to a node with no outgoing edges, then that node is equal to destination.
     * The number of possible paths from source to destination is a finite number.
     * Return true if and only if all roads from source lead to destination.
     */
    public boolean leadsToDestination(int n, int[][] edges, int source, int destination) {
        Map<Integer, Set<Integer>> graph = new HashMap<>();
        for (int[] edge : edges) {
            graph.putIfAbsent(edge[0], new HashSet<>());
            graph.get(edge[0]).add(edge[1]);
        }

        return leadsToDestination(graph, new State[n], source, destination);
    }

    private boolean leadsToDestination(Map<Integer, Set<Integer>> graph, State[] state, int source, int destination) {

        if (state[source] != null)
            return state[source] == State.PROCESSED;

        if (!graph.containsKey(source))
            return source == destination;

        state[source] = State.PROCESSING;
        for (Integer neighbor : graph.get(source)) {
            if (!leadsToDestination(graph, state, neighbor, destination))
                return false;
        }
        state[source] = State.PROCESSED;
        return true;
    }

    /**
     * 210. Course Schedule II
     * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.
     * <p>
     * For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
     * Return the ordering of courses you should take to finish all courses. If there are many valid answers, return any of them. If it is impossible to finish all courses, return an empty array.
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        List<Integer> topologicalOrder = new ArrayList<>();
        HashMap<Integer, Integer> color = new HashMap<>();
        AtomicBoolean isPossible = new AtomicBoolean(true);

        // By default, all vertices are WHITE
        for (int i = 0; i < numCourses; i++) {
            color.put(i, 1);
        }

        for (int[] prerequisite : prerequisites) {
            graph.putIfAbsent(prerequisite[1], new ArrayList<>());
            graph.get(prerequisite[1]).add(prerequisite[0]);
        }


        // If the node is unprocessed, then call dfs on it.
        for (int i = 0; i < numCourses; i++) {
            if (color.get(i) == 1) {
                findOrder(i, color, graph, topologicalOrder, isPossible);
            }
        }

        int[] order;
        if (isPossible.get()) {
            order = new int[numCourses];
            for (int i = 0; i < numCourses; i++) {
                order[i] = topologicalOrder.get(numCourses - i - 1);
            }
        } else {
            order = new int[0];
        }

        return order;
    }

    private void findOrder(int node, HashMap<Integer, Integer> color, Map<Integer, List<Integer>> graph, List<Integer> topologicalOrder, AtomicBoolean isPossible) {
        if (!isPossible.get())
            return;

        // Start the recursion
        color.put(node, 2);
        if (graph.containsKey(node)) {
            for (Integer neighbor : graph.get(node)) {
                if (color.get(neighbor) == 1)
                    findOrder(neighbor, color, graph, topologicalOrder, isPossible);
                else if (color.get(neighbor) == 2) {
                    isPossible.set(false);
                }
            }
        }
        color.put(node, 3);
        topologicalOrder.add(node);
    }

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> courses = new HashMap<>();
        for (int i = 0; i < numCourses; i++) {
            courses.put(i, new ArrayList<>());
        }
        for (int[] prerequisite : prerequisites) {
            courses.get(prerequisite[0]).add(prerequisite[1]);
        }

        boolean[] seen = new boolean[numCourses];   // Tracks the current path (to detect cycles)
        boolean[] visited = new boolean[numCourses]; // Tracks fully processed nodes (to avoid reprocessing)

        for (Integer i : courses.keySet()) {
            if (!canFinish(i, courses, seen, visited))
                return false;
        }
        return true;
    }

    private boolean canFinish(int course, Map<Integer, List<Integer>> courses, boolean[] seen, boolean[] visited) {
        if (visited[course]) {
            return true;
        }
        if (seen[course]) {
            return false;
        }

        seen[course] = true;
        for (Integer preCourse : courses.get(course)) {
            if (!canFinish(preCourse, courses, seen, visited)) {
                return false;
            }
        }
        visited[course] = true;
        seen[course] = false;
        return true;
    }

    /**
     * 2097. Valid Arrangement of Pairs
     * You are given a 0-indexed 2D integer array pairs where pairs[i] = [starti, endi]. An arrangement of pairs is valid if for every index i where 1 <= i < pairs.length, we have endi-1 == starti.
     * <p>
     * Return any valid arrangement of pairs.
     * <p>
     * Note: The inputs will be generated such that there exists a valid arrangement of pairs.
     */
    public int[][] validArrangement(int[][] pairs) {
        Map<Integer, Deque<Integer>> adjacencyMatrix = new HashMap<>();
        Map<Integer, Integer> inDegree = new HashMap<>();
        Map<Integer, Integer> outDegree = new HashMap<>();

        // Build the adjacency list and track in-degrees and out-degrees
        for (int[] pair : pairs) {
            int start = pair[0], end = pair[1];
            adjacencyMatrix
                    .computeIfAbsent(start, k -> new ArrayDeque<>())
                    .add(end);
            outDegree.put(start, outDegree.getOrDefault(start, 0) + 1);
            inDegree.put(end, inDegree.getOrDefault(end, 0) + 1);
        }

        // Find the start node (outDegree == inDegree + 1)
        int startNode = -1;
        for (int node : outDegree.keySet()) {
            if (outDegree.get(node) == inDegree.getOrDefault(node, 0) + 1) {
                startNode = node;
                break;
            }
        }

        if (startNode == -1) {
            startNode = pairs[0][0];
        }

        List<Integer> result = new ArrayList<>();

        validArrangement(startNode, adjacencyMatrix, result);

        Collections.reverse(result);

        // Construct the result pairs
        int[][] pairedResult = new int[result.size() - 1][2];
        for (int i = 1; i < result.size(); i++) {
            pairedResult[i - 1] = new int[]{
                    result.get(i - 1),
                    result.get(i),
            };
        }

        return pairedResult;
    }


    private void validArrangement(int node, Map<Integer, Deque<Integer>> adjMatrix, List<Integer> res) {
        Deque<Integer> neighbors = adjMatrix.get(node);
        while (neighbors != null && !neighbors.isEmpty()) {
            int nextNode = neighbors.pollFirst();
            validArrangement(nextNode, adjMatrix, res);
        }
        res.add(node);
    }

    /**
     * 2204. Distance to a Cycle in Undirected Graphs
     * You are given a positive integer n representing the number of nodes in a connected undirected graph containing exactly one cycle. The nodes are numbered from 0 to n - 1 (inclusive).
     * <p>
     * You are also given a 2D integer array edges, where edges[i] = [node1i, node2i] denotes that there is a bidirectional edge connecting node1i and node2i in the graph.
     * <p>
     * The distance between two nodes a and b is defined to be the minimum number of edges that are needed to go from a to b.
     * <p>
     * Return an integer array answer of size n, where answer[i] is the minimum distance between the ith node and any node in the cycle.
     */
    public int[] distanceToCycle(int n, int[][] edges) {
        return null;
        //TODO

    }

}
