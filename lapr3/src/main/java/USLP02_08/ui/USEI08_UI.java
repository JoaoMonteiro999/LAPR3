package USLP02_08.ui;

import USEI01_04.USEI01;
import USEI01_04.USEI08;
import USEI01_04.graph.Graph;
import USEI01_04.graph.domain.*;
import USLP02_08.ui.utils.Utils;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


public class USEI08_UI implements Runnable {

    public void run() {
        try {

            String vertices_files_small = "locais_small.csv";
            String edges_files_small = "distancias_small.csv";

            Graph<Node, Weight> graph = USEI01.mapGraph(edges_files_small, vertices_files_small);

            System.out.print("Indique o número do local de origem");
            int Vorig = Utils.readValidIntInInterval(graph.numVertices());
            Node VOrig = null;

            for (Node n1 : graph.vertices())
                if (n1.getLabel().equals("CT"+Vorig)){
                    VOrig = n1;
                }

            int hub = 1;

            ArrayList<Integer> hubs = new ArrayList<>();

            while (hub != 0){
                System.out.println("Indique o número do local que pretende que seja um hub (Digite 0 para parar de selecionar hubs)");
                hub = Utils.readValidIntInInterval2(graph.numVertices());
                if (!hubs.contains(hub)){
                    hubs.add(hub);
                } else if (hubs.contains(hub)) {
                    System.out.println("O hub selecionado já foi escolhido");
                }
            }


            List<HubStatus<Node>> GraphHubs = new ArrayList<>();

            for (Integer integer:hubs) {
                for (Node n1 : graph.vertices())
                    if (n1.getLabel().equals("CT"+integer)){
                        GraphHubs.add(new HubStatus<>(n1, 0, 0, 0));
                    }
            }

            USEI01_04.graph.domain.Utils.getNHubs(graph, GraphHubs, GraphHubs.size());

            System.out.print("Indique a hora de inicio da viagem");
            int hora = Utils.readValidIntInInterval2(23);

            System.out.print("Indique os minutos de inicio da viagem");
            int mins = Utils.readValidIntInInterval2(59);

            LocalTime time = LocalTime.of(hora, mins);

            System.out.print("Indique a velocidade do veículo");
            int velo = Utils.readValidPositiveInt();

            System.out.print("Indique a autonomia do veículo");
            int auto = Utils.readValidPositiveInt();

            System.out.print("Indique o tempo que o veículo demora a carregar (em minutos)");
            int carga = Utils.readValidPositiveInt();

            System.out.print("Indique o tempo que o veículo demora a descarregar (em minutos)");
            int descarga = Utils.readValidPositiveInt();

            System.out.print("Indique o número de hubs a considerar (recomendado 5 ou 6 ou 7)");
            int n = Utils.readValidPositiveInt();

            MostHubsPath<Node> result = USEI08.circuit(graph, VOrig, time, velo, auto, carga, descarga, n);

            System.out.println("Circuito encontrado :");

            for (POI_2<Node> v : result.getPath()) {
                System.out.println(v);
            }

            System.out.println(result);

        } catch (Exception e) {
            System.out.println("\nCircuito não encontrado para os valores fornecidos!\n");
        }
    }
}