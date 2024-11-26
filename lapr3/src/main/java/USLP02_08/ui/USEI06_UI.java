package USLP02_08.ui;

import USEI01_04.USEI01;
import USEI01_04.USEI02;
import USEI01_04.USEI06;
import USEI01_04.USEI07;
import USEI01_04.graph.Graph;
import USEI01_04.graph.domain.*;
import USLP02_08.ui.utils.Utils;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class USEI06_UI implements Runnable {

    public void run() {
        try {
            String vertices_files_small = "locais_small.csv";
            String edges_files_small = "distancias_small.csv";

            Graph<Node, Weight> graph = USEI01.mapGraph(edges_files_small, vertices_files_small);

            int hub = 1;

            ArrayList<Integer> hubs = new ArrayList<>();

            while (hub != 0) {
                System.out.println("Indique o número do local que pretende que seja um hub (Digite 0 para parar de selecionar hubs)");
                hub = Utils.readValidIntInInterval2(graph.numVertices());
                if (!hubs.contains(hub)) {
                    hubs.add(hub);
                } else if (hubs.contains(hub)) {
                    System.out.println("O hub selecionado já foi escolhido");
                }
            }

            System.out.print("Indique o número do local de origem");
            int Vorig = Utils.readValidIntInInterval(graph.numVertices());
            String originNodeLabel = null;

            for (Node node : graph.vertices())
                if (node.getLabel().equals("CT"+Vorig)){
                    originNodeLabel = "CT" + Vorig;
                }

            System.out.print("Indique o número do local de destino");
            int Vdest = Utils.readValidIntInInterval(graph.numVertices());
            String destinationNodeLabel = null;
            Node VDest = null;

            for (Node node : graph.vertices())
                if (node.getLabel().equals("CT"+Vdest)){
                    destinationNodeLabel = "CT" + Vdest;
                    VDest = node;
                }

            List<HubStatus<Node>> GraphHubs = new ArrayList<>();

            GraphHubs.add(new HubStatus<>(VDest, 0, 0, 0));

            USEI01_04.graph.domain.Utils.getNHubs(graph, GraphHubs, GraphHubs.size());

            System.out.print("Indique a autonomia do veículo (Km)");
            float autonomia = Utils.readValidPositiveInt();

            System.out.print("Indique a velocidade média do veículo (Km/h)");
            int veloMedia = Utils.readValidPositiveInt();

            ArrayList<Route<Node>> result = USEI06.routesToHub(originNodeLabel, destinationNodeLabel, autonomia, veloMedia, graph);
            System.out.println("\nCaminho encontrado: ");

            int nRotas = 1;

            for (Route<Node> route : result) {
                System.out.println("\nRota " + nRotas + ":");

                for (POI<Node> node : route.getRoute())
                    System.out.print("  " + node.getElement().getLabel());
            nRotas++;
            }

        } catch (Exception e) {
            System.out.println("\nCaminho não encontrado para os valores fornecidos!\n");
        }
    }
}
