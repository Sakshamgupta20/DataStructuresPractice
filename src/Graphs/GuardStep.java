package Graphs;


public class GuardStep {
    int row;
    int column;
    int[][] next;

    public GuardStep(int row, int column, int[][] next) {
        this.row = row;
        this.column = column;
        this.next = next;
    }
}
