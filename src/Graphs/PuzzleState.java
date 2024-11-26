package Graphs;

public class PuzzleState {
    String state; // Current board configuration as a string
    int steps;    // Number of steps taken to reach this configuration

    public PuzzleState(String state, int steps) {
        this.state = state;
        this.steps = steps;
    }
}
