package Graphs.DisjointSet;

public class DisjointSetPractice {
    int[] nodes;
    int[] rank;

    public DisjointSetPractice(int size) {
        nodes = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            nodes[i] = i;
            rank[i] = 1;
        }
    }

    public void union(int x, int y) {
        int xRoot = find(x);
        int yRoot = find(y);
        if (xRoot == yRoot)
            return;
        if (rank[xRoot] > rank[yRoot])
            nodes[yRoot] = xRoot;
        else if (rank[xRoot] < rank[yRoot])
            nodes[xRoot] = yRoot;
        else {
            nodes[yRoot] = xRoot;
            rank[xRoot]++;
        }
    }

    public int find(int x) {
        if (nodes[x] == x)
            return x;
        int res = find(nodes[x]);
        nodes[x] = res;
        return res;
    }

    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
}
