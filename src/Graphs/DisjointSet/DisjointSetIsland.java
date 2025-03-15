package Graphs.DisjointSet;

public class DisjointSetIsland {
    public int[] root;
    public int[] rank;

    public DisjointSetIsland(int size) {
        root = new int[size];
        rank = new int[size];
        for (int i = 0; i < size; i++) {
            root[i] = i;
            rank[i] = 1; // The initial "rank" of each vertex is 1, because each of them is
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

        if (rank[rootX] < rank[rootY]) {
            root[rootX] = rootY;
            rank[rootY] += rank[rootX];
        } else {
            root[rootY] = rootX;
            rank[rootX] += rank[rootY];
        }
    }


    public boolean connected(int x, int y) {
        return find(x) == find(y);
    }

}
