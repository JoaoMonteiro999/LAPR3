package USEI01_04.graph.domain;

public class Coords {
    private double lat;
    private double lon;

    public Coords(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coords coords = (Coords) o;
        return Double.compare(coords.lat, lat) == 0 && Double.compare(coords.lon, lon) == 0;
    }

    @Override
    public String toString() {
        return  "(" + lon + ", " + lat + ")";
    }
}
