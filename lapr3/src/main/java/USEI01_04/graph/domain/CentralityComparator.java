package USEI01_04.graph.domain;

import java.util.Comparator;

public class CentralityComparator implements Comparator<HubStatus<Node>> {

    @Override
    public int compare(HubStatus<Node> o1, HubStatus<Node> o2) {
        return Integer.compare(o1.getCentrality(), o2.getCentrality());
    }
}
