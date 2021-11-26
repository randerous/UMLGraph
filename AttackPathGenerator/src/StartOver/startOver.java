package StartOver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import AttackPathGenerator.AttackPath;
import AttackPathGenerator.Graph;
import AttackPathGenerator.Node;
import AttackPathGenerator.simplifier;
import AttackPathGenerator.umlParser;
import CriticalNodesGenerator.CriticalNodes;
import EvaluationWork.connectAreaNum;
import EvaluationWork.totalEvaluation;
import Visualization.visualizeGraph;
import adjustmentWork.DeleteOneControlNode;
import controlNodeWork.setNewGraph;

public class startOver {
	
	public static void main(String[] args) throws Exception {
		
		Graph G = new Graph(); //存储抽取出来的最原始图
		Graph handledGraph = new Graph(); //存储经过处理的图
		Graph basicGraph = new Graph();
		AttackPath paths = new AttackPath();
		List<Node> criticalNode = new ArrayList();
		int Max = 10000; //可以接受的最大路径数量
		
		boolean AttackPathTarget = true; //从G生成攻击路径数量Num小于1w，则AttackPathTarget=false.
		int N = 2;
		
		//国涛算法1：生成连通图G
		umlParser umlParser = new umlParser();
		simplifier  simplifier = new simplifier();
		G = umlParser.genGraph("../test/test.uml");	
//		G = umlParser.genGraph("UMLmodels.uml");	
		G = simplifier.simplify(G);

		umlParser umlParser2 = new umlParser();
		simplifier  simplifier2 = new simplifier();
		handledGraph = umlParser2.genGraph("../test/test.uml");	
//		handledGraph = umlParser2.genGraph("UMLmodels.uml");	
		handledGraph = simplifier2.simplify(handledGraph);
		

		
		while(AttackPathTarget) {
			//国涛算法2：从G生成攻击路径数量
			paths.genPath(G);
			paths.showInfo();
			
			if(paths.pathSet.size() < Max) {
				AttackPathTarget = false;
			}
			
			if(AttackPathTarget) {
				//雅妮算法1：从图G提取N个韧性控制点
				CriticalNodes cNode =new CriticalNodes(G);
				List<Node> NodeS = cNode.getCriticalNodes();
				
				//将这N个韧性控制点加入List
				criticalNode.add(NodeS.get(0));
				criticalNode.add(NodeS.get(1));
				
				setNewGraph s = new setNewGraph();
				//从图G中删去这N个韧性控制点的入度连接：adjustment
				G = s.applyControlNode(G, criticalNode);
				
			}
		}
		
		//将剩余韧性控制点集合加入list
		//雅妮算法2：从G和攻击路径paths选择剩余韧性控制点集合
		CriticalNodes cNodes=new CriticalNodes(G);
		criticalNode = cNodes.GetTwoNodesInOnePath(paths.pathSet);
		for(Node node:criticalNode)
			System.out.println(node.getID());
		
//		visualize graph
		String inputDot="D://input.dot";
		visualizeGraph.dotGenerator(G,criticalNode,inputDot);
		
		//!!!
//		connectAreaNum name = new connectAreaNum();
//		name.setConnectNum(basicGraph);
//		System.out.print(name.getConnectNum());
		
		//评估
		totalEvaluation evaluator = new totalEvaluation();
		evaluator.showEvaluationResult(G, paths, criticalNode);
		
		//!!!
//		name.setConnectNum(basicGraph);
//		System.out.print(name.getConnectNum());
		
		//接收选择意见
        System.out.println("您是否满意当前隔离域数量？如满意请输入y，否则请输入n");
        char choice = ' ';
        
        choice = (char)System.in.read();

        //优化处理
        if (choice == 'y') { //接受
        	for(int i = 0; i < criticalNode.size(); i++) {
        		System.out.println(criticalNode.get(i).getID());
        	}
        	
        	System.out.println("Over!");
        }
        else { //不接受
            System.out.println("请输入隔离域数量上限");
        	int areaNum = 0; //隔离域数量上限
        	
        	Scanner in = new Scanner(System.in);
        	areaNum = in.nextInt();
        	  
            //删减韧性点，判断连通域数量
            connectAreaNum con = new connectAreaNum();
            setNewGraph set = new setNewGraph();
            DeleteOneControlNode oneNode = new DeleteOneControlNode();

        	boolean target = true;
            while (target) {
                /**
                 * 假定最佳韧性控制点数量m>最少数量n（实际上也是>=）
                 * 从m开始，算隔离域数量，所有m的都不行，就m-1，直到n
                 */
        		umlParser umlParser3 = new umlParser();
        		simplifier  simplifier3 = new simplifier();
        		basicGraph = umlParser3.genGraph("../test/test.uml");	
//        		handledGraph = umlParser3.genGraph("UMLmodels.uml");	
        		basicGraph = simplifier3.simplify(handledGraph);
            	
            	basicGraph = set.applyControlNode(basicGraph, criticalNode); //将新的控制点集合应用到图上
            	con.setConnectNum(basicGraph);
            	System.out.print(con.getConnectNum()); //14
            	
        		con.setConnectNum(basicGraph); //计算图的连通域数量
            	int numsize = con.getConnectNum(); //获取连通域数量
        		
        		if(numsize <= areaNum) { //隔离域数量小于areaNum
            		target = false;
            		System.out.print("达到要求\n");
            		for(int i = 0; i < criticalNode.size(); i++) {
                		System.out.println(criticalNode.get(i).getID());
                	}
                	
                	System.out.println("Over!");
                	break;
            	}
            	
        		if(criticalNode.size() == 0 && numsize > areaNum) {
            		System.out.print("达不到要求");
            		break;
            	}
        		
            	criticalNode = oneNode.delete(G, criticalNode); //从控制点集合删去一个点
            }
        }   
        
	}
	
}
