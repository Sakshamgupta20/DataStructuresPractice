package Graphs.DisjointSet;

public class  UnionPathCompression {
    private int[] root;
    private int[] rank;

    public UnionPathCompression(int size) {
        root = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            root[i] = i;
            rank[i] = 1;
        }
    }

    public int find(int x) {
        if (root[x] == x)
            return x;
        int rootOfX = find(root[x]);
        root[x] = rootOfX;
        return rootOfX;

        //Shorter version
        //return root[x] = find(x);
    }

    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX != rootY) {
            root[rootY] = rootX;
        }
    }

    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }
}
