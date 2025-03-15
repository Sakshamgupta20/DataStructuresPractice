package StringProblems;

import java.util.*;

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

    /**
     * 796. Rotate String
     * Given two strings s and goal, return true if and only if s can become goal after some number of shifts on s.
     * <p>
     * A shift on s consists of moving the leftmost character of s to the rightmost position.
     * <p>
     * For example, if s = "abcde", then it will be "bcdea" after one shift.
     */
    public boolean rotateString(String s, String goal) {
        if (Objects.equals(s, goal))
            return true;
        for (int i = 0; i < s.length(); i++) {
            s = s.substring(1) + s.charAt(0);
            if (s.equals(goal))
                return true;
        }
        return false;
    }

    /**
     * 3163. String Compression III
     * Begin with an empty string comp. While word is not empty, use the following operation:
     * Remove a maximum length prefix of word made of a single character c repeating at most 9 times.
     * Append the length of the prefix followed by c to comp.
     * Return the string comp.
     */
    public String compressedString(String word) {
        int currCount = 0;
        StringBuilder builder = new StringBuilder();

        int last = -1;
        for (int i = 0; i < word.length(); i++) {
            int c = word.charAt(i) - 'a';

            if (last != -1 && last != c) {
                builder.append(currCount).append((char) (last + 'a'));
                currCount = 1;
            }

            if (currCount == 9) {
                builder.append(currCount).append((char) (last + 'a'));
                currCount = 0;
            }

            if (last == -1 || last == c)
                currCount++;
            last = c;
        }
        builder.append(currCount).append((char) (last + 'a'));
        return builder.toString();
    }

    /**
     * 2914. Minimum Number of Changes to Make Binary String Beautiful
     * You are given a 0-indexed binary string s having an even length.
     * <p>
     * A string is beautiful if it's possible to partition it into one or more substrings such that:
     * <p>
     * Each substring has an even length.
     * Each substring contains only 1's or only 0's.
     * You can change any character in s to 0 or 1.
     * <p>
     * Return the minimum number of changes required to make the string s beautiful.
     */
    public int minChanges(String s) {
        int count = 0;
        for (int i = 0; i < s.length(); i += 2) {
            char c1 = s.charAt(i);
            char c2 = s.charAt(i + 1);
            if (c1 != c2)
                count++;
        }
        return count;
    }

    /**
     * 2955. Number of Same-End Substrings
     * You are given a 0-indexed string s, and a 2D array of integers queries, where queries[i] = [li, ri] indicates a substring of s starting from the index li and ending at the index ri (both inclusive), i.e. s[li..ri].
     * <p>
     * Return an array ans where ans[i] is the number of same-end substrings of queries[i].
     * <p>
     * A 0-indexed string t of length n is called same-end if it has the same character at both of its ends, i.e., t[0] == t[n - 1].
     * <p>
     * A substring is a contiguous non-empty sequence of characters within a string.
     */
    public int[] sameEndSubstringCount(String s, int[][] queries) {
        int[][] counts = new int[s.length() + 1][26];
        int[] currr = new int[26];
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'a';
            currr[c]++;
            counts[i + 1] = Arrays.copyOf(currr, 26);
        }
        int[] ans = new int[queries.length];
        int index = 0;
        for (int[] query : queries) {
            int res = 0;
            int[] c1 = counts[query[0]];
            int[] c2 = counts[query[1] + 1];
            for (int i = 0; i < 26; i++) {
                int count = c2[i] - c1[i];
                res += count;
                res += count * (count - 1) / 2;
            }
            ans[index++] = res;
        }
        return ans;
    }

    /**
     * 2085. Count Common Words With One Occurrence
     * <p>
     * Given two string arrays words1 and words2, return the number of strings that appear exactly once in each of the two arrays.
     */
    public int countWords(String[] words1, String[] words2) {
        Set<String> filter = new HashSet<>();
        Set<String> dead = new HashSet<>();

        for (String word : words1) {
            if (dead.contains(word)) continue;
            if (!filter.add(word)) {
                dead.add(word);
                filter.remove(word);
            }
        }

        dead.clear();
        Set<String> result = new HashSet<>();

        for (String word : words2) {
            if (dead.contains(word))
                continue;
            if (!filter.contains(word))
                continue;
            if (result.contains(word)) {
                dead.add(word);
                result.remove(word);
                continue;
            }
            result.add(word);
        }
        return result.size();
    }

    /**
     * 2351. First Letter to Appear Twice
     * Given a string s consisting of lowercase English letters, return the first letter to appear twice.
     * <p>
     * Note:
     * <p>
     * A letter a appears twice before another letter b if the second occurrence of a is before the second occurrence of b.
     * s will contain at least one letter that appears twice.
     */
    public char repeatedCharacter(String s) {
        //Array to store character
        boolean[] chars = new boolean[26];
        for (char c : s.toCharArray()) {
            if (chars[c - 'a'])
                return c;
            chars[c - 'a'] = true;
        }
        //It means no character is getting repeated
        return 'X';
    }

    /**
     * 1455. Check If a Word Occurs As a Prefix of Any Word in a Sentence
     * Given a sentence that consists of some words separated by a single space, and a searchWord, check if searchWord is a prefix of any word in sentence.
     * <p>
     * Return the index of the word in sentence (1-indexed) where searchWord is a prefix of this word. If searchWord is a prefix of more than one word, return the index of the first word (minimum index). If there is no such word return -1.
     * <p>
     * A prefix of a string s is any leading contiguous substring of s.
     */
    public int isPrefixOfWord(String sentence, String searchWord) {
        String[] chars = sentence.split(" ");
        for (int i = 0; i < chars.length; i++) {
            if (chars[i].indexOf(searchWord) == 0)
                return i + 1;
        }
        return -1;
    }

    /**
     * 2109. Adding Spaces to a String
     * You are given a 0-indexed string s and a 0-indexed integer array spaces that describes the indices in the original string where spaces will be added. Each space should be inserted before the character at the given index.
     * <p>
     * For example, given s = "EnjoyYourCoffee" and spaces = [5, 9], we place spaces before 'Y' and 'C', which are at indices 5 and 9 respectively. Thus, we obtain "Enjoy Your Coffee".
     * Return the modified string after the spaces have been added.
     */
    public String addSpaces(String s, int[] spaces) {
        char[] ch = s.toCharArray();
        char[] charr = new char[s.length() + spaces.length];
        int idx = 0, c = 0;

        for (int sp : spaces) {
            while (c < sp) {
                charr[idx] = ch[c];
                idx++;
                c++;
            }
            charr[idx] = ' ';
            idx++;
        }
        while (c < s.length()) {
            charr[idx] = ch[c];
            idx++;
            c++;
        }
        return new String(charr);
    }

    /**
     * 2825. Make String a Subsequence Using Cyclic Increments
     * You are given two 0-indexed strings str1 and str2.
     * <p>
     * In an operation, you select a set of indices in str1, and for each index i in the set, increment str1[i] to the next character cyclically. That is 'a' becomes 'b', 'b' becomes 'c', and so on, and 'z' becomes 'a'.
     * <p>
     * Return true if it is possible to make str2 a subsequence of str1 by performing the operation at most once, and false otherwise.
     * <p>
     * Note: A subsequence of a string is a new string that is formed from the original string by deleting some (possibly none) of the characters without disturbing the relative positions of the remaining characters.
     */
    public boolean canMakeSubsequence(String str1, String str2) {
        int str2Index = 0;
        int lengthStr1 = str1.length(), lengthStr2 = str2.length();
        for (int str1Index = 0; str1Index < lengthStr1 && str2Index < lengthStr2; ++str1Index) {
            if (str1.charAt(str1Index) == str2.charAt(str2Index) ||
                    (str1.charAt(str1Index) + 1 == str2.charAt(str2Index)) ||
                    (str1.charAt(str1Index) - 25 == str2.charAt(str2Index))
            ) {
                str2Index++;
            }
        }
        // Check if all characters in str2 were matched
        return str2Index == lengthStr2;
    }

    public boolean canChange(String start, String target) {
        int startLength = start.length();
        // Pointers for start string and target string
        int startIndex = 0, targetIndex = 0;
        while (startIndex < startLength || targetIndex < startLength) {
            while (startIndex < startLength && start.charAt(startIndex) == '_'
            ) {
                startIndex++;
            }
            while (
                    targetIndex < startLength && target.charAt(targetIndex) == '_'
            ) {
                targetIndex++;
            }

            if (startIndex == startLength || targetIndex == startLength) {
                return startIndex == startLength && targetIndex == startLength;
            }


            if (start.charAt(startIndex) != target.charAt(targetIndex) ||
                    (start.charAt(startIndex) == 'L' && startIndex < targetIndex) ||
                    (start.charAt(startIndex) == 'R' && startIndex > targetIndex)
            ) return false;

            startIndex++;
            targetIndex++;
        }
        return true;

    }

    /**
     * 2182. Construct String With Repeat Limit
     * You are given a string s and an integer repeatLimit. Construct a new string repeatLimitedString using the characters of s such that no letter appears more than repeatLimit times in a row. You do not have to use all characters from s.
     * <p>
     * Return the lexicographically largest repeatLimitedString possible.
     * <p>
     * A string a is lexicographically larger than a string b if in the first position where a and b differ, string a has a letter that appears later in the alphabet than the corresponding letter in b. If the first min(a.length, b.length) characters do not differ, then the longer string is the lexicographically larger one.
     */
    public String repeatLimitedString(String s, int repeatLimit) {
        int[] freq = new int[26];
        for (char ch : s.toCharArray()) {
            freq[ch - 'a']++;
        }

        StringBuilder result = new StringBuilder();
        int currentCharIndex = 25;
        while (currentCharIndex >= 0) {
            if (freq[currentCharIndex] == 0) {
                currentCharIndex--;
                continue;
            }
            int use = Math.min(freq[currentCharIndex], repeatLimit);
            result.append(String.valueOf((char) ('a' + currentCharIndex)).repeat(Math.max(0, use)));
            freq[currentCharIndex] -= use;

            if (freq[currentCharIndex] > 0) {
                int smallerCharIndex = currentCharIndex - 1;
                while (smallerCharIndex >= 0 && freq[smallerCharIndex] == 0) {
                    smallerCharIndex--;
                }
                if (smallerCharIndex < 0) {
                    break;
                }
                result.append((char) ('a' + smallerCharIndex));
                freq[smallerCharIndex]--;
            }
        }
        return result.toString();

    }

    /**
     * 1790. Check if One String Swap Can Make Strings Equal
     * You are given two strings s1 and s2 of equal length. A string swap is an operation where you choose two indices in a string (not necessarily different) and swap the characters at these indices.
     * <p>
     * Return true if it is possible to make both strings equal by performing at most one string swap on exactly one of the strings. Otherwise, return false.
     */
    public boolean areAlmostEqual(String s1, String s2) {
        int difference = 0;
        int diffIndex = -1;
        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                if (diffIndex == -1) {
                    diffIndex = i;
                } else {
                    if (s1.charAt(i) != s2.charAt(diffIndex) || s2.charAt(i) != s1.charAt(diffIndex))
                        return false;
                }
                difference++;
            }
        }
        return difference == 2 || difference == 0;
    }

    /**
     * 1910. Remove All Occurrences of a Substring
     * Given two strings s and part, perform the following operation on s until all occurrences of the substring part are removed:
     * <p>
     * Find the leftmost occurrence of the substring part and remove it from s.
     * Return s after removing all occurrences of part.
     * <p>
     * A substring is a contiguous sequence of characters in a string.
     */
    public String removeOccurrences(String s, String part) {
        StringBuilder res = new StringBuilder(s);
        while (true) {
            int index = res.indexOf(part);
            if (index == -1)
                break;
            res.replace(index, index + part.length(), "");
        }
        return res.toString();
    }


}
