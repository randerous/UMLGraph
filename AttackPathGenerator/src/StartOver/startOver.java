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
import UMLgenerator.UMLgenerator;
import Visualization.visualizeGraph;
import adjustmentWork.DeleteOneControlNode;
import controlNodeWork.setNewGraph;

public class startOver {
	
	public static void main(String[] args) throws Exception {
		
		Graph G = new Graph(); //�洢����������ͼ
		
		AttackPath paths = new AttackPath();
		List<Node> criticalNode = new ArrayList();
		int Max = 1000000; //���Խ��ܵ����·������
		
		boolean AttackPathTarget = true; //��G���ɹ���·������NumС��1w����AttackPathTarget=false.
		int N = 2;
		
		//�����㷨1��������ͨͼG
		//��������
		UMLgenerator.generator(100, 2);
		
		umlParser umlParser = new umlParser();
		simplifier  simplifier = new simplifier();
//		G = umlParser.genGraph("../test/test.uml");	
		G = umlParser.genGraph("Example_UML.uml");	
//		G = simplifier.simplify(G);
		
		Graph handledGraph = new Graph(G); //�洢��ȡ��������ԭʼͼ
		
		CriticalNodes cNode =new CriticalNodes(G);
		List<Node> NodeS = cNode.getCriticalNodes();
		
		int t = 0;
		while(AttackPathTarget && NodeS.size() > 1) {
			//�����㷨2����G���ɹ���·������
			paths.genPath(G, Max);
			paths.showInfo();
//			
			if(paths.pathSet.size() <= Max) {
				AttackPathTarget = false;
			}
			

			if(AttackPathTarget) {
				//�����㷨1����ͼG��ȡN�����Կ��Ƶ�
				
				
				//����N�����Կ��Ƶ����List
				if(NodeS.size() > 1)
				{
					criticalNode.add(NodeS.get(0));
					criticalNode.add(NodeS.get(1));
					NodeS.remove(0);
					NodeS.remove(0);
					System.out.println(NodeS.get(0).getName());
					System.out.println(NodeS.get(1).getName());
				}
				
				
				
				setNewGraph s = new setNewGraph();
				//��ͼG��ɾȥ��N�����Կ��Ƶ��������ӣ�adjustment
				G = s.applyControlNode(G, criticalNode);
				
				t++;
			}
		}
		
		//��ʣ�����Կ��Ƶ㼯�ϼ���list
		//�����㷨2����G�͹���·��pathsѡ��ʣ�����Կ��Ƶ㼯��
		CriticalNodes cNodes=new CriticalNodes(G);
		List<Node> criticalNodeS = cNodes.GetTwoNodesInOnePath(paths.pathSet);
		
		for(Node n : criticalNodeS) {
			criticalNode.add(n);
		}
		
		System.out.print("\nһ������" + t + "�Σ�������" + 2*t + "����ʼ���Կ��Ƶ㣬");
		System.out.print("�������Կ��Ƶ�����Ϊ"+criticalNodeS.size() + "������" + criticalNode.size() + "�����Կ��Ƶ�\n");
		System.out.println("���ɵ����Կ��Ƶ����£�");
		
		for(Node node:criticalNode)
			System.out.println(node.getID());
		System.out.println();
//		visualize graph
		String inputDot="D://input.dot";
		visualizeGraph.dotGenerator(G,criticalNode,inputDot);
		
		//����
		
		totalEvaluation evaluator = new totalEvaluation();
		evaluator.showEvaluationResult(G, paths, criticalNode);
		
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
        		Graph basicGraph = new Graph(handledGraph);
            	
            	basicGraph = set.applyControlNode(basicGraph, criticalNode); //���µĿ��Ƶ㼯��Ӧ�õ�ͼ��
            	con.setConnectNum(basicGraph);
//            	System.out.print(con.getConnectNum()); 
            	
        		con.setConnectNum(basicGraph); //����ͼ����ͨ������
            	int numsize = con.getConnectNum(); //��ȡ��ͨ������
        		
        		if(numsize <= areaNum) { //����������С��areaNum
            		target = false;
            		System.out.print("���¿��Ƶ㼯�ϴﵽҪ��\n");
            		for(int i = 0; i < criticalNode.size(); i++) {
                		System.out.println(criticalNode.get(i).getID());
                	}
                	
                	System.out.println("��"+ criticalNode.size() + "��\nOver!");
                	break;
            	}
            	
        		if(criticalNode.size() == 0 && numsize > areaNum) {
            		System.out.print("�޷��ﵽҪ��..\nOver!");
            		break;
            	}
        		
//            	criticalNode = oneNode.delete(G, criticalNode, paths); //�ӿ��Ƶ㼯��ɾȥһ����
        		criticalNode = oneNode.delete(G, criticalNode); //�ӿ��Ƶ㼯��ɾȥһ����
            }
        }   
        
	}
	
}