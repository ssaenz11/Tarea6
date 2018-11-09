import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;




public class Digrafo {
	
	SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph;
	
	public Digrafo(int [][] list){
		
		graph = new SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//Se crea los nodor totales 
		
		int tamanio = list.length-1;
		
		while(tamanio >= 0){
			
			String vertex = tamanio + "";
			
			graph.addVertex(vertex);
		
			tamanio = tamanio-1;
			
		}
		
	
		for(int i = 0; i<list.length; i++){
			
			String vertexi = i +"";
			
			for(int j = 0; j<list.length;j++){
				
				String vertexj = j +"";
				
				if(i != j &&  list[i][j] != -1){
					
					int weight = list[i][j];
					
					 DefaultWeightedEdge e1 = graph.addEdge(vertexi, vertexj); 
			            graph.setEdgeWeight(e1, weight); 
				
				}
				
			}
		}
		
	
	}
	

	public static void main(String[] args) throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

		System.out.println("Se está cargando la información de todos los archivos");
		int[][] numbersList = new int [5][5];

		try (FileReader reader = new FileReader("data/d5.txt");
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
		
		Digrafo di = new Digrafo(numbersList);
		
		
		System.out.println("cuál archivo desea escoger:");
		System.out.println("5:");
		System.out.println("100");
		System.out.println("1.000:");
		
	}

}
