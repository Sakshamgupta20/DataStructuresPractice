package Graphs.DijkstrasAlgorithm;

public class Node {
    int node;
    int distance;
    int count;

    public Node(int node, int distance) {
        this.node = node;
        this.distance = distance;
    }

    public Node(int node, int distance, int count) {
        this.node = node;
        this.distance = distance;
        this.count = count;
    }
}
