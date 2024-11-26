package USEI01_04;

import USEI01_04.graph.Graph;
import USEI01_04.graph.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class USEI08Test {

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
    void graphNull() {

        List<HubStatus<Node>> hubs = new ArrayList<>();
        hubs.add(new HubStatus<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT2")), 0, 0, 0));
        hubs.add(new HubStatus<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT3")), 0, 0, 0));
        hubs.add(new HubStatus<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT4")), 0, 0, 0));
        hubs.add(new HubStatus<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT5")), 0, 0, 0));

        Utils.getNHubs(mapGraph_xs, hubs, 4);

        MostHubsPath<Node> result = USEI07.maxHubs(null, mapGraph_xs.vertex(Utils.getKeyFromLabel("CT1")), LocalTime.of(9, 1), 1, 95, 5, 10);

        assertEquals(null, result);

    }


    @Test
    void nodeNull() {

        List<HubStatus<Node>> hubs = new ArrayList<>();
        hubs.add(new HubStatus<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT2")), 0, 0, 0));
        hubs.add(new HubStatus<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT3")), 0, 0, 0));
        hubs.add(new HubStatus<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT4")), 0, 0, 0));
        hubs.add(new HubStatus<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT5")), 0, 0, 0));

        Utils.getNHubs(mapGraph_xs, hubs, 4);

        MostHubsPath<Node> result = USEI07.maxHubs(matrixGraph_xs, null, LocalTime.of(9, 1), 1, 95, 5, 10);

        assertEquals(null, result);

    }


    @Test
    void velocityZero() {

        List<HubStatus<Node>> hubs = new ArrayList<>();
        hubs.add(new HubStatus<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT2")), 0, 0, 0));
        hubs.add(new HubStatus<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT3")), 0, 0, 0));
        hubs.add(new HubStatus<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT4")), 0, 0, 0));
        hubs.add(new HubStatus<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT5")), 0, 0, 0));

        Utils.getNHubs(mapGraph_xs, hubs, 4);

        MostHubsPath<Node> result = USEI07.maxHubs(matrixGraph_xs, null, LocalTime.of(9, 1), 0, 95, 5, 10);

        assertEquals(null, result);

    }


    @Test
    void autonomyZero() {

        List<HubStatus<Node>> hubs = new ArrayList<>();
        hubs.add(new HubStatus<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT2")), 0, 0, 0));
        hubs.add(new HubStatus<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT3")), 0, 0, 0));
        hubs.add(new HubStatus<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT4")), 0, 0, 0));
        hubs.add(new HubStatus<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT5")), 0, 0, 0));

        Utils.getNHubs(mapGraph_xs, hubs, 4);

        MostHubsPath<Node> result = USEI07.maxHubs(matrixGraph_xs, null, LocalTime.of(9, 1), 1, 0, 5, 10);

        assertEquals(null, result);

    }


    @Test
    void mapGraphXs() {

        List<HubStatus<Node>> hubs = new ArrayList<>();
        hubs.add(new HubStatus<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT2")), 0, 0, 0));
        hubs.add(new HubStatus<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT3")), 0, 0, 0));
        hubs.add(new HubStatus<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT4")), 0, 0, 0));
        hubs.add(new HubStatus<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT5")), 0, 0, 0));

        Utils.getNHubs(mapGraph_xs, hubs, 4);

        MostHubsPath<Node> result = USEI07.maxHubs(mapGraph_xs, mapGraph_xs.vertex(Utils.getKeyFromLabel("CT1")), LocalTime.of(9, 1), 1, 95, 5, 10);

        LinkedList<POI_2<Node>> expectedList = new LinkedList<>();

        expectedList.add(new POI_2<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT1")), false, LocalTime.of(9, 1), LocalTime.of(9, 1)));
        expectedList.add(new POI_2<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT2")), true, LocalTime.of(9, 1), LocalTime.of(9, 11)));
        expectedList.add(new POI_2<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT4")), true, LocalTime.of(9, 11), LocalTime.of(9, 21)));
        expectedList.add(new POI_2<>(mapGraph_xs.vertex(Utils.getKeyFromLabel("CT5")), true, LocalTime.of(9, 21), LocalTime.of(9, 31)));

        MostHubsPath<Node> expected = new MostHubsPath<>(expectedList, 8, 0, 30, 0, 0, 30);

        assertEquals(expected, result);

    }


    @Test
    void matrixGraphXs() {

        List<HubStatus<Node>> hubs = new ArrayList<>();
        hubs.add(new HubStatus<>(matrixGraph_xs.vertex(Utils.getKeyFromLabel("CT2")), 0, 0, 0));
        hubs.add(new HubStatus<>(matrixGraph_xs.vertex(Utils.getKeyFromLabel("CT3")), 0, 0, 0));
        hubs.add(new HubStatus<>(matrixGraph_xs.vertex(Utils.getKeyFromLabel("CT4")), 0, 0, 0));
        hubs.add(new HubStatus<>(matrixGraph_xs.vertex(Utils.getKeyFromLabel("CT5")), 0, 0, 0));

        Utils.getNHubs(matrixGraph_xs, hubs, 4);

        MostHubsPath<Node> result = USEI07.maxHubs(matrixGraph_xs, matrixGraph_xs.vertex(Utils.getKeyFromLabel("CT1")), LocalTime.of(9, 1), 1, 95, 5, 10);

        LinkedList<POI_2<Node>> expectedList = new LinkedList<>();

        expectedList.add(new POI_2<>(matrixGraph_xs.vertex(Utils.getKeyFromLabel("CT1")), false, LocalTime.of(9, 1), LocalTime.of(9, 1)));
        expectedList.add(new POI_2<>(matrixGraph_xs.vertex(Utils.getKeyFromLabel("CT2")), true, LocalTime.of(9, 1), LocalTime.of(9, 11)));
        expectedList.add(new POI_2<>(matrixGraph_xs.vertex(Utils.getKeyFromLabel("CT4")), true, LocalTime.of(9, 11), LocalTime.of(9, 21)));
        expectedList.add(new POI_2<>(matrixGraph_xs.vertex(Utils.getKeyFromLabel("CT5")), true, LocalTime.of(9, 21), LocalTime.of(9, 31)));

        MostHubsPath<Node> expected = new MostHubsPath<>(expectedList, 8, 0, 30, 0, 0, 30);

        assertEquals(expected, result);

    }


    @Test
    void mapGraphSmall() {

        List<HubStatus<Node>> hubs = new ArrayList<>();
        hubs.add(new HubStatus<>(mapGraph_small.vertex(Utils.getKeyFromLabel("CT2")), 0, 0, 0));
        hubs.add(new HubStatus<>(mapGraph_small.vertex(Utils.getKeyFromLabel("CT3")), 0, 0, 0));
        hubs.add(new HubStatus<>(mapGraph_small.vertex(Utils.getKeyFromLabel("CT16")), 0, 0, 0));
        hubs.add(new HubStatus<>(mapGraph_small.vertex(Utils.getKeyFromLabel("CT10")), 0, 0, 0));

        Utils.getNHubs(mapGraph_small, hubs, 4);

        MostHubsPath<Node> result = USEI07.maxHubs(mapGraph_small, mapGraph_small.vertex(Utils.getKeyFromLabel("CT1")), LocalTime.of(9, 1), 200, 200000, 5, 10);

        LinkedList<POI_2<Node>> expectedList = new LinkedList<>();

        expectedList.add(new POI_2<>(mapGraph_small.vertex(Utils.getKeyFromLabel("CT1")), false, LocalTime.of(9, 19), LocalTime.of(9, 19)));
        expectedList.add(new POI_2<>(mapGraph_small.vertex(Utils.getKeyFromLabel("CT6")), false, LocalTime.of(9, 34), LocalTime.of(9, 34)));
        expectedList.add(new POI_2<>(mapGraph_small.vertex(Utils.getKeyFromLabel("CT3")), true, LocalTime.of(9, 54), LocalTime.of(10, 4)));
        expectedList.add(new POI_2<>(mapGraph_small.vertex(Utils.getKeyFromLabel("CT5")), false, LocalTime.of(10, 27), LocalTime.of(10, 27)));
        expectedList.add(new POI_2<>(mapGraph_small.vertex(Utils.getKeyFromLabel("CT15")), false, LocalTime.of(10, 54), LocalTime.of(10, 54)));
        expectedList.add(new POI_2<>(mapGraph_small.vertex(Utils.getKeyFromLabel("CT16")), true, LocalTime.of(11, 14), LocalTime.of(11, 29)));
        expectedList.add(new POI_2<>(mapGraph_small.vertex(Utils.getKeyFromLabel("CT17")), false, LocalTime.of(11, 48), LocalTime.of(11, 48)));
        expectedList.add(new POI_2<>(mapGraph_small.vertex(Utils.getKeyFromLabel("CT9")), false, LocalTime.of(12, 14), LocalTime.of(12, 14)));
        expectedList.add(new POI_2<>(mapGraph_small.vertex(Utils.getKeyFromLabel("CT10")), true, LocalTime.of(12, 47), LocalTime.of(12, 57)));
        expectedList.add(new POI_2<>(mapGraph_small.vertex(Utils.getKeyFromLabel("CT7")), false, LocalTime.of(13, 16), LocalTime.of(13, 16)));
        expectedList.add(new POI_2<>(mapGraph_small.vertex(Utils.getKeyFromLabel("CT2")), true, LocalTime.of(13, 21), LocalTime.of(13, 31)));

        MostHubsPath<Node> expected = new MostHubsPath<>(expectedList, 718065, 4, 270, 20, 210, 40);

        LinkedList<POI_2<Node>> d = result.getPath();

        LinkedList<POI_2<Node>> res = result.getPath();

        assertEquals(expected, result);

    }

}