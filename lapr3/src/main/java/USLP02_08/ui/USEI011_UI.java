package USLP02_08.ui;

import USEI01_04.USEI01;
import USEI01_04.USEI11;
import USEI01_04.graph.Graph;
import USEI01_04.graph.domain.Node;
import USEI01_04.graph.domain.Weight;
import USLP02_08.ui.utils.Utils;


public class USEI011_UI implements Runnable {

    public void run() {
        try {
            System.out.println("Escolha o grafo:");
            System.out.println("1.Xs");
            System.out.println("2.Small");
            System.out.println("3.Big");

            String vertices_files_small = "locais_xs.csv";
            String edges_files_small = "distancias_xs.csv";

            int opt = Utils.readValidIntInInterval2(3);
            if (opt == 1) {
                vertices_files_small = "locais_xs.csv";
                edges_files_small = "distancias_xs.csv";
            } else if (opt == 2) {
                vertices_files_small = "locais_small.csv";
                edges_files_small = "distancias_small.csv";
            } else if (opt == 3) {
                vertices_files_small = "locais_small.csv";
                edges_files_small = "distancias_small.csv";
            }

            Graph<Node, Weight> graph = USEI01.mapGraph(edges_files_small, vertices_files_small);

            String fileName = "hubs.csv";

            USEI11.updateSchedules(graph, fileName);

            System.out.println("\nHubs e os seus horários atualizados de acordo com o ficheiro '" + fileName + "'");

        } catch (Exception e) {
            System.out.println("\nErro ao ler o ficheiro\n");
        }
    }
}