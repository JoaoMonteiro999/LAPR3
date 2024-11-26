package USEI01_04.graph.domain;

import java.util.Objects;

public class Weight {
    private int distance;
    private String unit;

    public Weight(int distance, String unit) {
        this.distance = distance;
        this.unit = unit;
    }

    public static int compare(Weight w1, Weight w2) {
        return Integer.compare(w1.distance, w2.distance);
    }

    public static Weight sum(Weight w1, Weight w2) {
        return new Weight(Integer.sum(w1.distance, w2.distance), "m");
    }

    public int getDistance() {
        return distance;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Weight weight = (Weight) o;
        return distance == weight.distance && Objects.equals(unit, weight.unit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(distance, unit);
    }

    @Override
    public String toString() {
        return "Weight{" +
                "distance=" + distance +
                ", unit='" + unit + '\'' +
                '}';
    }
}
