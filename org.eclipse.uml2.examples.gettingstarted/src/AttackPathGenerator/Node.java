package AttackPathGenerator;

import java.util.ArrayList;

public class Node {
	String id;
    String name;
    int level;// arch level
    ArrayList<Vulnerability> vulnerabilities;
    
    public Node(String id, String name, int level)
    {
    	this.id = id;
    	this.name = name;
    	this.level = level;
    	vulnerabilities = new ArrayList<Vulnerability>();
    }
    public void  addVunlerability(Vulnerability v) {
    	this.vulnerabilities.add(v);
    }
    
    public void showNodeInfo() {
    	System.out.printf("Node:\nid: %s\nname : %s\nlevel: %d\n\r", id, name, level);
    }
    
    public void showDetailNodeInfo() {
    	System.out.printf("Node:\nid: %s\nname : %s\nlevel: %d\n\r", id, name, level);
    	if(vulnerabilities.size() > 0) {
    		for(Vulnerability v: vulnerabilities) {
    			v.showInfo();
    		}
    	}
    }
    
}


