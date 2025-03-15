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

    /**
     * 3174. Clear Digits
     * You are given a string s.
     * <p>
     * Your task is to remove all digits by doing this operation repeatedly:
     * <p>
     * Delete the first digit and the closest non-digit character to its left.
     * Return the resulting string after removing all digits.
     */
    public String clearDigits(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                if (!stack.isEmpty() && !Character.isDigit(stack.peek()))
                    stack.pop();
                else
                    stack.push(c);
            } else
                stack.push(c);
        }
        StringBuilder res = new StringBuilder();
        while (!stack.isEmpty())
            res.append(stack.pop());
        return res.reverse().toString();
    }

    /**
     * 2375. Construct Smallest Number From DI String
     * You are given a 0-indexed string pattern of length n consisting of the characters 'I' meaning increasing and 'D' meaning decreasing.
     * <p>
     * A 0-indexed string num of length n + 1 is created using the following conditions:
     * <p>
     * num consists of the digits '1' to '9', where each digit is used at most once.
     * If pattern[i] == 'I', then num[i] < num[i + 1].
     * If pattern[i] == 'D', then num[i] > num[i + 1].
     * Return the lexicographically smallest possible string num that meets the conditions.
     */
    public String smallestNumber(String pattern) {
        StringBuilder result = new StringBuilder();
        Stack<Integer> numStack = new Stack<>();

        // Iterate through the pattern
        for (int index = 0; index <= pattern.length(); index++) {
            // Push the next number onto the stack
            numStack.push(index + 1);

            // If 'I' is encountered or we reach the end, pop all stack elements
            if (index == pattern.length() || pattern.charAt(index) == 'I') {
                while (!numStack.isEmpty()) {
                    result.append(numStack.pop());
                }
            }
        }

        return result.toString();
    }
}
