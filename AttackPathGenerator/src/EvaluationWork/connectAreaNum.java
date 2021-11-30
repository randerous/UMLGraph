package EvaluationWork;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Queue;

import java.util.Iterator;

import AttackPathGenerator.Graph;
import AttackPathGenerator.Vertex;

public class connectAreaNum {
	public int connectNum;
	ArrayList<areaNumTargetStructure>  mark;
	
	Set<String> visit;
	
	int parent[];
	
    public int getConnectNum() {
        return connectNum;
    }

    /**
     * ������ͨ��ͼ������������������
     * @param G
     */
    public void setConnectNum (Graph G) {
        //��ʶ����
        int num = G.vertexes.size();
        
        parent = new int[num];
		for(int j = 0; j < num; j++)
			parent[j] = j;
		
		
		int inum = 0;
		Map<String, Integer> conId = new HashMap<String, Integer>();
		
		Map<Integer, String> Idcon = new HashMap<Integer, String> ();
		for(Map.Entry<String, Vertex> entry: G.vertexes.entrySet())
		{
//			System.out.printf("%s\n", entry.getValue().getName());
			Idcon.put(inum,entry.getValue().getItself().getID());
			conId.put(entry.getValue().getItself().getID(), inum++);
		}
		//System.out.println("");
		for(Map.Entry<String, Vertex> entry: G.vertexes.entrySet())
		{
//			System.out.printf("%s\n", entry.getValue().getName());
			Vertex source = entry.getValue();
			for(Vertex v: source.getNextV())
			{
				int pa = getParent( conId.get(source.getItself().getID()));
				int pb = getParent( conId.get(v.getItself().getID()));
				if(pa != pb)
				{
					parent[pb] = pa;
//					System.out.printf("merge %s %s\n", entry.getValue().getName(), v.getName() );
				}	
			}
		}
		
		Set<Integer> connectNums = new HashSet<Integer>();
		
		//ˢ�²��鼯��ʹ����������
		for(int i = 0; i < num; i++)
		{
			getParent(i);
		}
		
		//ͳ�Ʋ��鼯�еļ�����
		for(int i = 0; i < num; i++)
		{
//			getParent(i);
			connectNums.add(parent[i]);
//			System.out.printf("parent : %d\n", parent[i]);
		}
		//�鿴���鼯�е�Ԫ��
//		for(Integer i: connectNums)
//		{
//			//System.out.printf("%d\n", i);
//			for(int j = 0; j < num; j++)
//			{
//				if(parent[j] == i)
//				{
//					String id = Idcon.get(j);
//					String name = G.vertexes.get(id).getName();
//					//System.out.printf("%s\n", name);
//				}
//			}
//		}
		//System.out.printf("liantongyu : %d\n", connectNums.size());

        this.connectNum =  connectNums.size();
    }
    
	private int getParent(int index) {
		if(index == parent[index])
			return index;
		return parent[index] = getParent(parent[index]);
	}

    /**
     * ������ȱ�����������ȱ�����Ч����һ����
     * ��������������ô����
     * @param G
     * @param s idΪI�Ľڵ�
     */
    private void VisitNode (Graph G) {
    	
    	
    	Set<String> keySett = G.vertexes.keySet();
    	Iterator<String> itt =keySett.iterator();
    	
    	String s = new String();
    	
    	while(itt.hasNext()) {
    		String keyy = itt.next();
    		
    		if(!visit.contains(keyy)) {
    			s = keyy;
    			break;
    		}
    	}

    	Queue<String> queue = new ArrayDeque();
    	
    	queue.add(s);
    	
    	//������ȱ���
    	while(!queue.isEmpty()) {
    		//���Ϊ1���Ѿ����ʹ�
    		String present = queue.poll();
    		visit.add(present);
    		
    		for(Vertex v : G.vertexes.get(present).getNextV()) {
    			if(!visit.contains(v.getItself().getID())) {
    				queue.add(v.getItself().getID());
    			}
    		}	
    	}
    }
}
