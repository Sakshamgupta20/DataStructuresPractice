package Graphs.DisjointSet;

import common.CommonUtils;

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
        return new int[]{};
    }

    public List<Boolean> checkIfPrerequisite(int numCourses, int[][] prerequisites, int[][] queries) {
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        int[] indegree = new int[numCourses];

        for (int[] edge : prerequisites) {
            adjList
                    .computeIfAbsent(edge[0], k -> new ArrayList<>())
                    .add(edge[1]);
            indegree[edge[1]]++;
        }

        Queue<Integer> q = new LinkedList<>();
        for (int i = 0; i < numCourses; i++) {
            if (indegree[i] == 0) {
                q.offer(i);
            }
        }

        Map<Integer, Set<Integer>> nodePrerequisites = new HashMap<>();

        while (!q.isEmpty()) {
            int node = q.poll();

            for (int adj : adjList.getOrDefault(node, new ArrayList<>())) {
                // Add node and prerequisites of the node to the prerequisites of adj
                nodePrerequisites
                        .computeIfAbsent(adj, k -> new HashSet<>())
                        .add(node);
                for (int prereq : nodePrerequisites.getOrDefault(
                        node,
                        new HashSet<>()
                )) {
                    nodePrerequisites.get(adj).add(prereq);
                }

                indegree[adj]--;
                if (indegree[adj] == 0) {
                    q.offer(adj);
                }
            }
        }

        List<Boolean> answer = new ArrayList<>();
        for (int[] query : queries) {
            answer.add(
                    nodePrerequisites
                            .getOrDefault(query[1], new HashSet<>())
                            .contains(query[0])
            );
        }

        return answer;
    }

    public int largestIsland(int[][] grid) {
        int rows = grid.length;
        int columns = grid[0].length;

        // Initialize DSU for the entire grid
        DisjointSetIsland ds = new DisjointSetIsland(rows * columns);

        int[] rowDirections = {1, -1, 0, 0};
        int[] columnDirections = {0, 0, 1, -1};

        for (int currentRow = 0; currentRow < rows; currentRow++) {
            for (int currentColumn = 0; currentColumn < columns; currentColumn++) {
                if (grid[currentRow][currentColumn] == 1) {
                    int currentNode = (columns * currentRow) + currentColumn;

                    for (int direction = 0; direction < 4; direction++) {
                        int neighborRow = currentRow + rowDirections[direction];
                        int neighborColumn =
                                currentColumn + columnDirections[direction];

                        if (CommonUtils.validGrid(rows, columns, neighborRow, neighborColumn) &&
                                grid[neighborRow][neighborColumn] == 1
                        ) {
                            int neighborNode = columns * neighborRow + neighborColumn;
                            ds.union(currentNode, neighborNode);
                        }
                    }
                }
            }
        }

        //Now we have all islands with a leader for them

        int maxIslandSize = 0;
        boolean hasZero = false;
        Set<Integer> uniqueRoots = new HashSet<>();

        for (int currentRow = 0; currentRow < rows; currentRow++) {
            for (int currentColumn = 0; currentColumn < columns; currentColumn++) {
                if (grid[currentRow][currentColumn] == 0) {
                    hasZero = true;
                    int currentIslandSize = 1;
                    for (int direction = 0; direction < 4; direction++) {
                        int neighborRow = currentRow + rowDirections[direction];
                        int neighborColumn =
                                currentColumn + columnDirections[direction];

                        if (CommonUtils.validGrid(rows, columns, neighborRow, neighborColumn) &&
                                grid[neighborRow][neighborColumn] == 1
                        ) {
                            int neighborNode = columns * neighborRow + neighborColumn;
                            int root = ds.find(neighborNode);
                            uniqueRoots.add(root);
                        }
                    }

                    for (int root : uniqueRoots) {
                        currentIslandSize += ds.rank[root];
                    }

                    // Clear the set for the next `0`
                    uniqueRoots.clear();

                    // Update the result with the largest island size found
                    maxIslandSize = Math.max(maxIslandSize, currentIslandSize);
                }
            }
        }


        if (!hasZero) return rows * columns;

        return maxIslandSize;
    }

}
