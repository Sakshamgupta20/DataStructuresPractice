package Graphs;

import java.util.*;

public class KahnAlgorithm {

    /**
     * 210. Course Schedule II
     * There are a total of numCourses courses you have to take, labeled from 0 to numCourses - 1. You are given an array prerequisites where prerequisites[i] = [ai, bi] indicates that you must take course bi first if you want to take course ai.
     * <p>
     * For example, the pair [0, 1], indicates that to take course 0 you have to first take course 1.
     * Return the ordering of courses you should take to finish all courses. If there are many valid answers, return any of them. If it is impossible to finish all courses, return an empty array.
     */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> courses = new HashMap<>();
        int[] indegree = new int[numCourses];

        for (int i = 0; i < numCourses; i++) {
            courses.put(i, new ArrayList<>());
        }
        for (int[] prerequisite : prerequisites) {
            courses.get(prerequisite[1]).add(prerequisite[0]);
            indegree[prerequisite[0]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if(indegree[i] == 0)
                queue.add(i);
        }
        int[] ans = new int[numCourses];
        int index = 0;
        while (!queue.isEmpty()) {
            int node = queue.poll();
            ans[index++] = node;
            for (Integer co : courses.get(node)) {
                indegree[co]--;
                if(indegree[co] == 0)
                    queue.add(co);
            }
        }
        // Check to see if topological sort is possible or not.
        if (index == numCourses) {
            return ans;
        }

        return new int[0];
    }

    /**
     * 269. Alien Dictionary
     * There is a new alien language that uses the English alphabet. However, the order of the letters is unknown to you.
     * <p>
     * You are given a list of strings words from the alien language's dictionary. Now it is claimed that the strings in words are
     * sorted lexicographically
     * by the rules of this new language.
     * <p>
     * If this claim is incorrect, and the given arrangement of string in words cannot correspond to any order of letters, return "".
     * <p>
     * Otherwise, return a string of the unique letters in the new alien language sorted in lexicographically increasing order by the new language's rules. If there are multiple solutions, return any of them.
     * TODO
     */
    public String alienOrder(String[] words) {
        Map<Character, List<Character>> graph = new HashMap<>();
        HashMap<Character, Integer> indegree = new HashMap<>();
        for (String word : words) {
            char[] chars = word.toCharArray();
            for (int i = 1; i < word.length(); i++) {
                char first = chars[i - 1];
                char second = chars[i];
                graph.putIfAbsent(first, new ArrayList<>());
                graph.get(first).add(second);
                indegree.putIfAbsent(first, 0);
                indegree.put(second, indegree.getOrDefault(second, 0) + 1);
            }
        }
        Queue<Character> queue = new LinkedList<>();
        indegree.forEach((alpha, count) -> {
            if (count == 0)
                queue.add(alpha);
        });

        StringBuilder ans = new StringBuilder();
        while (!queue.isEmpty()) {
            Character alpha = queue.poll();
            ans.append(alpha);

            if (graph.containsKey(alpha)) {
                for (Character neighbor : graph.get(alpha)) {
                    indegree.put(neighbor, indegree.get(neighbor) - 1);
                    if (indegree.get(neighbor) == 0)
                        queue.add(neighbor);
                }
            }
        }
        indegree.forEach((alpha, count) -> {
            if (count != 0)
                ans.append(alpha);
        });
        return ans.toString();
    }

    /**
     * 310. Minimum Height Trees
     * A tree is an undirected graph in which any two vertices are connected by exactly one path. In other words, any connected graph without simple cycles is a tree.
     * <p>
     * Given a tree of n nodes labelled from 0 to n - 1, and an array of n - 1 edges where edges[i] = [ai, bi] indicates that there is an undirected edge between the two nodes ai and bi in the tree, you can choose any node of the tree as the root. When you select a node x as the root, the result tree has height h. Among all possible rooted trees, those with minimum height (i.e. min(h))  are called minimum height trees (MHTs).
     * <p>
     * Return a list of all MHTs' root labels. You can return the answer in any order.
     * <p>
     * The height of a rooted tree is the number of edges on the longest downward path between the root and a leaf.
     * TODO
     */
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        return null;
    }

    /**
     * 1136. Parallel Courses
     * You are given an integer n, which indicates that there are n courses labeled from 1 to n. You are also given an array relations where relations[i] = [prevCoursei, nextCoursei], representing a prerequisite relationship between course prevCoursei and course nextCoursei: course prevCoursei has to be taken before course nextCoursei.
     * <p>
     * In one semester, you can take any number of courses as long as you have taken all the prerequisites in the previous semester for the courses you are taking.
     * <p>
     * Return the minimum number of semesters needed to take all courses. If there is no way to take all the courses, return -1.
     */
    public int minimumSemesters(int n, int[][] relations) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        int[] indegree = new int[n + 1];

        for (int[] prerequisite : relations) {
            graph.putIfAbsent(prerequisite[0], new ArrayList<>());
            graph.get(prerequisite[0]).add(prerequisite[1]);
            indegree[prerequisite[1]]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        boolean possible = false;
        for (int i = 1; i <= n; i++) {
            if (indegree[i] == 0) {
                queue.add(i);
                possible = true;
            }
        }

        if (!possible)
            return -1;

        int count = 0;
        int studiedCount = 0;
        while (!queue.isEmpty()) {
            int currCount = queue.size();
            for (int i = 0; i < currCount; i++) {
                studiedCount += 1;
                int course = queue.poll();
                for (Integer neighbor : graph.getOrDefault(course, new ArrayList<>())) {
                    indegree[neighbor] -= 1;
                    if (indegree[neighbor] == 0)
                        queue.add(neighbor);
                }
            }
            count += 1;
        }
        return studiedCount == n ? count : -1;

    }
}
