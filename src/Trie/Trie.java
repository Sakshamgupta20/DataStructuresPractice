package Trie;

import java.util.HashMap;

public class Trie {
    HashMap<Character, Trie> map;
    boolean word;

    public Trie() {
        map = new HashMap<>();
    }

    public void insert(String word) {
        Trie trie = this;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            trie.map.putIfAbsent(c, new Trie());
            if (i == word.length() - 1)
                trie.map.get(c).word = true;
            trie = trie.map.get(c);
        }
    }

    public boolean search(String word) {
        Trie trie = this;
        for (char c : word.toCharArray()) {
            if(!trie.map.containsKey(c))
                return false;
            trie = trie.map.get(c);
        }
        return trie.word;
    }

    public boolean startsWith(String prefix) {
        Trie trie = this;
        for (char c : prefix.toCharArray()) {
            if(!trie.map.containsKey(c))
                return false;
            trie = trie.map.get(c);
        }
        return true;
    }
}