package StringProblems;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Codec {

    // Encodes a list of strings to a single string.
    public String encode(List<String> strs) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < strs.size(); i++) {
            stringBuilder.append(strs.get(i).length());
            stringBuilder.append("#");
            stringBuilder.append(strs.get(i));
        }
        return stringBuilder.toString();
    }

    // Decodes a single string to a list of strings.
    public List<String> decode(String s) {
        List<String> res = new ArrayList<>();
        int index = 0;
        while (index < s.length()) {
            // Find the delimiter.
            int delim = s.indexOf("#", index);
            int length = Integer.parseInt(s.substring(index, delim));
            String str = s.substring(delim + 1, delim + 1 + length);
            res.add(str);
            // Move the index to the start of the next length.
            index = delim + 1 + length;
        }
        return res;
    }
}