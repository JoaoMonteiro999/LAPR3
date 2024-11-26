package USEI01_04;

import USEI01_04.graph.Graph;
import USEI01_04.graph.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class USEI03Test {
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
    void testMapGraphXs() {

        LinkedList<POI<Node>> poiList = new LinkedList<>();

        String[] labelsList = {"CT3", "CT2", "CT4", "CT5"};

        Node node1;
        Node node2 = Utils.getNodeFromLabel(mapGraph_xs, labelsList[0]);

        for (int i = 1; i < labelsList.length; i++) {
            node1 = node2;
            node2 = Utils.getNodeFromLabel(mapGraph_xs, labelsList[i]);

            poiList.add(new POI<>(node1, false, mapGraph_xs.edge(node1, node2).getWeight().getDistance()));
        }
        node2 = Utils.getNodeFromLabel(mapGraph_xs, labelsList[labelsList.length - 1]);

        poiList.add(new POI<>(node2, false, 0));

        Route<Node> expected = new Route<>(poiList, 3, 610255);

        Route<Node> result = USEI03.autonomyCheck(mapGraph_xs, 180000);

        assertEquals(expected.getRoute().size(), result.getRoute().size());
        assertEquals(expected.getOrigin(), result.getOrigin());
        assertEquals(expected.getDest(), result.getDest());
        assertEquals(expected, result);
    }

    @Test
    void testMatrixGraphXs() {

        LinkedList<POI<Node>> poiList = new LinkedList<>();

        String[] labelsList = {"CT3", "CT2", "CT4", "CT5"};

        Node node1;
        Node node2 = Utils.getNodeFromLabel(matrixGraph_xs, labelsList[0]);

        for (int i = 1; i < labelsList.length; i++) {
            node1 = node2;
            node2 = Utils.getNodeFromLabel(matrixGraph_xs, labelsList[i]);

            poiList.add(new POI<>(node1, false, matrixGraph_xs.edge(node1, node2).getWeight().getDistance()));
        }
        node2 = Utils.getNodeFromLabel(matrixGraph_xs, labelsList[labelsList.length - 1]);

        poiList.add(new POI<>(node2, false, 0));

        Route<Node> expected = new Route<>(poiList, 3, 610255);

        Route<Node> result = USEI03.autonomyCheck(matrixGraph_xs, 180000);

        assertEquals(expected.getRoute().size(), result.getRoute().size());
        assertEquals(expected.getOrigin(), result.getOrigin());
        assertEquals(expected.getDest(), result.getDest());
        assertEquals(expected, result);
    }

    @Test
    void testMatrixGraphSmall() {

        LinkedList<POI<Node>> poiList = new LinkedList<>();

        String[] labelsList = {"CT15", "CT12", "CT1", "CT10", "CT13", "CT7", "CT2", "CT8"};

        Node node1;
        Node node2 = Utils.getNodeFromLabel(matrixGraph_small, labelsList[0]);

        for (int i = 1; i < labelsList.length; i++) {
            node1 = node2;
            node2 = Utils.getNodeFromLabel(matrixGraph_small, labelsList[i]);

            poiList.add(new POI<>(node1, false, matrixGraph_small.edge(node1, node2).getWeight().getDistance()));
        }
        node2 = Utils.getNodeFromLabel(matrixGraph_small, labelsList[labelsList.length - 1]);

        poiList.add(new POI<>(node2, false, 0));

        Route<Node> expected = new Route<>(poiList, 3, 610255);

        Route<Node> result = USEI03.autonomyCheck(matrixGraph_small, 180000);

        assertEquals(expected.getRoute().size(), result.getRoute().size());
        assertEquals(expected.getOrigin(), result.getOrigin());
        assertEquals(expected.getDest(), result.getDest());
        assertEquals(expected, result);
    }

    @Test
    void testMapGraphSmall() {

        LinkedList<POI<Node>> poiList = new LinkedList<>();

        String[] labelsList = {"CT15", "CT12", "CT1", "CT10", "CT13", "CT7", "CT2", "CT8"};

        Node node1;
        Node node2 = Utils.getNodeFromLabel(mapGraph_small, labelsList[0]);

        for (int i = 1; i < labelsList.length; i++) {
            node1 = node2;
            node2 = Utils.getNodeFromLabel(mapGraph_small, labelsList[i]);

            poiList.add(new POI<>(node1, false, mapGraph_small.edge(node1, node2).getWeight().getDistance()));
        }
        node2 = Utils.getNodeFromLabel(mapGraph_small, labelsList[labelsList.length - 1]);

        poiList.add(new POI<>(node2, false, 0));

        Route<Node> expected = new Route<>(poiList, 3, 610255);

        Route<Node> result = USEI03.autonomyCheck(mapGraph_small, 180000);

        assertEquals(expected.getRoute().size(), result.getRoute().size());
        assertEquals(expected.getOrigin(), result.getOrigin());
        assertEquals(expected.getDest(), result.getDest());
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