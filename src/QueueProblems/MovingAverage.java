package QueueProblems;

import java.util.LinkedList;
import java.util.Queue;

public class MovingAverage {
    private int size;
    private Queue<Integer> numbers = new LinkedList<>();
    int currentSum = 0;

    public MovingAverage(int size) {
        this.size = size;
    }

    public double next(int val) {
        if (numbers.size() >= this.size) {
            int last = numbers.poll();
            currentSum -= last;
        }
        numbers.add(val);
        currentSum += val;
        double res = (double) currentSum / numbers.size();
        System.out.println(res);

        return res;
    }
}
