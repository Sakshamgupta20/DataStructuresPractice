package Heap;

import java.util.HashSet;
import java.util.PriorityQueue;

public class SmallestInfiniteSet {
    int marker;
    HashSet<Integer> isPresent = new HashSet<>();
    PriorityQueue<Integer> queue = new PriorityQueue<>();

    public SmallestInfiniteSet() {
        marker = 1;
    }

    public int popSmallest() {
        int answer;
        if (!queue.isEmpty()) {
            answer = queue.poll();
            isPresent.remove(answer);
        }
        else {
            answer = marker++;
        }
        return answer;
    }

    public void addBack(int num) {
        if (marker <= num || isPresent.contains(num))
            return;
        queue.add(num);
        isPresent.add(num);
    }
}
