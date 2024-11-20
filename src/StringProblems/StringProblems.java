package StringProblems;

public class StringProblems {

    public boolean isAnagram(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        int[] table = new int[26];
        for (int i = 0; i < s.length(); i++) {
            table[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < t.length(); i++) {
            table[t.charAt(i) - 'a']--;
            if (table[t.charAt(i) - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 1957. Delete Characters to Make Fancy String
     * A fancy string is a string where no three consecutive characters are equal.
     * <p>
     * Given a string s, delete the minimum possible number of characters from s to make it fancy.
     * <p>
     * Return the final string after the deletion. It can be shown that the answer will always be unique.
     */
    public String makeFancyString(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (i < 2) {
                stringBuilder.append(c);
            } else {
                int n = stringBuilder.length();
                char last = stringBuilder.charAt((n - 1));
                char secondLast = stringBuilder.charAt((n - 2));
                if (!(last == c && secondLast == c)) {
                    stringBuilder.append(c);
                }
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 2490. Circular Sentence
     * A sentence is a list of words that are separated by a single space with no leading or trailing spaces.
     * <p>
     * For example, "Hello World", "HELLO", "hello world hello world" are all sentences.
     * Words consist of only uppercase and lowercase English letters. Uppercase and lowercase English letters are considered different.
     * <p>
     * A sentence is circular if:
     * <p>
     * The last character of a word is equal to the first character of the next word.
     * The last character of the last word is equal to the first character of the first word.
     * For example, "leetcode exercises sound delightful", "eetcode", "leetcode eats soul" are all circular sentences. However, "Leetcode is cool", "happy Leetcode", "Leetcode" and "I like Leetcode" are not circular sentences.
     * <p>
     * Given a string sentence, return true if it is circular. Otherwise, return false.
     */
    public boolean isCircularSentence(String sentence) {
        String[] strings = sentence.split(" ");
        char c = strings[0].charAt(strings[0].length() - 1);
        for (int i = 1; i < strings.length; i++) {
            if (c != strings[i].charAt(0))
                return false;
            c = strings[i].charAt(strings[i].length() - 1);
        }
        return strings[0].charAt(0) == c;
    }


}
