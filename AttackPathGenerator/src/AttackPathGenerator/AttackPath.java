package AttackPathGenerator;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList; 
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;

import UMLgenerator.UMLgenerator;
public class AttackPath {
	public ArrayList <ArrayList<Vertex>> pathSet;
	Stack<Vertex> path;
	public Set<String> visit;
	public Set<String> invalid;//store nodes that won't get to target
 
	
	public AttackPath()
	{
		pathSet = new ArrayList<ArrayList<Vertex>>();
		path = new Stack<Vertex>();
		visit = new HashSet<String>();
		invalid = new HashSet<String>();
		 
		 
				
	}
	
	public void addPath(Stack <Vertex> p)
	{
		ArrayList<Vertex> path = new ArrayList<Vertex>();
		for(Vertex v: p) {
			path.add(v);
		}
		pathSet.add(path);
	}
	
	
	private void dfs(Vertex cur, Vertex dest, int maxSize)  
	{

//        out.write(cur.getName()+"\n");
        
//		System.out.println(visit.size());
//		System.out.println(cur.getName()+" "+cur.getItself().getID());
//		if(pathSet.size() > 1000000) return;
//		System.out.println(cur.getName());
		if(pathSet.size() == maxSize+1)
			return;
		if(cur.itself.id == dest.itself.id) {
			path.add(cur);
			addPath(path);
//			System.out.println(pathSet.size());
			if(UMLgenerator.DEBUG && pathSet.size()%1000000 == 0)
			{
				System.out.println(pathSet.size());
			}
			path.remove(cur);
			return;
		}
		visit.add(cur.itself.id);
		path.add(cur);
		 
		for(Vertex v: cur.next_vertexes)
		{
			if(!visit.contains(v.itself.id))
			{
				dfs(v,dest, maxSize);
			}
		}
		visit.remove(cur.itself.id); 
		path.remove(cur);		
	}
	
	public int getNums()
	{
		return pathSet.size();
	}
	Boolean dfs1(Set<Vertex> vi, Vertex source ,Vertex dest)
	{
		if(source.itself.id == dest.itself.id)
			return true;
		vi.add(source);
//		System.out.println(source.getName());
		for(Vertex v: source.getNextV())
		{
			if( !vi.contains(v) && dfs1(vi, v, dest))
				return true;
		}
		vi.remove(source);
		return false;
	}
	
	Boolean bfs(Set<Vertex> vi, Vertex source ,Vertex dest)
	{
		if(source.itself.id == dest.itself.id)
			return true;
		vi.add(source);
		Queue<Vertex> q = new LinkedList<Vertex>();
		q.add(source);
		while(!q.isEmpty())
		{
			Vertex v = q.poll();
			for(Vertex i: v.getNextV())
			{
				if(!vi.contains(i))
				{
					if(i.itself.id == dest.itself.id)
						return true;
					vi.add(i);
					q.add(i);
				}
			}
		}
		return false;
	}
	private ArrayList<Vertex> remove_invalidNode(Graph g,  Vertex dest)
	{
 
		
		Set<Vertex> vi = new HashSet<Vertex>();
		ArrayList<Vertex> invalid = new ArrayList<Vertex>();
		for(Map.Entry<String, Vertex> entry: g.vertexes.entrySet())
		{
			Vertex v = entry.getValue();
			if(!bfs(vi, v , dest))
				invalid.add(v);
//			System.out.println("over");
			vi.clear();
		}

		return invalid;
	}
	
	void delete_node(Graph G,  ArrayList<Vertex> invalid)
	{
		for(Vertex v: invalid)
		{
			G.rmTotallyElem(v);
		}
	}
	
	void add_node(Graph G,  ArrayList<Vertex> invalid)
	{
		for(Vertex v: invalid)
		{
			G.addTotallyElem(v);
		}
	}
	
//	private void dfs_nr(Vertex cur, Vertex dest)  
//	{
//		 
//		Stack<Set<Vertex>> next = new Stack<Set<Vertex>>();
//		path.push(cur);
//		
//		Set<Vertex> t = new HashSet<Vertex>(cur.getNextV());
//		next.push(t);
//		while(!path.empty())
//		{
//			if(next.peek().isEmpty())
//			{
////				System.out.println("empty");
//				visit.remove(path.peek().itself.id);
//				path.pop();
//				next.pop();
//				continue;
//			}
//			Set<Vertex> tmp = next.pop(); 
//			Vertex v = null; 
//			for(Vertex i: tmp)
//			{
//				v = i;
//				break;
//			}
//			tmp.remove(v); 
//			next.push(tmp);
//			
////			System.out.println(v.getName());
//			if(v.itself.id == dest.itself.id) {
//				path.push(v);
//				addPath(path);
////				System.out.println(pathSet.size());
//				if(pathSet.size()%10000 == 0)
//				{
//					System.out.println(pathSet.size());
//				}
//				path.pop(); 
//				continue;
//			}
//			visit.add(v.itself.id);
//			path.push(v);
//			
//			tmp = new HashSet<Vertex>();
//			for(Vertex i: v.getNextV())
//			{
//				if(!visit.contains(i.itself.id))
//				{
//					tmp.add(i);
////					System.out.println(i.getName());
//				}
//					
//			}
//			next.push(tmp);
//			
////			visit.remove(v.itself.id);
////			path.pop();
//		}
//			
//	}
	/*
	 * generator all path from nodes belongs exposure to nodes belongs asset
	 * (1)minimal graph, delete nodes that can't reach target asset
	 * (2)dfs
	 * (3)restore graph
	 */
	
	public void genPath(Graph p, int maxSize)  
	{
		pathSet.clear();
		path.clear();
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
		
		System.out.println("exposure"+  source.size());
		System.out.println("asset"+  destination.size());
		
		if(!source.isEmpty() && !destination.isEmpty())
		{
			for(Vertex cur: source)
			{
				for(Vertex dest: destination)
				{
//					System.out.println("--");
					ArrayList<Vertex> invalid = remove_invalidNode(p, dest);
					delete_node(p, invalid);
//					System.out.println("size"+p.vertexes.size());
					Vertex s = p.vertexes.get(cur.itself.id);
					Vertex d = p.vertexes.get(dest.itself.id);
					
//					dfs_nr(cur, dest); 
					if(s!= null && d != null)
					{
						dfs(s, d, maxSize); 
						visit.clear(); 
					}
					add_node(p, invalid);
//					System.out.println("size"+p.vertexes.size());
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
		
		boolean is_write = false;
		if(is_write) {
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
	            System.out.println("successfully");
	        } catch (IOException e) {
	        }
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
	
	public void showInfo2() {
		if(pathSet.isEmpty()) {
			System.out.println("No path");
			return;
		}
		int i=0;
		System.out.printf("path nums: %d\n\n", pathSet.size());
		for(ArrayList<Vertex> path: pathSet)
		{	
			System.out.print(i+":");
			for(Vertex v:path) {
				System.out.print(v.getItself().getID()+"->");
			}
			System.out.print("\n");
			i++;
		}
		
	}
	

}
