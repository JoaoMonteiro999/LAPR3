package USEI01_04;

import USEI01_04.graph.Graph;
import USEI01_04.graph.domain.Coords;
import USEI01_04.graph.domain.Node;
import USEI01_04.graph.domain.Utils;
import USEI01_04.graph.domain.Weight;
import USEI01_04.graph.map.MapGraph;
import USEI01_04.graph.matrix.MatrixGraph;

import java.util.ArrayList;

public class USEI01 {
    public static Graph<Node, Weight> mapGraph(String edges_file, String vertices_file) {
        return createMapGraph(false, edges_file, vertices_file);
    }

    public static Graph<Node, Weight> matrixGraph(String edges_file, String vertices_file) {
        return createMatrixGraph(false, edges_file, vertices_file);
    }

    private static void fillGraph(Graph<Node, Weight> graph, String edges_file, String vertices_file) {
        ArrayList<String[]> data_vertices = Utils.readCSV(vertices_file);
        ArrayList<String[]> data_edges = Utils.readCSV(edges_file);

        for (String[] s : data_vertices)
            graph.addVertex(new Node(s[0], new Coords(Utils.toDouble(s[1]), Utils.toDouble(s[2]))));

        Node vOrig = null;
        Node vDest = null;

        for (String[] s : data_edges) {
            for (Node v : graph.vertices()) {
                if (v.getLabel().equals(s[0]))
                    vOrig = v;
                if (v.getLabel().equals(s[1]))
                    vDest = v;
            }
            Weight weight = new Weight(Utils.toInt(s[2]), "m");
            graph.addEdge(vOrig, vDest, weight);
        }
    }

    public static Graph<Node, Weight> createMatrixGraph(Boolean directed, String edges_file, String vertices_file) {
        Graph<Node, Weight> graph = new MatrixGraph<>(directed);
        fillGraph(graph, edges_file, vertices_file);
        return graph;
    }

    public static Graph<Node, Weight> createMapGraph(Boolean directed, String edges_file, String vertices_file) {
        Graph<Node, Weight> graph = new MapGraph<>(directed);
        fillGraph(graph, edges_file, vertices_file);
        return graph;
    }

}
