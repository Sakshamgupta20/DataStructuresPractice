import common.CommonUtils;

import java.util.*;

public class BackTrackingProblems {

    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        subsetsBacktrack(ans, new ArrayList<Integer>(), nums, 0);
        return ans;
    }

    private void subsetsBacktrack(List<List<Integer>> ans, ArrayList<Integer> curr, int[] nums, int index) {
        if (index > nums.length) {
            return;
        }
        ans.add(new ArrayList<>(curr));
        for (int i = index; i < nums.length; i++) {
            curr.add(nums[i]);
            subsetsBacktrack(ans, curr, nums, i + 1);
            curr.remove(curr.size() - 1);
        }
    }

    public List<List<Integer>> combine(int n, int k) {
        List<List<Integer>> ans = new ArrayList<>();
        combineBacktrack(ans, new ArrayList<Integer>(), n, k, 0);
        return ans;
    }

    private void combineBacktrack(List<List<Integer>> ans, ArrayList<Integer> cur, int n, int k, int index) {
        if (index > n) {
            return;
        }
        if (cur.size() == k) {
            ans.add(new ArrayList<>(cur));
            return;
        }

        // Calculate the number of remaining elements we need to fill the combination
        int remainingSlots = k - cur.size();

        // The maximum starting index where we can still fill all remaining slots
        int maxStart = n - remainingSlots + 1;
        for (int i = index; i <= maxStart; i++) {
            cur.add(i + 1);
            combineBacktrack(ans, cur, n, k, i + 1);
            cur.remove(cur.size() - 1);
        }

    }

    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < graph.length; i++) {
            map.putIfAbsent(i, new ArrayList<>());
            for (int node : graph[i]) {
                map.get(i).add(node);
            }
        }
        List<List<Integer>> results = new ArrayList<>();
        boolean[] seen = new boolean[graph.length];
        seen[0] = true;
        List<Integer> curr = new ArrayList<>();
        curr.add(0);
        allPathsSourceTarget(results, seen, map, 0, graph.length - 1, curr);
        return results;

    }

    private void allPathsSourceTarget(List<List<Integer>> results, boolean[] seen, HashMap<Integer, List<Integer>> map,
                                      int node, int target, List<Integer> curr) {

        if (node == target) {
            results.add(new ArrayList<>(curr));
            return;
        }

        for (Integer i : map.get(node)) {
            if (seen[i])
                return;
            seen[i] = true;
            curr.add(i);

            allPathsSourceTarget(results, seen, map, i, target, curr);

            curr.remove(curr.size() - 1);
            seen[i] = false;
        }
    }

    /**
     * 17. Letter Combinations of a Phone Number
     * Given a string containing digits from 2-9 inclusive, return all possible letter combinations that the number could represent. Return the answer in any order.
     * <p>
     * A mapping of digits to letters (just like on the telephone buttons) is given below. Note that 1 does not map to any letters.
     */
    public List<String> letterCombinations(String digits) {
        HashMap<Character, String> combinations = new HashMap<>();
        combinations.put('2', "abc");
        combinations.put('3', "def");
        combinations.put('4', "ghi");
        combinations.put('5', "jkl");
        combinations.put('6', "mno");
        combinations.put('7', "pqrs");
        combinations.put('8', "tuv");
        combinations.put('9', "wxyz");

        List<String> res = new ArrayList<>();

        letterCombinations(digits, 0, combinations, res, new StringBuilder());

        return res;
    }

    private void letterCombinations(String digits, int index, HashMap<Character, String> combinations, List<String> res, StringBuilder curr) {
        if (index > digits.length())
            return;
        if (digits.length() == curr.length()) {
            if (!curr.isEmpty())
                res.add(curr.toString());
            return;
        }
        char c = digits.charAt(index);
        for (int i = 0; i < combinations.get(c).length(); i++) {
            curr.append(combinations.get(c).charAt(i));
            letterCombinations(digits, index + 1, combinations, res, curr);
            curr.deleteCharAt(curr.length() - 1);
        }
    }

    /**
     * 79. Word Search
     * Given an m x n grid of characters board and a string word, return true if word exists in the grid.
     * <p>
     * The word can be constructed from letters of sequentially adjacent cells, where adjacent cells are horizontally or vertically neighboring. The same letter cell may not be used more than once.
     */
    public boolean exist(char[][] board, String word) {
        int n = board.length;
        int m = board[0].length;
        int[][] diretions = CommonUtils.getVerticalHorizontalDirections();

        for (int row = 0; row < n; row++) {
            for (int column = 0; column < m; column++) {
                boolean[][] seen = new boolean[n][m];
                seen[row][column] = true;
                if (exist(board, word, seen, 0, diretions, row, column))
                    return true;
                seen[row][column] = false;
            }
        }
        return false;
    }

    private boolean exist(char[][] board, String word, boolean[][] seen, int wordIndex, int[][] diretions, int row, int column) {

        if (word.charAt(wordIndex) != board[row][column])
            return false;

        wordIndex = wordIndex + 1;
        if (word.length() == wordIndex)
            return true;
        for (int[] diretion : diretions) {
            int newRow = row + diretion[0];
            int newColumn = column + diretion[1];
            if (CommonUtils.validGrid(board.length, board[0].length, newRow, newColumn) && !seen[newRow][newColumn]) {
                seen[newRow][newColumn] = true;
                if (exist(board, word, seen, wordIndex, diretions, newRow, newColumn))
                    return true;
                seen[newRow][newColumn] = false;
            }
        }
        return false;
    }

    /**
     * 22. Generate Parentheses
     * Given n pairs of parentheses, write a function to generate all combinations of well-formed parentheses.
     */
    public List<String> generateParenthesis(int n) {
        List<String> res = new ArrayList<>();
        generateParenthesis(res, 0, 0, n, new StringBuilder());
        return res;
    }

    private void generateParenthesis(List<String> res, int open, int close, int n, StringBuilder curr) {
        if (curr.length() == 2 * n) {
            res.add(curr.toString());
            return;
        }

        if (open < n) {
            curr.append("(");
            generateParenthesis(res, open + 1, close, n, curr);
            curr.deleteCharAt(curr.length() - 1);  // Backtrack
        }

        if (close < open) {
            curr.append(")");
            generateParenthesis(res, open, close + 1, n, curr);
            curr.deleteCharAt(curr.length() - 1);  // Backtrack
        }
    }

    /**
     * 967. Numbers With Same Consecutive Differences
     * Given two integers n and k, return an array of all the integers of length n where the difference between every two consecutive digits is k. You may return the answer in any order.
     * <p>
     * Note that the integers should not have leading zeros. Integers as 02 and 043 are not allowed.
     */
    public int[] numsSameConsecDiff(int n, int k) {
        List<Integer> res = new ArrayList<>();

        // Start with every digit from 1 to 9 (cannot start with 0)
        for (int i = 1; i <= 9; i++) {
            numsSameConsecDiff(n, k, i, 1, res); // Start with i and length 1
        }

        // Create a new int array of the same size as the list
        int[] result = new int[res.size()];

        // Loop through the list and assign values to the array
        for (int i = 0; i < res.size(); i++) {
            result[i] = res.get(i);
        }

        return result;

    }

    private void numsSameConsecDiff(int n, int k, int currNum, int currLen, List<Integer> res) {

        if (currLen == n) {
            res.add(currNum);
            return;
        }

        int lastDigit = currNum % 10;

        int nextDigitBack = lastDigit - k;
        int nextDigitFront = lastDigit + k;

        if (nextDigitBack >= 0) {
            numsSameConsecDiff(n, k, currNum * 10 + nextDigitBack, currLen + 1, res);
        }

        if (k != 0 && nextDigitFront < 10) {
            numsSameConsecDiff(n, k, currNum * 10 + nextDigitFront, currLen + 1, res);
        }

    }

    public List<List<Integer>> combinationSum3(int k, int n) {
        List<List<Integer>> res = new ArrayList<>();
        combinationSum3(res, new ArrayList<Integer>(), n, k, 0, 1);
        return res;
    }

    private void combinationSum3(List<List<Integer>> res, ArrayList<Integer> curr, int n, int k, int currSum, int index) {
        if (curr.size() == k) {
            if (currSum == n)
                res.add(new ArrayList<>(curr));
            return;
        }

        for (int i = index; i < 10; i++) {
            if (!curr.contains(i)) {
                curr.add(i);
                currSum += i;
                combinationSum3(res, curr, n, k, currSum, i);
                curr.remove(curr.size() - 1);
                currSum -= i;
            }

        }
    }

    /**
     * 39. Combination Sum
     * Given an array of distinct integers candidates and a target integer target, return a list of all unique combinations of candidates where the chosen numbers sum to target. You may return the combinations in any order.
     * <p>
     * The same number may be chosen from candidates an unlimited number of times. Two combinations are unique if the
     * frequency
     * of at least one of the chosen numbers is different.
     * <p>
     * The test cases are generated such that the number of unique combinations that sum up to target is less than 150 combinations for the given input.
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        combinationSum(candidates, target, 0, res, new ArrayList<>(), 0);
        return res;
    }

    private void combinationSum(int[] candidates, int target, int currSum, List<List<Integer>> res, List<Integer> curr, int start) {
        if (currSum == target) {
            res.add(new ArrayList<>(curr));
            return;
        }

        if (currSum > target)
            return;

        for (int j = start; j < candidates.length; j++) {
            curr.add(candidates[j]);
            combinationSum(candidates, target, currSum + candidates[j], res, curr, j);
            curr.remove(curr.size() - 1);
        }
    }

    /**
     * 46. Permutations
     * Given an array nums of distinct integers, return all the possible
     * permutations
     * . You can return the answer in any order.
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        permute(nums, res, new ArrayList<>());
        return res;
    }

    private void permute(int[] nums, List<List<Integer>> res, ArrayList<Integer> curr) {
        if (curr.size() == nums.length) {
            res.add(new ArrayList<>(curr));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (!curr.contains(nums[i])) {
                curr.add(nums[i]);
                permute(nums, res, curr);
                curr.remove(curr.size() - 1);
            }
        }
    }

    /**
     * 90. Subsets II
     * Given an integer array nums that may contain duplicates, return all possible
     * subsets
     * (the power set).
     * <p>
     * The solution set must not contain duplicate subsets. Return the solution in any order.
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> res = new ArrayList<>();
        subsetsWithDup(nums, res, new ArrayList<>(), 0);
        return res;
    }

    private void subsetsWithDup(int[] nums, List<List<Integer>> res, ArrayList<Integer> curr, int index) {
        res.add(new ArrayList<>(curr));

        if (index == nums.length) {
            return;
        }

        for (int i = index; i < nums.length; i++) {
            // If the current element is a duplicate, ignore.
            if (i != index && nums[i] == nums[i - 1]) {
                continue;
            }
            curr.add(nums[i]);
            subsetsWithDup(nums, res, curr, i + 1);
            curr.remove(curr.size() - 1);
        }
    }

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> res = new ArrayList<>();
        combinationSum2(candidates, target, res, new ArrayList<>(), 0);
        return res;
    }

    /**
     * 40. Combination Sum II
     * Given a collection of candidate numbers (candidates) and a target number (target), find all unique combinations in candidates where the candidate numbers sum to target.
     * <p>
     * Each number in candidates may only be used once in the combination.
     * <p>
     * Note: The solution set must not contain duplicate combinations.
     */
    private void combinationSum2(int[] candidates, int target, List<List<Integer>> res, ArrayList<Integer> curr, int index) {
        if (target == 0) {
            res.add(new ArrayList<>(curr));
            return;
        }
        if (target < 0 || index >= candidates.length) {
            return;
        }

        for (int i = index; i < candidates.length && target >= candidates[i]; i++) {
            if (i > index && candidates[i] == candidates[i - 1]) continue;
            curr.add(candidates[i]);
            combinationSum2(candidates, target - candidates[i], res, curr, i + 1);
            curr.remove(curr.size() - 1);
        }

    }

    /**
     * 131. Palindrome Partitioning
     * Given a string s, partition s such that every
     * substring
     * of the partition is a
     * palindrome
     * . Return all possible palindrome partitioning of s.
     */
    public List<List<String>> partition(String s) {
        List<List<String>> res = new ArrayList<>();
        partition(s, res, new ArrayList<>(), 0);
        return res;
    }

    private void partition(String s, List<List<String>> res, ArrayList<String> curr, int start) {
        if (start >= s.length()) {
            res.add(new ArrayList<String>(curr));
            return;
        }

        for (int end = start; end < s.length(); end++) {
            if (isPalindrome(s, start, end)) {
                // add current substring in the currentList
                curr.add(s.substring(start, end + 1));
                partition(s, res, curr, end + 1);
                // backtrack and remove the current substring from currentList
                curr.remove(curr.size() - 1);
            }
        }

    }

    boolean isPalindrome(String s, int low, int high) {
        while (low < high) {
            if (s.charAt(low++) != s.charAt(high--)) return false;
        }
        return true;
    }

    /**
     * 51. N-Queens
     * <p>
     * The n-queens puzzle is the problem of placing n queens on an n x n chessboard such that no two queens attack each other.
     * <p>
     * Given an integer n, return all distinct solutions to the n-queens puzzle. You may return the answer in any order.
     * <p>
     * Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate a queen and an empty space, respectively.
     */
    public List<List<String>> solveNQueens(int n) {
        List<List<String>> res = new ArrayList<>();
        char emptyBoard[][] = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                emptyBoard[i][j] = '.';
            }
        }
        solveNQueens(new HashSet<Integer>(), new HashSet<Integer>(), new HashSet<Integer>(), emptyBoard, n, res, 0);
        return res;
    }

    private void solveNQueens(HashSet<Integer> diagonal, HashSet<Integer> reverseDiagonal,
                              HashSet<Integer> columns, char emptyBoard[][], int n, List<List<String>> res, int row) {
        // Base case - N queens have been placed
        if (row == n) {
            res.add(createBoard(emptyBoard, n));
            return;
        }

        for (int col = 0; col < n; col++) {
            int currDiagonal = row - col;
            int currAntiDiagonal = row + col;
            if (columns.contains(col) ||
                    diagonal.contains(currDiagonal) ||
                    reverseDiagonal.contains(currAntiDiagonal)) {
                continue;
            }
            // "Add" the queen to the board
            columns.add(col);
            diagonal.add(currDiagonal);
            reverseDiagonal.add(currAntiDiagonal);
            emptyBoard[row][col] = 'Q';

            solveNQueens(diagonal, reverseDiagonal, columns, emptyBoard, n, res, row + 1);

            // "Remove" the queen from the board since we have already
            // explored all valid paths using the above function call
            columns.remove(col);
            diagonal.remove(currDiagonal);
            reverseDiagonal.remove(currAntiDiagonal);
            emptyBoard[row][col] = '.';
        }
    }

    private List<String> createBoard(char[][] state, int n) {
        List<String> board = new ArrayList<String>();
        for (int row = 0; row < n; row++) {
            String current_row = new String(state[row]);
            board.add(current_row);
        }

        return board;
    }

    /**
     * 1415. The k-th Lexicographical String of All Happy Strings of Length n
     * A happy string is a string that:
     * <p>
     * consists only of letters of the set ['a', 'b', 'c'].
     * s[i] != s[i + 1] for all values of i from 1 to s.length - 1 (string is 1-indexed).
     * For example, strings "abc", "ac", "b" and "abcbabcbcb" are all happy strings and strings "aa", "baa" and "ababbc" are not happy strings.
     * <p>
     * Given two integers n and k, consider a list of all happy strings of length n sorted in lexicographical order.
     * <p>
     * Return the kth string of this list or return an empty string if there are less than k happy strings of length n.
     */
    public String getHappyString(int n, int k) {
        int bracketSize = (int) Math.pow(2, n - 1);
        if(bracketSize * 3 < k)
            return "";
        int bracket = k / bracketSize;
        int diff = k % bracketSize;
        if(diff > 0)
            bracket += 1;
        char curr = (char) (bracket - 1 + 'a');

        k = k - ((bracket - 1) * bracketSize);
        return getHappyStringDfs(new StringBuilder(curr + ""), n, new int[] {k});
    }

    private String getHappyStringDfs(StringBuilder sb, int n, int[] k) {
        if (sb.length() == n) return --k[0] == 0 ? sb.toString() : "";
        for (char i = 'a'; i <= 'c'; i++) {
            if (sb.charAt(sb.length() - 1) == i) continue;
            sb.append(i);
            String r = getHappyStringDfs(sb, n, k);
            if(!r.isEmpty())
                return r;
            sb.deleteCharAt(sb.length() - 1); // Backtrack
        }
        return "";

    }

}
