package USEI01_04;

import USEI01_04.graph.Algorithms;
import USEI01_04.graph.Edge;
import USEI01_04.graph.Graph;
import USEI01_04.graph.domain.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class USEI07 {
    public static MostHubsPath<Node> maxHubs(Graph<Node, Weight> graph, Node vOrig, LocalTime time, int velocity, int autonomy, int chargerTime, int unloadTime) {
        ArrayList<LinkedList<Node>> allPaths = new ArrayList<>();
        LinkedList<Node> path = new LinkedList<>();
        LinkedList<POI_2<Node>> list = new LinkedList<>();

        if (velocity < 0 || autonomy < 0 || graph == null || vOrig == null)
            return null;

        for (Edge<Node, Weight> e : graph.edges())
            if (e.getWeight().getDistance() > autonomy)
                graph.removeEdge(e.getVOrig(), e.getVDest());

        for (Node n1 : graph.vertices())
            if ((!n1.equals(vOrig)) && n1.getIsHub())
                allPaths.addAll(Algorithms.allPaths(graph, vOrig, n1));

        int nHubs;
        int maxHubs = 0;

        for (LinkedList<Node> l : allPaths) {

            if ((nHubs = Utils.getNumOfHubs(graph, l, time, velocity, autonomy, chargerTime, unloadTime)) > maxHubs ) {

                maxHubs = nHubs;
                path = l;
            }
        }

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
}