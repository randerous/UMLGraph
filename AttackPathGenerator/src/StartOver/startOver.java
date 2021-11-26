package StartOver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import AttackPathGenerator.AttackPath;
import AttackPathGenerator.Graph;
import AttackPathGenerator.Node;
import CriticalNodesGenerator.CriticalNodes;
import EvaluationWork.totalEvaluation;
import Visualization.visualizeGraph;

public class startOver {
	
	public static void main(String[] args) throws Exception {
		
		Graph G = new Graph();
		AttackPath paths = new AttackPath();
		List<Node> list = new ArrayList();
		
		boolean AttackPathTarget = true; //从G生成攻击路径数量Num小于1w，则AttackPathTarget=false.
		int N = 2;
		
		//国涛算法1：生成连通图G
		
		
		while(AttackPathTarget) {
			//国涛算法2：从G生成攻击路径数量
			target = A(G);
			
			if(AttackPathTarget) {
				
				//雅妮算法1：从图G提取N个韧性控制点
				
				//从图G中删去这N个韧性控制点的入度连接：adjustment
				
				//将这N个韧性控制点加入List
			}else {
				//生成的攻击路径存入paths中
				
			}
		}
		
		//雅妮算法2：从G和攻击路径paths选择剩余韧性控制点集合
		CriticalNodes cNodes=new CriticalNodes(G);
		List<Node> criticalNode=cNodes.GetTwoNodesInOnePath(paths.pathSet);
		for(Node node:criticalNode)
			System.out.println(node.id);
		
//		visualize graph
		String inputDot="D://input.dot";
		visualizeGraph.dotGenerator(G,criticalNode,inputDot);
		
		//将剩余韧性控制点集合加入list
		
		//评估
		totalEvaluation evaluator = new totalEvaluation();
		evaluator.showEvaluationResult(G, paths, criticalNode);
		
		//接收选择意见
        System.out.println("您是否满意当前隔离域数量？是请输入y，否请输入n");
        char choice = ' ';
        try {
            choice = (char)System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //循环.....！！！
        //优化处理
        if (choice == 'y') { //接受
        	for(int i = 0; i < criticalNode.size(); i++) {
        		System.out.println(criticalNode.get(i).getId());
        	}
        	
        	System.out.println("Over!");
        }
        else { //不接受
            System.out.println("请输入隔离域数量上限");
        	int areaNum = 0;
            try {
                areaNum = (int)System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        	
        	boolean target = true;
            while (target) {
                /**
                 * 假定最佳韧性控制点数量m>最少数量n（实际上也是>=）
                 * 从m开始，算隔离域数量，所有m的都不行，就m-1，直到n
                 */
            	
            	if() { //隔离域数量小于areaNum
            		target = false;
            	}else {
            		//从list中选择一个点删去
            		
            	
            	}
            	
            }
        }   
        
        //输出list及全部信息
	}
	
}
