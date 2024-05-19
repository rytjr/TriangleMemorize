package TriangleMemorize;

import java.util.*;

public class TSP {
    private final int n; // Number of nodes
    private final int[][] dist; // Distance matrix
    private final int[][] dp; // DP table
    private final int[][] parent; // Parent table for path reconstruction
    private final int VISITED_ALL; // Bitmask for all nodes visited
    private final int INF = Integer.MAX_VALUE / 2; // Infinity value

    public TSP(int n, int[][] dist) {
        this.n = n;
        this.dist = dist;
        this.VISITED_ALL = (1 << n) - 1;
        this.dp = new int[n][1 << n];
        this.parent = new int[n][1 << n];
        for (int[] row : dp) {
            Arrays.fill(row, -1);
        }
        for (int[] row : parent) {
            Arrays.fill(row, -1);
        }
    }

    public int findShortestTour(int start) {
        int cost = tsp(start, 1 << start);
        printPath(start);
        return cost;
    }

    private int tsp(int current, int visited) {
        if (visited == VISITED_ALL) {
            return dist[current][0] != 0 ? dist[current][0] : INF; // Return to start
        }

        if (dp[current][visited] != -1) {
            return dp[current][visited];
        }

        int minCost = INF;
        for (int next = 0; next < n; next++) {
            if ((visited & (1 << next)) == 0 && dist[current][next] != 0) {
                int cost = dist[current][next] + tsp(next, visited | (1 << next));
                if (cost < minCost) {
                    minCost = cost;
                    parent[current][visited] = next;
                }
            }
        }
        dp[current][visited] = minCost;

        // Print the current path and cost
        printCurrentPathAndCost(current, visited, minCost);

        return minCost;
    }

    private void printCurrentPathAndCost(int current, int visited, int cost) {
        List<Character> path = new ArrayList<>();
        int mask = visited;
        int node = current;

        while (mask != 0) {
            path.add((char) ('a' + node));
            int nextNode = parent[node][mask];
            if (nextNode == -1) break;
            mask ^= (1 << node);
            node = nextNode;
        }
        Collections.reverse(path);
        System.out.print("Path: ");
        for (char c : path) {
            System.out.print(c + " ");
        }
        System.out.println("Cost: " + cost);
    }

    private void printPath(int start) {
        List<Character> path = new ArrayList<>();
        int mask = 1 << start;
        int current = start;
        path.add((char) ('a' + current));

        while (true) {
            int next = parent[current][mask];
            if (next == -1) break;
            path.add((char) ('a' + next));
            current = next;
            mask |= (1 << current);
        }
        path.add('a');
        
        System.out.print("Final Path: ");
        for (char c : path) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        // Example graph
        int n = 8;
        int[][] dist = {
            {0, 20, 0, 15, 0, 0, 0, 0},
            {20, 0, 13, 0, 2, 0, 0, 3},
            {0, 13, 0, 10, 3, 5, 0, 7},
            {15, 0, 10, 0, 4, 9, 0, 0},
            {0, 2, 3, 4, 0, 1, 2, 0},
            {0, 0, 5, 9, 1, 0, 1, 2},
            {0, 0, 0, 0, 2, 1, 0, 1},
            {0, 3, 7, 0, 0, 2, 1, 0}
        };

        TSP tsp = new TSP(n, dist);
        int start = 0; // Starting node is 'a'
        int shortestTourCost = tsp.findShortestTour(start);
        System.out.println("Shortest tour cost: " + shortestTourCost);
    }
}