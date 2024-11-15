package Trees;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
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

        if (Math.abs(root.val - target) < Math.abs(answer.get() - target))
            answer.set(root.val);
        else if (Math.abs(root.val - target) == Math.abs(answer.get() - target))
            answer.set(Math.min(root.val, answer.get()));
    }

    /**
     * 700. Search in a Binary Search Tree
     * You are given the root of a binary search tree (BST) and an integer val.
     * <p>
     * Find the node in the BST that the node's value equals val and return the subtree rooted with that node. If such a node does not exist, return null.
     */
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null)
            return null;

        if (root.val == val)
            return root;

        if (val > root.val)
            return searchBST(root.right, val);
        else
            return searchBST(root.left, val);
    }

    public List<Integer> getAllElements(TreeNode root1, TreeNode root2) {
        List<Integer> sorted1 = new ArrayList<>();
        getAllElements(root1, sorted1);

        List<Integer> sorted2 = new ArrayList<>();
        getAllElements(root2, sorted2);

        List<Integer> combined = new ArrayList<>();
        int i = 0;
        int j = 0;

        while (i < sorted1.size() && j < sorted2.size()) {
            if (sorted1.get(i) < sorted2.get(j)) {
                combined.add(sorted1.get(i++));
            } else
                combined.add(sorted2.get(j++));
        }

        while (i < sorted1.size())
            combined.add(sorted1.get(i++));

        while (j < sorted2.size())
            combined.add(sorted2.get(j++));

        return combined;


    }

    public void getAllElements(TreeNode node, List<Integer> sorted) {
        if (node == null)
            return;
        getAllElements(node.left, sorted);
        sorted.add(node.val);
        getAllElements(node.right, sorted);
    }


    /**
     * 235. Lowest Common Ancestor of a Binary Search Tree
     * Given a binary search tree (BST), find the lowest common ancestor (LCA) node of two given nodes in the BST.
     * <p>
     * According to the definition of LCA on Wikipedia: “The lowest common ancestor is defined between two nodes p and q as the lowest node in T that has both p and q as descendants (where we allow a node to be a descendant of itself).”
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null)
            return null;

        if (p.val > root.val && q.val > root.val)
            return lowestCommonAncestor(root.right, p, q);
        else if (p.val < root.val && q.val < root.val)
            return lowestCommonAncestor(root.left, p, q);
        else
            return root;
    }

    /**
     * 450. Delete Node in a BST
     * Given a root node reference of a BST and a key, delete the node with the given key in the BST. Return the root node reference (possibly updated) of the BST.
     * <p>
     * Basically, the deletion can be divided into two stages:
     * <p>
     * Search for a node to remove.
     * If the node is found, delete the node.
     */
    public TreeNode deleteNode(TreeNode root, int key) {
        return root;
    }

    public ArrayList<Integer> inorder(TreeNode root, ArrayList<Integer> arr) {
        if (root == null) return arr;
        inorder(root.left, arr);
        arr.add(root.val);
        inorder(root.right, arr);
        return arr;
    }

    public int kthSmallest(TreeNode root, int k) {
        ArrayList<Integer> nums = inorder(root, new ArrayList<Integer>());
        return nums.get(k - 1);
    }

}
