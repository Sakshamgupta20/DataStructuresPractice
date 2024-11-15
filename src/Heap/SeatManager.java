package Heap;

import java.util.PriorityQueue;

class SeatManager {
    int marker;
    PriorityQueue<Integer> queue = new PriorityQueue<>();

    public SeatManager(int n) {
        marker = 1;
    }

    public int reserve() {
        if (!queue.isEmpty()) {
            return queue.poll();
        }
        return marker++;
    }

    public void unreserve(int seatNumber) {
        queue.add(seatNumber);
    }
}