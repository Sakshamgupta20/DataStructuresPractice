package Trie;

public class WordDictionary {
    TrieNode trieNode;

    public WordDictionary() {
        trieNode = new TrieNode();
    }

    public void addWord(String word) {
        TrieNode curr = trieNode;
        for (int i = 0; i < word.length(); i++) {
            int c = word.charAt(i) - 'a';
            if (curr.chars[c] == null)
                curr.chars[c] = new TrieNode();
            curr = curr.chars[c];
        }
        curr.word = true;
        curr.value = word;
    }

    public boolean search(String word) {
        return search(word,trieNode,0);
    }

    public boolean search(String word, TrieNode curr, int index) {
        if(index >= word.length())
            return curr.word;

        for (int i = index; i < word.length(); i++) {
            char s = word.charAt(i);
            if (s == '.') {
                // Explore all possible paths
                for (TrieNode aChar : curr.chars) {
                    if (aChar != null && search(word, aChar, i + 1)) {
                        return true;  // Return true if any path matches
                    }
                }
                return false;
            } else {
                int c = s - 'a';
                if (curr.chars[c] == null)
                    return false;
                return search(word, curr.chars[c], index + 1);
            }
        }
        return curr.word;
    }
}