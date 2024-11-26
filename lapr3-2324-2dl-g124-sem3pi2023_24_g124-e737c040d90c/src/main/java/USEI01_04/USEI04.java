package USEI01_04;

import USEI01_04.graph.Algorithms;
import USEI01_04.graph.Edge;
import USEI01_04.graph.Graph;
import USEI01_04.graph.domain.MST;
import USEI01_04.graph.domain.Node;
import USEI01_04.graph.domain.Weight;
import USEI01_04.graph.matrix.MatrixGraph;

public class USEI04 {
    public static MST<Node, Weight> minCostNetwork(Graph<Node, Weight> graph) {

        Graph<Node, Weight> mst = new MatrixGraph<>(false);

        if (graph == null) return null;
        if (graph.numVertices() == 0) return new MST<>(mst, 0);

        Algorithms.kruskal(graph, Weight::compare, mst);

        int totalDist = 0;
        for (Edge<Node, Weight> e: mst.edges())
            totalDist += e.getWeight().getDistance();

        return new MST<>(mst, totalDist);
    }
}
