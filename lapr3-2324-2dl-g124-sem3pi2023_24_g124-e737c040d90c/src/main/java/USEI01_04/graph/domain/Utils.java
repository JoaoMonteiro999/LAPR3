package USEI01_04.graph.domain;

import USEI01_04.graph.Graph;

import java.io.File;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Utils {
    private static final String path = "resources/USEI01_04/";

    public static ArrayList<String[]> readCSV(String filename) {
        ArrayList<String[]> data = new ArrayList<>();
        try {
            File file = new File(path + filename);
            Scanner sc = new Scanner(file);
            sc.nextLine();
            while (sc.hasNextLine())
                data.add(sc.nextLine().split(","));
        } catch (Exception e) {
            System.out.printf("Error in %s\n", filename);
        }
        return data;
    }

    public static Node getNodeFromLabel(Graph<Node, Weight> graph, String label) {
        return graph.vertex(p -> p.getLabel().equals(label));
    }

    public static int getKeyFromLabel(String label) {
        return toInt(label.substring(2))-1;
    }

    public static int toInt(String s) {
        return Integer.parseInt(s);
    }

    public static double toDouble(String s) {
        return Double.parseDouble(s);
    }

    public static void getNHubs(Graph<Node, Weight> graph, List<HubStatus<Node>> list, int n) {
        if (!(list.size() > graph.numVertices() || n > list.size()))
            for (int i = 0; i < n; i++)
                getNodeFromLabel(graph, list.get(i).getElement().getLabel()).setHub(true);
    }

    public static int getNumOfHubs(Graph<Node, Weight> graph, LinkedList<Node> list, LocalTime time, int velocity, int autonomy, int chargerTime, int unloadTime) {
        int nHubs = 0;
        int autonomyNow = autonomy;
        int vel = convertKMHtoMM(velocity);

        Node n1;
        Node n2 = null;

        for (int i = 0; i < list.size() - 1; i++) {
            n1 = list.get(i);
            n2 = list.get(i + 1);

            int dist = graph.edge(n1, n2).getWeight().getDistance();

            int minTotal = dist / vel;

            time = time.plusMinutes(minTotal);

            if (autonomyNow < dist) {
                autonomyNow = autonomy;
                time = time.plusMinutes(chargerTime);
            }
            autonomyNow -= dist;

            if (n1.getIsHub() && n1.isOpen(time)) {
                time = time.plusMinutes(unloadTime);
                nHubs++;
            }
        }

        if (n2.getIsHub() && n2.isOpen(time)) {
            time = time.plusMinutes(unloadTime);
            nHubs++;
        }

        return nHubs;
    }

    public static int convertKMHtoMM(int kmh) {
        return (kmh * 1000) / 60;
    }
}
