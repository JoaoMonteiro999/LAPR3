package USEI01_04;

import USEI01_04.graph.Graph;
import USEI01_04.graph.domain.Node;
import USEI01_04.graph.domain.Utils;
import USEI01_04.graph.domain.Weight;

import java.time.LocalTime;
import java.util.ArrayList;

import static USEI01_04.graph.domain.Utils.toInt;

public class USEI11 {
    public static void updateSchedules(Graph<Node, Weight> graph, String filename) {
        ArrayList<String[]> data = Utils.readCSV(filename);

        for (String[] s : data) {
            Node n = graph.vertex(Utils.getKeyFromLabel(s[0]));
            if (n == null) {
                System.out.printf("Erro, localidade não encontrada\n");
                return;
            }
            System.out.println("\nHora de abertura e fecho anterior da localidade " + n.getLabel() + ": " + n.getOpenTime() + ";" + n.getCloseTime());
            n.setOpenTime(LocalTime.of(toInt(s[1].split(":")[0]), toInt(s[1].split(":")[1])));
            n.setCloseTime(LocalTime.of(toInt(s[2].split(":")[0]), toInt(s[2].split(":")[1])));
            System.out.println("Hora de abertura e fecho atualizadas da localidade " + n.getLabel() + ": " + n.getOpenTime() + ";" + n.getCloseTime());

        }
    }
}
