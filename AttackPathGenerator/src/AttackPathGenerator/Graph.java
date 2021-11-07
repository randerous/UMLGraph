package AttackPathGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Graph {
 
	public Map<String,Vertex> vertexes;
	
	public Graph()
	{
		vertexes = new HashMap();
		 
	}
	
	public void addElem(Vertex v) {
		vertexes.put(v.itself.id, v);
	}
	
	public void showInfo()
	{
		System.out.printf("Vertex nums: %d\n", vertexes.size());
		for(Map.Entry<String, Vertex> v : vertexes.entrySet())
		{
			v.getValue().showInfo();
		}
	}
	
	public void addEdge(String sourceID, String targetID)
	{
		if(vertexes.containsKey(sourceID) && vertexes.containsKey(targetID)) {
			vertexes.get(sourceID).next_vertexes.
			add((vertexes.get(targetID)));	
		}
	}
}
