package adjustmentWork;

import java.util.List;

import AttackPathGenerator.Graph;
import AttackPathGenerator.Node;

public class DeleteOneControlNode {
	int DeleteSize;
	String one;
	
	public List<Node> delete(Graph G, List<Node> presentList){
		DeleteSize = 0;
		one = new String();
		
		for(int i = 0; i < presentList.size(); i++) {
			//��G��ɾ���õ�
			
			//�������������
			int num = 0;
			
			if(num > DeleteSize) {
				one = presentList.get(i).getID();
			}
		}
		
		presentList.remove(one);
		return presentList;
	}
	
}
