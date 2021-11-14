package AttackPathGenerator;

import java.util.ArrayList;


//Equilvalence for Node 
public class Equivalence {
	int id; 
	ArrayList <Integer> vertexes;
	
	public Equivalence(int id )
	{
		this.id = id; 
		vertexes = new ArrayList<Integer>();
	}
	
	public void add(int v)
	{
		vertexes.add(v);
	}
	
	public void showInfo()
	{
		System.out.printf("Merge id:%d \n", id);
		for(Integer v: vertexes)
		{
			System.out.println(v.toString());
		}
	}

}
