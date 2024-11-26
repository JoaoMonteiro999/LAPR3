package USEI01_04.graph.domain;

import java.time.LocalTime;
import java.util.Objects;

public class POI_2<V> {
    private V element;
    private boolean hub;
    private LocalTime arrivalTime;
    private LocalTime departureTime;

    public POI_2(V element, boolean hub, LocalTime arrivalTime, LocalTime departureTime) {
        this.element = element;
        this.hub = hub;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
    }

    public V getElement() {
        return element;
    }

    public boolean isHub() {
        return hub;
    }

    public LocalTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalTime departureTime) {
        this.departureTime = departureTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        POI_2<?> poi2 = (POI_2<?>) o;
        return hub == poi2.hub && Objects.equals(element, poi2.element) && Objects.equals(arrivalTime, poi2.arrivalTime) && Objects.equals(departureTime, poi2.departureTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(element, hub, arrivalTime, departureTime);
    }

    @Override
    public String toString() {
        return "\n"+element +
                ", Hub Status : " + hub +
                ", Tempo de Chegada : " + arrivalTime +
                ", Tempo de Partida : " + departureTime;
    }
}
