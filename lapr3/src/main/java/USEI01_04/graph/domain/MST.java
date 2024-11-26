package USEI01_04.graph.domain;

import USEI01_04.graph.Graph;

import java.util.Objects;

public class MST<V, E> {
    private Graph<V, E> graph;
    private int totalDistance;

    public MST(Graph<V, E> graph, int totalDistance) {
        this.graph = graph;
        this.totalDistance = totalDistance;
    }

    public Graph<V, E> getGraph() {
        return graph;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MST<?, ?> mst = (MST<?, ?>) o;
        return Objects.equals(graph, mst.graph);
    }

    @Override
    public int hashCode() {
        return Objects.hash(graph, totalDistance);
    }

    @Override
    public String toString() {
        return "MST{" +
                "graph=" + graph +
                ", totalDistance=" + totalDistance +
                '}';
    }
}
