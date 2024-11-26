package USEI01_04.graph.domain;

import java.time.LocalTime;
import java.util.Objects;

public class Node {
    private String label;
    private Coords coords;
    private LocalTime openTime;
    private LocalTime closeTime;
    private boolean isHub;

    public Node(String label, Coords coords) {
        this.label = label;
        this.coords = coords;
        setOpenCloseTime(label);
    }

    private void setOpenCloseTime(String label) {
        int key = Utils.getKeyFromLabel(label);
        int hO = 0, hC = 0;

        if (key <= 105) {
            hO = 9;
            hC = 14;
        } else if (key <= 215) {
            hO = 11;
            hC = 16;
        }  else {
            hO = 12;
            hC = 17;
        }
        this.openTime = LocalTime.of(hO, 0);
        this.closeTime = LocalTime.of(hC, 0);
    }

    public boolean isOpen(LocalTime time) {
        return (openTime.isBefore(time) && closeTime.isAfter(time));
    }

    public String getLabel() {
        return label;
    }

    public Coords getCoords() {
        return coords;
    }

    public LocalTime getOpenTime() {
        return openTime;
    }

    public LocalTime getCloseTime() {
        return closeTime;
    }

    public void setOpenTime(LocalTime openTime) {
        this.openTime = openTime;
    }

    public void setCloseTime(LocalTime closeTime) {
        this.closeTime = closeTime;
    }

    public boolean getIsHub() { return isHub; }
    public void setHub(boolean hub) {
        this.isHub = hub;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return Objects.equals(label, node.label) || Objects.equals(coords, node.coords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, coords, openTime, closeTime);
    }

    @Override
    public String toString() {
        return "Localidade " + label +
                ", Coordenadas : " + coords +
                ", Tempo de Abertura : " + openTime +
                ", Tempo de Encerramento : " + closeTime;
    }
    public String toString2() {
        return "Localidade " + label;
    }
}
