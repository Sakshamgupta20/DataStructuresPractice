package Graphs;

public class Pair {
    String value;
    Integer steps;
    Double ratio;
    Integer id;

    public Pair(String value, Integer steps) {
        this.value = value;
        this.steps = steps;
    }

    public Pair(String value, Double ratio) {
        this.value = value;
        this.ratio = ratio;
    }

    public Pair(Integer id, Integer steps) {
        this.id = id;
        this.steps = steps;
    }
}
