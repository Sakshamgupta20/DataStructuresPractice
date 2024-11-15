package Trie;

import Graphs.Pair;
import common.CommonUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TrieProblems {
    public List<String> findWords(char[][] board, String[] words) {
        int n = board.length;
        int m = board[0].length;
        WordDictionary wordDictionary = new WordDictionary();
        for (String word : words) {
            wordDictionary.addWord(word);
        }

        List<String> res = new ArrayList<>();
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                boolean[][] seen = new boolean[n][m];
                backTrack(board, wordDictionary.trieNode, n, m, res, row, col);
            }
        }
        return res;
    }

    private void backTrack(char[][] board, TrieNode trieNodeI, int n, int m, List<String> res, int rowI, int colI) {
        Queue<WordNode> queue = new LinkedList<>();

        int c1 = board[rowI][colI] - 'a';

        // If the Trie has a node starting with board[rowI][colI]
        if (trieNodeI.chars[c1] != null)
            queue.add(new WordNode(rowI, colI, trieNodeI.chars[c1]));

        boolean[][] seen = new boolean[n][m];
        int[][] directions = CommonUtils.getVerticalHorizontalDirections();

        while (!queue.isEmpty()) {
            WordNode node = queue.poll();
            int row = node.row;
            int column = node.column;
            TrieNode trieNode = node.trieNode;

            seen[row][column] = true; // Mark as visited

            // If the node represents a word, add it to the result
            if (trieNode.word) {
                res.add(trieNode.value);
                trieNode.word = false; // Prevent adding the same word multiple times
            }

            // Explore all 4 directions (up, down, left, right)
            for (int[] direction : directions) {
                int newRow = row + direction[0];
                int newColumn = column + direction[1];

                if (CommonUtils.validGrid(n, m, newRow, newColumn) && !seen[newRow][newColumn]) {
                    int c = board[newRow][newColumn] - 'a';
                    if (trieNode.chars[c] != null) {
                        queue.add(new WordNode(newRow, newColumn, trieNode.chars[c]));
                    }
                }
            }
        }
    }
}
