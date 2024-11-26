package USEI01_04.graph.domain;

import java.util.LinkedList;
import java.util.Objects;

public class MostHubsPath<V> {
    private LinkedList<POI_2<V>> path;
    private int totalDistance;
    private int chargingTimes;
    private int totalTime;
    private int chargingTimeTotal;
    private int pathTimeTotal;
    private int unloadTimeTotal;

    public MostHubsPath(LinkedList<POI_2<V>> path, int totalDistance, int chargingTimes, int totalTime, int chargingTimeTotal, int pathTimeTotal, int unloadTimeTotal) {
        this.path = path;
        this.totalDistance = totalDistance;
        this.chargingTimes = chargingTimes;
        this.totalTime = totalTime;
        this.chargingTimeTotal = chargingTimeTotal;
        this.pathTimeTotal = pathTimeTotal;
        this.unloadTimeTotal = unloadTimeTotal;
    }

    public LinkedList<POI_2<V>> getPath() {
        return path;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public int getChargingTimes() {
        return chargingTimes;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public int getChargingTimeTotal() {
        return chargingTimeTotal;
    }

    public int getPathTimeTotal() {
        return pathTimeTotal;
    }

    public int getUnloadTimeTotal() {
        return unloadTimeTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MostHubsPath<?> that = (MostHubsPath<?>) o;
        return totalDistance == that.totalDistance && chargingTimes == that.chargingTimes && totalTime == that.totalTime && chargingTimeTotal == that.chargingTimeTotal && pathTimeTotal == that.pathTimeTotal && unloadTimeTotal == that.unloadTimeTotal && Objects.equals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(path, totalDistance, chargingTimes, totalTime, chargingTimeTotal, pathTimeTotal, unloadTimeTotal);
    }

    @Override
    public String toString() {
        return "\nDistância total percorrida : " + totalDistance + " m" +
                "\nQuantidade de vezes que o veículo foi carregado : " + chargingTimes +
                "\nTempo total de viagem : " + totalTime + " mins" +
                "\nTempo total de carga : " + chargingTimeTotal + " mins" +
                "\nTempo total do caminho : " + pathTimeTotal + " mins" +
                "\nTempo total de descarga : " + unloadTimeTotal + " mins";
    }
}
