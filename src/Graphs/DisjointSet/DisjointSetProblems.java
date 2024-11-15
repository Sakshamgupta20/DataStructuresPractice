package Graphs.DisjointSet;

import java.util.*;

public class DisjointSetProblems {

    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        DisjointSet disjointSet = new DisjointSet(n);

        int numberOfComponents = n;
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (isConnected[i][j] == 1 && disjointSet.find(i) != disjointSet.find(j)) {
                    numberOfComponents--;
                    disjointSet.union(i, j);
                }
            }
        }

        return numberOfComponents;
    }

    public boolean validTree(int n, int[][] edges) {
        DisjointSet disjointSet = new DisjointSet(n);
        int components = n;
        for (int[] edge : edges) {
            int rootX = disjointSet.find(edge[0]);
            int rootY = disjointSet.find(edge[1]);
            if (rootX != rootY) {
                disjointSet.union(edge[0], edge[1]);
                n--;
            } else
                return false;
        }
        return n == 1;
    }

    public int countComponents(int n, int[][] edges) {
        DisjointSet disjointSet = new DisjointSet(n);
        int numberOfComponents = n;
        for (int[] edge : edges) {
            int rootX = disjointSet.find(edge[0]);
            int rootY = disjointSet.find(edge[1]);
            if (rootX != rootY) {
                disjointSet.union(edge[0], edge[1]);
                n--;
            }
        }

        return numberOfComponents;
    }

    public int earliestAcq(int[][] logs, int n) {

        Arrays.sort(logs, new Comparator<int[]>() {
            @Override
            public int compare(int[] log1, int[] log2) {
                Integer tsp1 = new Integer(log1[0]);
                Integer tsp2 = new Integer(log2[0]);
                return tsp1.compareTo(tsp2);
            }
        });

        DisjointSet disjointSet = new DisjointSet(n);
        int numberOfComponents = 0;
        for (int[] edge : logs) {
            int rootX = disjointSet.find(edge[1]);
            int rootY = disjointSet.find(edge[2]);
            if (rootX != rootY) {
                disjointSet.union(edge[1], edge[2]);
                numberOfComponents++;
            }
            if (numberOfComponents == n - 1)
                return edge[0];
        }

        return -1;
    }

    public String smallestStringWithSwaps(String s, List<List<Integer>> pairs) {
        DisjointSet disjointSet = new DisjointSet(s.length());
        pairs.forEach(pair -> {
            disjointSet.union(pair.get(0), pair.get(1));
        });

        Map<Integer, List<Integer>> roots = new HashMap<>();
        // Group the vertices that are in the same component
        for (int vertex = 0; vertex < s.length(); vertex++) {
            int root = disjointSet.find(vertex);
            // Add the vertices corresponding to the component root
            roots.putIfAbsent(root, new ArrayList<>());
            roots.get(root).add(vertex);
        }

        char[] res = new char[s.length()];

        for (List<Integer> value : roots.values()) {
            List<Character> characters = new ArrayList<>();
            for (Integer i : value) {
                characters.add(s.charAt(i));
            }
            Collections.sort(characters);
            for (int i = 0; i < value.size(); i++) {
                res[value.get(i)] = characters.get(i);
            }
        }
        return new String(res);
    }

    public int[] findRedundantConnection(int[][] edges) {
        DisjointSet disjointSet = new DisjointSet(edges.length);
        for (int[] edge : edges) {
            int rootX = disjointSet.find(edge[0]);
            int rootY = disjointSet.find(edge[1]);
            if (rootX != rootY) {
                disjointSet.union(edge[0], edge[1]);
            } else
                return edge;
        }
        return new int[] {};
    }

}
