package Trees;

import java.util.*;

public class TreeNode {
    public int val;
    public TreeNode left;
    public TreeNode right;

    TreeNode() {
    }

    public TreeNode(int val) {
        this.val = val;
    }

    TreeNode(int val, TreeNode left, TreeNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
    }

    public TreeNode(List<Integer> nums) {
        if (nums.isEmpty())
            return;
        int index = 0;
        this.val = nums.get(index++);
        Queue<TreeNode> nodes = new LinkedList<>();
        nodes.add(this);

        while (!nodes.isEmpty() && index < nums.size()) {
            TreeNode node = nodes.remove();


            Integer num = nums.get(index++);
            if (Objects.nonNull(num)) {
                TreeNode left = new TreeNode(num);
                node.left = left;
                nodes.add(left);
            }

            if (index < nums.size()) {
                num = nums.get(index++);
                if (Objects.nonNull(num)) {
                    TreeNode right = new TreeNode(num);
                    node.right = right;
                    nodes.add(right);
                }
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(val);

        String pointerRight = "└──";
        String pointerLeft = (right != null) ? "├──" : "└──";

        traverseNodes(sb, "", pointerLeft, left, right != null);
        traverseNodes(sb, "", pointerRight, right, false);

        return sb.toString();
    }

    public void traverseNodes(StringBuilder sb, String padding, String pointer, TreeNode node,
                              boolean hasRightSibling) {
        if (node != null) {
            sb.append("\n");
            sb.append(padding);
            sb.append(pointer);
            sb.append(node.val);


            StringBuilder paddingBuilder = new StringBuilder(padding);
            if (hasRightSibling) {
                paddingBuilder.append("│  ");
            } else {
                paddingBuilder.append("   ");
            }

            String paddingForBoth = paddingBuilder.toString();
            String pointerRight = "└──";
            String pointerLeft = (node.right != null) ? "├──" : "└──";

            traverseNodes(sb, paddingForBoth, pointerLeft, node.left, node.right != null);
            traverseNodes(sb, paddingForBoth, pointerRight, node.right, false);
        }
    }
}
