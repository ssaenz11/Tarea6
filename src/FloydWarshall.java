import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import static java.lang.String.format;

public class FloydWarshall {
	private int n;
	  private boolean solved;
	  private double[][] dp;
	  private Integer[][] next;

	  private static final int REACHES_NEGATIVE_CYCLE = -1;

	// Se utiliza la matriz generada por el lector 
	  public FloydWarshall(double[][] matrix) {
	    n = matrix.length;
	    dp = new double[n][n];
	    next = new Integer[n][n];

	    
	    for(int i = 0; i < n; i++) {
	      for (int j = 0; j < n; j++) {
	        if (matrix[i][j] != Double.POSITIVE_INFINITY)
	          next[i][j] = j;
	        dp[i][j] = matrix[i][j];
	      }
	    }
	  }

	 
	  public double[][] getApspMatrix() {
	    solve();
	    return dp;
	  }

	 
	  public void solve() {
	    if (solved) return;

	    // computa el par de caminos
	    for (int k = 0; k < n; k++) {
	      for (int i = 0; i < n; i++) {
	        for (int j = 0; j < n; j++) {
	          if (dp[i][k] + dp[k][j] < dp[i][j] && dp[i][k] !=-1 && dp[k][j] !=-1 ) {
	            dp[i][j] = dp[i][k] + dp[k][j];
	            next[i][j] = next[i][k];
	          }
	        }
	      }
	    }

	   //identifica ciclos negativos
	    for (int k = 0; k < n; k++)
	      for (int i = 0; i < n; i++)
	        for (int j = 0; j < n; j++)
	          if (dp[i][k] + dp[k][j] < dp[i][j] && dp[i][k] !=-1 && dp[k][j] !=-1) {
	            dp[i][j] = Double.NEGATIVE_INFINITY;
	            next[i][j] = REACHES_NEGATIVE_CYCLE;
	          }

	    solved = true;
	  }

	  /**
	   * Reconstructs the shortest path (of nodes) from 'start' to 'end' inclusive.
	   *
	   * @return An array of nodes indexes of the shortest path from 'start' to 'end'.
	   * If 'start' and 'end' are not connected return an empty array. If the shortest 
	   * path from 'start' to 'end' are reachable by a negative cycle return -1.
	   */
	  public List<Integer> reconstructShortestPath(int start, int end) {
	    solve();
	    List<Integer> path = new ArrayList<>();
	    if (dp[start][end] == Double.POSITIVE_INFINITY) return path;
	    int at = start;
	    for(;at != end; at = next[at][end]) {
	     
	      if (at == REACHES_NEGATIVE_CYCLE) return null;
	      path.add(at);
	    }
	   
	    if (next[at][end] == REACHES_NEGATIVE_CYCLE) return null;
	    path.add(end);
	    return path;
	  }

	   
	  public static double[][] createGraph(int n) {
	    double[][] matrix = new double[n][n];
	    for (int i = 0; i < n; i++) {
	      java.util.Arrays.fill(matrix[i], Double.POSITIVE_INFINITY);
	      matrix[i][i] = 0;
	    }
	    return matrix;
	  }

	  public static void main(String[] args) throws FileNotFoundException, IOException {
		  
		  System.out.println("Escriba el número de datos que desea procesar: (puede ser 5, 100, 1000 )");

			Scanner sc0 = new Scanner(System.in);
			int tama = sc0.nextInt();

			double[][] numbersList = new double [tama][tama];

			try (FileReader reader = new FileReader("data/d"+tama+".txt");
					BufferedReader in = new BufferedReader(reader)) { 
				String line = in.readLine();
				for (int i=0;line != null;i++) {
					try {
						String [] x =  line.split("\\s+");

						for(int j = 0; j< x.length; j++){
							
							if(Integer.parseInt(x[j]) != -1)

							numbersList[i][j] = Integer.parseInt(x[j]);
							
							else{
								numbersList[i][j] =1000000000;
							}

						}
					} catch (Exception e) {
						System.err.println("Can not read number from line "+i+" content: "+line);
						e.printStackTrace();
					}
					line = in.readLine();
				}
			}

		  
		  
	   
	    int n = tama;
	    double[][] m = createGraph(n);

	   
	   m =numbersList;

	    FloydWarshall solver = new FloydWarshall(m);
	    double[][] dist = solver.getApspMatrix();

	    for (int i = 0; i < n; i++)
	      for (int j = 0; j < n; j++)
	        System.out.printf("El camino más corto desde el nodo %d hasta el nodo %d es %.3f\n", i, j, dist[i][j]);



	   

	    
	    for (int i = 0; i < n; i++) {
	      for (int j = 0; j < n; j++) {
	        List<Integer> path = solver.reconstructShortestPath(i, j);
	        String str;
	        if (path == null) {
	          str = "Tiene ∞ número de soluciones! ";
	        } else if (path.size() == 0) {
	          str = String.format("No existe (nodo %d no alcanza al nodo %d)", i, j);
	        } else {
	          str = String.join(" -> ", path.stream()
	                                        .map(Object::toString)
	                                        .collect(java.util.stream.Collectors.toList()));
	          str = "es: [" + str + "]";
	        }

	        System.out.printf("El camino más corto desde el nodo  %d hasta el nodo %d %s\n", i, j, str);
	      }
	    }

	   
	  }
}
