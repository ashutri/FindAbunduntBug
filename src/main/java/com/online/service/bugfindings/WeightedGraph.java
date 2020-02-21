package com.online.service.bugfindings;
import java.util.LinkedList;

import org.springframework.stereotype.Component;

@Component
public class WeightedGraph {
    static class Edge {
        int source;
        int destination;
        int percentage;
        String occurrences;

        public Edge(int source, int destination, String occurrences, int percentage) {
            this.source = source;
            this.destination = destination;
            this.occurrences = occurrences;
            this.percentage = percentage;
        }
    }
    
    public class Graph {
        int vertices;
        LinkedList<Edge> [] adjacencylist;
        Graph() {}
        Graph(int vertices) {
            this.vertices = vertices;
            adjacencylist = new LinkedList[vertices];
            //initialize adjacency lists for all the vertices
            for (int i = 0; i <vertices ; i++) {
                adjacencylist[i] = new LinkedList<>();
            }
        }

        
        public void addEgde(int source, int destination, String occurrences, int percentage) {
            Edge edge = new Edge(source, destination, occurrences, percentage);
            adjacencylist[source].addFirst(edge); //for directed graph
        }
        
        public void printGraph(){
            for (int i = 0; i <vertices ; i++) {
                LinkedList<Edge> list = adjacencylist[i];
                for (int j = 0; j <list.size() ; j++) {
                    System.out.println(i + " - >" +
                            list.get(j).destination +" with Occurrences " +  list.get(j).occurrences+ " with weight " +  list.get(j).percentage);
                }
            }
        }
    }
      
}
