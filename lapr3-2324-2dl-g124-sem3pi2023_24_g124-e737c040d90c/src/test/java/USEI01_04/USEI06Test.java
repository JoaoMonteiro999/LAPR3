package USEI01_04;

import USEI01_04.graph.Graph;
import USEI01_04.graph.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

class USEI06Test {

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

        Utils.getNHubs(matrixGraph_small,USEI02.hubsLocation(matrixGraph_small),3);
        Utils.getNHubs(matrixGraph_xs,USEI02.hubsLocation(matrixGraph_xs),3);
    }

    /**
     * From CT1 to CT2
     */
    @Test
    void normalTest1() {
        ArrayList<Route<Node>> res = USEI06.routesToHub( "CT1", "CT2", 1_200_000,120,matrixGraph_xs);

        ArrayList<Route<Node>> resExpected = new ArrayList<Route<Node>>();
        Node n1 = new Node("CT1",new Coords(40.6389,-8.6553));
        Node n2 = new Node("CT2",new Coords(38.0333,-7.8833));

        LinkedList<POI<Node>> nodes = new LinkedList<POI<Node>>();
        POI<Node> poi1 = new POI<>(n1,false,0);

        POI<Node> poi2 = new POI<>(n2,false,0);
        nodes.add(poi1);
        nodes.add(poi2);
        Route<Node> r1 = new Route<Node>(nodes,0,0);

        resExpected.add(r1);
        assertEquals(resExpected, res);
    }

    /**
     * From CT1 to CT5
     */
    @Test
    void normalTest2() {
        ArrayList<Route<Node>> res = USEI06.routesToHub( "CT1", "CT5", 1_200_000,120,matrixGraph_xs);

        ArrayList<Route<Node>> resExpected = new ArrayList<Route<Node>>();
        Node n1 = new Node("CT1",new Coords(40.6389,-8.6553));
        Node n2 = new Node("CT2",new Coords(38.0333,-7.8833));
        Node n4 = new Node("CT4",new Coords(41.5333,-8.4167));
        Node n5 = new Node("CT5",new Coords(39.823,-7.4931));

        LinkedList<POI<Node>> nodes = new LinkedList<POI<Node>>();
        POI<Node> poi1 = new POI<>(n1,false,0);
        POI<Node> poi2 = new POI<>(n2,false,0);
        POI<Node> poi3 = new POI<>(n4,false,0);
        POI<Node> poi4 = new POI<>(n5,false,0);

        nodes.add(poi1);
        nodes.add(poi2);
        nodes.add(poi3);
        nodes.add(poi4);

        Route<Node> r1 = new Route<Node>(nodes,0,0);

        resExpected.add(r1);
        assertEquals(resExpected, res);
    }

    /**
     * From CT4 to CT5
     */
    @Test
    void normalTest3() {
        ArrayList<Route<Node>> res = USEI06.routesToHub( "CT4", "CT5", 1_200_000,120,matrixGraph_xs);

        ArrayList<Route<Node>> resExpected = new ArrayList<Route<Node>>();
        Node n4 = new Node("CT4",new Coords(41.5333,-8.4167));
        Node n5 = new Node("CT5",new Coords(39.823,-7.4931));

        LinkedList<POI<Node>> nodes = new LinkedList<POI<Node>>();
        POI<Node> poi3 = new POI<>(n4,false,0);
        POI<Node> poi4 = new POI<>(n5,false,0);

        nodes.add(poi3);
        nodes.add(poi4);

        Route<Node> r1 = new Route<Node>(nodes,0,0);

        resExpected.add(r1);
        assertEquals(resExpected, res);
    }

    /**
     * Test with null graph
     */
    @Test
    void testNullGraph() {
        ArrayList<Route<Node>> res = USEI06.routesToHub( "CT4", "CT5", 1_200_000,120,null);
        assertNull(res);
    }
    /**
     * Test with 0 autonomy
     */
    @Test
    void testAutonomy0() {
        ArrayList<Route<Node>> res = USEI06.routesToHub( "CT4", "CT5", 0,120,matrixGraph_xs);
        assertNull(res);
    }
    /**
     * Test with 0 average speed
     */
    @Test
    void testAverageSpeed0() {
        ArrayList<Route<Node>> res = USEI06.routesToHub( "CT4", "CT5", 1234,0,matrixGraph_xs);
        assertNull(res);
    }

    /**
     * Test size of list with big file 1
     */
    @Test
    void bigTest1() {
        ArrayList<Route<Node>> res1 = USEI06.routesToHub( "CT10", "CT15", 1_200_000,120,matrixGraph_small);

        int res = res1.size();

        assertEquals(505, res);
    }

    /**
     * Test size of list with big file 2
     */
    @Test
    void bigTest2() {
        ArrayList<Route<Node>> res1 = USEI06.routesToHub( "CT14", "CT15", 1_200_000,120,matrixGraph_small);
        int res = res1.size();

        assertEquals(68, res);
    }
}