package org.eclipse.uml2.examples.gettingstarted;

import java.util.ArrayList;

public class Graph {
	public ArrayList<Vertex> vertexes;
	
	public Graph()
	{
		vertexes = new ArrayList<Vertex>();
		 
	}
	
//	public void Check()
//	{
//		System.out.println("fasd");
//	}
	
	public void addElem(Vertex v) {
		vertexes.add(v);
	}
	
	public void showInfo()
	{
		System.out.printf("Vertex nums: %d\n", vertexes.size());
		for(Vertex v : vertexes)
		{
			v.showInfo();
		}
	}
	
	public void addEdge(String sourceID, String targetID)
	{
		for(Vertex vi : vertexes)
		{
			if(vi.itself.id.equals(sourceID) ) {
				for(Vertex vj : vertexes)
				{
					if(vj.itself.id.equals(targetID)) {
						vi.next_vertexes.add(vj.itself);
						break;
					}
				}
				break;
			}
		}
	}
}
