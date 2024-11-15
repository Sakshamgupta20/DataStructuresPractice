package linkedList;

import java.util.List;

public class LinkedList {

    public ListNode middleNode(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    public ListNode deleteDuplicates(ListNode head) {
        if (head == null)
            return null;
        ListNode slow = head;
        ListNode fast = head.next;

        while (fast != null) {
            if (fast.val != slow.val) {
                slow = slow.next;
                fast = fast.next;
            } else {
                slow.next = fast.next;
                fast = slow.next;
            }
        }
        return head;
    }

    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextNode = curr.next; // first, make sure we don't lose the next node
            curr.next = prev;              // reverse the direction of the pointer
            prev = curr;                   // set the current node to prev for the next node
            curr = nextNode;               // move on
        }

        return prev;
    }

    public ListNode swapPairs(ListNode head) {
        // Check edge case: linked list has 0 or 1 nodes, just return
        if (head == null || head.next == null) {
            return head;
        }
        ListNode prev = null;
        ListNode dummy = head.next;
        while (head != null && head.next != null) {
            if (prev != null) {
                prev.next = head.next;
            }
            prev = head;
            ListNode next = head.next.next;
            head.next.next = head;
            head.next = next;
            head = next;
        }
        return dummy;
    }

    public ListNode swapPairsRecursive(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode firstNode = head;
        ListNode secondNode = head.next;

        firstNode.next = swapPairsRecursive(secondNode.next);
        secondNode.next = firstNode;

        return secondNode;
    }

    public ListNode reverseListRecursion(ListNode head) {
        if (head == null || head.next == null)
            return head;

        ListNode p = reverseListRecursion(head.next);
        head.next.next = head;
        head.next = null;
        return p;
    }

    public int pairSum(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        ListNode prev = null;
        while (slow != null) {
            ListNode next = slow.next;
            slow.next = prev;
            prev = slow;
            slow = next;
        }

        slow = head;

        int max = 0;
        while (prev != null) {
            max = Math.max(slow.val + prev.val, max);
            slow = slow.next;
            prev = prev.next;
        }
        return max;
    }

    public ListNode deleteMiddle(ListNode head) {
        if (head.next == null)
            return null;
        ListNode fast = head.next.next;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return head;
    }

    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode fast = head;
        ListNode slow = head;
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }

        if (fast == null) {
            head = head.next;
            return head;
        }

        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }
        slow.next = slow.next.next;
        return head;
    }

    public ListNode deleteDuplicates2(ListNode head) {
        if (head == null || head.next == null)
            return head;
        ListNode tempHead = new ListNode(0);
        tempHead.next = head;
        ListNode prev = tempHead;
        ListNode slow = tempHead.next;
        ListNode fast = tempHead.next.next;

        int count = 0;
        while (fast != null) {
            if (slow.val == fast.val) {
                count++;
            } else {
                if (count > 0) {
                    prev.next = fast;
                    count = 0;
                } else
                    prev = slow;
                slow = fast;
            }
            fast = fast.next;
        }

        if (count > 0) {
            prev.next = fast;
        }
        return tempHead.next;
    }

    public ListNode swapNodes(ListNode head, int k) {
        ListNode fast = head;
        ListNode swap = null;
        for (int i = 0; i < k; i++) {
            swap = fast;
            fast = fast.next;
        }
        ListNode slow = head;
        while (fast != null) {
            slow = slow.next;
            fast = fast.next;
        }
        int temp = swap.val;
        swap.val = slow.val;
        slow.val = temp;
        return head;
    }

    public boolean isPalindrome(ListNode head) {
        //Find middle node of list
        ListNode endOfFirstHalf = endOfFirstHalf(head);

        //Reverse list from middle node
        ListNode startOfReversedList = reverseList2(endOfFirstHalf.next);

        ListNode p1 = head;
        ListNode p2 = startOfReversedList;
        boolean result = true;
        while (result && p2 != null) {
            if (p1.val != p2.val) result = false;
            p1 = p1.next;
            p2 = p2.next;
        }

        endOfFirstHalf.next = reverseList2(startOfReversedList);

        return result;
    }

    private ListNode endOfFirstHalf(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    public ListNode reverseList2(ListNode middleNode) {
        ListNode prev = null;
        ListNode curr = middleNode;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    public ListNode removeElements(ListNode head, int val) {
        if (head == null)
            return null;
        ListNode sentinelNode = new ListNode(0, head);
        ListNode prev = sentinelNode;
        ListNode curr = sentinelNode.next;

        while (curr != null) {
            if (curr.val == val)
                prev.next = curr.next;
            else
                prev = curr;
            curr = curr.next;
        }
        return sentinelNode.next;
    }

    public ListNode reverseEvenLengthGroups(ListNode head) {
        int index = 2;

        int countNodes = 0;
        ListNode temp = head.next;
        while (temp != null) {
            temp = temp.next;
            countNodes++;
        }

        ListNode curr = head;
        int usedNodes = 0;
        while (curr != null && curr.next != null) {
            int pendingNodes = countNodes - usedNodes;
            if ((index < pendingNodes && index % 2 == 0) || (index >= pendingNodes && pendingNodes % 2 == 0))
                curr = reverseListTillIndex(curr, index);
            else {
                int i = 0;
                while (curr != null && i < index) {
                    curr = curr.next;
                    i++;
                }
            }
            usedNodes += index;
            index++;
        }
        return head;
    }

    private ListNode reverseListTillIndex(ListNode from, int index) {
        ListNode prev = null;
        ListNode start = from.next;
        ListNode curr = start;
        int i = 0;
        while (curr != null && i < index) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
            i++;
        }
        start.next = curr;
        from.next = prev;
        return start;
    }

    public ListNode oddEvenList(ListNode head) {
        if (head == null)
            return null;
        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenRef = even;
        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }
        odd.next = evenRef;
        return head;
    }
}
