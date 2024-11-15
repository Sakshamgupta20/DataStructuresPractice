package Graphs;

import common.CommonUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ImplicitGraphProblems {

    /**
     * 752. Open the Lock
     * You have a lock in front of you with 4 circular wheels. Each wheel has 10 slots: '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'. The wheels can rotate freely and wrap around: for example we can turn '9' to be '0', or '0' to be '9'. Each move consists of turning one wheel one slot.
     * <p>
     * The lock initially starts at '0000', a string representing the state of the 4 wheels.
     * <p>
     * You are given a list of deadends dead ends, meaning if the lock displays any of these codes, the wheels of the lock will stop turning and you will be unable to open it.
     * <p>
     * Given a target representing the value of the wheels that will unlock the lock, return the minimum total number of turns required to open the lock, or -1 if it is impossible.
     */
    public int openLock(String[] deadends, String target) {
        Set<String> seen = new HashSet<>();
        for (String deadend : deadends) {
            if (deadend.equals("0000")) {
                return -1;
            }
            seen.add(deadend);
        }

        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair("0000", 0));
        seen.add(queue.peek().value);

        while (!queue.isEmpty()) {
            Pair pair = queue.remove();
            String value = pair.value;
            Integer steps = pair.steps;

            if (Objects.equals(value, target))
                return steps;

            for (String neighbor : getNeighbors(value)) {
                if (!seen.contains(neighbor)) {
                    queue.add(new Pair(neighbor, steps + 1));
                    seen.add(neighbor);
                }
            }

        }
        return -1;
    }

    private List<String> getNeighbors(String value) {
        List<String> ans = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            char c = value.charAt(i);
            int cValue = c - '0';
            for (int change : new int[]{-1, 1}) {
                int x = (cValue + change + 10) % 10;
                ans.add(value.substring(0, i) + ("" + x) + value.substring(i + 1));
            }
        }
        return ans;
    }

    /**
     * 399. Evaluate Division
     * You are given an array of variable pairs equations and an array of real numbers values, where equations[i] = [Ai, Bi] and values[i] represent the equation Ai / Bi = values[i]. Each Ai or Bi is a string that represents a single variable.
     * <p>
     * You are also given some queries, where queries[j] = [Cj, Dj] represents the jth query where you must find the answer for Cj / Dj = ?.
     * <p>
     * Return the answers to all queries. If a single answer cannot be determined, return -1.0.
     * <p>
     * Note: The input is always valid. You may assume that evaluating the queries will not result in division by zero and that there is no contradiction.
     * <p>
     * Note: The variables that do not occur in the list of equations are undefined, so the answer cannot be determined for them.
     */
    public double[] calcEquation(List<List<String>> equations, double[] values, List<List<String>> queries) {
        Map<String, Map<String, Double>> graph = new HashMap<>();

        for (int i = 0; i < equations.size(); i++) {
            List<String> equation = equations.get(i);
            String numerator = equation.get(0);
            String denominator = equation.get(1);
            graph.putIfAbsent(numerator, new HashMap<>());
            graph.putIfAbsent(denominator, new HashMap<>());

            graph.get(numerator).put(denominator, values[i]);
            graph.get(denominator).put(numerator, 1 / values[i]);
        }

        double[] ans = new double[queries.size()];

        for (int i = 0; i < queries.size(); i++) {
            ans[i] = calcEquation(graph, queries.get(i).get(0), queries.get(i).get(1));
        }
        return ans;
    }

    private double calcEquation(Map<String, Map<String, Double>> graph, String a, String b) {
        if (!graph.containsKey(a))
            return -1;
        Stack<Pair> stack = new Stack<>();
        Set<String> seen = new HashSet<>();
        stack.add(new Pair(a, 1d));
        seen.add(a);

        while (!stack.isEmpty()) {
            Pair pair = stack.pop();
            String value = pair.value;
            double ratio = pair.ratio;

            if (Objects.equals(value, b)) {
                return ratio;
            }

            if (graph.containsKey(value)) {
                for (String neighbor : graph.get(value).keySet()) {
                    if (!seen.contains(neighbor)) {
                        seen.add(neighbor);
                        stack.push(new Pair(neighbor, ratio * graph.get(value).get(neighbor)));
                    }
                }
            }
        }

        return -1;

    }

    /**
     * 433. Minimum Genetic Mutation
     * A gene string can be represented by an 8-character long string, with choices from 'A', 'C', 'G', and 'T'.
     * <p>
     * Suppose we need to investigate a mutation from a gene string startGene to a gene string endGene where one mutation is defined as one single character changed in the gene string.
     * <p>
     * For example, "AACCGGTT" --> "AACCGGTA" is one mutation.
     * There is also a gene bank bank that records all the valid gene mutations. A gene must be in bank to make it a valid gene string.
     * <p>
     * Given the two gene strings startGene and endGene and the gene bank bank, return the minimum number of mutations needed to mutate from startGene to endGene. If there is no such a mutation, return -1.
     * <p>
     * Note that the starting point is assumed to be valid, so it might not be included in the bank.
     */
    public int minMutation(String startGene, String endGene, String[] bank) {
        Set<String> bankSet = new HashSet<>(Arrays.asList(bank));

        Queue<String> queue = new LinkedList<>();
        queue.offer(startGene);

        Set<String> seen = new HashSet<>();
        seen.add(startGene);
        int steps = 0;
        while (!queue.isEmpty()) {
            int count = queue.size();

            for (int i = 0; i < count; i++) {
                String gene = queue.poll();
                if (gene.equals(endGene))
                    return steps;

                for (String neighbor : minMutationNeighbors(gene, bankSet)) {
                    if (!seen.contains(neighbor)) {
                        queue.offer(neighbor);
                        seen.add(neighbor);
                    }
                }
            }
            steps += 1;
        }
        return -1;

    }

    private List<String> minMutationNeighbors(String gene, Set<String> bankSet) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (char mutation : new char[]{'A', 'C', 'G', 'T'}) {
                if (mutation == gene.charAt(i))
                    continue;
                String mutatedGene = gene.substring(0, i) + ("" + mutation) + gene.substring(i + 1);
                if (bankSet.contains(mutatedGene))
                    res.add(mutatedGene);
            }
        }
        return res;
    }

    public boolean canReach(int[] arr, int start) {
        int n = arr.length;
        boolean[] seen = new boolean[n];
        Queue<Integer> q = new LinkedList<>();
        q.add(start);

        while (!q.isEmpty()) {
            int node = q.poll();
            // check if reach zero
            if (arr[node] == 0) {
                return true;
            }
            if (arr[node] < 0 || seen[node]) {
                continue;
            }

            // check available next steps
            if (node + arr[node] < n) {
                q.offer(node + arr[node]);
            }
            if (node - arr[node] >= 0) {
                q.offer(node - arr[node]);
            }

            seen[node] = true;
        }

        return false;
    }


    /**
     * 127. Word Ladder
     * A transformation sequence from word beginWord to word endWord using a dictionary wordList is a sequence of words beginWord -> s1 -> s2 -> ... -> sk such that:
     * <p>
     * Every adjacent pair of words differs by a single letter.
     * Every si for 1 <= i <= k is in wordList. Note that beginWord does not need to be in wordList.
     * sk == endWord
     * Given two words, beginWord and endWord, and a dictionary wordList, return the number of words in the shortest transformation sequence from beginWord to endWord, or 0 if no such sequence exists.
     */
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> bankSet = new HashSet<>(wordList);

        if (!wordList.contains(endWord))
            return 0;

        Queue<String> queue = new LinkedList<>();
        queue.offer(beginWord);

        Set<String> seen = new HashSet<>();
        seen.add(beginWord);
        int steps = 0;
        while (!queue.isEmpty()) {
            int count = queue.size();

            for (int i = 0; i < count; i++) {
                String gene = queue.poll();
                if (gene.equals(endWord))
                    return steps + 1;

                for (String neighbor : ladderLength(gene, bankSet)) {
                    if (!seen.contains(neighbor)) {
                        queue.offer(neighbor);
                        seen.add(neighbor);
                    }
                }
            }
            steps += 1;
        }
        return 0;
    }

    private List<String> ladderLength(String word, Set<String> set) {
        List<String> res = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            for (char mutation : "abcdefghijklmnopqrstuvwxyz".toCharArray()) {
                if (mutation == word.charAt(i))
                    continue;
                String mutatedGene = word.substring(0, i) + ("" + mutation) + word.substring(i + 1);
                if (set.contains(mutatedGene))
                    res.add(mutatedGene);
            }
        }
        return res;
    }

    /**
     * 997. Find the Town Judge
     * In a town, there are n people labeled from 1 to n. There is a rumor that one of these people is secretly the town judge.
     * <p>
     * If the town judge exists, then:
     * <p>
     * The town judge trusts nobody.
     * Everybody (except for the town judge) trusts the town judge.
     * There is exactly one person that satisfies properties 1 and 2.
     * You are given an array trust where trust[i] = [ai, bi] representing that the person labeled ai trusts the person labeled bi. If a trust relationship does not exist in trust array, then such a trust relationship does not exist.
     * <p>
     * Return the label of the town judge if the town judge exists and can be identified, or return -1 otherwise.
     */
    public int findJudge(int n, int[][] trust) {
        int[][] graph = new int[n + 1][2];

        for (int[] t : trust) {
            graph[t[0]][0]++;
            graph[t[1]][1]++;
        }

        for (int i = 1; i <= n; i++) {
            if (graph[i][0] == 0 && graph[i][1] == n - 1)
                return i;
        }
        return -1;
    }

    /**
     * 463. Island Perimeter
     * You are given row x col grid representing a map where grid[i][j] = 1 represents land and grid[i][j] = 0 represents water.
     * <p>
     * Grid cells are connected horizontally/vertically (not diagonally). The grid is completely surrounded by water, and there is exactly one island (i.e., one or more connected land cells).
     * <p>
     * The island doesn't have "lakes", meaning the water inside isn't connected to the water around the island. One cell is a square with side length 1. The grid is rectangular, width and height don't exceed 100. Determine the perimeter of the island.
     */
    public int islandPerimeter(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] directions = CommonUtils.getVerticalHorizontalDirections();
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1) {
                    for (int[] direction : directions) {
                        int newRow = i + direction[0];
                        int newColumn = j + direction[1];
                        if (!CommonUtils.validGrid(n, m, newRow, newColumn) || grid[newRow][newColumn] != 1) {
                            res++;
                        }
                    }
                }
            }
        }
        return res;
    }

    /**
     * 1020. Number of Enclaves
     * You are given an m x n binary matrix grid, where 0 represents a sea cell and 1 represents a land cell.
     * <p>
     * A move consists of walking from one land cell to another adjacent (4-directionally) land cell or walking off the boundary of the grid.
     * <p>
     * Return the number of land cells in grid for which we cannot walk off the boundary of the grid in any number of moves
     */
    public int numEnclaves(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int[][] directions = CommonUtils.getVerticalHorizontalDirections();
        boolean[][] seen = new boolean[n][m];

        for (int row = 0; row < n; row++) {
            if (!seen[row][0] && grid[row][0] == 1) {
                numEnclaves(grid, seen, row, 0, directions);
            }

            if (!seen[row][m - 1] && grid[row][m - 1] == 1) {
                numEnclaves(grid, seen, row, m - 1, directions);
            }
        }

        for (int column = 0; column < m; column++) {
            if (!seen[0][column] && grid[0][column] == 1) {
                numEnclaves(grid, seen, 0, column, directions);
            }

            if (!seen[n - 1][column] && grid[n - 1][column] == 1) {
                numEnclaves(grid, seen, n - 1, column, directions);
            }
        }
        int ans = 0;

        for (int i = 1; i < n - 1; i++) {
            for (int j = 1; j < m - 1; j++) {
                if (!seen[i][j] && grid[i][j] == 1)
                    ans++;
            }
        }
        return ans;
    }

    private void numEnclaves(int[][] grid, boolean[][] seen, int row, int column, int[][] directions) {
        seen[row][column] = true;
        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newColumn = column + direction[1];
            if (CommonUtils.validGrid(grid.length, grid[0].length, newRow, newColumn)
                    && !seen[newRow][newColumn]
                    && grid[newRow][newColumn] == 1) {
                numEnclaves(grid, seen, newRow, newColumn, directions);
            }
        }
    }

    /**
     * 1376. Time Needed to Inform All Employees
     * A company has n employees with a unique ID for each employee from 0 to n - 1. The head of the company is the one with headID.
     * <p>
     * Each employee has one direct manager given in the manager array where manager[i] is the direct manager of the i-th employee, manager[headID] = -1. Also, it is guaranteed that the subordination relationships have a tree structure.
     * <p>
     * The head of the company wants to inform all the company employees of an urgent piece of news. He will inform his direct subordinates, and they will inform their subordinates, and so on until all employees know about the urgent news.
     * <p>
     * The i-th employee needs informTime[i] minutes to inform all of his direct subordinates (i.e., After informTime[i] minutes, all his direct subordinates can start spreading the news).
     * <p>
     * Return the number of minutes needed to inform all the employees about the urgent news.
     */
    public int numOfMinutes(int n, int headID, int[] manager, int[] informTime) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < n; i++) {
            if (i == headID)
                continue;
            graph.putIfAbsent(manager[i], new ArrayList<>());
            graph.get(manager[i]).add(i);
        }

        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(headID, 0));
        int ans = 0;
        while (!queue.isEmpty()) {
            Pair curr = queue.remove();
            int id = curr.id;
            int sum = curr.steps;

            ans = Math.max(sum, ans);
            for (Integer employee : graph.getOrDefault(id, new ArrayList<>())) {
                queue.add(new Pair(employee, sum + informTime[id]));
            }
        }
        return ans;
    }

    public List<List<Integer>> getAncestors(int n, int[][] edges) {
        List<List<Integer>> res = new ArrayList<>();
        Map<Integer, List<Integer>> outDegree = new HashMap<>();

        for (int i = 0; i < n; i++) {
            outDegree.put(i, new ArrayList<>());
            res.add(new ArrayList<>());
        }

        for (int[] edge : edges) {
            outDegree.get(edge[0]).add(edge[1]);
        }

        for (int i = 0; i < n; i++) {
            findAncestorsDFS(i, i, outDegree, res);
        }
        return res;
    }

    private void findAncestorsDFS(int currentNode, int ancestor, Map<Integer, List<Integer>> outDegree, List<List<Integer>> res) {
        for (Integer neighbor : outDegree.get(currentNode)) {
            if (res.get(neighbor).isEmpty() ||
                    res.get(neighbor).get(res.get(neighbor).size() - 1) != ancestor) {
                res.get(neighbor).add(ancestor);
                findAncestorsDFS(neighbor, ancestor, outDegree, res);
            }
        }
    }

    /**
     * 2192. All Ancestors of a Node in a Directed Acyclic Graph
     * You are given a positive integer n representing the number of nodes of a Directed Acyclic Graph (DAG). The nodes are numbered from 0 to n - 1 (inclusive).
     * <p>
     * You are also given a 2D integer array edges, where edges[i] = [fromi, toi] denotes that there is a unidirectional edge from fromi to toi in the graph.
     * <p>
     * Return a list answer, where answer[i] is the list of ancestors of the ith node, sorted in ascending order.
     * <p>
     * A node u is an ancestor of another node v if u can reach v via a set of edges.
     */
    public boolean equationsPossible(String[] equations) {
        Set<Character> characters = new HashSet<>();
        HashMap<Character, Set<Character>> equals = new HashMap<>();
        HashMap<Character, Set<Character>> notEquals = new HashMap<>();
        for (String equation : equations) {
            Character a = equation.charAt(0);
            Character b = equation.charAt(3);
            String condition = equation.substring(1, 3);
            characters.add(a);
            characters.add(b);
            if (condition.equals("==")) {
                equals.putIfAbsent(a, new HashSet<>());
                equals.putIfAbsent(b, new HashSet<>());
                equals.get(a).add(b);
                equals.get(b).add(a);
            } else {
                notEquals.putIfAbsent(a, new HashSet<>());
                notEquals.putIfAbsent(b, new HashSet<>());
                notEquals.get(a).add(b);
                notEquals.get(b).add(a);
            }
        }

        for (Character character : characters) {
            Set<Character> seen = new HashSet<>();
            if (!equationsPossible(equals, notEquals, character, seen, character))
                return false;
        }

        return true;
    }

    private boolean equationsPossible(HashMap<Character, Set<Character>> equals,
                                      HashMap<Character, Set<Character>> notEquals, Character character,
                                      Set<Character> seen, Character parent) {
        seen.add(character);
        if (notEquals.getOrDefault(character, new HashSet<>()).contains(character))
            return false;
        for (Character neighbor : equals.getOrDefault(character, new HashSet<>())) {
            if (!seen.contains(neighbor)) {
                if (Objects.nonNull(notEquals.get(neighbor)) && (notEquals.get(neighbor).contains(parent) || notEquals.get(neighbor).contains(character)))
                    return false;
                return equationsPossible(equals, notEquals, neighbor, seen, parent);
            }
        }
        return true;
    }

    /**
     * 994. Rotting Oranges
     * You are given an m x n grid where each cell can have one of three values:
     * <p>
     * 0 representing an empty cell,
     * 1 representing a fresh orange, or
     * 2 representing a rotten orange.
     * Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.
     * <p>
     * Return the minimum number of minutes that must elapse until no cell has a fresh orange. If this is impossible, return -1.
     */
    public int orangesRotting(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        Queue<Step> queue = new LinkedList<>();

        int[][] directions = CommonUtils.getVerticalHorizontalDirections();

        int total = 0;
        boolean[][] seen = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 2) {
                    queue.add(new Step(i, j, 0));
                    seen[i][j] = true;
                }
                if (grid[i][j] == 1)
                    total++;
            }
        }

        int minutes = 0;
        while (!queue.isEmpty() && total != 0) {
            int count = queue.size();
            minutes++;
            for (int i = 0; i < count; i++) {
                Step step = queue.remove();
                int row = step.row;
                int column = step.column;

                for (int[] direction : directions) {
                    int newRow = row + direction[0];
                    int newColumn = column + direction[1];

                    if (CommonUtils.validGrid(n, m, newRow, newColumn) && !seen[newRow][newColumn] && grid[newRow][newColumn] == 1) {
                        seen[newRow][newColumn] = true;
                        total--;
                        queue.add(new Step(newRow, newColumn, 0));
                    }
                }
            }
        }
        return total == 0 ? minutes : -1;
    }
}
