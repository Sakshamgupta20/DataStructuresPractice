package Trees;


import Graphs.Pair;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class TreeProblems {
    public void postOrder(TreeNode node) {
        if (node == null)
            return;
        postOrder(node.left);
        postOrder(node.right);
        System.out.print(node.val);
    }

    public void preOrder(TreeNode node) {
        if (node == null)
            return;
        System.out.print(node.val);
        preOrder(node.left);
        preOrder(node.right);
    }

    public void inOrder(TreeNode node) {
        if (node == null)
            return;
        inOrder(node.left);
        System.out.print(node.val);
        inOrder(node.right);
    }

    public int maxDepthRecursion(TreeNode root) {
        if (root == null)
            return 0;
        return Math.max(maxDepthRecursion(root.left) + 1, maxDepthRecursion(root.right) + 1);
    }

    public int maxDepthIterative(TreeNode root) {
        if (root == null)
            return 0;
        Stack<SimpleEntry> stack = new Stack<>();
        stack.add(new SimpleEntry(root, 1));

        int ans = 0;
        while (!stack.isEmpty()) {
            SimpleEntry<TreeNode, Integer> pair = stack.pop();

            if (Objects.nonNull(pair.getKey().left))
                stack.add(new SimpleEntry(pair.getKey().left, pair.getValue() + 1));

            if (Objects.nonNull(pair.getKey().right))
                stack.add(new SimpleEntry(pair.getKey().right, pair.getValue() + 1));

            ans = Math.max(ans, pair.getValue());
        }
        return ans;
    }

    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null)
            return false;

        targetSum -= root.val;
        if (Objects.isNull(root.left) && Objects.isNull(root.right))
            return targetSum == 0;

        return hasPathSum(root.left, targetSum) || hasPathSum(root.right, targetSum);
    }

    /**
     * Given a binary tree root, a node X in the tree is named good if in the path from root to X there are no nodes with a value greater than X.
     * <p>
     * Return the number of good nodes in the binary tree.
     */
    public int goodNodes(TreeNode root) {
        AtomicInteger integer = new AtomicInteger(0);
        goodNodes(root, null, integer);
        return integer.get();
    }

    public void goodNodes(TreeNode root, Integer lastValue, AtomicInteger answer) {
        if (root == null)
            return;

        if (Objects.isNull(lastValue) || lastValue <= root.val)
            answer.incrementAndGet();

        int maxSoFar = Objects.isNull(lastValue) ? root.val : Math.max(root.val, lastValue);
        goodNodes(root.left, maxSoFar, answer);
        goodNodes(root.right, maxSoFar, answer);
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null)
            return true;

        if (p == null || q == null)
            return false;

        boolean left = isSameTree(p.left, q.left);
        boolean right = isSameTree(p.right, q.right);

        return left && right && q.val == p.val;

    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null)
            return null;

        if (root == p || root == q)
            return root;

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null)
            return root;

        if (left != null)
            return left;
        return right;
    }

    public int minDepth(TreeNode root) {
        if (root == null)
            return 0;

        if (root.left == null)
            return 1 + minDepth(root.right);
        else if (root.right == null) {
            return 1 + minDepth(root.left);
        }

        return Math.min(minDepth(root.left), minDepth(root.right)) + 1;
    }

    public int maxAncestorDiff(TreeNode root) {
        AtomicInteger result = new AtomicInteger(0);
        maxAncestorDiff(root, root.val, root.val, result);
        return result.get();
    }

    public void maxAncestorDiff(TreeNode root, int currMax, int currMin, AtomicInteger result) {
        if (root == null)
            return;

        int possibleResult = Math.max(Math.abs(currMax - root.val), Math.abs(currMin - root.val));
        result.set(Math.max(result.get(), possibleResult));

        currMax = Math.max(currMax, root.val);
        currMin = Math.min(currMin, root.val);
        maxAncestorDiff(root.left, currMax, currMin, result);
        maxAncestorDiff(root.right, currMax, currMin, result);
    }

    public int diameterOfBinaryTree(TreeNode root) {
        AtomicInteger result = new AtomicInteger();
        diameterOfBinaryTree(root, result);
        return result.get();
    }

    public int diameterOfBinaryTree(TreeNode root, AtomicInteger result) {
        if (root == null)
            return 0;

        int left = diameterOfBinaryTree(root.left, result);
        int right = diameterOfBinaryTree(root.right, result);

        result.set(Math.max(result.get(), left + right));

        return Math.max(left, right) + 1;
    }

    public List<Integer> rightSideViewBfs(TreeNode root) {
        List<Integer> answers = new ArrayList<>();
        if (root == null)
            return Collections.emptyList();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int currentSize = queue.size();
            Integer prev = null;
            for (int i = 0; i < currentSize; i++) {
                TreeNode node = queue.poll();
                prev = node.val;

                if (Objects.nonNull(node.left))
                    queue.add(node.left);
                if (Objects.nonNull(node.right))
                    queue.add(node.right);
            }

            answers.add(prev);

        }
        return answers;
    }

    public List<Integer> rightSideViewDfs(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        rightSideViewDfs(root, 0, res);
        return res;

    }

    private void rightSideViewDfs(TreeNode root, int depth, List<Integer> res) {
        if (root == null) {
            return;
        }
        if (depth == res.size()) {
            res.add(root.val);
        } else {
            res.set(depth, root.val);
        }
        rightSideViewDfs(root.left, depth + 1, res);
        rightSideViewDfs(root.right, depth + 1, res);
    }


    /**
     * Find Largest Value in Each Tree Row
     */
    public List<Integer> largestValuesBfs(TreeNode root) {
        List<Integer> answers = new ArrayList<>();
        if (root == null)
            return Collections.emptyList();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            int currentSize = queue.size();
            int currMax = Integer.MIN_VALUE;
            for (int i = 0; i < currentSize; i++) {
                TreeNode node = queue.poll();
                currMax = Math.max(currMax, node.val);

                if (Objects.nonNull(node.left))
                    queue.add(node.left);
                if (Objects.nonNull(node.right))
                    queue.add(node.right);
            }

            answers.add(currMax);

        }
        return answers;
    }

    /**
     * Find Largest Value in Each Tree Row
     */
    public List<Integer> largestValuesDfs(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        largestValuesDfs(root, result, 0);
        return result;
    }

    public void largestValuesDfs(TreeNode root, List<Integer> list, int level) {
        if (root == null) {
            return;
        }
        if (level == list.size()) {
            list.add(root.val);
        } else {
            list.set(level, Math.max(list.get(level), root.val));
        }
        largestValuesDfs(root.left, list, level + 1);
        largestValuesDfs(root.right, list, level + 1);
    }

    /**
     * Given the root of a binary tree, return the sum of values of its deepest leaves.
     */
    public int deepestLeavesSumBfs(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int answer = 0;

        while (!queue.isEmpty()) {
            int currentSize = queue.size();
            answer = 0;
            for (int i = 0; i < currentSize; i++) {
                TreeNode node = queue.poll();
                answer = answer + node.val;

                if (Objects.nonNull(node.left))
                    queue.add(node.left);
                if (Objects.nonNull(node.right))
                    queue.add(node.right);
            }

        }
        return answer;
    }

    /**
     * Given the root of a binary tree, return the sum of values of its deepest leaves.
     */
    public int deepestLeavesSumDfs(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        deepestLeavesSumDfs(root, res, 0);
        return res.get(res.size() - 1);
    }

    private void deepestLeavesSumDfs(TreeNode root, List<Integer> res, int depth) {
        if (root == null)
            return;

        if (depth == res.size()) {
            res.add(root.val);
        } else {
            res.set(depth, res.get(depth) + root.val);
        }

        deepestLeavesSumDfs(root.left, res, depth + 1);
        deepestLeavesSumDfs(root.right, res, depth + 1);
    }

    /**
     * Given the root of a binary tree, return the zigzag level order traversal of its nodes' values. (i.e., from left to right, then right to left for the next level and alternate between).
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> answers = new ArrayList<>();
        Queue<TreeNode> queue = new LinkedList<>();
        if (root == null)
            return answers;
        queue.add(root);

        while (!queue.isEmpty()) {
            int currentSize = queue.size();
            List<Integer> current = new ArrayList<>();
            for (int i = 0; i < currentSize; i++) {
                TreeNode node = queue.poll();
                if (answers.size() % 2 != 0)
                    current.add(0, node.val);
                else
                    current.add(node.val);

                if (Objects.nonNull(node.left))
                    queue.add(node.left);
                if (Objects.nonNull(node.right))
                    queue.add(node.right);
            }
            answers.add(current);

        }
        return answers;
    }

    /**
     * 872. Leaf-Similar Trees
     * Consider all the leaves of a binary tree, from left to right order, the values of those leaves form a leaf value sequence.
     */
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        List<Integer> root1Leafs = new ArrayList<>();
        List<Integer> root2Leafs = new ArrayList<>();
        leafSimilar(root1, root1Leafs);
        leafSimilar(root2, root2Leafs);
        return root1Leafs.equals(root2Leafs);
    }

    private void leafSimilar(TreeNode node, List<Integer> rootLeafs) {
        if (node == null)
            return;
        if (node.left == null && node.right == null) {
            rootLeafs.add(node.val);
            return;
        }

        leafSimilar(node.left, rootLeafs);
        leafSimilar(node.right, rootLeafs);
    }

    /**
     * 226. Invert Binary Tree
     * Given the root of a binary tree, invert the tree, and return its root.
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null)
            return null;
        TreeNode right = invertTree(root.right);
        TreeNode left = invertTree(root.left);

        root.left = right;
        root.right = left;
        return root;
    }

    public boolean isSymmetric(TreeNode root) {
        if (root == null)
            return false;
        return isSymmetricDfs(root.left, root.right);
    }

    public boolean isSymmetricDfs(TreeNode left, TreeNode right) {
        if (left == null && right == null)
            return true;
        else if (left == null || right == null)
            return false;


        return left.val == right.val && isSymmetricDfs(left.left, right.right) && isSymmetricDfs(left.right, right.left);
    }

    /**
     * 113. Path Sum II
     * Given the root of a binary tree and an integer targetSum, return all root-to-leaf paths where the sum of the node values in the path equals targetSum. Each path should be returned as a list of the node values, not node references.
     * <p>
     * A root-to-leaf path is a path starting from the root and ending at any leaf node. A leaf is a node with no children.
     */
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> res = new ArrayList<>();
        pathSumDfs(root, targetSum, res, new ArrayList<>());
        return res;
    }

    private void pathSumDfs(TreeNode node, int remaining, List<List<Integer>> res, List<Integer> curr) {

        if (node == null)
            return;

        curr.add(node.val);
        if (remaining == node.val && node.left == null && node.right == null)
            res.add(new ArrayList<>(curr));
        else {
            pathSumDfs(node.left, remaining - node.val, res, curr);
            pathSumDfs(node.right, remaining - node.val, res, curr);
        }

        curr.remove(curr.size() - 1);

    }

    /**
     * 1325. Delete Leaves With a Given Value
     * Given a binary tree root and an integer target, delete all the leaf nodes with value target.
     * <p>
     * Note that once you delete a leaf node with value target, if its parent node becomes a leaf node and has the value target, it should also be deleted (you need to continue doing that until you cannot).
     */
    public TreeNode removeLeafNodes(TreeNode root, int target) {
        if (root == null)
            return null;

        root.left = removeLeafNodes(root.left, target);
        root.right = removeLeafNodes(root.right, target);

        if (root.left == null && root.right == null && root.val == target) {
            return null;
        }
        return root;
    }

    /**
     * 437. Path Sum III
     * Given the root of a binary tree and an integer targetSum, return the number of paths where the sum of the values along the path equals targetSum.
     * <p>
     * The path does not need to start or end at the root or a leaf, but it must go downwards (i.e., traveling only from parent nodes to child nodes).
     */
    public int pathSum3(TreeNode root, int targetSum) {
        HashMap<Long, Integer> prefixSum = new HashMap<>();
        prefixSum.put(0L, 1);
        AtomicInteger ans = new AtomicInteger();
        targetSum(root, targetSum, prefixSum, ans, 0L);
        return ans.get();
    }

    private void targetSum(TreeNode node, int targetSum, HashMap<Long, Integer> prefixSum, AtomicInteger ans, long currSum) {
        if (node == null)
            return;
        currSum += node.val;

        ans.set(ans.get() + prefixSum.getOrDefault(currSum - targetSum, 0));

        prefixSum.put(currSum, prefixSum.getOrDefault(currSum, 0) + 1);

        targetSum(node.left, targetSum, prefixSum, ans, currSum);
        targetSum(node.right, targetSum, prefixSum, ans, currSum);

        prefixSum.put(currSum, prefixSum.get(currSum) - 1);
    }


    /**
     * 1372. Longest ZigZag Path in a Binary Tree
     * You are given the root of a binary tree.
     * <p>
     * A ZigZag path for a binary tree is defined as follow:
     * <p>
     * Choose any node in the binary tree and a direction (right or left).
     * If the current direction is right, move to the right child of the current node; otherwise, move to the left child.
     * Change the direction from right to left or from left to right.
     * Repeat the second and third steps until you can't move in the tree.
     * Zigzag length is defined as the number of nodes visited - 1. (A single node has a length of 0).
     * <p>
     * Return the longest ZigZag path contained in that tree.
     */
    public int longestZigZag(TreeNode root) {
        AtomicInteger max = new AtomicInteger();
        longestZigZag(root.left, max, 1, true);
        if (Objects.isNull(root.left))
            longestZigZag(root.right, max, 0, true);
        else
            longestZigZag(root.right, max, 1, true);
        return max.get();
    }

    private void longestZigZag(TreeNode node, AtomicInteger max, int curr, boolean left) {
        if (node == null)
            return;

        if (left)
            longestZigZag(node.right, max, curr + 1, false);
        else
            longestZigZag(node.left, max, curr + 1, true);

        max.set(Math.max(max.get(), curr));

    }

    /**
     * 2101. Detonate the Maximum Bombs
     * You are given a list of bombs. The range of a bomb is defined as the area where its effect can be felt. This area is in the shape of a circle with the center as the location of the bomb.
     * <p>
     * The bombs are represented by a 0-indexed 2D integer array bombs where bombs[i] = [xi, yi, ri]. xi and yi denote the X-coordinate and Y-coordinate of the location of the ith bomb, whereas ri denotes the radius of its range.
     * <p>
     * You may choose to detonate a single bomb. When a bomb is detonated, it will detonate all bombs that lie in its range. These bombs will further detonate the bombs that lie in their ranges.
     * <p>
     * Given the list of bombs, return the maximum number of bombs that can be detonated if you are allowed to detonate only one bomb.
     */
    public int maximumDetonation(int[][] bombs) {
        HashMap<Integer, List<Integer>> graph = new HashMap<>();
        for (int i = 0; i < bombs.length; i++) {
            for (int j = 0; j < bombs.length; j++) {
                if (i == j)
                    continue;
                int xi = bombs[i][0], yi = bombs[i][1], ri = bombs[i][2];
                int xj = bombs[j][0], yj = bombs[j][1];

                if ((long) ri * ri >= (long) (xi - xj) * (xi - xj) + (long) (yi - yj) * (yi - yj)) {
                    graph.computeIfAbsent(i, k -> new ArrayList<>()).add(j);
                }
            }
        }

        int answer = 0;
        for (int i = 0; i < bombs.length; i++) {
            int count = maximumDetonation(i, new HashSet<>(), graph);
            answer = Math.max(answer, count);
        }

        return answer;

    }

    private int maximumDetonation(int i, HashSet<Integer> visited, HashMap<Integer, List<Integer>> graph) {
        visited.add(i);
        int count = 1;
        for (Integer neighbor : graph.getOrDefault(i, new ArrayList<>())) {
            if (!visited.contains(neighbor)) {
                count += maximumDetonation(neighbor, visited, graph);
            }
        }
        return count;
    }

    /**
     * 102. Binary Tree Level Order Traversal
     * Given the root of a binary tree, return the level order traversal of its nodes' values. (i.e., from left to right, level by level).
     */
    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null)
            return Collections.emptyList();
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        List<List<Integer>> res = new ArrayList<>();

        while (!queue.isEmpty()) {
            int count = queue.size();

            List<Integer> level = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                TreeNode node = queue.remove();
                level.add(node.val);

                if (node.left != null)
                    queue.add(node.left);

                if (node.right != null)
                    queue.add(node.right);
            }
            res.add(level);
        }
        return res;
    }

    /**
     * 1161. Maximum Level Sum of a Binary Tree
     * <p>
     * Given the root of a binary tree, the level of its root is 1, the level of its children is 2, and so on.
     * <p>
     * Return the smallest level x such that the sum of all the values of nodes at level x is maximal.
     */
    public int maxLevelSum(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int max = Integer.MIN_VALUE;
        int maxLevel = 0;
        int level = 0;
        while (!queue.isEmpty()) {
            int count = queue.size();
            level++;

            int levelSum = 0;
            for (int i = 0; i < count; i++) {
                TreeNode node = queue.remove();
                levelSum += node.val;

                if (node.left != null)
                    queue.add(node.left);

                if (node.right != null)
                    queue.add(node.right);
            }
            if (levelSum > max) {
                max = levelSum;
                maxLevel = level;
            }
        }
        return maxLevel;
    }

    public int maxLevelSumDfs(TreeNode root) {
        List<Integer> sums = new ArrayList<>();
        maxLevelSumDfs(root, sums, 0);

        int maxSum = Integer.MIN_VALUE;
        int ans = 0;

        for (int i = 0; i < sums.size(); i++) {
            if (maxSum < sums.get(i)) {
                maxSum = sums.get(i);
                ans = i + 1;
            }
        }
        return ans;
    }

    public void maxLevelSumDfs(TreeNode node, List<Integer> sums, int level) {
        if (node == null)
            return;

        if (sums.size() == level)
            sums.add(level, node.val);
        else
            sums.set(level, sums.get(level) + node.val);

        maxLevelSumDfs(node.left, sums, level + 1);
        maxLevelSumDfs(node.right, sums, level + 1);

    }

    /**
     * 637. Average of Levels in Binary Tree
     * Given the root of a binary tree, return the average value of the nodes on each level in the form of an array. Answers within 10-5 of the actual answer will be accepted.
     */
    public List<Double> averageOfLevels(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        List<Double> res = new ArrayList<>();
        while (!queue.isEmpty()) {
            int count = queue.size();

            double levelSum = 0;
            for (int i = 0; i < count; i++) {
                TreeNode node = queue.remove();
                levelSum += node.val;

                if (node.left != null)
                    queue.add(node.left);

                if (node.right != null)
                    queue.add(node.right);
            }
            res.add(levelSum / count);
        }
        return res;
    }

    /**
     * 1609. Even Odd Tree
     * A binary tree is named Even-Odd if it meets the following conditions:
     * <p>
     * The root of the binary tree is at level index 0, its children are at level index 1, their children are at level index 2, etc.
     * For every even-indexed level, all nodes at the level have odd integer values in strictly increasing order (from left to right).
     * For every odd-indexed level, all nodes at the level have even integer values in strictly decreasing order (from left to right).
     * Given the root of a binary tree, return true if the binary tree is Even-Odd, otherwise return false.
     */
    public boolean isEvenOddTree(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int level = 0;
        while (!queue.isEmpty()) {
            int count = queue.size();

            int lastValue = 0;
            for (int i = 0; i < count; i++) {
                TreeNode node = queue.remove();

                boolean even = level % 2 == 0;
                if (even && (node.val % 2 == 0))
                    return false;
                if (!even && (node.val % 2 != 0))
                    return false;

                if (i > 0) {
                    if (even && lastValue >= node.val) {
                        return false;
                    }
                    if (!even && lastValue <= node.val)
                        return false;
                }

                if (node.left != null)
                    queue.add(node.left);

                if (node.right != null)
                    queue.add(node.right);
                lastValue = node.val;
            }
            level++;
        }
        return true;
    }

    public boolean isBalanced(TreeNode root) {
        return isBalancedR(root) != -1;
    }

    public int isBalancedR(TreeNode root) {
        if (root == null)
            return 0;
        int left = isBalancedR(root.left);
        int right = isBalancedR(root.right);
        if (left == -1 || right == -1 || Math.abs(left - right) > 1) return -1;
        return Math.max(left, right) + 1;
    }

    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null)
            return false;
        if (isIdentical(root, subRoot))
            return true;
        return isSubtree(root.left, subRoot) || isSubtree(root.right, subRoot);
    }

    private boolean isIdentical(TreeNode node1, TreeNode node2) {

        // If any of the nodes is null, then both must be null
        if (node1 == null || node2 == null) {
            return node1 == null && node2 == null;
        }

        return node1.val == node2.val && isIdentical(node1.left, node2.left) && isIdentical(node1.right, node2.right);
    }

    public int goodNodes1(TreeNode root) {
        AtomicInteger integer = new AtomicInteger();
        goodNodes(root, root.val, integer);
        return integer.get();
    }

    public void goodNodes(TreeNode root, int currHighest, AtomicInteger integer) {
        if (root == null)
            return;

        if (root.val >= currHighest) {
            currHighest = root.val;
            integer.getAndIncrement();
        }

        goodNodes(root.left, currHighest, integer);
        goodNodes(root.right, currHighest, integer);
    }

    public int maxPathSum(TreeNode root) {
        AtomicInteger max = new AtomicInteger(root.val);
        maxPathSum(root, max);
        return max.get();
    }

    public int maxPathSum(TreeNode root, AtomicInteger max) {
        if (root == null)
            return 0;
        int left = Math.max(maxPathSum(root.left, max), 0);
        int right = Math.max(maxPathSum(root.right, max), 0);

        int currentMax = root.val + left + right;
        max.set(Math.max(max.get(), currentMax));

        return root.val + Math.max(left, right);
    }

    /**
     * 2924. Find Champion II
     * There are n teams numbered from 0 to n - 1 in a tournament; each team is also a node in a DAG.
     * <p>
     * You are given the integer n and a 0-indexed 2D integer array edges of length m representing the DAG, where edges[i] = [ui, vi] indicates that there is a directed edge from team ui to team vi in the graph.
     * <p>
     * A directed edge from a to b in the graph means that team a is stronger than team b and team b is weaker than team a.
     * <p>
     * Team a will be the champion of the tournament if there is no team b that is stronger than team a.
     * <p>
     * Return the team that will be the champion of the tournament if there is a unique champion, otherwise, return -1.
     * <p>
     * Notes
     * <p>
     * A cycle is a series of nodes a1, a2, ..., an, an+1 such that node a1 is the same node as node an+1, the nodes a1, a2, ..., an are distinct, and there is a directed edge from the node ai to node ai+1 for every i in the range [1, n].
     * A DAG is a directed graph that does not have any cycle.
     */
    public int findChampion(int n, int[][] edges) {
        int[] weakTeams = new int[n];
        for (int[] edge : edges) {
            weakTeams[edge[1]]++;
        }
        int result = -1;

        for (int i = 0; i < n; i++) {
            if (weakTeams[i] == 0 && result != -1) {
                return -1;
            } else if (weakTeams[i] == 0) {
                result = i;
            }
        }

        return result;
    }

    /**
     * 2415. Reverse Odd Levels of Binary Tree
     * Given the root of a perfect binary tree, reverse the node values at each odd level of the tree.
     * <p>
     * For example, suppose the node values at level 3 are [2,1,3,4,7,11,29,18], then it should become [18,29,11,7,4,3,1,2].
     * Return the root of the reversed tree.
     * <p>
     * A binary tree is perfect if all parent nodes have two children and all leaves are on the same level.
     * <p>
     * The level of a node is the number of edges along the path between it and the root node.
     */
    public TreeNode reverseOddLevels(TreeNode root) {
        if (root == null) {
            return null;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        int level = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            List<TreeNode> currentLevelNodes = new ArrayList<>();

            // Process all nodes at the current level.
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                currentLevelNodes.add(node);

                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            }

            if (level % 2 == 1) {
                int left = 0, right = currentLevelNodes.size() - 1;
                while (left < right) {
                    int temp = currentLevelNodes.get(left).val;
                    currentLevelNodes.get(left).val = currentLevelNodes.get(right).val;
                    currentLevelNodes.get(right).val = temp;
                    left++;
                    right--;
                }
            }

            level++;
        }

        return root;
    }

    /**
     * 2872. Maximum Number of K-Divisible Components
     * There is an undirected tree with n nodes labeled from 0 to n - 1. You are given the integer n and a 2D integer array edges of length n - 1, where edges[i] = [ai, bi] indicates that there is an edge between nodes ai and bi in the tree.
     * <p>
     * You are also given a 0-indexed integer array values of length n, where values[i] is the value associated with the ith node, and an integer k.
     * <p>
     * A valid split of the tree is obtained by removing any set of edges, possibly empty, from the tree such that the resulting components all have values that are divisible by k, where the value of a connected component is the sum of the values of its nodes.
     * <p>
     * Return the maximum number of components in any valid split.
     */
    public int maxKDivisibleComponents(int n, int[][] edges, int[] values, int k) {
        List<Integer>[] adjList = new ArrayList[n];

        for (int i = 0; i < n; i++) {
            adjList[i] = new ArrayList<>();
        }

        for (int[] edge : edges) {
            adjList[(edge[0])].add(edge[1]);
            adjList[(edge[1])].add(edge[0]);
        }

        int[] componentCount = new int[1];

        maxKDivisibleComponentsDfs(adjList, 0, -1, values, k, componentCount);

        return componentCount[0];
    }

    private int maxKDivisibleComponentsDfs(List<Integer>[] adjList, int node, int parentNode, int[] values, int k, int[] componentCount) {

        int sum = 0;
        for (Integer neighbor : adjList[node]) {
            if (neighbor != parentNode) {
                sum += maxKDivisibleComponentsDfs(adjList, neighbor, node, values, k, componentCount);
                sum %= k;
            }
        }
        sum += values[node];
        sum %= k;

        if (sum == 0) {
            componentCount[0]++;
        }

        return sum;
    }

    /**
     * 1028. Recover a Tree From Preorder Traversal
     * We run a preorder depth-first search (DFS) on the root of a binary tree.
     * <p>
     * At each node in this traversal, we output D dashes (where D is the depth of this node), then we output the value of this node.  If the depth of a node is D, the depth of its immediate child is D + 1.  The depth of the root node is 0.
     * <p>
     * If a node has only one child, that child is guaranteed to be the left child.
     * <p>
     * Given the output traversal of this traversal, recover the tree and return its root.
     */
    public TreeNode recoverFromPreorder(String traversal) {
        List<TreeNode> levels = new ArrayList<>();
        int index = 0, n = traversal.length();

        while (index < n) {
            int depth = 0;
            while (index < n && traversal.charAt(index) == '-') {
                depth++;
                index++;
            }

            // Extract node value
            int value = 0;
            while (index < n && Character.isDigit(traversal.charAt(index))) {
                value = value * 10 + (traversal.charAt(index) - '0');
                index++;
            }

            // Create the new node
            TreeNode node = new TreeNode(value);

            // Adjust levels list to match the current depth
            if (depth < levels.size()) {
                levels.set(depth, node);
            } else {
                levels.add(node);
            }

            // Attach the node to its parent
            if (depth > 0) {
                TreeNode parent = levels.get(depth - 1);
                if (parent.left == null) {
                    parent.left = node;
                } else {
                    parent.right = node;
                }
            }
        }

        // The root node is always at index 0
        return levels.get(0);
    }

    public TreeNode constructFromPrePost(int[] preorder, int[] postorder) {
        int n = postorder.length;
        int[] postIndex = new int[n + 1];
        for (int i = 0; i < n; i++) {
            postIndex[postorder[i]] = i;
        }

        return constructFromPrePost(0, n - 1, 0, preorder, postIndex);
    }

    private TreeNode constructFromPrePost(int preStart, int preEnd, int postStart, int[] preorder, int[] postIndex) {
        if (preStart > preEnd)
            return null;
        if (preStart == preEnd)
            return new TreeNode(preorder[preStart]);

        int leftNode = preorder[preStart + 1];
        int totalLeftNodes = postIndex[leftNode] - postStart + 1;

        TreeNode root = new TreeNode(preorder[preStart]);

        root.left = constructFromPrePost(preStart + 1,
                preStart + totalLeftNodes, postStart, preorder, postIndex);

        root.right = constructFromPrePost(preStart + totalLeftNodes + 1,
                preEnd, postStart + totalLeftNodes, preorder, postIndex);
        return root;
    }

    /**
     * 2467. Most Profitable Path in a Tree
     * There is an undirected tree with n nodes labeled from 0 to n - 1, rooted at node 0. You are given a 2D integer array edges of length n - 1 where edges[i] = [ai, bi] indicates that there is an edge between nodes ai and bi in the tree.
     * <p>
     * At every node i, there is a gate. You are also given an array of even integers amount, where amount[i] represents:
     * <p>
     * the price needed to open the gate at node i, if amount[i] is negative, or,
     * the cash reward obtained on opening the gate at node i, otherwise.
     * The game goes on as follows:
     * <p>
     * Initially, Alice is at node 0 and Bob is at node bob.
     * At every second, Alice and Bob each move to an adjacent node. Alice moves towards some leaf node, while Bob moves towards node 0.
     * For every node along their path, Alice and Bob either spend money to open the gate at that node, or accept the reward. Note that:
     * If the gate is already open, no price will be required, nor will there be any cash reward.
     * If Alice and Bob reach the node simultaneously, they share the price/reward for opening the gate there. In other words, if the price to open the gate is c, then both Alice and Bob pay c / 2 each. Similarly, if the reward at the gate is c, both of them receive c / 2 each.
     * If Alice reaches a leaf node, she stops moving. Similarly, if Bob reaches node 0, he stops moving. Note that these events are independent of each other.
     * Return the maximum net income Alice can have if she travels towards the optimal leaf node.
     */
    public int mostProfitablePath(int[][] edges, int bob, int[] amount) {
        int n = amount.length, maxIncome = Integer.MIN_VALUE;
        Map<Integer, Integer> bobPath = new HashMap<>();
        boolean[] visited = new boolean[n];
        List<List<Integer>> tree = new ArrayList<>();
        Queue<int[]> nodeQueue = new LinkedList<>();
        nodeQueue.add(new int[]{0, 0, 0});

        for (int i = 0; i < n; i++) {
            tree.add(new ArrayList<>());
        }

        // Form tree with edges
        for (int[] edge : edges) {
            tree.get(edge[0]).add(edge[1]);
            tree.get(edge[1]).add(edge[0]);
        }

        findBobPath(bob, 0,bobPath,visited,tree);

        Arrays.fill(visited, false);

        while (!nodeQueue.isEmpty()) {
            int[] node = nodeQueue.poll();
            int sourceNode = node[0], time = node[1], income = node[2];

            // Alice reaches the node first
            if (
                    !bobPath.containsKey(sourceNode) ||
                            time < bobPath.get(sourceNode)
            ) {
                income += amount[sourceNode];
            }
            // Alice and Bob reach the node at the same time
            else if (time == bobPath.get(sourceNode)) {
                income += amount[sourceNode] / 2;
            }

            // Update max value if current node is a new leaf
            if (tree.get(sourceNode).size() == 1 && sourceNode != 0) {
                maxIncome = Math.max(maxIncome, income);
            }
            // Explore adjacent unvisited vertices
            for (int adjacentNode : tree.get(sourceNode)) {
                if (!visited[adjacentNode]) {
                    nodeQueue.add(new int[] { adjacentNode, time + 1, income });
                }
            }

            // Mark and remove current node
            visited[sourceNode] = true;
        }
        return maxIncome;
    }

    private boolean findBobPath(int sourceNode, int time, Map<Integer, Integer> bobPath, boolean[] visited, List<List<Integer>> tree) {
        // Mark and set time node is reached
        bobPath.put(sourceNode, time);
        visited[sourceNode] = true;

        // Destination for Bob is found
        if (sourceNode == 0) {
            return true;
        }

        // Traverse through unvisited nodes
        for (int adjacentNode : tree.get(sourceNode)) {
            if (!visited[adjacentNode]) {
                if (findBobPath(adjacentNode, time + 1, bobPath, visited, tree)) {
                    return true;
                }
            }
        }
        // If node 0 isn't reached, remove current node from path
        bobPath.remove(sourceNode);
        return false;
    }

    public TreeNode lcaDeepestLeaves(TreeNode root) {
        return lcaDeepestLeavesDfs(root).node;
    }

    private TreeNodePair lcaDeepestLeavesDfs(TreeNode curr) {
        if(curr == null)
            return new TreeNodePair(null,0);
        TreeNodePair left = lcaDeepestLeavesDfs(curr.left);
        TreeNodePair right = lcaDeepestLeavesDfs(curr.right);

        if(left.level > right.level) {
            return new TreeNodePair(left.node, left.level + 1);
        }
        else if (left.level < right.level)
            return new TreeNodePair(right.node,right.level + 1);
        return new TreeNodePair(curr,left.level + 1);
    }


}
