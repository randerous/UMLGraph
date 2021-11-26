package adjustmentWork;

import java.util.ArrayList;
import java.util.List;

import AttackPathGenerator.AttackPath;
import AttackPathGenerator.Graph;
import AttackPathGenerator.Node;
import EvaluationWork.connectAreaNum;
import controlNodeWork.setNewGraph;

public class DeleteOneControlNode {
	int DeleteSize;
	int one;
	
	public List<Node> delete(Graph G, List<Node> presentList){ //需要加入路径的概念
		DeleteSize = 0;
		one = 0;
		
		for(int i = 0; i < presentList.size(); i++) {

			//从G中删除该点
			Graph g = G;
			List<Node> list = new ArrayList();
			list.add(presentList.get(i));
			
			setNewGraph s = new setNewGraph();
			g = s.applyControlNode(g, list);
			
			//计算隔离域数量
			int num = 0;
			
			connectAreaNum c = new connectAreaNum();
			c.setConnectNum(g);
			num = c.getConnectNum();

			if(num > DeleteSize) {
				one = i;
			}
		}
		
		presentList.remove(presentList.get(one));
		return presentList;
	}
	
}
