package USEI01_04;

import USEI01_04.graph.Edge;
import USEI01_04.graph.Graph;
import USEI01_04.graph.domain.Coords;
import USEI01_04.graph.domain.Node;
import USEI01_04.graph.domain.Utils;
import USEI01_04.graph.domain.Weight;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class USEI01Test {
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
    void testNotNull() {
        assertNotNull(USEI01.matrixGraph(edges_files_big, vertices_files_big));
        assertNotNull(USEI01.mapGraph(edges_files_big, vertices_files_big));
    }

    @Test
    void matrixGraphXs() {

        ArrayList<String[]> data_vertices = Utils.readCSV(vertices_files_xs);
        ArrayList<String[]> data_edges = Utils.readCSV(edges_files_xs);

        int numVerticesExpected = data_vertices.size();
        int numEdgesExpected = data_edges.size() * 2;

        Graph<Node, Weight> result = matrixGraph_xs;

        Node node1;
        int key;
        for (String[] s : data_vertices) {
            node1 = new Node(s[0], new Coords(Utils.toDouble(s[1]), Utils.toDouble(s[2])));
            key = matrixGraph_xs.key(node1);
            assertEquals(node1, result.vertex(key));
        }

        Node node2;
        Edge<Node, Weight> edgeExpected;
        for (String[] s : data_edges) {
            node1 = Utils.getNodeFromLabel(matrixGraph_xs, s[0]);
            node2 = Utils.getNodeFromLabel(matrixGraph_xs, s[1]);
            edgeExpected = matrixGraph_xs.edge(node1, node2);
            assertEquals(edgeExpected, result.edge(node1, node2));
        }

        assertEquals(numVerticesExpected, result.numVertices());
        assertEquals(numEdgesExpected, result.edges().size());
    }

    @Test
    void mapGraphXs() {

        ArrayList<String[]> data_vertices = Utils.readCSV(vertices_files_xs);
        ArrayList<String[]> data_edges = Utils.readCSV(edges_files_xs);

        int numVerticesExpected = data_vertices.size();
        int numEdgesExpected = data_edges.size() * 2;

        Graph<Node, Weight> result = mapGraph_xs;

        Node node1;
        int key;
        for (String[] s : data_vertices) {
            node1 = new Node(s[0], new Coords(Utils.toDouble(s[1]), Utils.toDouble(s[2])));
            key = mapGraph_xs.key(node1);
            assertEquals(node1, result.vertex(key));
        }

        Node node2;
        Edge<Node, Weight> edgeExpected;
        for (String[] s : data_edges) {
            node1 = Utils.getNodeFromLabel(mapGraph_xs, s[0]);
            node2 = Utils.getNodeFromLabel(mapGraph_xs, s[1]);
            edgeExpected = mapGraph_xs.edge(node1, node2);
            assertEquals(edgeExpected, result.edge(node1, node2));
        }

        assertEquals(numVerticesExpected, result.numVertices());
        assertEquals(numEdgesExpected, result.edges().size());
    }

    @Test
    void mapGraphSmall() {

        ArrayList<String[]> data_vertices = Utils.readCSV(vertices_files_small);
        ArrayList<String[]> data_edges = Utils.readCSV(edges_files_small);

        int numVerticesExpected = data_vertices.size();
        int numEdgesExpected = data_edges.size() * 2;

        Graph<Node, Weight> result = mapGraph_small;

        Node node1;
        int key;
        for (String[] s : data_vertices) {
            node1 = new Node(s[0], new Coords(Utils.toDouble(s[1]), Utils.toDouble(s[2])));
            key = mapGraph_small.key(node1);
            assertEquals(node1, result.vertex(key));
        }

        Node node2;
        Edge<Node, Weight> edgeExpected;
        for (String[] s : data_edges) {
            node1 = Utils.getNodeFromLabel(mapGraph_small, s[0]);
            node2 = Utils.getNodeFromLabel(mapGraph_small, s[1]);
            edgeExpected = mapGraph_small.edge(node1, node2);
            assertEquals(edgeExpected, result.edge(node1, node2));
        }

        assertEquals(numVerticesExpected, result.numVertices());
        assertEquals(numEdgesExpected, result.edges().size());
    }

    @Test
    void matrixGraphSmall() {

        ArrayList<String[]> data_vertices = Utils.readCSV(vertices_files_small);
        ArrayList<String[]> data_edges = Utils.readCSV(edges_files_small);

        int numVerticesExpected = data_vertices.size();
        int numEdgesExpected = data_edges.size() * 2;

        Graph<Node, Weight> result = matrixGraph_small;

        Node node1;
        int key;
        for (String[] s : data_vertices) {
            node1 = new Node(s[0], new Coords(Utils.toDouble(s[1]), Utils.toDouble(s[2])));
            key = matrixGraph_small.key(node1);
            assertEquals(node1, result.vertex(key));
        }

        Node node2;
        Edge<Node, Weight> edgeExpected;
        for (String[] s : data_edges) {
            node1 = Utils.getNodeFromLabel(matrixGraph_small, s[0]);
            node2 = Utils.getNodeFromLabel(matrixGraph_small, s[1]);
            edgeExpected = matrixGraph_small.edge(node1, node2);
            assertEquals(edgeExpected, result.edge(node1, node2));
        }

        assertEquals(numVerticesExpected, result.numVertices());
        assertEquals(numEdgesExpected, result.edges().size());
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