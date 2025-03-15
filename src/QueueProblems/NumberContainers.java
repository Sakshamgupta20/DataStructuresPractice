package QueueProblems;

import java.util.HashMap;
import java.util.PriorityQueue;

public class NumberContainers {
    HashMap<Integer, PriorityQueue<Integer>> nums = new HashMap<>();
    HashMap<Integer, Integer> arr = new HashMap<>();

    public NumberContainers() {

    }

    public void change(int index, int number) {
        nums.putIfAbsent(number, new PriorityQueue<>());
        nums.get(number).add(index);

        arr.put(index, number);
    }

    public int find(int number) {
        if (!nums.containsKey(number))
            return -1;
        PriorityQueue<Integer> minHeap = nums.get(number);

        //Lazy update
        while (!minHeap.isEmpty()) {
            int index = minHeap.peek();
            if(arr.get(index) == number)
                return index;
            minHeap.poll();
        }
        return -1;
    }
}