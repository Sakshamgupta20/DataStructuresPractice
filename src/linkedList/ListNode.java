package linkedList;

public class ListNode {
    int val;
    ListNode next;

    ListNode() {
    }

    ListNode(int val) {
        this.val = val;
    }

    ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public ListNode(int[] arr) {
        this.val = arr[0];
        ListNode dummy = this;
        for (int i = 1; i < arr.length; i++) {
            dummy.next = new ListNode(arr[i]);
            dummy = dummy.next;
        }
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                ", next=" + next +
                '}';
    }

    public void print() {
        ListNode head = this;
        StringBuilder res = new StringBuilder();
        while (head != null) {
            res.append(head.val);
            res.append("->");
            head = head.next;
        }
        System.out.println(res);
    }
}
