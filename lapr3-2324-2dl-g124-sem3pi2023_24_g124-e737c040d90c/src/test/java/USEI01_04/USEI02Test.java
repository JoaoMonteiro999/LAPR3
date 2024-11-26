package USEI01_04;

import USEI01_04.graph.Graph;
import USEI01_04.graph.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class USEI02Test {
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
    void testMatrixGraphXs() {
        List<HubStatus<Node>> expected = new ArrayList<>();

        String[] labelsList = {"CT2", "CT4", "CT5", "CT3", "CT1"};
        int[] influenceList = {3, 2, 1, 1, 1};
        int[] proximityList = {13, 16, 28, 19, 16};
        int[] centralityList = {18, 14, 8, 8, 8};

        for (int i = 0; i < labelsList.length; i++)
            expected.add(new HubStatus<>(Utils.getNodeFromLabel(matrixGraph_xs, labelsList[i]), influenceList[i], proximityList[i], centralityList[i]));

        List<HubStatus<Node>> result = USEI02.hubsLocation(matrixGraph_xs);

        assertEquals(expected.get(0), result.get(0));
        assertEquals(expected.get(expected.size() - 1), result.get(result.size() - 1));
        assertEquals(expected.size(), result.size());
        assertEquals(expected, result);
    }

    @Test
    void testMapGraphXs() {
        List<HubStatus<Node>> expected = new ArrayList<>();

        String[] labelsList = {"CT2", "CT4", "CT5", "CT3", "CT1"};
        int[] influenceList = {3, 2, 1, 1, 1};
        int[] proximityList = {13, 16, 28, 19, 16};
        int[] centralityList = {18, 14, 8, 8, 8};

        for (int i = 0; i < labelsList.length; i++)
            expected.add(new HubStatus<>(Utils.getNodeFromLabel(mapGraph_xs, labelsList[i]), influenceList[i], proximityList[i], centralityList[i]));

        List<HubStatus<Node>> result = USEI02.hubsLocation(mapGraph_xs);

        assertEquals(expected.get(0), result.get(0));
        assertEquals(expected.get(expected.size() - 1), result.get(result.size() - 1));
        assertEquals(expected.size(), result.size());
        assertEquals(expected, result);
    }

    @Test
    void testMatrixGraphSmall() {
        List<HubStatus<Node>> expected = new ArrayList<>();

        String[] labelsList = {"CT10", "CT13", "CT1", "CT17", "CT6", "CT12", "CT5", "CT16", "CT11", "CT2", "CT14", "CT7", "CT9", "CT3", "CT4", "CT8", "CT15"};
        int[] influenceList = {5, 4, 4, 5, 4, 4, 5, 5, 4, 4, 4, 3, 4, 4, 3, 2, 2,};
        int[] proximityList = {2979452, 3335004, 3005527, 2991122, 2826782, 3437328, 3189871, 3536735, 3457920, 4689869, 4244871, 4455602, 3384411, 3845307, 4901230, 6499026, 4264395};
        int[] centralityList = {106, 100, 84, 76, 76, 72, 66, 62, 58, 50, 46, 46, 40, 40, 32, 32, 32};

        for (int i = 0; i < labelsList.length; i++)
            expected.add(new HubStatus<>(Utils.getNodeFromLabel(matrixGraph_small, labelsList[i]), influenceList[i], proximityList[i], centralityList[i]));

        List<HubStatus<Node>> result = USEI02.hubsLocation(matrixGraph_small);

        assertEquals(expected.get(0), result.get(0));
        assertEquals(expected.get(expected.size() - 1), result.get(result.size() - 1));
        assertEquals(expected.size(), result.size());
        assertEquals(expected, result);
    }

    @Test
    void testMapGraphSmall() {
        List<HubStatus<Node>> expected = new ArrayList<>();

        String[] labelsList = {"CT10", "CT13", "CT1", "CT17", "CT6", "CT12", "CT5", "CT16", "CT11", "CT2", "CT14", "CT7", "CT9", "CT3", "CT4", "CT8", "CT15"};
        int[] influenceList = {5, 4, 4, 5, 4, 4, 5, 5, 4, 4, 4, 3, 4, 4, 3, 2, 2,};
        int[] proximityList = {2979452, 3335004, 3005527, 2991122, 2826782, 3437328, 3189871, 3536735, 3457920, 4689869, 4244871, 4455602, 3384411, 3845307, 4901230, 6499026, 4264395};
        int[] centralityList = {106, 100, 84, 76, 76, 72, 66, 62, 58, 50, 46, 46, 40, 40, 32, 32, 32};

        for (int i = 0; i < labelsList.length; i++)
            expected.add(new HubStatus<>(Utils.getNodeFromLabel(mapGraph_small, labelsList[i]), influenceList[i], proximityList[i], centralityList[i]));

        List<HubStatus<Node>> result = USEI02.hubsLocation(mapGraph_small);

        assertEquals(expected.get(0), result.get(0));
        assertEquals(expected.get(expected.size() - 1), result.get(result.size() - 1));
        assertEquals(expected.size(), result.size());
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