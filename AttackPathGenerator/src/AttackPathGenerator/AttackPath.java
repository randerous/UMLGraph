package AttackPathGenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList; 
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
		pathSet = new ArrayList<ArrayList<Vertex>>();
		path = new Stack<Vertex>();
		visit = new HashSet<String>();
				
	}
	
	public void addPath(Stack <Vertex> p)
	{
		ArrayList<Vertex> path = new ArrayList<Vertex>();
		for(Vertex v: p) {
			path.add(v);
		}
		pathSet.add(path);
	}
	
	
	private void dfs(Vertex cur, Vertex dest)
	{
//		if(pathSet.size() > 1000000) return;
//		System.out.println(cur.getName());
		if(cur.itself.id == dest.itself.id) {
			path.add(cur);
			addPath(path);
			if(pathSet.size()%100 == 0)
			{
				System.out.println(pathSet.size());
			}
			
			
			path.remove(cur);
			return;
		}
		visit.add( cur.itself.id);
		path.add(cur);
		
		for(Vertex v: cur.next_vertexes)
		{
			if(!visit.contains(v.itself.id))
			{
				dfs(v,dest);
			}
		}
		visit.remove( cur.itself.id);
		path.remove(cur);		
	}
	
	public void genPath(Graph p)
	{
		ArrayList<Vertex> source = new ArrayList<Vertex>();
		ArrayList<Vertex> destination = new ArrayList<Vertex>();
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
		
		try {
            BufferedWriter out = new BufferedWriter(new FileWriter("path.txt"));
            out.write("path nums:"+pathSet.size()+"\n");
            
            
    		int i = 0;
    		for(ArrayList<Vertex> path: pathSet)
    		{
    			out.write("path: " + i + "\n");
    			i++;
    			for(Vertex v: path) {
    				out.write(v.getName());
    			}
    			out.write("end path\n");
    		}
    		out.close();
            System.out.println("文件创建成功！");
        } catch (IOException e) {
        }
//		int i = 0;
//		for(ArrayList<Vertex> path: pathSet)
//		{
//			System.out.printf("path: %d\n\n", i++);
//			for(Vertex v: path) {
//				v.showInfo();
//			}
//			System.out.println("end path\n");
//		}
	}
	
	
	

}
