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
		
		Graph G = new Graph(); //�洢��ȡ��������ԭʼͼ
		Graph handledGraph = new Graph(); //�洢����������ͼ
		Graph basicGraph = new Graph();
		AttackPath paths = new AttackPath();
		List<Node> criticalNode = new ArrayList();
		int Max = 10000; //���Խ��ܵ����·������
		
		boolean AttackPathTarget = true; //��G���ɹ���·������NumС��1w����AttackPathTarget=false.
		int N = 2;
		
		//�����㷨1��������ͨͼG
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
			//�����㷨2����G���ɹ���·������
			paths.genPath(G);
			paths.showInfo();
			
			if(paths.pathSet.size() < Max) {
				AttackPathTarget = false;
			}
			
			if(AttackPathTarget) {
				//�����㷨1����ͼG��ȡN�����Կ��Ƶ�
				CriticalNodes cNode =new CriticalNodes(G);
				List<Node> NodeS = cNode.getCriticalNodes();
				
				//����N�����Կ��Ƶ����List
				criticalNode.add(NodeS.get(0));
				criticalNode.add(NodeS.get(1));
				
				setNewGraph s = new setNewGraph();
				//��ͼG��ɾȥ��N�����Կ��Ƶ��������ӣ�adjustment
				G = s.applyControlNode(G, criticalNode);
				
			}
		}
		
		//��ʣ�����Կ��Ƶ㼯�ϼ���list
		//�����㷨2����G�͹���·��pathsѡ��ʣ�����Կ��Ƶ㼯��
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
		
		//����
		totalEvaluation evaluator = new totalEvaluation();
		evaluator.showEvaluationResult(G, paths, criticalNode);
		
		//!!!
//		name.setConnectNum(basicGraph);
//		System.out.print(name.getConnectNum());
		
		//����ѡ�����
        System.out.println("���Ƿ����⵱ǰ������������������������y������������n");
        char choice = ' ';
        
        choice = (char)System.in.read();

        //�Ż�����
        if (choice == 'y') { //����
        	for(int i = 0; i < criticalNode.size(); i++) {
        		System.out.println(criticalNode.get(i).getID());
        	}
        	
        	System.out.println("Over!");
        }
        else { //������
            System.out.println("�������������������");
        	int areaNum = 0; //��������������
        	
        	Scanner in = new Scanner(System.in);
        	areaNum = in.nextInt();
        	  
            //ɾ�����Ե㣬�ж���ͨ������
            connectAreaNum con = new connectAreaNum();
            setNewGraph set = new setNewGraph();
            DeleteOneControlNode oneNode = new DeleteOneControlNode();

        	boolean target = true;
            while (target) {
                /**
                 * �ٶ�������Կ��Ƶ�����m>��������n��ʵ����Ҳ��>=��
                 * ��m��ʼ�������������������m�Ķ����У���m-1��ֱ��n
                 */
        		umlParser umlParser3 = new umlParser();
        		simplifier  simplifier3 = new simplifier();
        		basicGraph = umlParser3.genGraph("../test/test.uml");	
//        		handledGraph = umlParser3.genGraph("UMLmodels.uml");	
        		basicGraph = simplifier3.simplify(handledGraph);
            	
            	basicGraph = set.applyControlNode(basicGraph, criticalNode); //���µĿ��Ƶ㼯��Ӧ�õ�ͼ��
            	con.setConnectNum(basicGraph);
            	System.out.print(con.getConnectNum()); //14
            	
        		con.setConnectNum(basicGraph); //����ͼ����ͨ������
            	int numsize = con.getConnectNum(); //��ȡ��ͨ������
        		
        		if(numsize <= areaNum) { //����������С��areaNum
            		target = false;
            		System.out.print("�ﵽҪ��\n");
            		for(int i = 0; i < criticalNode.size(); i++) {
                		System.out.println(criticalNode.get(i).getID());
                	}
                	
                	System.out.println("Over!");
                	break;
            	}
            	
        		if(criticalNode.size() == 0 && numsize > areaNum) {
            		System.out.print("�ﲻ��Ҫ��");
            		break;
            	}
        		
            	criticalNode = oneNode.delete(G, criticalNode); //�ӿ��Ƶ㼯��ɾȥһ����
            }
        }   
        
	}
	
}
