package USEI01_04;

import USEI01_04.graph.Algorithms;
import USEI01_04.graph.Edge;
import USEI01_04.graph.Graph;
import USEI01_04.graph.domain.Cluster;
import USEI01_04.graph.domain.HubStatus;
import USEI01_04.graph.domain.Node;
import USEI01_04.graph.domain.Weight;
import USEI01_04.graph.map.MapGraph;


import java.util.*;

public class USEI09 {

    public static int numeroDeClustersCriados = 0;

    public static  Graph<Node,Weight> grafoFinal = new MapGraph<>(false);
    public static List<Cluster> clusters = new ArrayList<>();


    public static List<Cluster> organizarClusters(Graph<Node, Weight> grafo, List<HubStatus<Node>> hubs) {
        ArrayList<LinkedList<Node>> paths = new ArrayList<>();
        numeroDeClustersCriados = 0;
        clusters = new ArrayList<>();

        HashMap<Edge<Node, Weight>, Integer> numeroDeArestasRepetidas = new HashMap<>();

        contarNumeroArestasMinimas(grafo,paths,numeroDeArestasRepetidas);

        if (hubs.size() == 1){
            grafo.removeVertex(hubs.get(0).getElement());
            clusters.add(new Cluster(hubs.get(0),grafo.vertices()));
        }

        while (!numeroDeArestasRepetidas.isEmpty() || numeroDeClustersCriados != hubs.size()) {
            Edge<Node, Weight> arestaMaxima = calcularArestaMaxima(numeroDeArestasRepetidas);
            grafo.removeEdge(arestaMaxima.getVOrig(), arestaMaxima.getVDest());
            if (!isConnected(grafo)) {
                List<Graph<Node,Weight>> componentesConexos = dividirEmComponentesConexos(grafo);
                verificacoes(hubs,grafo,arestaMaxima,componentesConexos,numeroDeArestasRepetidas);
            }

            numeroDeArestasRepetidas.remove(arestaMaxima);
        }
        return clusters;
    }


    private static void verificacoes(List<HubStatus<Node>> hubs, Graph<Node, Weight> grafo, Edge<Node, Weight> arestaMaxima, List<Graph<Node, Weight>> componentesConexos, Map<Edge<Node, Weight>, Integer> numeroDeArestasRepetidas) {

        boolean adicionouArestas = false;

        for (Graph<Node, Weight> subGrafo : componentesConexos) {
            // Verificar se as partes não conexas consistem apenas em localidades
            if (contemApenasLocalidades(subGrafo, hubs)) {
                grafo.addEdge(arestaMaxima.getVOrig(), arestaMaxima.getVDest(), arestaMaxima.getWeight());
                grafo.addEdge(arestaMaxima.getVDest(), arestaMaxima.getVOrig(), arestaMaxima.getWeight());
                adicionouArestas = true;
                break;
            }
        }

        if (!adicionouArestas) {
            for (Graph<Node, Weight> subGrafo : componentesConexos) {
                // Se deu origem a apenas hubs, cada um será um cluster separado
                if (contemApenasHubs(subGrafo.vertices(), hubs)) {
                    criarClustersIsolados(subGrafo.vertices(), hubs);
                    removerArestasUsadasPeloClusterHubs(subGrafo, numeroDeArestasRepetidas);
                    removerVerticesUsadosPeloCluster(subGrafo,grafo);
                } else {
                    // Se deu origem a localidades e a 1 hub, essa parte será um cluster
                    // Verificar se há pelo menos 1 hub e há localidades
                    if (contarHubs(subGrafo.vertices(), hubs) == 1 && contemLocalidade(subGrafo.vertices(), hubs)) {
                        criarCluster(subGrafo.vertices(), hubs);
                        removerArestasUsadasPeloCluster(subGrafo, numeroDeArestasRepetidas);
                        removerVerticesUsadosPeloCluster(subGrafo,grafo);
                    } else if (contarHubs(subGrafo.vertices(), hubs) > 1 && contemLocalidade(subGrafo.vertices(), hubs)) {
                        // quando tem varios hubs e contem pelo menos 1 localidade
                        dividirSubGrafos(hubs, numeroDeArestasRepetidas, arestaMaxima, subGrafo, componentesConexos);
                    }
                }
            }
        }
    }

    private static void dividirSubGrafos(List<HubStatus<Node>> hubs, Map<Edge<Node, Weight>, Integer> numeroDeArestasRepetidas, Edge<Node, Weight> arestaMaxima, Graph<Node,Weight> subGrafo, List<Graph<Node,Weight>> componentesConexos) {

        if(subGrafo.edges().contains(arestaMaxima)){
            subGrafo.removeEdge(arestaMaxima.getVOrig(),arestaMaxima.getVDest());
            subGrafo.removeEdge(arestaMaxima.getVOrig(), arestaMaxima.getVDest());
            if (!isConnected(subGrafo)) {
                componentesConexos = dividirEmComponentesConexos(subGrafo);
                verificacoes(hubs,subGrafo,arestaMaxima,componentesConexos,numeroDeArestasRepetidas);
            }
            numeroDeArestasRepetidas.remove(arestaMaxima);

        }else {
            encontrarProximaArestaMaxima(numeroDeArestasRepetidas,subGrafo);
        }


    }

    private static void removerVerticesUsadosPeloCluster(Graph<Node, Weight> subGrafo,Graph<Node, Weight> grafo){
        for (Node location : subGrafo.vertices()){
            grafo.removeVertex(location);
            grafoFinal.addVertex(location);
        }
    }

    private static void removerArestasUsadasPeloCluster(Graph<Node, Weight> subGrafo,Map<Edge<Node, Weight>, Integer> numeroDeArestasRepetidas){
        for (Edge<Node,Weight> aresta : subGrafo.edges()){
            numeroDeArestasRepetidas.remove(aresta);
            grafoFinal.addEdge(aresta.getVOrig(),aresta.getVDest(),aresta.getWeight());
        }
    }

    private static void removerArestasUsadasPeloClusterHubs(Graph<Node, Weight> subGrafo,Map<Edge<Node, Weight>, Integer> numeroDeArestasRepetidas){
        for (Edge<Node,Weight> aresta : subGrafo.edges()){
            numeroDeArestasRepetidas.remove(aresta);
        }
    }



    private static void criarClustersIsolados(ArrayList<Node> componente, List<HubStatus<Node>> hubs) {
        for (Node location : componente){
            clusters.add(new Cluster(obterHubLocalidade(location,hubs)));
            numeroDeClustersCriados +=1;
        }

    }

    private static void criarCluster(ArrayList<Node> componente, List<HubStatus<Node>> hubs) {
        for (Node location : componente){
            HubStatus<Node> hub = obterHubLocalidade(location, hubs);
            if (hub != null){
                componente.remove(location);
                clusters.add(new Cluster(hub, componente));
                numeroDeClustersCriados += 1;
                return;
            }
        }
    }

    public static HubStatus<Node> obterHubLocalidade(Node location, List<HubStatus<Node>> hubs){

        for (HubStatus<Node> hub : hubs) {
            if (hub.getElement().equals(location)){
                return hub;
            }
        }
        return null;
    }


    private static List<Graph<Node, Weight>> dividirEmComponentesConexos(Graph<Node, Weight> grafo) {
        List<Graph<Node, Weight>> componentesConexos = new ArrayList<>();
        Set<Node> visitados = new HashSet<>();

        for (Node vertice : grafo.vertices()) {
            if (!visitados.contains(vertice)) {
                Graph<Node, Weight> componente = criarSubgrafo(grafo, vertice, visitados);
                componentesConexos.add(componente);
            }
        }

        return componentesConexos;
    }

    private static Graph<Node, Weight> criarSubgrafo(Graph<Node, Weight> grafo, Node vertice, Set<Node> visitados) {
        Graph<Node, Weight> subgrafo = new MapGraph<>(false);;

        Set<Node> verticesPendentes = new HashSet<>();
        verticesPendentes.add(vertice);

        while (!verticesPendentes.isEmpty()) {
            Node atual = verticesPendentes.iterator().next();
            verticesPendentes.remove(atual);

            if (!visitados.contains(atual)) {
                visitados.add(atual);
                subgrafo.addVertex(atual);

                for (Node vizinho : grafo.adjVertices(atual)) {
                    if (!visitados.contains(vizinho)) {
                        verticesPendentes.add(vizinho);
                        subgrafo.addVertex(vizinho);
                        subgrafo.addEdge(atual, vizinho, grafo.edge(atual, vizinho).getWeight());
                        subgrafo.addEdge(vizinho, atual, grafo.edge(atual, vizinho).getWeight());
                    }
                }
            }
        }
        return subgrafo;
    }


    private static boolean contemLocalidade(ArrayList<Node> componente,List<HubStatus<Node>> hubs) {
        for (Node localidade : componente) {
            if (!existeHubComLocalidade(localidade, hubs)) {
                return true;
            }
        }
        return false;
    }

    private static int contarHubs(ArrayList<Node> componente, List<HubStatus<Node>> hubs) {
        int count = 0;
        for (Node location : componente) {
            if (existeHubComLocalidade(location, hubs)) {
                count++;
            }
        }
        return count;
    }

    private static boolean contemApenasHubs(ArrayList<Node> componente, List<HubStatus<Node>> hubs) {
        for (Node localidade : componente) {
            if (!existeHubComLocalidade(localidade, hubs)) {
                // Se encontrarmos uma localidade que não é um hub, retornamos falso
                return false;
            }
        }

        // Se todas as localidades são hubs, retornamos verdadeiro
        return true;
    }


    private static boolean contemApenasLocalidades(Graph<Node, Weight> subGrafo, List<HubStatus<Node>> hubs) {
        ArrayList<Node> locais = subGrafo.vertices();

        for (Node localidade : locais) {
            if (existeHubComLocalidade(localidade, hubs)) {
                // Se encontrarmos um hub associado a esta localidade, retornamos falso
                return false;
            }
        }
        // Se nenhum hub foi encontrado para qualquer localidade, retornamos verdadeiro
        return true;
    }

    private static boolean existeHubComLocalidade(Node location, List<HubStatus<Node>> hubs) {
        for (HubStatus<Node> h : hubs) {
            if (h.getElement().equals(location)) {
                return true;
            }
        }
        return false;
    }

    public static Edge<Node, Weight> calcularArestaMaxima(Map<Edge<Node, Weight>, Integer> numeroDeArestasRepetidas ){

        Integer valorArestaMaxima  = 0;
        Edge<Node, Weight> arestaMaxima = null;
        for (Edge<Node, Weight> aresta : numeroDeArestasRepetidas.keySet()){
            Integer valorArestaAtual = numeroDeArestasRepetidas.get(aresta);
            if(valorArestaAtual > valorArestaMaxima){
                arestaMaxima = aresta;
            }

        }
        return arestaMaxima;
    }

    public static Edge<Node, Weight> encontrarProximaArestaMaxima(Map<Edge<Node, Weight>, Integer> numeroDeArestasRepetidas, Graph<Node, Weight> subGrafo) {
        Integer valorArestaMaxima = 0;
        Edge<Node, Weight> arestaMaxima = null;

        for (Edge<Node, Weight> aresta : numeroDeArestasRepetidas.keySet()) {
            if (!subGrafo.edges().contains(aresta)) {
                continue; // Pula as arestas que não estão no subgrafo
            }

            Integer valorArestaAtual = numeroDeArestasRepetidas.get(aresta);
            if (valorArestaAtual != null && valorArestaAtual > valorArestaMaxima) {
                arestaMaxima = aresta;
                valorArestaMaxima = valorArestaAtual;
            }
        }

        return arestaMaxima;
    }



    public static void contarNumeroArestasMinimas (Graph<Node, Weight> grafo,ArrayList<LinkedList<Node>> paths,HashMap<Edge<Node, Weight>, Integer> numeroDeArestasRepetidas){
        ArrayList<Weight> dists = new ArrayList<>();
        for (int k = 0; k < grafo.numVertices(); k++) {

            Algorithms.shortestPaths(grafo, grafo.vertex(k), Weight::compare, Weight::sum, new Weight(0,"m"), paths, dists);

            for (LinkedList<Node> path : paths) {
                for (int j = 0; j < path.size() - 1; j++) {
                    Node origem = path.get(j);
                    Node destino = path.get(j + 1);
                    Edge<Node, Weight> chaveAresta;
                    if (origem.getLabel().compareTo(destino.getLabel()) < 0) {
                        chaveAresta = new Edge(origem, destino, 0);
                    } else {
                        chaveAresta = new Edge(destino, origem, 0);
                    }
                    Weight w = grafo.edge(origem, destino).getWeight();
                    chaveAresta.setWeight(w);
                    numeroDeArestasRepetidas.put(chaveAresta, numeroDeArestasRepetidas.getOrDefault(chaveAresta, 0) + 1);
                }
            }
        }
    }


    public static  boolean isConnected(Graph<Node, Weight> grafo) {
        if (grafo.numVertices() == 0) {
            return false;
        }

        Set<Node> visitados = new HashSet<>();

        Iterator<Node> verticesIterator = grafo.vertices().iterator();
        if (!verticesIterator.hasNext()) {
            return false; // Grafo vazio
        }

        Node primeiroVertice = verticesIterator.next();
        LinkedList<Node> fila = Algorithms.BreadthFirstSearch(grafo, primeiroVertice);

        if (fila == null) {
            return false; // Vértice inicial inválido
        }

        visitados.addAll(fila);

        while (!fila.isEmpty()) {
            Node verticeAtual = fila.poll();

            for (Node vizinho : grafo.adjVertices(verticeAtual)) {
                if (!visitados.contains(vizinho)) {
                    visitados.add(vizinho);
                    fila.add(vizinho);
                }
            }
        }

        // Verificar se todos os vértices foram visitados
        return visitados.size() == grafo.numVertices();
    }




}
