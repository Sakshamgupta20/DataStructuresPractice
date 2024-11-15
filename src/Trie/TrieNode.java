package Trie;

public class TrieNode {
    TrieNode[] chars;
    boolean word;
    String value;


    public TrieNode() {
        chars = new TrieNode[26];
    }
}
