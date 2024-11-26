package USEI01_04;

import USEI01_04.graph.Graph;
import USEI01_04.graph.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class USEI09Test {

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
    void testSmall(){
        Node n1 = new Node("CT1",new Coords(40.6389,-8.6553));
        HubStatus<Node> hub1= new HubStatus<>(n1,1,1,1);

        Node n2 = new Node("CT2",new Coords(38.0333,-7.8833));
        HubStatus<Node> hub2= new HubStatus<>(n2,1,1,1);

        Node n3 = new Node("CT3",new Coords(41.5333,-8.4167));
        HubStatus<Node> hub3= new HubStatus<>(n3,1,1,1);

        Node n4 = new Node("CT4",new Coords(41.8,-6.75));
        HubStatus<Node> hub4= new HubStatus<>(n4,1,1,1);

        List<HubStatus<Node>> hubs = new ArrayList<>();
        hubs.add(hub1);
        hubs.add(hub2);
        hubs.add(hub3);
        hubs.add(hub4);

        List<Cluster> result = USEI09.organizarClusters(matrixGraph_small,hubs);

        assertEquals(result.size(),hubs.size());

        assertEquals(result.get(0).getHub(),hubs.get(0));
        assertEquals(result.get(1).getHub(),hubs.get(1));





    }























}