package adjustmentWork;

import java.util.ArrayList;
import java.util.List;

import AttackPathGenerator.AttackPath;
import AttackPathGenerator.Graph;
import AttackPathGenerator.Node;
import AttackPathGenerator.Vertex;
import EvaluationWork.connectAreaNum;
import controlNodeWork.setNewGraph;

public class DeleteOneControlNode {
	int DeleteSize;
	int one;
	
	/**
	 * ������ÿ��·������һ���㣬����ʱ�临�Ӷ�̫�ߣ�Ӧ����д�����⣬�����ÿռ任ʱ��
	 * @param G
	 * @param presentList
	 * @param paths
	 * @return
	 */
	public List<Node> delete(Graph G, List<Node> presentList, AttackPath paths){ //��Ҫ����·���ĸ���
		DeleteSize = 0;
		one = 0;
		
		for(int i = 0; i < presentList.size(); i++) {

			//��G��ɾ���õ�
			Graph g = G;
			List<Node> list = new ArrayList();
			list.add(presentList.get(i));
			
			setNewGraph s = new setNewGraph();
			g = s.applyControlNode(g, list);
			
			//�������������
			int num = 0;
			
			connectAreaNum c = new connectAreaNum();
			c.setConnectNum(g);
			num = c.getConnectNum();

			if(num > DeleteSize) {
				
				for(ArrayList<Vertex> v : paths.pathSet) { //ÿ��·��
					int otherControlNodeNum = 0;
					for(Vertex n : v) { //·����ÿ����
						if(n.getItself().getID().equals(presentList.get(i).getID())) { //�����õ�
							for(Node node : presentList) {
								for(Vertex nn : v) {
									if(nn.getItself().getID().equals(node.getID())) { //·���Ƿ��������������õ�
										otherControlNodeNum++;
									}
								}
								
							}
						}
						
						
					}
					
					if(otherControlNodeNum > 1) one = i;
				}
				
				
				
//				one = i;
			}
		}
		
		presentList.remove(presentList.get(one));
		return presentList;
	}
	
	/**
	 * û����ÿ��·��������һ����
	 * @param G
	 * @param presentList
	 * @return
	 */
	public List<Node> delete(Graph G, List<Node> presentList, List<Node> basicList){ //��Ҫ����·���ĸ���
		DeleteSize = 0;
		one = 0;
		
		for(int i = 0; i < presentList.size(); i++) {

			//��G��ɾ���õ�
			Graph g = G;
			List<Node> list = new ArrayList();
			list.add(presentList.get(i));
			
			setNewGraph s = new setNewGraph();
			g = s.applyControlNode(g, list);
			
			//�������������
			int num = 0;
			
			connectAreaNum c = new connectAreaNum();
			c.setConnectNum(g);
			num = c.getConnectNum();

			if(!basicList.contains(presentList.get(one))) one = i;
		}
		
		presentList.remove(presentList.get(one));
		return presentList;
	}
}
