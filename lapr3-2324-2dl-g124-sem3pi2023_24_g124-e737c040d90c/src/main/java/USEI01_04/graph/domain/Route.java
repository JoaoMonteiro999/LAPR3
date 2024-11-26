package USEI01_04.graph.domain;

import java.util.LinkedList;
import java.util.Objects;

public class Route<V> {
    private LinkedList<POI<V>> route;
    private int totalChargingTimes;
    private int totalDistance; //meters
    private float totalTime; //hours

    public Route(LinkedList<POI<V>> route, int totalChargingTimes, int totalDistance) {
        this.route = route;
        this.totalChargingTimes = totalChargingTimes;
        this.totalDistance = totalDistance;
    }
    public Route(LinkedList<POI<V>> route, int totalDistance, float totalTime) {
        this.route = route;
        this.totalDistance = totalDistance;
        this.totalTime = totalTime;
    }

    public LinkedList<POI<V>> getRoute() {
        return route;
    }

    public int getTotalChargingTimes() {
        return totalChargingTimes;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public POI<V> getOrigin() {
        return this.route.getFirst();
    }

    public POI<V> getDest() {
        return this.route.getLast();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route<?> route1 = (Route<?>) o;
        return Objects.equals(route, route1.route);
    }

    @Override
    public int hashCode() {
        return Objects.hash(route, totalChargingTimes, totalDistance);
    }

    @Override
    public String toString() {
        return "Route{" +
                "route=" + route +
                ", totalChargingTimes=" + totalChargingTimes +
                ", totalDistance=" + totalDistance +
                '}';
    }
}
