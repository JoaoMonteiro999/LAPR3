package USEI01_04;

import USEI01_04.graph.Graph;
import USEI01_04.graph.domain.*;
import USEI01_04.graph.matrix.MatrixGraph;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class USEI04Test {
    String vertices_files_xs = "locais_xs.csv";
    String edges_files_xs = "distancias_xs.csv";
    String vertices_files_small = "locais_small.csv";
    String edges_files_small = "distancias_small.csv";
    String vertices_files_big = "locais_big.csv";
    String edges_files_big = "distancias_big.csv";

    Graph<Node, Weight> mapGraph_xs;
    Graph<Node, Weight> matrixGraph_xs;
    Graph<Node, Weight> mapGraph_small;
    Graph<Node, Weight> matrixGraph_small;
    Graph<Node, Weight> mapGraph_big;
    Graph<Node, Weight> matrixGraph_big;

    @BeforeEach
    void setUP() {

        mapGraph_xs = USEI01.mapGraph(edges_files_xs, vertices_files_xs);
        matrixGraph_xs = USEI01.matrixGraph(edges_files_xs, vertices_files_xs);

        mapGraph_small = USEI01.mapGraph(edges_files_small, vertices_files_small);
        matrixGraph_small = USEI01.matrixGraph(edges_files_small, vertices_files_small);

        mapGraph_big = USEI01.mapGraph(edges_files_big, vertices_files_big);
        matrixGraph_big = USEI01.matrixGraph(edges_files_big, vertices_files_big);
    }

    @Test
    void matrixGraphXS() {

        Graph<Node, Weight> graph = new MatrixGraph<>(false);

        String[] edges = {"CT1", "CT2", "CT2", "CT3", "CT2", "CT4", "CT4", "CT5","CT2", "CT1", "CT3", "CT2", "CT4", "CT2", "CT5", "CT4"};

        for (Node n : matrixGraph_xs.vertices())
            graph.addVertex(n);

        Node node1;
        Node node2;
        int totalDist = 0;
        for (int i = 0; i < edges.length; i += 2) {
            node1 = Utils.getNodeFromLabel(matrixGraph_xs, edges[i]);
            node2 = Utils.getNodeFromLabel(matrixGraph_xs, edges[i + 1]);
            totalDist += matrixGraph_xs.edge(node1, node2).getWeight().getDistance();
            graph.addEdge(node1, node2, matrixGraph_xs.edge(node1, node2).getWeight());
        }

        MST<Node, Weight> expected = new MST<>(graph, totalDist);

        MST<Node, Weight> result = USEI04.minCostNetwork(matrixGraph_xs);

        assertEquals(expected.getGraph().numVertices(), result.getGraph().numVertices());
        assertEquals(expected.getGraph().edges(), result.getGraph().edges());
        assertEquals(expected.getTotalDistance(), result.getTotalDistance());
        assertEquals(expected, result);
    }


    @Test
    void mapGraphXS() {

        Graph<Node, Weight> graph = new MatrixGraph<>(false);

        String[] edges = {"CT1", "CT2", "CT2", "CT3", "CT2", "CT4", "CT4", "CT5","CT2", "CT1", "CT3", "CT2", "CT4", "CT2", "CT5", "CT4"};

        for (Node n : mapGraph_xs.vertices())
            graph.addVertex(n);

        Node node1;
        Node node2;
        int totalDist = 0;
        for (int i = 0; i < edges.length; i += 2) {
            node1 = Utils.getNodeFromLabel(mapGraph_xs, edges[i]);
            node2 = Utils.getNodeFromLabel(mapGraph_xs, edges[i + 1]);
            totalDist += mapGraph_xs.edge(node1, node2).getWeight().getDistance();
            graph.addEdge(node1, node2, mapGraph_xs.edge(node1, node2).getWeight());
        }

        MST<Node, Weight> expected = new MST<>(graph, totalDist);

        MST<Node, Weight> result = USEI04.minCostNetwork(mapGraph_xs);

        assertEquals(expected.getGraph().numVertices(), result.getGraph().numVertices());
        assertEquals(expected.getGraph().edges(), result.getGraph().edges());
        assertEquals(expected.getTotalDistance(), result.getTotalDistance());
        assertEquals(expected, result);
    }




    @Test
    void mapGraph_small() {

        Graph<Node, Weight> graph = new MatrixGraph<>(false);

        String[] edges = {"CT1", "CT12", "CT1", "CT17", "CT1", "CT6", "CT2", "CT7", "CT2", "CT8", "CT3", "CT15", "CT3", "CT16", "CT3", "CT12", "CT15", "CT3", "CT16", "CT3", "CT16", "CT4", "CT12", "CT1", "CT12", "CT3", "CT7", "CT2", "CT7", "CT14", "CT8", "CT2", "CT13", "CT14", "CT13", "CT10", "CT14", "CT7", "CT14", "CT13", "CT11", "CT5", "CT5", "CT11", "CT5", "CT9", "CT9", "CT5", "CT9", "CT17", "CT4", "CT16", "CT17", "CT1", "CT17", "CT9", "CT6", "CT1", "CT6", "CT10", "CT10", "CT13", "CT10", "CT6"};

        for (Node n : mapGraph_small.vertices())
            graph.addVertex(n);

        Node node1;
        Node node2;
        int totalDist = 0;
        for (int i = 0; i < edges.length; i += 2) {
            node1 = Utils.getNodeFromLabel(mapGraph_small, edges[i]);
            node2 = Utils.getNodeFromLabel(mapGraph_small, edges[i + 1]);
            totalDist += mapGraph_small.edge(node1, node2).getWeight().getDistance();
            graph.addEdge(node1, node2, mapGraph_small.edge(node1, node2).getWeight());
        }

        MST<Node, Weight> expected = new MST<>(graph, totalDist);

        MST<Node, Weight> result = USEI04.minCostNetwork(mapGraph_small);

        assertEquals(expected.getGraph().numVertices(), result.getGraph().numVertices());
        assertEquals(expected.getGraph().edges(), result.getGraph().edges());
        assertEquals(expected.getTotalDistance(), result.getTotalDistance());
        assertEquals(expected, result);
    }

    @Test
    void matrixGraph_small() {

        Graph<Node, Weight> graph = new MatrixGraph<>(false);

        String[] edges = {"CT1", "CT12", "CT1", "CT17", "CT1", "CT6", "CT2", "CT7", "CT2", "CT8", "CT3", "CT15", "CT3", "CT16", "CT3", "CT12", "CT15", "CT3", "CT16", "CT3", "CT16", "CT4", "CT12", "CT1", "CT12", "CT3", "CT7", "CT2", "CT7", "CT14", "CT8", "CT2", "CT13", "CT14", "CT13", "CT10", "CT14", "CT7", "CT14", "CT13", "CT11", "CT5", "CT5", "CT11", "CT5", "CT9", "CT9", "CT5", "CT9", "CT17", "CT4", "CT16", "CT17", "CT1", "CT17", "CT9", "CT6", "CT1", "CT6", "CT10", "CT10", "CT13", "CT10", "CT6"};

        for (Node n : matrixGraph_small.vertices())
            graph.addVertex(n);

        Node node1;
        Node node2;
        int totalDist = 0;
        for (int i = 0; i < edges.length; i += 2) {
            node1 = Utils.getNodeFromLabel(matrixGraph_small, edges[i]);
            node2 = Utils.getNodeFromLabel(matrixGraph_small, edges[i + 1]);
            totalDist += matrixGraph_small.edge(node1, node2).getWeight().getDistance();
            graph.addEdge(node1, node2, matrixGraph_small.edge(node1, node2).getWeight());
        }

        MST<Node, Weight> expected = new MST<>(graph, totalDist);

        MST<Node, Weight> result = USEI04.minCostNetwork(matrixGraph_small);

        assertEquals(expected.getGraph().numVertices(), result.getGraph().numVertices());
        assertEquals(expected.getGraph().edges(), result.getGraph().edges());
        assertEquals(expected.getTotalDistance(), result.getTotalDistance());
        assertEquals(expected, result);
    }

    @Test
    void mapGraphBig() {

        int verticesExpected = 323;
        int egdesExpected = 1566;

        assertEquals(verticesExpected, mapGraph_big.numVertices());
        assertEquals(egdesExpected, mapGraph_big.edges().size());
    }

    @Test
    void matrixGraphBig() {

        int verticesExpected = 323;
        int egdesExpected = 1566;

        assertEquals(verticesExpected, mapGraph_big.numVertices());
        assertEquals(egdesExpected, mapGraph_big.edges().size());
    }
}