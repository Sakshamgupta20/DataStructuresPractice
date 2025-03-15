package Graphs.DijkstrasAlgorithm;

import Graphs.Step;
import common.CommonUtils;

import java.util.*;

import static common.CommonUtils.validGrid;

public class DijkstrasAlgorithm {

    /**
     * 743. Network Delay Time
     * You are given a network of n nodes, labeled from 1 to n. You are also given times, a list of travel times as directed edges times[i] = (ui, vi, wi), where ui is the source node, vi is the target node, and wi is the time it takes for a signal to travel from source to target.
     * <p>
     * We will send a signal from a given node k. Return the minimum time it takes for all the n nodes to receive the signal. If it is impossible for all the n nodes to receive the signal, return -1.
     */
    public int networkDelayTime(int[][] times, int n, int k) {
        HashMap<Integer, List<Node>> nodes = new HashMap<>();
        for (int[] time : times) {
            int source = time[0];
            int destination = time[1];
            int timeTaken = time[2];
            nodes.putIfAbsent(source, new ArrayList<>());
            nodes.get(source).add(new Node(destination, timeTaken));
        }

        int[] signalReceivedAt = new int[n + 1];
        Arrays.fill(signalReceivedAt, Integer.MAX_VALUE);

        networkDelayTime(signalReceivedAt, nodes, k);
        int answer = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            answer = Math.max(answer, signalReceivedAt[i]);
        }

        // INT_MAX signifies atleat one node is unreachable
        return answer == Integer.MAX_VALUE ? -1 : answer;
    }

    private void networkDelayTime(int[] signalReceivedAt, HashMap<Integer, List<Node>> nodes, int source) {
        Queue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.distance));

        pq.add(new Node(source, 0));

        signalReceivedAt[source] = 0;


        while (!pq.isEmpty()) {
            Node node = pq.poll();
            int currNode = node.node;
            int currNodeTime = node.distance;

            if (currNodeTime > signalReceivedAt[currNode]) {
                continue;
            }

            if (!nodes.containsKey(currNode)) {
                continue;
            }

            for (Node neighbor : nodes.get(currNode)) {
                int destination = neighbor.node;
                int distance = neighbor.distance;

                int totalDistance = distance + currNodeTime;

                if (signalReceivedAt[destination] > totalDistance) {
                    signalReceivedAt[destination] = totalDistance;
                    pq.add(new Node(destination, totalDistance));
                }
            }

        }
    }

    /**
     * 787. Cheapest Flights Within K Stops
     * There are n cities connected by some number of flights. You are given an array flights where flights[i] = [fromi, toi, pricei] indicates that there is a flight from city fromi to city toi with cost pricei.
     * <p>
     * You are also given three integers src, dst, and k, return the cheapest price from src to dst with at most k stops. If there is no such route, return -1.
     */
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        HashMap<Integer, List<Node>> flightsMap = new HashMap<>();
        for (int[] flight : flights) {
            flightsMap.putIfAbsent(flight[0], new ArrayList<>());
            flightsMap.get(flight[0]).add(new Node(flight[1], flight[2]));
        }

        Queue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.distance));
        pq.add(new Node(src, 0, 0));
        int[] stops = new int[n];

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            int currFlight = node.node;
            int currFlightTime = node.distance;
            int currStops = node.count;

            if (currStops > stops[currFlight] || currStops > k + 1)
                continue;

            if (!flightsMap.containsKey(currFlight))
                continue;

            stops[currFlight] = currStops;
            if (currFlight == dst)
                return currFlightTime;

            for (Node neighbor : flightsMap.get(currFlight)) {
                int destination = neighbor.node;
                int distance = neighbor.distance;

                int totalDistance = distance + currFlightTime;

                pq.add(new Node(destination, totalDistance, currStops + 1));
            }

        }
        return -1;

    }

    public int swimInWater(int[][] grid) {
        int n = grid.length;

        PriorityQueue<Tuple> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.maxWt));
        int[][] visited = new int[n][n];

        pq.add(new Tuple(grid[0][0], 0, 0));
        int[][] directions = CommonUtils.getVerticalHorizontalDirections();

        while (!pq.isEmpty()) {
            Tuple t = pq.poll();
            int x = t.x;
            int y = t.y;
            int wt = t.maxWt;

            if (visited[x][y] == 1) {
                continue;
            }

            visited[x][y] = 1;

            if (x == n - 1 && y == n - 1) {
                return wt;
            }

            for (int[] direction : directions) {
                int newRow = x + direction[0];
                int newColumn = y + direction[1];
                if (validGrid(n, n, newRow, newColumn)) {
                    int we = Math.max(wt, grid[newRow][newColumn]);
                    pq.add(new Tuple(we, newRow, newColumn));
                }
            }

        }
        return 0;
    }

    /**
     * 2290. Minimum Obstacle Removal to Reach Corner
     * You are given a 0-indexed 2D integer array grid of size m x n. Each cell has one of two values:
     * <p>
     * 0 represents an empty cell,
     * 1 represents an obstacle that may be removed.
     * You can move up, down, left, or right from and to an empty cell.
     * <p>
     * Return the minimum number of obstacles to remove so you can move from the upper left corner (0, 0) to the lower right corner (m - 1, n - 1).
     */
    public int minimumObstacles(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        // Array to track the minimum steps required to reach each cell
        int[][] seen = new int[n][m];
        // Initialize seen with a large value (representing unvisited state)
        for (int i = 0; i < n; i++) {
            Arrays.fill(seen[i], Integer.MAX_VALUE);
        }

        // Priority queue to process cells in order of steps (min-heap)
        PriorityQueue<Step> queue = new PriorityQueue<>((a, b) -> a.steps - b.steps);

        // Start from the top-left corner
        queue.add(new Step(0, 0, 0));
        seen[0][0] = 0;

        // Predefined directions for moving up, down, left, and right
        int[][] directions = CommonUtils.getVerticalHorizontalDirections();

        // Perform Dijkstra-like traversal
        while (!queue.isEmpty()) {
            Step step = queue.poll();
            int row = step.row;
            int column = step.column;
            int steps = step.steps;

            // If we reach the bottom-right corner, return the steps taken
            if (row == n - 1 && column == m - 1) {
                return steps;
            }

            // Process all possible directions
            for (int[] direction : directions) {
                int newRow = row + direction[0];
                int newColumn = column + direction[1];

                // Check if the new cell is within bounds
                if (validGrid(n, m, newRow, newColumn)) {
                    // Calculate new steps based on obstacle presence
                    int newSteps = steps + (grid[newRow][newColumn] == 1 ? 1 : 0);

                    // If we found a shorter path to this cell, update and enqueue
                    if (newSteps < seen[newRow][newColumn]) {
                        seen[newRow][newColumn] = newSteps;
                        queue.add(new Step(newRow, newColumn, newSteps));
                    }
                }
            }
        }

        // If we exit the loop without reaching the bottom-right corner, return -1 (shouldn't happen)
        return -1;

    }

    /**
     * 2577. Minimum Time to Visit a Cell In a Gri
     * You are given a m x n matrix grid consisting of non-negative integers where grid[row][col] represents the minimum time required to be able to visit the cell (row, col), which means you can visit the cell (row, col) only when the time you visit it is greater than or equal to grid[row][col].
     * <p>
     * You are standing in the top-left cell of the matrix in the 0th second, and you must move to any adjacent cell in the four directions: up, down, left, and right. Each move you make takes 1 second.
     * <p>
     * Return the minimum time required in which you can visit the bottom-right cell of the matrix. If you cannot visit the bottom-right cell, then return -1.
     */
    public int minimumTime(int[][] grid) {
        int n = grid.length; // Number of rows in the grid
        int m = grid[0].length; // Number of columns in the grid

        // Early exit condition: If the first step cannot move to either (0,1) or (1,0)
        if (grid[0][1] > 1 && grid[1][0] > 1) {
            return -1;
        }

        // Array to track visited cells
        boolean[][] seen = new boolean[n][m];

        // Priority queue to process cells in order of steps (min-heap)
        PriorityQueue<Step> queue = new PriorityQueue<>((a, b) -> Integer.compare(a.steps, b.steps));

        // Add the starting point (0,0) to the queue with 0 initial steps
        queue.add(new Step(0, 0, 0));
        seen[0][0] = true; // Mark the starting cell as visited

        // Directions for moving up, down, left, and right
        int[][] directions = CommonUtils.getVerticalHorizontalDirections();

        // Perform a Dijkstra-like traversal
        while (!queue.isEmpty()) {
            Step step = queue.poll(); // Get the cell with the smallest steps so far
            int row = step.row;
            int column = step.column;
            int steps = step.steps;

            // If we reach the bottom-right corner, return the steps taken
            if (row == n - 1 && column == m - 1) {
                return steps;
            }

            // Explore all possible directions
            for (int[] direction : directions) {
                int newRow = row + direction[0];
                int newColumn = column + direction[1];

                // Check if the new cell is within bounds and not visited
                if (validGrid(n, m, newRow, newColumn) && !seen[newRow][newColumn]) {
                    int newSteps = steps + 1; // Increment step count

                    // Adjust newSteps if it doesn't meet the current grid cell's minimum requirement
                    if (newSteps < grid[newRow][newColumn]) {
                        int diff = grid[newRow][newColumn] - newSteps;
                        newSteps += (diff % 2 == 0) ? diff : diff + 1; // Adjust steps to satisfy parity
                    }

                    // Add the new step to the queue and mark as visited
                    queue.add(new Step(newRow, newColumn, newSteps));
                    seen[newRow][newColumn] = true;
                }
            }
        }

        // If we exit the loop without reaching the bottom-right corner, return -1
        return -1;
    }

    /**
     * 1368. Minimum Cost to Make at Least One Valid Path in a Grid
     * Given an m x n grid. Each cell of the grid has a sign pointing to the next cell you should visit if you are currently in this cell. The sign of grid[i][j] can be:
     * <p>
     * 1 which means go to the cell to the right. (i.e go from grid[i][j] to grid[i][j + 1])
     * 2 which means go to the cell to the left. (i.e go from grid[i][j] to grid[i][j - 1])
     * 3 which means go to the lower cell. (i.e go from grid[i][j] to grid[i + 1][j])
     * 4 which means go to the upper cell. (i.e go from grid[i][j] to grid[i - 1][j])
     * Notice that there could be some signs on the cells of the grid that point outside the grid.
     * <p>
     * You will initially start at the upper left cell (0, 0). A valid path in the grid is a path that starts from the upper left cell (0, 0) and ends at the bottom-right cell (m - 1, n - 1) following the signs on the grid. The valid path does not have to be the shortest.
     * <p>
     * You can modify the sign on a cell with cost = 1. You can modify the sign on a cell one time only.
     * <p>
     * Return the minimum cost to make the grid have at least one valid path.
     */
    public int minCost(int[][] grid) {
        int numRows = grid.length;
        int numCols = grid[0].length;
        PriorityQueue<Step> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a.steps));
        queue.add(new Step(0, 0, 0));

        int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        // Track minimum cost to reach each cell
        int[][] minCost = new int[numRows][numCols];
        for (int row = 0; row < numRows; row++) {
            Arrays.fill(minCost[row], Integer.MAX_VALUE);
        }
        minCost[0][0] = 0;

        while (!queue.isEmpty()) {
            Step step = queue.poll();
            int row = step.row;
            int column = step.column;
            int steps = step.steps;

            // Skip processing if we already have a better way to reach this cell
            if (steps > minCost[row][column] ) continue;

            for (int dir = 0; dir < dirs.length; dir++) {
                int newRow = row + dirs[dir][0];
                int newCol = column + dirs[dir][1];

                if (validGrid(numRows, numCols, newRow, newCol)) {
                    // Calculate new cost to move to (newRow, newCol)
                    int newCost = steps + (dir != (grid[row][column] - 1) ? 1 : 0);

                    // Only push the new step to the queue if the cost is better
                    if (minCost[newRow][newCol] > newCost) {
                        minCost[newRow][newCol] = newCost;
                        queue.offer(new Step(newRow, newCol, newCost));
                    }
                }
            }
        }

        return minCost[numRows - 1][numCols - 1];
    }

    public int minCost01(int[][] grid) {
        int numRows = grid.length;
        int numCols = grid[0].length;
        // Use deque for 0-1 BFS - add zero cost moves to front, cost=1 to back
        Deque<Step> queue = new ArrayDeque<>();
        queue.offerFirst(new Step(0,0,0));

        int[][] dirs = new int[][]{{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

        // Track minimum cost to reach each cell
        int[][] minCost = new int[numRows][numCols];
        for (int row = 0; row < numRows; row++) {
            Arrays.fill(minCost[row], Integer.MAX_VALUE);
        }
        minCost[0][0] = 0;

        while (!queue.isEmpty()) {
            Step step = queue.poll();
            int row = step.row;
            int column = step.column;

            for (int dir = 0; dir < dirs.length; dir++) {
                int newRow = row + dirs[dir][0];
                int newCol = column + dirs[dir][1];
                int cost = (grid[row][column] != (dir + 1)) ? 1 : 0;

                if (validGrid(numRows, numCols, newRow, newCol) && minCost[row][column] + cost < minCost[newRow][newCol]) {
                    minCost[newRow][newCol] = minCost[row][column] + cost;

                    // Only push the new step to the queue if the cost is better
                    if (cost == 1) {
                        queue.offerLast((new Step(newRow,newCol,0)));
                    } else {
                        queue.offerFirst(new Step(newRow,newCol,0));
                    }
                }
            }
        }

        return minCost[numRows - 1][numCols - 1];
    }
}
