package org.example;

import java.util.*;

class Graph {
    private List<Node> nodes;
    private List<Edge> edges;

    public Graph() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public void addNode(String name) {
        nodes.add(new Node(name));
    }

    public void addEdge(String fromName, String toName, int weight) {
        Node from = findNode(fromName);
        Node to = findNode(toName);

        if (from == null || to == null) {
            throw new IllegalArgumentException("Invalid node names provided.");
        }

        edges.add(new Edge(from, to, weight));
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    private Node findNode(String name) {
        for (Node node : nodes) {
            if (node.getName().equals(name)) {
                return node;
            }
        }
        return null;
    }

    public List<Edge> kruskalMST() {
        List<Edge> mst = new ArrayList<>();
        edges.sort(Comparator.comparingInt(Edge::getWeight));

        // Union-Find data structure
        Map<Node, Node> parent = new HashMap<>();
        for (Node node : nodes) {
            parent.put(node, node);
        }

        for (Edge edge : edges) {
            Node root1 = findRootKruskal(edge.getFrom(), parent);
            Node root2 = findRootKruskal(edge.getTo(), parent);

            if (!root1.equals(root2)) {
                mst.add(edge);
                parent.put(root1, root2);
            }

            if (mst.size() == nodes.size() - 1) {
                break;
            }
        }

        return mst;
    }

    public List<Edge> primMST() {
        List<Edge> mst = new ArrayList<>();
        if (nodes.isEmpty()) return mst;

        Set<Node> visited = new HashSet<>();
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));

        Node start = nodes.get(0);
        visited.add(start);

        for (Edge edge : edges) {
            if (edge.getFrom().equals(start) || edge.getTo().equals(start)) {
                priorityQueue.add(edge);
            }
        }

        while (!priorityQueue.isEmpty() && mst.size() < nodes.size() - 1) {
            Edge edge = priorityQueue.poll();
            Node target = !visited.contains(edge.getFrom()) ? edge.getFrom() : edge.getTo();

            if (!visited.contains(target)) {
                mst.add(edge);
                visited.add(target);

                for (Edge e : edges) {
                    if ((e.getFrom().equals(target) || e.getTo().equals(target)) &&
                            (!visited.contains(e.getFrom()) || !visited.contains(e.getTo()))) {
                        priorityQueue.add(e);
                    }
                }
            }
        }

        return mst;
    }

    public List<Edge> boruvkaMST() {
        List<Edge> mst = new ArrayList<>();
        if (nodes.isEmpty()) return mst;

        Map<Node, Node> parent = new HashMap<>();
        Map<Node, Integer> rank = new HashMap<>();

        for (Node node : nodes) {
            parent.put(node, node);
            rank.put(node, 0);
        }

        int components = nodes.size();
        while (components > 1) {
            Map<Node, Edge> cheapest = new HashMap<>();

            for (Edge edge : edges) {
                Node root1 = findRootBoruvka(edge.getFrom(), parent);
                Node root2 = findRootBoruvka(edge.getTo(), parent);

                if (!root1.equals(root2)) {
                    if (!cheapest.containsKey(root1) || edge.getWeight() < cheapest.get(root1).getWeight()) {
                        cheapest.put(root1, edge);
                    }
                    if (!cheapest.containsKey(root2) || edge.getWeight() < cheapest.get(root2).getWeight()) {
                        cheapest.put(root2, edge);
                    }
                }
            }

            for (Edge edge : cheapest.values()) {
                Node root1 = findRootBoruvka(edge.getFrom(), parent);
                Node root2 = findRootBoruvka(edge.getTo(), parent);

                if (!root1.equals(root2)) {
                    mst.add(edge);
                    union(root1, root2, parent, rank);
                    components--;
                }
            }
        }

        return mst;
    }



    private Node findRootKruskal(Node node, Map<Node, Node> parent) {
        while (!parent.get(node).equals(node)) {
            node = parent.get(node);
        }
        return node;
    }

    private Node findRootBoruvka(Node node, Map<Node, Node> parent) {
        if (!parent.get(node).equals(node)) {
            parent.put(node, findRootKruskal(parent.get(node), parent));
        }
        return parent.get(node);
    }

    private void union(Node root1, Node root2, Map<Node, Node> parent, Map<Node, Integer> rank) {
        if (rank.get(root1) < rank.get(root2)) {
            parent.put(root1, root2);
        } else if (rank.get(root1) > rank.get(root2)) {
            parent.put(root2, root1);
        } else {
            parent.put(root2, root1);
            rank.put(root1, rank.get(root1) + 1);
        }
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Nodes: ").append(nodes).append("\n");
        builder.append("Edges:\n");
        for (Edge edge : edges) {
            builder.append("  ").append(edge).append("\n");
        }
        return builder.toString();
    }
}
