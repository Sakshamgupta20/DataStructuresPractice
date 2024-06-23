package Monotonic;

import java.util.ArrayList;
import java.util.Stack;

public class StockSpanner {
    Stack<Integer> stack;
    ArrayList<Integer> prices;
    int n;

    public StockSpanner() {
        stack = new Stack<>();
        prices = new ArrayList<>();
        n = 0;
    }

    public int next(int price) {
        while (!stack.isEmpty() && price >= prices.get(stack.peek())) {
            stack.pop();
        }
        int last = stack.isEmpty() ? -1 : stack.peek();

        stack.push(n++);
        prices.add(price);

        return n - last - 1;
    }
}
