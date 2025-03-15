package Graphs;


public class Step {
    public int row;
    public int column;
    public int steps;
    int remains;

    public Step(int row, int column, int steps) {
        this.row = row;
        this.column = column;
        this.steps = steps;
    }

    public Step(int row, int column, int steps, int remains) {
        this.row = row;
        this.column = column;
        this.steps = steps;
        this.remains = remains;
    }
}
