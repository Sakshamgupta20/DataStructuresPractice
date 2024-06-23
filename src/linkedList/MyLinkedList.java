package linkedList;


import java.util.List;

public class MyLinkedList {
    int size;
    public ListNode head;

    public MyLinkedList() {
        this.size = 0;
        head = new ListNode(0);
    }

    public int get(int index) {
        if (index < 0 || index >= this.size)
            return -1;
        ListNode node = this.head;
        for (int i = 0; i < index + 1; i++) {
            node = node.next;
        }
        return node.val;
    }

    public void addAtHead(int val) {
        addAtIndex(0, val);
    }

    public void addAtTail(int val) {
        addAtIndex(size, val);
    }

    public void addAtIndex(int index, int val) {
        if (index > size)
            return;
        if (index < 0)
            index = 0;

        this.size++;
        //Find predecessor of the node to be added
        ListNode pred = head;
        for (int i = 0; i < index; ++i) pred = pred.next;


        ListNode toAdd = new ListNode(val);

        toAdd.next = pred.next;
        pred.next = toAdd;
    }

    public void deleteAtIndex(int index) {
        // If the index is invalid, do nothing
        if (index < 0 || index >= size) return;
        this.size--;
        //Find predecessor of the node to be added
        ListNode pred = head;
        for (int i = 0; i < index; ++i) pred = pred.next;
        pred.next = pred.next.next;

    }
}
