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

    /**
     * 1926. Nearest Exit from Entrance in Maze
     * You are given an m x n matrix maze (0-indexed) with empty cells (represented as '.') and walls (represented as '+'). You are also given the entrance of the maze, where entrance = [entrancerow, entrancecol] denotes the row and column of the cell you are initially standing at.
     * <p>
     * In one step, you can move one cell up, down, left, or right. You cannot step into a cell with a wall, and you cannot step outside the maze. Your goal is to find the nearest exit from the entrance. An exit is defined as an empty cell that is at the border of the maze. The entrance does not count as an exit.
     * <p>
     * Return the number of steps in the shortest path from the entrance to the nearest exit, or -1 if no such path exists.
     */
    public int nearestExit(char[][] maze, int[] entrance) {
        int n = maze.length;
        int m = maze[0].length;
        boolean[][] seen = new boolean[n][m];

        Queue<Step> queue = new LinkedList<>();
        queue.add(new Step(entrance[0], entrance[1], 0));
        seen[entrance[0]][entrance[1]] = true;
        int[][] directions = CommonUtils.getVerticalHorizontalDirections();

        while (!queue.isEmpty()) {
            Step node = queue.remove();
            int row = node.row;
            int column = node.column;
            int steps = node.steps;

            if ((row == n - 1 || column == m - 1 || column == 0 || row == 0) && !(row == entrance[0] && column == entrance[1]))
                return steps;

            for (int[] direction : directions) {
                int newRow = row + direction[0];
                int newColumn = column + direction[1];

                if (CommonUtils.validGrid(n, m, newRow, newColumn) && maze[newRow][newColumn] != '+' && !seen[newRow][newColumn]) {
                    queue.add(new Step(newRow, newColumn, steps + 1));
                    seen[newRow][newColumn] = true;
                }
            }
        }
        return -1;
    }

    /**
     * 909. Snakes and Ladders
     * You are given an n x n integer matrix board where the cells are labeled from 1 to n2 in a Boustrophedon style starting from the bottom left of the board (i.e. board[n - 1][0]) and alternating direction each row.
     * <p>
     * You start on square 1 of the board. In each move, starting from square curr, do the following:
     * <p>
     * Choose a destination square next with a label in the range [curr + 1, min(curr + 6, n2)].
     * This choice simulates the result of a standard 6-sided die roll: i.e., there are always at most 6 destinations, regardless of the size of the board.
     * If next has a snake or ladder, you must move to the destination of that snake or ladder. Otherwise, you move to next.
     * The game ends when you reach the square n2.
     * A board square on row r and column c has a snake or ladder if board[r][c] != -1. The destination of that snake or ladder is board[r][c]. Squares 1 and n2 do not have a snake or ladder.
     * <p>
     * Note that you only take a snake or ladder at most once per move. If the destination to a snake or ladder is the start of another snake or ladder, you do not follow the subsequent snake or ladder.
     * <p>
     * For example, suppose the board is [[-1,4],[-1,3]], and on the first move, your destination square is 2. You follow the ladder to square 3, but do not follow the subsequent ladder to 4.
     * Return the least number of moves required to reach the square n2. If it is not possible to reach the square, return -1.
     */
    public int snakesAndLadders(int[][] board) {
        int n = board.length;
        int ans = (int) Math.pow(n, 2);
        boolean[][] seen = new boolean[n][n];

        Queue<StepDice> queue = new LinkedList<>();
        queue.add(new StepDice(0, 1));


        while (!queue.isEmpty()) {
            StepDice node = queue.remove();
            int count = node.count;
            int steps = node.steps;

            for (int i = 1; i <= 6; i++) {
                int newCount = count + i;
                newCount = Math.min(newCount, ans);

                int newRow = getRowValue(n, newCount);
                int newColumn = getColumnValue(n, newCount);

                if (board[newRow][newColumn] != -1) {
                    newCount = board[newRow][newColumn];
                    newRow = getRowValue(n, newCount);
                    newColumn = getColumnValue(n, newCount);
                }

                if (!seen[newRow][newColumn]) {
                    if (newCount == ans)
                        return steps + 1;

                    seen[newRow][newColumn] = true;
                    queue.add(new StepDice(steps + 1, newCount));
                }
            }
        }
        return -1;
    }

    public int getRowValue(int n, int i) {
        return (int) (n - Math.ceil((float) i / n));
    }

    public int getColumnValue(int n, int i) {
        int row = (int) (n - Math.ceil((float) i / n));
        boolean evenRow = (n - row - 1) % 2 == 0;
        int columnValue = i % n;
        int column;
        if (evenRow)
            column = columnValue != 0 ? columnValue - 1 : n - 1;
        else
            column = columnValue != 0 ? n - columnValue : columnValue;
        return column;
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
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> answers = new ArrayList<>();

        Queue<List<Integer>> queue = new LinkedList<>();
        queue.add(List.of(0));
        while (!queue.isEmpty()) {
            List<Integer> curr = queue.poll();
            int node = curr.get(curr.size() - 1);

            for (int nextNode : graph[node]) {
                List<Integer> tmpPath = new ArrayList<>(curr);
                tmpPath.add(nextNode);
                if (nextNode == graph.length - 1) {
                    answers.add(new ArrayList<>(tmpPath));
                } else {
                    queue.add(new ArrayList<>(tmpPath));
                }
            }
        }
        return answers;
    }

    /**
     * 116. Populating Next Right Pointers in Each Node
     * You are given a perfect binary tree where all leaves are on the same level, and every parent has two children. The binary tree has the following definition:
     * <p>
     * struct Node {
     * int val;
     * Node *left;
     * Node *right;
     * Node *next;
     * }
     * Populate each next pointer to point to its next right node. If there is no next right node, the next pointer should be set to NULL.
     * <p>
     * Initially, all next pointers are set to NULL.
     */
    public Node connect(Node root) {
        if (root == null)
            return null;

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int count = queue.size();
            Node prev = null;
            for (int i = 0; i < count; i++) {
                Node next = queue.poll();
                if (Objects.nonNull(prev))
                    prev.next = next;
                prev = next;

                if (next.left != null)
                    queue.add(next.left);
                if (next.right != null)
                    queue.add(next.right);
            }
        }
        return root;
    }

    /**
     * 429. N-ary Tree Level Order Traversal
     * Medium
     * Topics
     * Companies
     * Given an n-ary tree, return the level order traversal of its nodes' values.
     * <p>
     * Nary-Tree input serialization is represented in their level order traversal, each group of children is separated by the null value (See examples).
     *
     * @param root
     * @return
     */
    public List<List<Integer>> levelOrder(Node2 root) {
        if (root == null)
            return Collections.emptyList();

        Queue<Node2> queue = new LinkedList<>();
        queue.add(root);

        List<List<Integer>> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            int count = queue.size();
            List<Integer> curL = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                Node2 curr = queue.poll();
                List<Node2> children = curr.children;
                curL.add(curr.val);
                queue.addAll(children);
            }
            res.add(curL);
        }
        return res;
    }

    /**
     * 286. Walls and Gates
     * You are given an m x n grid rooms initialized with these three possible values.
     * <p>
     * -1 A wall or an obstacle.
     * 0 A gate.
     * INF Infinity means an empty room. We use the value 231 - 1 = 2147483647 to represent INF as you may assume that the distance to a gate is less than 2147483647.
     * Fill each empty room with the distance to its nearest gate. If it is impossible to reach a gate, it should be filled with INF.
     */
    public void wallsAndGates(int[][] rooms) {
        int n = rooms.length;
        int m = rooms[0].length;

        Queue<Step> queue = new LinkedList<>();

        boolean[][] seen = new boolean[n][m];
        for (int row = 0; row < n; row++) {
            for (int column = 0; column < m; column++) {
                if (rooms[row][column] == 0) {
                    seen[row][column] = true;
                    queue.add(new Step(row, column, 0));
                }
            }
        }
        int[][] directions = CommonUtils.getVerticalHorizontalDirections();

        while (!queue.isEmpty()) {
            Step step = queue.poll();
            int row = step.row;
            int column = step.column;
            int steps = step.steps;

            rooms[row][column] = Math.min(steps, rooms[row][column]);

            for (int[] direction : directions) {
                int newRow = row + direction[0];
                int newColumn = column + direction[1];

                if (CommonUtils.validGrid(n, m, newRow, newColumn) && !seen[newRow][newColumn] && rooms[newRow][newColumn] != -1) {
                    queue.add(new Step(newRow, newColumn, steps + 1));
                    seen[newRow][newColumn] = true;
                }
            }

        }
    }

    /**
     * 2257. Count Unguarded Cells in the Grid
     * <p>
     * You are given two integers m and n representing a 0-indexed m x n grid. You are also given two 2D integer arrays guards and walls where guards[i] = [rowi, coli] and walls[j] = [rowj, colj] represent the positions of the ith guard and jth wall respectively.
     * <p>
     * A guard can see every cell in the four cardinal directions (north, east, south, or west) starting from their position unless obstructed by a wall or another guard. A cell is guarded if there is at least one guard that can see it.
     * <p>
     * Return the number of unoccupied cells that are not guarded.
     */
    public int countUnguarded(int m, int n, int[][] guards, int[][] walls) {
        int[][] grid = new int[m][n]; // 0 = unvisited, 1 = guarded, 2 = wall/guard

        // Mark walls and guards
        for (int[] wall : walls) {
            grid[wall[0]][wall[1]] = 2; // 2 for walls
        }
        for (int[] guard : guards) {
            grid[guard[0]][guard[1]] = 2; // 2 for guards
        }

        // Directions for vertical and horizontal movement
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        // Process guards and mark guarded cells
        for (int[] guard : guards) {
            for (int[] dir : directions) {
                int x = guard[0], y = guard[1];
                while (true) {
                    x += dir[0];
                    y += dir[1];
                    if (x < 0 || x >= m || y < 0 || y >= n || grid[x][y] == 2) {
                        break; // Stop if out of bounds or hit a guard/wall
                    }
                    if (grid[x][y] == 0) {
                        grid[x][y] = 1; // Mark as guarded
                    }
                }
            }
        }

        // Count unguarded cells
        int unguarded = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    unguarded++;
                }
            }
        }

        return unguarded;
    }

    /**
     * 3243. Shortest Distance After Road Addition Queries I
     * You are given an integer n and a 2D integer array queries.
     * <p>
     * There are n cities numbered from 0 to n - 1. Initially, there is a unidirectional road from city i to city i + 1 for all 0 <= i < n - 1.
     * <p>
     * queries[i] = [ui, vi] represents the addition of a new unidirectional road from city ui to city vi. After each query, you need to find the length of the shortest path from city 0 to city n - 1.
     * <p>
     * Return an array answer where for each i in the range [0, queries.length - 1], answer[i] is the length of the shortest path from city 0 to city n - 1 after processing the first i + 1 queries.
     */
    public int[] shortestDistanceAfterQueries(int n, int[][] queries) {
        List<Integer> answer = new ArrayList<>();
        List<List<Integer>> adjList = new ArrayList<>(n);

        for (int i = 0; i < n; i++) {
            adjList.add(new ArrayList<>());
            adjList.get(i).add(i + 1);
        }

        for (int[] road : queries) {
            int u = road[0];
            int v = road[1];
            adjList.get(u).add(v);
            answer.add(findMinDistanceBfs(adjList, n));
        }

        return answer.stream().mapToInt(i -> i).toArray();

    }

    private Integer findMinDistanceBfs(List<List<Integer>> adjList, int n) {
        Set<Integer> visit = new HashSet<>();
        Queue<int[]> nodeQueue = new LinkedList<>();
        nodeQueue.add(new int[]{0, 0});
        visit.add(0);

        while (!nodeQueue.isEmpty()) {
            int[] currentNode = nodeQueue.poll();
            int cur = currentNode[0];
            int length = currentNode[1];

            if (cur == n - 1) {
                return length;
            }


            for (int neighbor : adjList.get(cur)) {
                if (!visit.contains(neighbor)) {
                    nodeQueue.offer(new int[]{neighbor, currentNode[1] += 1});
                    visit.add(neighbor);
                }
            }
        }

        return -1;

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
        int n = grid.length; // Number of rows in the grid
        int m = grid[0].length; // Number of columns in the grid

        // Array to track the minimum obstacles removed to reach each cell
        int[][] seen = new int[n][m];
        // Initialize seen with a large value to represent unvisited state
        for (int i = 0; i < n; i++) {
            Arrays.fill(seen[i], Integer.MAX_VALUE);
        }

        // Double-ended queue for 0-1 BFS
        Deque<Step> queue = new LinkedList<>();

        // Start from the top-left corner (0, 0) with 0 obstacles removed
        queue.add(new Step(0, 0, 0));
        seen[0][0] = 0;

        // Predefined directions for moving up, down, left, and right
        int[][] directions = CommonUtils.getVerticalHorizontalDirections();

        // Perform 0-1 BFS
        while (!queue.isEmpty()) {
            Step step = queue.poll();
            int row = step.row;
            int column = step.column;
            int steps = step.steps;

            // If we reach the bottom-right corner, return the number of obstacles removed
            if (row == n - 1 && column == m - 1) {
                return steps;
            }

            // Explore all possible directions
            for (int[] direction : directions) {
                int newRow = row + direction[0];
                int newColumn = column + direction[1];

                // Check if the new cell is within bounds and not yet visited
                if (CommonUtils.validGrid(n, m, newRow, newColumn) && seen[newRow][newColumn] == Integer.MAX_VALUE) {
                    // If the new cell has an obstacle
                    if (grid[newRow][newColumn] == 1) {
                        // Add 1 to the obstacle count and push to the back of the queue
                        seen[newRow][newColumn] = steps + 1;
                        queue.addLast(new Step(newRow, newColumn, steps + 1));
                    } else {
                        // If it's an empty cell, retain the obstacle count and push to the front of the queue
                        seen[newRow][newColumn] = steps;
                        queue.addFirst(new Step(newRow, newColumn, steps));
                    }
                }
            }
        }

        // If we cannot reach the bottom-right corner, return -1 (shouldn't happen for valid grids)
        return -1;
    }

    /**
     * 2658. Maximum Number of Fish in a Grid
     * You are given a 0-indexed 2D matrix grid of size m x n, where (r, c) represents:
     * <p>
     * A land cell if grid[r][c] = 0, or
     * A water cell containing grid[r][c] fish, if grid[r][c] > 0.
     * A fisher can start at any water cell (r, c) and can do the following operations any number of times:
     * <p>
     * Catch all the fish at cell (r, c), or
     * Move to any adjacent water cell.
     * Return the maximum number of fish the fisher can catch if he chooses his starting cell optimally, or 0 if no water cell exists.
     * <p>
     * An adjacent cell of the cell (r, c), is one of the cells (r, c + 1), (r, c - 1), (r + 1, c) or (r - 1, c) if it exists.
     */
    public int findMaxFish(int[][] grid) {
        int numRows = grid.length, numCols = grid[0].length, result = 0;
        boolean[][] visited = new boolean[numRows][numCols];

        // Iterating through the entire grid
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (grid[i][j] != 0 && !visited[i][j]) {
                    result = Math.max(result, countFishes(grid, visited, i, j));
                }
            }
        }
        return result;
    }

    private int countFishes(
            int[][] grid,
            boolean[][] visited,
            int row,
            int col
    ) {
        int numRows = grid.length, numCols = grid[0].length, fishCount = 0;
        Queue<int[]> q = new LinkedList<>();
        q.add(new int[] { row, col });
        visited[row][col] = true;

        // Directions for exploring up, down, left, right
        int[] rowDirections = { 0, 0, 1, -1 };
        int[] colDirections = { 1, -1, 0, 0 };

        // BFS traversal
        while (!q.isEmpty()) {
            int[] cell = q.poll();
            row = cell[0];
            col = cell[1];
            fishCount += grid[row][col];

            // Exploring all four directions
            for (int i = 0; i < 4; i++) {
                int newRow = row + rowDirections[i];
                int newCol = col + colDirections[i];
                if (
                        0 <= newRow &&
                                newRow < numRows &&
                                0 <= newCol &&
                                newCol < numCols &&
                                grid[newRow][newCol] != 0 &&
                                !visited[newRow][newCol]
                ) {
                    q.add(new int[] { newRow, newCol });
                    visited[newRow][newCol] = true;
                }
            }
        }
        return fishCount;
    }
}
