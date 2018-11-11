import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class Dijkstra {

	public int[][] graph;
	public Node[] nodes;



	public Dijkstra(int[][] array) {
		graph =array;
		nodes = new Node[graph.length];
	}

	public static void main(String[] args) throws IOException {

		System.out.println("Escriba el número de datos que desea procesar: (puede ser 5, 100, 1000 )");

		Scanner sc0 = new Scanner(System.in);
		int tama = sc0.nextInt();

		int[][] numbersList = new int [tama][tama];

		try (FileReader reader = new FileReader("data/d"+tama+".txt");
				BufferedReader in = new BufferedReader(reader)) { 
			String line = in.readLine();
			for (int i=0;line != null;i++) {
				try {
					String [] x =  line.split("\\s+");

					for(int j = 0; j< x.length; j++){

						numbersList[i][j] = Integer.parseInt(x[j]);

					}
				} catch (Exception e) {
					System.err.println("Can not read number from line "+i+" content: "+line);
					e.printStackTrace();
				}
				line = in.readLine();
			}
		}


		Dijkstra dijkstra = new Dijkstra(numbersList);
		for(int i = 0; i < dijkstra.nodes.length; i++)
			dijkstra.nodes[i] = new Node(null, Integer.MAX_VALUE, i);

		System.out.println("Por favor escriba de cuál nodo desea aplicar el algoritmo dijkstra:(ejemplo 0)");

		Scanner sc = new Scanner(System.in);
		int source = sc.nextInt();
		// se elige sobre cuál nodo empezar
		for (int i = 0; i<tama ; i++){

			int destination = i;

			if(source !=destination){
				dijkstra.shortestPath(source,i );
				System.out.println("El camino más corto desde " + source + " hasta " + destination + "  es " + dijkstra.nodes[destination].cost);
				Node temp = dijkstra.nodes[destination];
				System.out.println("Path is ");
				while(temp.parent != null) {
					System.out.print(temp.id + " <--- ");
					temp = temp.parent;
				}
				System.out.println(temp.id);
			}

		}

	}

	public void shortestPath(int source, int destination) {
		Set visited = new HashSet();
		PriorityQueue<Node> pQueue = new PriorityQueue<>(new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return o1.cost - o2.cost;
			}
		});
		nodes[source].cost = 0;
		pQueue.add(nodes[source]);
		while(!pQueue.isEmpty()) {
			Node currVertex = pQueue.poll();
			for(int i = 0; i < graph.length; i++) {
				if(graph[currVertex.id][i]!=0 && !visited.contains(nodes[i]) && graph[currVertex.id][i]!=-1 ) {
					if(!pQueue.contains(nodes[i])) {
						nodes[i].cost = currVertex.cost + graph[currVertex.id][i];
						nodes[i].parent = currVertex;
						pQueue.add(nodes[i]);
					}
					else {
						nodes[i].cost = Math.min(nodes[i].cost, currVertex.cost + graph[currVertex.id][i]);
						if(nodes[i].cost == currVertex.cost + graph[currVertex.id][i])
							nodes[i].parent = currVertex;
					}
				}
			}
			visited.add(currVertex);
		}
	}


}
