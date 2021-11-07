package AttackPathGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class AttackPath {
	public ArrayList <ArrayList<Vertex>> pathSet;
	Stack<Vertex> path;
	public Map<String,Boolean> visit;
	
	public AttackPath()
	{
		pathSet = new ArrayList();
		path = new Stack();
		visit = new HashMap();
				
	}
	
	public void addPath(Stack <Vertex> p)
	{
		ArrayList<Vertex> path = new ArrayList();
		for(Vertex v: p) {
			path.add(v);
		}
		pathSet.add(path);
	}
	
	
	private void dfs(Vertex cur, Vertex dest)
	{
		if(cur.itself.id.equals(dest.itself.id)) {
			addPath(path);
			return;
		}
		visit.put(cur.itself.id, true);
		path.add(cur);
		
		for(Vertex v: cur.next_vertexes)
		{
			if(!visit.containsKey(v.itself.id))
			{
				dfs(v,dest);
			}
		}
		visit.remove(cur.itself.id);
		path.remove(cur);		
	}
	
	public void genPath(Graph p)
	{
		ArrayList<Vertex> source = new ArrayList();
		ArrayList<Vertex> destination = new ArrayList();
		for(Map.Entry<String, Vertex> v : p.vertexes.entrySet())
		{
			if(v.getValue().type == 0)
				source.add(v.getValue());
			if(v.getValue().type == 2)
				destination.add(v.getValue());
		}
		
		if(!source.isEmpty() && !destination.isEmpty())
		{
			for(Vertex cur: source)
			{
				for(Vertex dest: destination)
				{
					dfs(cur, dest);
				}
			}
		}
	}
	
	public void showInfo()
	{
		if(pathSet.isEmpty()) {
			System.out.println("No path");
			return;
		}
		
		
		System.out.printf("path nums: %d\n\n", pathSet.size());
		int i = 0;
		for(ArrayList<Vertex> path: pathSet)
		{
			System.out.printf("path: %d\n\n", i++);
			for(Vertex v: path) {
				v.showInfo();
			}
			System.out.println("end path\n");
		}
	}
	
	
	

}
