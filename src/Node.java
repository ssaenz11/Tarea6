
public class Node {
	
	 public Node parent;
     public int cost;
     public int id;

     public Node(Node parent, int cost, int id) {
         this.parent = parent;
         this.cost = cost;
         this.id = id;
     }

}
