package AttackPathGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class AttackPath {
	public ArrayList <ArrayList<Vertex>> pathSet;
	Stack<Vertex> path;
	public Set<String> visit;
	
	public AttackPath()
	{
		pathSet = new ArrayList();
		path = new Stack();
		visit = new HashSet<String>();
				
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
			path.add(cur);
			addPath(path);
			path.remove(cur);
			return;
		}
		visit.add(cur.itself.id);
		path.add(cur);
		
		for(Vertex v: cur.next_vertexes)
		{
			if(!visit.contains(v.itself.id))
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
			{
				source.add(v.getValue());
//				System.out.println(v.getValue().itself.name);
			}
				
			if(v.getValue().type == 2)
			{
//				System.out.println(v.getValue().itself.name);
				destination.add(v.getValue());
			}
				
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
