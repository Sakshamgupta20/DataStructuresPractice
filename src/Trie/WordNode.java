package Trie;

public class WordNode {
    int row;
    int column;
    TrieNode trieNode;

    public WordNode(int row, int column, TrieNode trieNode) {
        this.row = row;
        this.column = column;
        this.trieNode = trieNode;
    }
}
