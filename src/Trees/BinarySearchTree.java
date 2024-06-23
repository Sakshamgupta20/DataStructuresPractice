package Trees;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BinarySearchTree {
    public int rangeSumBST(TreeNode root, int low, int high) {
        if (root == null)
            return 0;

        int ans = 0;
        if (root.val >= low && root.val <= high)
            ans += root.val;

        if (low < root.val)
            ans += rangeSumBST(root.left, low, high);
        if (high > root.val)
            ans += rangeSumBST(root.right, low, high);

        return ans;
    }

    /**
     * Given the root of a Binary Search Tree (BST), return the minimum absolute difference between the values of any two different nodes in the tree.
     */
    public int getMinimumDifference(TreeNode root) {
        List<Integer> numbers = new ArrayList<>();
        getMinimumDifference(root, numbers);
        int ans = Integer.MAX_VALUE;
        for (int i = 1; i < numbers.size(); i++) {
            ans = Math.min(ans, numbers.get(i) - numbers.get(i - 1));
        }
        return ans;
    }

    public void getMinimumDifference(TreeNode root, List<Integer> numbers) {
        if (root == null)
            return;
        getMinimumDifference(root.left, numbers);
        numbers.add(root.val);
        getMinimumDifference(root.right, numbers);
    }

    /**
     * Given the root of a binary tree, determine if it is a valid binary search tree (BST).
     * <p>
     * A valid BST is defined as follows:
     * <p>
     * The left
     * subtree
     * of a node contains only nodes with keys less than the node's key.
     * The right subtree of a node contains only nodes with keys greater than the node's key.
     * Both the left and right subtrees must also be binary search trees.
     */
    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public boolean isValidBST(TreeNode node, long low, long high) {
        if (node == null)
            return true;

        if (low >= node.val || node.val >= high)
            return false;

        return isValidBST(node.left, low, node.val) && isValidBST(node.right, node.val, high);
    }

    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null)
            return new TreeNode(val);

        if (val > root.val) {
            root.right = insertIntoBST(root.right, val);
        } else {
            root.left = insertIntoBST(root.left, val);
        }
        return root;
    }

    public int closestValue(TreeNode root, double target) {
        if (root == null)
            return -1;

        AtomicInteger ans = new AtomicInteger(root.val);
        closestValue(root, target, ans);
        return ans.get();
    }

    public void closestValue(TreeNode root, double target, AtomicInteger answer) {
        if (root == null)
            return;

        if (target < root.val)
            closestValue(root.left, target, answer);
        else
            closestValue(root.right, target, answer);

        if(Math.abs(root.val - target) < Math.abs(answer.get() - target))
            answer.set(root.val);
        else if(Math.abs(root.val - target) == Math.abs(answer.get() - target))
            answer.set(Math.min(root.val,answer.get()));
    }

}
