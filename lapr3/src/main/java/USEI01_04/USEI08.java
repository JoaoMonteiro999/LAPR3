package USEI01_04;

import USEI01_04.graph.Algorithms;
import USEI01_04.graph.Edge;
import USEI01_04.graph.Graph;
import USEI01_04.graph.domain.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class USEI08 {
    public static MostHubsPath<Node> circuit(Graph<Node, Weight> graph, Node vOrig, LocalTime time, int velocity, int autonomy, int chargerTime, int unloadTime, int n) {
        ArrayList<LinkedList<Node>> allPaths = new ArrayList<>();
        ArrayList<LinkedList<Node>> newList = new ArrayList<>();
        LinkedList<POI_2<Node>> list = new LinkedList<>();
        LinkedList<Node> hubList = getNhubs(graph, n);
        LinkedList<Node> path = new LinkedList<>();

        for (Edge<Node, Weight> e : graph.edges())
            if (e.getWeight().getDistance() > autonomy)
                graph.removeEdge(e.getVOrig(), e.getVDest());

        for (Node n1 : hubList)
            allPaths.addAll(Algorithms.allPaths(graph, vOrig, n1));

        for (LinkedList<Node> allPath : allPaths) {
            if (containsList(allPath, hubList))
                newList.add(allPath);
        }

        int distance;
        int minDist = Integer.MAX_VALUE;

        for (int i = 0; i < newList.size(); i++) {
            if ((distance = getTotalDist(graph, newList.get(i))) < minDist) {
                minDist = distance;
                path = allPaths.get(i);
            }
        }

        LinkedList<Node> list2 = new LinkedList<>();

        Algorithms.shortestPath(graph, path.get(path.size() - 1), vOrig, Weight::compare, Weight::sum, new Weight(0, "m"), list2);

        list2.remove(0);

        path.addAll(list2);

        int totalDist = 0;
        int chargingTimes = 0;

        int chargingTimeTotal = 0;
        int pathTimeTotal = 0;
        int unloadTimeTotal = 0;

        int autonomyNow = autonomy;
        int vel = Utils.convertKMHtoMM(velocity);
        int totalTime = 0;

        Node n1;
        Node n2 = null;
        POI_2<Node> poi;

        for (int i = 0; i < path.size() - 1; i++) {
            n1 = path.get(i);
            n2 = path.get(i + 1);

            int dist = graph.edge(n1, n2).getWeight().getDistance();

            int minTotal = dist / vel;

            totalTime += minTotal;
            time = time.plusMinutes(minTotal);
            pathTimeTotal += minTotal;

            poi = new POI_2<>(n1, n1.getIsHub(), time, time);


            if (autonomyNow < dist) {
                autonomyNow = autonomy;
                totalTime += chargerTime;
                chargingTimeTotal += chargerTime;
                time = time.plusMinutes(chargerTime);
                chargingTimes++;
            }

            autonomyNow -= dist;
            totalDist += dist;

            if (n1.getIsHub() && n1.isOpen(time)) {
                totalTime += unloadTime;
                unloadTimeTotal += unloadTime;
                time = time.plusMinutes(unloadTime);
                poi.setDepartureTime(time);
            }
            list.add(poi);
        }

        poi = new POI_2<>(n2, n2.getIsHub(), time, time);

        if (n2.getIsHub() && n2.isOpen(time)) {
            totalTime += unloadTime;
            unloadTimeTotal += unloadTime;
            time = time.plusMinutes(unloadTime);
            poi.setDepartureTime(time);
        }
        list.add(poi);

        return new MostHubsPath<>(list, totalDist, chargingTimes, totalTime, chargingTimeTotal, pathTimeTotal, unloadTimeTotal);
    }

    private static int getTotalDist(Graph<Node, Weight> graph, LinkedList<Node> nodes) {
        int sum = 0;

        Node n1;
        Node n2;

        for (int i = 0; i < nodes.size() - 1; i++) {
            n1 = nodes.get(i);
            n2 = nodes.get(i + 1);

            sum += graph.edge(n1, n2).getWeight().getDistance();
        }
        return sum;
    }

    private static boolean containsList(LinkedList<Node> nodes, LinkedList<Node> hubList) {
        int i = 0;

        for (Node n : nodes)
            if (hubList.contains(n))
                i++;
        return i == hubList.size();
    }

    private static LinkedList<Node> getNhubs(Graph<Node, Weight> graph, int n) {
        ArrayList<Node> list = graph.vertices();
        Collections.sort(list, new LabelComparator());

        LinkedList<Node> finalList = new LinkedList<>();

        for (int i = 0; i < n; i++)
            finalList.add(list.get(i));

        return finalList;
    }
}
