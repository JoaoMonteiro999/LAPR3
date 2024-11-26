package USEI01_04.graph.domain;

import java.util.List;

public class Cluster {


    private HubStatus<Node> hub;
    private List<Node> nodes;

    public Cluster(HubStatus<Node> hub, List<Node> nodes) {
        this.hub = hub;
        this.nodes = nodes;
    }

    public Cluster(HubStatus<Node> hub) {
        this.hub = hub;
    }


    public HubStatus<Node> getHub() {
        return hub;
    }

    public void setHub(HubStatus<Node> hub) {
        this.hub = hub;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void setNodes(List<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return "Cluster:\n" +
                "hub=" + hub.getElement().getLabel() +
                ", nodes=" + nodes +
                '}';
    }
}
