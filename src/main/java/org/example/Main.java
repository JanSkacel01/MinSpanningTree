package org.example;

import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Graph graph = new Graph();

        graph.addNode("Solitude");
        graph.addNode("Rivie");
        graph.addNode("Tristam");
        graph.addNode("Lordaeron");
        graph.addNode("Arraken");
        graph.addNode("Lannisport");
        graph.addNode("MinasTirith");
        graph.addNode("Gondolin");
        graph.addNode("Mordheim");
        graph.addNode("AnkhMorpork");
        graph.addNode("MosEisley");
        graph.addNode("LV426");
        graph.addNode("GordricsHollow");

        graph.addEdge("Solitude", "Rivie", 8);
        graph.addEdge("Rivie", "Tristam", 12);
        graph.addEdge("Rivie", "MinasTirith", 3);
        graph.addEdge("Tristam", "MinasTirith", 4);
        graph.addEdge("Tristam", "Arraken", 7);
        graph.addEdge("Tristam", "Lordaeron", 2);
        graph.addEdge("Lordaeron", "Arraken", 9);
        graph.addEdge("Arraken", "Lannisport", 16);
        graph.addEdge("Arraken", "AnkhMorpork", 24);
        graph.addEdge("Lannisport", "AnkhMorpork", 10);
        graph.addEdge("AnkhMorpork", "Gondolin", 5);
        graph.addEdge("AnkhMorpork", "MosEisley", 4);
        graph.addEdge("Gondolin", "MosEisley", 20);
        graph.addEdge("Gondolin", "MinasTirith", 9);
        graph.addEdge("Gondolin", "Mordheim", 5);
        graph.addEdge("Gondolin", "GordricsHollow", 15);
        graph.addEdge("MinasTirith", "Mordheim", 8);
        graph.addEdge("Mordheim", "GordricsHollow", 1);
        graph.addEdge("GordricsHollow", "LV426", 3);
        graph.addEdge("MosEisley", "LV426", 7);

        processMST("Kruskal's MST", graph.kruskalMST());
        processMST("Prim's MST", graph.primMST());
        processMST("Borůvka's MST", graph.boruvkaMST());
    }

    private static void processMST(String algorithmName, List<Edge> mstEdges) {
        System.out.println(algorithmName + ":");

        HashSet<Node> visitedNodes = new HashSet<>();
        int totalWorkDays = 0;
        int totalKm = 0;

        for (Edge edge : mstEdges) {
            int hours = 0;
            int km = edge.getWeight();

            if (visitedNodes.contains(edge.getFrom()) || visitedNodes.contains(edge.getTo()) || visitedNodes.isEmpty()) {
                hours = edge.getWeight();
            } else {
                hours = edge.getWeight() + 1;
            }

            visitedNodes.add(edge.getFrom());
            visitedNodes.add(edge.getTo());

            totalWorkDays += (hours + 7) / 8;
            totalKm += km;

            System.out.println(edge + " " + hours + " h, " + km + " km");
        }

        System.out.println("Result: " + totalWorkDays + " days, " + totalKm + " km\n");
    }



}
