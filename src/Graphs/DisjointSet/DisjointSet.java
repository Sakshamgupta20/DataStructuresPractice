package Graphs.DisjointSet;

import java.util.List;

public class DisjointSet {
    public int[] root;
    // Use a rank array to record the height of each vertex, i.e., the "rank" of each vertex.
    private int[] rank;

    public DisjointSet(int size) {
        root = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            root[i] = i;
            rank[i] = 1; // The initial "rank" of each vertex is 1, because each of them is
            // a standalone vertex with no connection to other vertices.
        }
    }

    // The find function here is the same as that in the disjoint set with path compression.
    public int find(int x) {
        if (x == root[x]) {
            return x;
        }
        int rootOfX = find(root[x]);
        root[x] = rootOfX;
        return rootOfX;

        //Shorter version
        //return root[x] = find(x);
    }

    // The union function with union by rank
    public void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if (rootX == rootY)
            return;

        if (rank[rootX] > rank[rootY]) {
            root[rootY] = rootX;
        } else if (rank[rootX] < rank[rootY]) {
            root[rootX] = rootY;
        } else {
            root[rootY] = rootX;
            rank[rootX] += 1;
        }
    }


    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }

}
