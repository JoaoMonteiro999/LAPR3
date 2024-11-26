package USLP02_08.ui;

import USEI01_04.USEI01;
import USEI01_04.USEI09;
import USEI01_04.graph.Graph;
import USEI01_04.graph.domain.*;
import USLP02_08.ui.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class USEI09_UI implements Runnable {
    public void run() {
        try {
            String vertices_files_small = "locais_small.csv";
            String edges_files_small = "distancias_small.csv";

            Graph<Node, Weight> graph = USEI01.mapGraph(edges_files_small, vertices_files_small);

            int hub = 1;

            ArrayList<Integer> hubs = new ArrayList<>();

            while (hub != 0){
                System.out.print("Indique o número do local que pretende que seja um hub (Digite 0 para parar de selecionar hubs)");
                hub = Utils.readValidIntInInterval2(graph.numVertices());
                if (!hubs.contains(hub)){
                    hubs.add(hub);
                } else if (hubs.contains(hub)) {
                    System.out.println("O hub selecionado já foi escolhido");
                }
            }

            List<HubStatus<Node>> graphHubs = new ArrayList<>();

            for (Integer integer:hubs) {
                for (Node n1 : graph.vertices())
                    if (n1.getLabel().equals("CT"+integer)){
                        graphHubs.add(new HubStatus<>(n1, 0, 0, 0));
                    }
            }

            List<Cluster> result = USEI09.organizarClusters(graph, graphHubs);

            System.out.println("Cluster(s) encontrado(s) :");

            for (Cluster c : result) {
                System.out.printf("\nHub:%s, Nodes:",c.getHub().getElement().getLabel());
                if (c.getNodes() == null || c.getNodes().isEmpty()){
                    System.out.print("Não existem localidades neste cluster para além do hub");
                }else{
                    for (int i = 0; i < c.getNodes().size() - 2; i++) {
                        Node n = c.getNodes().get(i);
                        System.out.printf(" %s,",n.getLabel());
                    }
                    System.out.print(" " + c.getNodes().get(c.getNodes().size()-1).getLabel());
                }
            }

        } catch (Exception e) {
            System.out.println("\nClusters não encontrados para os valores fornecidos!\n");
        }
    }
}
