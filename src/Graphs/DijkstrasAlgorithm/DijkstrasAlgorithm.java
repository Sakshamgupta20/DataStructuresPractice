package Graphs.DijkstrasAlgorithm;

import common.CommonUtils;

import java.util.*;

public class DijkstrasAlgorithm {

    /**
     * 743. Network Delay Time
     * You are given a network of n nodes, labeled from 1 to n. You are also given times, a list of travel times as directed edges times[i] = (ui, vi, wi), where ui is the source node, vi is the target node, and wi is the time it takes for a signal to travel from source to target.
     * <p>
     * We will send a signal from a given node k. Return the minimum time it takes for all the n nodes to receive the signal. If it is impossible for all the n nodes to receive the signal, return -1.
     */
    public int networkDelayTime(int[][] times, int n, int k) {
        HashMap<Integer, List<Node>> nodes = new HashMap<>();
        for (int[] time : times) {
            int source = time[0];
            int destination = time[1];
            int timeTaken = time[2];
            nodes.putIfAbsent(source, new ArrayList<>());
            nodes.get(source).add(new Node(destination, timeTaken));
        }

        int[] signalReceivedAt = new int[n + 1];
        Arrays.fill(signalReceivedAt, Integer.MAX_VALUE);

        networkDelayTime(signalReceivedAt, nodes, k);
        int answer = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            answer = Math.max(answer, signalReceivedAt[i]);
        }

        // INT_MAX signifies atleat one node is unreachable
        return answer == Integer.MAX_VALUE ? -1 : answer;
    }

    private void networkDelayTime(int[] signalReceivedAt, HashMap<Integer, List<Node>> nodes, int source) {
        Queue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.distance));

        pq.add(new Node(source, 0));

        signalReceivedAt[source] = 0;


        while (!pq.isEmpty()) {
            Node node = pq.poll();
            int currNode = node.node;
            int currNodeTime = node.distance;

            if (currNodeTime > signalReceivedAt[currNode]) {
                continue;
            }

            if (!nodes.containsKey(currNode)) {
                continue;
            }

            for (Node neighbor : nodes.get(currNode)) {
                int destination = neighbor.node;
                int distance = neighbor.distance;

                int totalDistance = distance + currNodeTime;

                if (signalReceivedAt[destination] > totalDistance) {
                    signalReceivedAt[destination] = totalDistance;
                    pq.add(new Node(destination, totalDistance));
                }
            }

        }
    }

    /**
     * 787. Cheapest Flights Within K Stops
     * There are n cities connected by some number of flights. You are given an array flights where flights[i] = [fromi, toi, pricei] indicates that there is a flight from city fromi to city toi with cost pricei.
     * <p>
     * You are also given three integers src, dst, and k, return the cheapest price from src to dst with at most k stops. If there is no such route, return -1.
     */
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        HashMap<Integer, List<Node>> flightsMap = new HashMap<>();
        for (int[] flight : flights) {
            flightsMap.putIfAbsent(flight[0], new ArrayList<>());
            flightsMap.get(flight[0]).add(new Node(flight[1], flight[2]));
        }

        Queue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.distance));
        pq.add(new Node(src, 0, 0));
        int[] stops = new int[n];

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            int currFlight = node.node;
            int currFlightTime = node.distance;
            int currStops = node.count;

            if (currStops > stops[currFlight] || currStops > k + 1)
                continue;

            if(!flightsMap.containsKey(currFlight))
                continue;

            stops[currFlight] = currStops;
            if (currFlight == dst)
                return currFlightTime;

            for (Node neighbor : flightsMap.get(currFlight)) {
                int destination = neighbor.node;
                int distance = neighbor.distance;

                int totalDistance = distance + currFlightTime;

                pq.add(new Node(destination, totalDistance, currStops + 1));
            }

        }
        return -1;

    }

    public int swimInWater(int[][] grid) {
        int n = grid.length;

        PriorityQueue<Tuple> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a.maxWt));
        int[][] visited = new int[n][n];

        pq.add(new Tuple(grid[0][0], 0, 0));
        int[][] directions = CommonUtils.getVerticalHorizontalDirections();

        while (!pq.isEmpty()) {
            Tuple t = pq.poll();
            int x = t.x;
            int y = t.y;
            int wt = t.maxWt;

            if (visited[x][y] == 1) {
                continue;
            }

            visited[x][y] = 1;

            if (x == n - 1 && y == n - 1) {
                return wt;
            }

            for (int[] direction : directions) {
                int newRow = x + direction[0];
                int newColumn = y + direction[1];
                if (CommonUtils.validGrid(n, n, newRow, newColumn)) {
                    int we = Math.max(wt, grid[newRow][newColumn]);
                    pq.add(new Tuple(we, newRow, newColumn));
                }
            }

        }
        return 0;
    }
}
