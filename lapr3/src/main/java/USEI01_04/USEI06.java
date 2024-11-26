package USEI01_04;

import USEI01_04.graph.Edge;
import USEI01_04.graph.Graph;
import USEI01_04.graph.domain.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

public class USEI06 {

    public static ArrayList<Route<Node>> routesToHub (String originNodeLabel, String destinationHubLabel, float autonomy, int averageSpeed, Graph<Node, Weight> graph) {

        if(autonomy <= 0 || averageSpeed <= 0 || originNodeLabel == null || destinationHubLabel == null || graph == null)
            return null;

        Node originNode = Utils.getNodeFromLabel(graph, originNodeLabel);
        Node destinationNode = Utils.getNodeFromLabel(graph,destinationHubLabel);

        return allPaths(graph,originNode,destinationNode,autonomy,averageSpeed);
    }

    public static ArrayList<Route<Node>> allPaths(Graph<Node, Weight> g, Node vOrig, Node vDest,float autonomy,int averageSpeed) {
        LinkedList<POI<Node>> path = new LinkedList<>();
        ArrayList<Route<Node>> paths = new ArrayList<>();

        boolean[] visited = new boolean[g.numVertices()];

        if (g.validVertex(vOrig) && g.validVertex(vDest))
            allPaths(g, vOrig, vDest,autonomy, visited, path, paths,0,0,averageSpeed);
        return paths;
    }

    private static void allPaths(Graph<Node, Weight> g, Node vOrig, Node vDest,float autonomy, boolean[] visited, LinkedList<POI<Node>> path,
        ArrayList<Route<Node>> paths, int totalDistance,int distanceToNextPOI,int averageSpeed) {

        int vKey = g.key(vOrig);

        if (visited[vKey])
            return;

        if (vOrig.equals(vDest)) {
            LinkedList<POI<Node>> pathCopy = new LinkedList<>(path);

            pathCopy.addFirst(new POI<>(vDest,false,distanceToNextPOI));

            Collections.reverse(pathCopy);

            float totalTime = (totalDistance / (float)(averageSpeed * 1000));
            paths.add(new Route<>(pathCopy,totalDistance,totalTime) );
            return;
        }

        path.push(new POI<>(vOrig,false,distanceToNextPOI));

        visited[vKey] = true;

        for (Node vAdj : g.adjVertices(vOrig)) {
            Edge<Node, Weight> edge = g.edge(vOrig, vAdj);
            Weight w =  edge.getWeight();
            if (autonomy > w.getDistance()) {
                allPaths(g, vAdj, vDest, (autonomy - w.getDistance()), visited, path, paths,(totalDistance += w.getDistance()),w.getDistance(),averageSpeed);
            } else return;
        }

        path.pop();

        visited[vKey] = false;
    }
}
