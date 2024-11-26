package USEI01_04;

import USEI01_04.graph.Algorithms;
import USEI01_04.graph.Graph;
import USEI01_04.graph.domain.*;

import java.util.*;

public class USEI02 {
    public static List<HubStatus<Node>> hubsLocation(Graph<Node, Weight> graph) {
        List<HubStatus<Node>> listStatus = new ArrayList<>();

        if (graph == null) return null;
        if (graph.numVertices() == 0) return listStatus;

        LinkedList<Node> res;
        Weight zero = new Weight(0, "m");

        int[] influence = new int[graph.numVertices()];
        int[] proximity = new int[graph.numVertices()];
        int[] centrality = new int[graph.numVertices()];

        for (Node n1: graph.vertices()) {
            for (Node n2: graph.vertices()) {
                if (n1.equals(n2))
                    continue;
                res = new LinkedList<>();
                Algorithms.shortestPath(graph.clone(), n1, n2, Weight::compare, Weight::sum, zero, res);

                if (res.size() == 0)
                    return new ArrayList<>();

                for (int i = 0; i < res.size() - 1; i++) {
                    centrality[graph.key(res.get(i))]++;
                    proximity[graph.key(n1)] += graph.edge(res.get(i), res.get(i + 1)).getWeight().getDistance();
                }
                centrality[graph.key(res.get(res.size() - 1))]++;
            }
            influence[graph.key(n1)] = graph.outDegree(n1);
        }

        for (int i = 0; i < graph.numVertices(); i++)
            listStatus.add(new HubStatus<>(graph.vertex(i), influence[i], proximity[i], centrality[i]));

        listStatus.sort(new InfluenceComparator());
        listStatus.sort(new CentralityComparator());
        Collections.reverse(listStatus);

        return listStatus;
    }
}
