package org.eclipse.uml2.examples.gettingstarted;

import java.util.ArrayList;

public class Vertex {
    int type; // 0: surface, 1: Node, 2: Asset
    Node itself;// keep current Node info
    ArrayList<Node> next_vertexes;
    
    public Vertex(int type) {
    	this.type = type;
    	next_vertexes = new ArrayList<Node>();
    }
    
    public void setSelfNode(Node n) {
    	this.itself = n;
    }
    
    public void addNode(Node n) {
    	this.next_vertexes.add(n);
    }
    
    public void showInfo() {
    	System.out.printf("type: %d\n", type);
    	itself.showNodeInfo();
    	for(Node n: next_vertexes) {
    		System.out.printf("edge end: \n");
    		n.showNodeInfo();
    	}
    	System.out.println();
    }
}
