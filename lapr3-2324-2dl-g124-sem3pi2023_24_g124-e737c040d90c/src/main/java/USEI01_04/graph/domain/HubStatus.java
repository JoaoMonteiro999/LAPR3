package USEI01_04.graph.domain;

import java.util.Objects;

public class HubStatus<V> {
    private V element;
    private int influence;
    private int proximity;
    private int centrality;

    public HubStatus(V element, int influence, int proximity, int centrality) {
        this.element = element;
        this.influence = influence;
        this.proximity = proximity;
        this.centrality = centrality;
    }

    public V getElement() {
        return element;
    }

    public int getInfluence() {
        return influence;
    }

    public int getProximity() {
        return proximity;
    }

    public int getCentrality() {
        return centrality;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HubStatus<?> hubStatus = (HubStatus<?>) o;
        return Objects.equals(element, hubStatus.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element, influence, proximity, centrality);
    }

    @Override
    public String toString() {
        return "HubStatus{" +
                "element=" + element +
                ", influence=" + influence +
                ", proximity=" + proximity +
                ", centrality=" + centrality +
                '}';
    }
}
