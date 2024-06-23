package QueueProblems;

import java.util.LinkedList;
import java.util.Queue;

public class QueueProblems {
    public String predictPartyVictory(String senate) {
        Queue<Character> turns = new LinkedList<>();
        int rTurns = 0;
        int dTurns = 0;


        for (char c : senate.toCharArray()) {
            turns.add(c);
        }

        while (!turns.isEmpty()) {
            Character c = turns.poll();
            if (c == 'D') {
                if (rTurns > 0) {
                    rTurns--;
                    turns.add('R');
                } else {
                    dTurns++;
                }
            } else {
                if (dTurns > 0) {
                    dTurns--;
                    turns.add('D');
                } else {
                    rTurns++;
                }
            }
        }
        return rTurns > 0 ? "Radiant" : "Dire";
    }
}
