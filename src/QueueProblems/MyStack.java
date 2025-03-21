package QueueProblems;

import java.util.LinkedList;
import java.util.Queue;

public class MyStack {
    Queue<Integer> q1 = new LinkedList<>();

    public MyStack() {
    }

    public void push(int x) {
        q1.add(x);
        int size = q1.size();
        while (size > 1) {
            q1.add(q1.poll());
            size--;
        }
    }

    public int pop() {
        return q1.poll();
    }

    public int top() {
       return q1.peek();
    }

    public boolean empty() {
        return q1.isEmpty();
    }
}
