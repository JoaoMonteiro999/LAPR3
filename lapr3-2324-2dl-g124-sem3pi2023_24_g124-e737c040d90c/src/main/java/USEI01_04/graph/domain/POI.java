package USEI01_04.graph.domain;

import java.util.Objects;

public class POI<V> {
    private V element;
    private boolean charged;
    private int distanceToNextPOI;

    public POI(V element, boolean charged, int distanceToNextPOI) {
        this.element = element;
        this.charged = charged;
        this.distanceToNextPOI = distanceToNextPOI;
    }

    public V getElement() {
        return element;
    }

    public boolean isCharged() {
        return charged;
    }

    public int getDistanceToNextPOI() {
        return distanceToNextPOI;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        POI<?> poi = (POI<?>) o;
        return Objects.equals(element, poi.element);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element, charged, distanceToNextPOI);
    }

    @Override
    public String toString() {
        return  "element=" + element +
                ", charged=" + charged +
                ", distanceToNextPOI=" + distanceToNextPOI +
                '}';
    }
}
