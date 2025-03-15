package Trees;

import java.util.*;

public class FindElements {
    BitSet recoveredValues = new BitSet();
    public FindElements(TreeNode root) {
        root.val = 0;

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            recoveredValues.set(node.val);
            if(node.left != null) {
                node.left.val = node.val * 2 + 1;
                queue.add(node.left);
            }
            if(node.right != null) {
                node.right.val = node.val * 2 + 2;
                queue.add(node.right);
            }
        }
        System.out.println("Ee");
    }
    
    public boolean find(int target) {
        return recoveredValues.get(target);
    }
}