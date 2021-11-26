package AttackPathGenerator;


import java.util.HashMap;
import java.util.Map;

public class Graph {
 
	public Map<String,Vertex> vertexes;
	
	public Graph()
	{
		vertexes = new HashMap<String, Vertex>();
	}
	
	public Graph(Graph g)
	{
		this.vertexes = new HashMap<String, Vertex>(g.getVertexes());
	}
	
	public Map<String,Vertex> getVertexes()
	{
		return this.vertexes;
	}
	
	public void addElem(Vertex v) {
		vertexes.put(v.itself.id, v);
	}
	
	public void rmElem(Vertex v)
	{
		vertexes.remove(v.itself.id, v);
	}
	
	public void addTotallyElem(Vertex v)
	{
		for(Vertex i : v.getPreV())
		{
			i.addNextNode(v);
		}
		addElem(v);
	}
	
	public void rmTotallyElem(Vertex v)
	{
		for(Vertex i : v.getPreV())
		{
			i.rmNextNode(v);
		}
		rmElem(v);
	}
	
	public void showInfo()
	{
		System.out.printf("Vertex nums: %d\n", vertexes.size());
		for(Map.Entry<String, Vertex> v : vertexes.entrySet())
		{
			v.getValue().showInfo();
		}
	}
	
	public void showDetailInfo()
	{
		System.out.printf("Vertex nums: %d\n", vertexes.size());
		for(Map.Entry<String, Vertex> v : vertexes.entrySet())
		{
			v.getValue().showDetailInfo();
		}
	}
	
	public void addEdge(String sourceID, String targetID)
	{
		if(vertexes.containsKey(sourceID) && vertexes.containsKey(targetID)) {
			vertexes.get(sourceID).addNextNode(vertexes.get(targetID));
			vertexes.get(targetID).addPreNode(vertexes.get(sourceID));
		}
	}
	

}
