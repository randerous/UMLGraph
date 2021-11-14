package AttackPathGenerator;

 
import java.util.HashSet;
import java.util.Set;

public class Vertex {
    int type; // 0: surface, 1: Node, 2: Asset
    Node itself;// keep current Node info
    Set<Vertex> next_vertexes;
    
    public Vertex(int type) {
    	this.type = type;
    	next_vertexes = new HashSet<Vertex>();
    }
    
    public void setSelfNode(Node n) {
    	this.itself = n;
    }
    
    public void addNode(Vertex n) {
    	this.next_vertexes.add(n);
    }
    
    public void showDetailInfo() {
    	System.out.printf("type: %d\n", type);
    	itself.showDetailNodeInfo();
    	System.out.printf("edge nums: %d\n", next_vertexes.size());
    	for(Vertex n: next_vertexes) {
    		System.out.printf("edge end: \n");
    		n.itself.showDetailNodeInfo();
    	}
    	System.out.println();
    }
    
    public void showInfo() {
    	System.out.printf("type: %d\n", type);
    	itself.showNodeInfo(); 
    }
}
