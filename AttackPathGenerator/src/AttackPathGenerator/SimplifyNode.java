package AttackPathGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Comparator;

public class SimplifyNode {
	ArrayList <Integer> preNodes;
	ArrayList <Integer> postNodes;
	int id;
	
	public SimplifyNode( int id)
	{
		preNodes = new ArrayList<Integer>();
		postNodes = new ArrayList<Integer>();
 
		this.id = id;
	}
	
	public void addPreNode(int v)
	{
		preNodes.add(v);
	}
	
	public void addPostNode(int v)
	{
		postNodes.add(v);
	}
	
	public void sortNodes()
	{
		Collections.sort(preNodes);
		Collections.sort(postNodes); 
	}
	
	public void showInfo()
	{
		System.out.printf("id : %d\n", id);
		for(Integer i: preNodes)
		{
			System.out.printf("pre : %d\n", i);
		}
		for(Integer i: postNodes)
		{
			System.out.printf("post : %d\n", i);
		}
		System.out.println();
	}

}
