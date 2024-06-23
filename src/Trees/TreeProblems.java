package Trees;


import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.concurrent.atomic.AtomicInteger;

public class TreeProblems {
    public int maxDepthRecursion(TreeNode root) {
        if (root == null)
            return 0;
        return Math.max(maxDepthRecursion(root.left), maxDepthRecursion(root.right)) + 1;
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
        largestValuesDfs(root,  result, 0);
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
}
