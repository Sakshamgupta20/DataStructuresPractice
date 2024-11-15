package Heap;

import java.util.Comparator;
import java.util.PriorityQueue;

public class MedianFinder {
    PriorityQueue<Integer> queue1 = new PriorityQueue<>((a,b) -> b - a);
    PriorityQueue<Integer> queue2 = new PriorityQueue<>();
    public MedianFinder() {
        
    }
    
    public void addNum(int num) {
        queue1.add(num);
        queue2.add(queue1.poll());
        if(queue2.size() - queue1.size() > 1) {
            int s = queue2.poll();
            queue1.add(s);
        }
    }
    
    public double findMedian() {
        if(queue1.size() == queue2.size()) {
            return (double) (queue1.peek() + queue2.peek()) / 2;
        }
        return queue2.peek();
    }
}