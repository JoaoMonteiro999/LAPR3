package USEI01_04;

import USEI01_04.graph.Algorithms;
import USEI01_04.graph.Edge;
import USEI01_04.graph.Graph;
import USEI01_04.graph.domain.*;

import java.util.LinkedList;

public class USEI03 {
    public static Route<Node> autonomyCheck(Graph<Node, Weight> graph, int autonomy) {

        if (graph == null) return null;
        if (autonomy <= 0) return new Route<>(new LinkedList<>(),0,0);

        Graph<Node, Weight> graphMinDist = Algorithms.minDistGraph(graph, Weight::compare, Weight::sum);

        Weight nullWeight = new Weight(0, "m");
        Weight maxWeight = nullWeight;
        Node vOrig = null;
        Node vDest = null;

        for (Edge<Node, Weight> e : graphMinDist.edges()) {
            if (Weight.compare(e.getWeight(), maxWeight) == 1) {
                maxWeight = e.getWeight();
                vOrig = e.getVOrig();
                vDest = e.getVDest();
            }
        }

        Graph<Node, Weight> copy_graph = graph.clone();

        for (Edge<Node, Weight> edge : copy_graph.edges()) {
            if (Weight.compare(edge.getWeight(), new Weight(autonomy, "m")) == 1)
                copy_graph.removeEdge(edge.getVOrig(), edge.getVDest());
        }

        LinkedList<Node> res = new LinkedList<>();
        Algorithms.shortestPath(copy_graph, vOrig, vDest, Weight::compare, Weight::sum, nullWeight, res);

        if (res.size() == 0)
            return new Route<>(null, 0, 0);

        LinkedList<POI<Node>> poiList = new LinkedList<>();
        int totalChargingTimes = 0;
        int totalDistance = 0;
        int autonomyNow = autonomy;
        int distance_edge;
        boolean charged;

        for (int i = 0; i < res.size() - 1; i++) {
            distance_edge = copy_graph.edge(res.get(i), res.get(i + 1)).getWeight().getDistance();
            totalDistance += distance_edge;
            charged = false;

            if (autonomyNow < distance_edge) {
                totalChargingTimes++;
                autonomyNow = autonomy;
                charged = true;
            }
            autonomyNow -= distance_edge;
            poiList.add(new POI<>(res.get(i), charged, distance_edge));
        }
        poiList.add(new POI<>(res.get(res.size() - 1), false, 0));

        return new Route<>(poiList, totalChargingTimes, totalDistance);
    }
}
