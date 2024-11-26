package USEI01_04.graph.domain;

import java.util.Comparator;

public class LabelComparator implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        return Integer.compare(Utils.toInt(o2.getLabel().substring(2)), Utils.toInt(o1.getLabel().substring(2)));
    }
}
