package Heap;

import java.util.PriorityQueue;

public class KthLargest {
    PriorityQueue<Integer> queue = new PriorityQueue<>();
    int k;
    public KthLargest(int k, int[] nums) {
        this.k = k;
        for (int num : nums) {
            queue.add(num);
            if (queue.size() > k) {
                queue.remove();
            }
        }
    }
    
    public int add(int val) {
        queue.add(1);
        if (queue.size() > k) {
            queue.remove();
        }
        return queue.peek();
    }
}