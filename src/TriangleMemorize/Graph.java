package TriangleMemorize;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;


class Graph {
    private final Map<String, List<Edge>> adjacencyList;

    public Graph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addEdge(String from, String to, int weight) {
        this.adjacencyList.putIfAbsent(from, new ArrayList<>());
        this.adjacencyList.putIfAbsent(to, new ArrayList<>());
        this.adjacencyList.get(from).add(new Edge(to, weight));
    }

    public Map<String, Integer> dijkstra(String start) {
        Map<String, Integer> distances = new HashMap<>();
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(edge -> edge.weight));
        priorityQueue.add(new Edge(start, 0));
        distances.put(start, 0);

        while (!priorityQueue.isEmpty()) {
            Edge current = priorityQueue.poll();
            String currentNode = current.node;
            int currentDistance = current.weight;

            for (Edge edge : adjacencyList.getOrDefault(currentNode, new ArrayList<>())) {
                String neighbor = edge.node;
                int newDist = currentDistance + edge.weight;

                if (newDist < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    distances.put(neighbor, newDist);
                    priorityQueue.add(new Edge(neighbor, newDist));
                }
            }
        }
        return distances;
    }

    public List<String> getNodes() {
        return new ArrayList<>(adjacencyList.keySet());
    }

    private static class Edge {
        String node;
        int weight;

        Edge(String node, int weight) {
            this.node = node;
            this.weight = weight;
        }
    }
}
