package StackProblems;

import java.lang.reflect.Array;
import java.util.*;
import java.util.function.BiFunction;

public class StackProblems {
    public boolean isValid(String s) {
        Map<Character, Character> brackets = new HashMap<>();
        brackets.put('{', '}');
        brackets.put('[', ']');
        brackets.put('(', ')');

        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (brackets.containsKey(c)) {
                stack.push(c);
            } else {
                if (stack.isEmpty())
                    return false;
                Character lastBracket = stack.pop();
                if (brackets.get(lastBracket) != c)
                    return false;
            }
        }
        return stack.isEmpty();
    }

    public String simplifyPath(String path) {
        Stack<String> res = new Stack<>();
        String[] splitParts = path.split("/");
        for (String splitPart : splitParts) {
            if (splitPart.equals(".") || splitPart.isEmpty()) {
                continue;
            } else if (Objects.equals(splitPart, "..")) {
                if (!res.isEmpty()) {
                    res.pop();
                }
            } else {
                res.push(splitPart);
            }
        }
        StringBuilder result = new StringBuilder();
        for (String dir : res) {
            result.append("/");
            result.append(dir);
        }

        return !result.isEmpty() ? result.toString() : "/";
    }

    public String makeGood(String s) {
        StringBuilder res = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (!res.isEmpty()) {
                char last = res.charAt(res.length() - 1);
                if (Math.abs(c - last) == 32) {
                    res.deleteCharAt(res.length() - 1);
                    continue;
                }
            }
            res.append(c);
        }
        return res.toString();
    }

    public String removeStars(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '*') {
                stack.pop();
            } else
                stack.push(c);
        }
        StringBuilder result = new StringBuilder();
        while (!stack.isEmpty())
            result.append(stack.pop());
        return result.reverse().toString();
    }

    public boolean validateStackSequences(int[] pushed, int[] popped) {
        Stack<Integer> stack = new Stack<>();
        int i = 0;
        for (int j : pushed) {
            stack.push(j);
            while (!stack.isEmpty() && popped[i] == stack.peek()) {
                stack.pop();
                i++;
            }
        }
        return stack.isEmpty();
    }

    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();
        for (int asteroid : asteroids) {
            boolean flag = true;

            while (!stack.isEmpty() && (stack.peek() > 0 && asteroid < 0)) {

                if (Math.abs(stack.peek()) < Math.abs(asteroid)) {
                    stack.pop();
                    continue;
                } else if (Math.abs(stack.peek()) == Math.abs(asteroid)) {
                    stack.pop();
                }
                flag = false;
                break;
            }

            if (flag) {
                stack.push(asteroid);
            }
        }

        // Add the asteroids from the stack to the vector in the reverse order.
        int[] remainingAsteroids = new int[stack.size()];
        for (int i = remainingAsteroids.length - 1; i >= 0; i--) {
            remainingAsteroids[i] = stack.peek();
            stack.pop();
        }

        return remainingAsteroids;
    }

    public int evalRPN(String[] tokens) {
        final Map<String, BiFunction<Integer, Integer, Integer>> OPERATIONS = new HashMap<>();
        OPERATIONS.put("+", (a, b) -> a + b);
        OPERATIONS.put("-", (a, b) -> a - b);
        OPERATIONS.put("*", (a, b) -> a * b);
        OPERATIONS.put("/", (a, b) -> a / b);

        Stack<Integer> stack = new Stack<>();
        for (String token : tokens) {
            if (!OPERATIONS.containsKey(token)) {
                stack.push(Integer.valueOf(token));
                continue;
            }
            int one = stack.pop();
            int two = stack.pop();
            int result = OPERATIONS.get(token).apply(two, one);
            stack.push(result);
        }
        return stack.pop();
    }
}
