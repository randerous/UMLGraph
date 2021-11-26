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
		
		boolean AttackPathTarget = true; //��G���ɹ���·������NumС��1w����AttackPathTarget=false.
		int N = 2;
		
		//�����㷨1��������ͨͼG
		
		
		while(AttackPathTarget) {
			//�����㷨2����G���ɹ���·������
			target = A(G);
			
			if(AttackPathTarget) {
				
				//�����㷨1����ͼG��ȡN�����Կ��Ƶ�
				
				//��ͼG��ɾȥ��N�����Կ��Ƶ��������ӣ�adjustment
				
				//����N�����Կ��Ƶ����List
			}else {
				//���ɵĹ���·������paths��
				
			}
		}
		
		//�����㷨2����G�͹���·��pathsѡ��ʣ�����Կ��Ƶ㼯��
		CriticalNodes cNodes=new CriticalNodes(G);
		List<Node> criticalNode=cNodes.GetTwoNodesInOnePath(paths.pathSet);
		for(Node node:criticalNode)
			System.out.println(node.id);
		
//		visualize graph
		String inputDot="D://input.dot";
		visualizeGraph.dotGenerator(G,criticalNode,inputDot);
		
		//��ʣ�����Կ��Ƶ㼯�ϼ���list
		
		//����
		totalEvaluation evaluator = new totalEvaluation();
		evaluator.showEvaluationResult(G, paths, criticalNode);
		
		//����ѡ�����
        System.out.println("���Ƿ����⵱ǰ��������������������y����������n");
        char choice = ' ';
        try {
            choice = (char)System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //ѭ��.....������
        //�Ż�����
        if (choice == 'y') { //����
        	for(int i = 0; i < criticalNode.size(); i++) {
        		System.out.println(criticalNode.get(i).getId());
        	}
        	
        	System.out.println("Over!");
        }
        else { //������
            System.out.println("�������������������");
        	int areaNum = 0;
            try {
                areaNum = (int)System.in.read();
            } catch (IOException e) {
                e.printStackTrace();
            }
        	
        	boolean target = true;
            while (target) {
                /**
                 * �ٶ�������Կ��Ƶ�����m>��������n��ʵ����Ҳ��>=��
                 * ��m��ʼ�������������������m�Ķ����У���m-1��ֱ��n
                 */
            	
            	if() { //����������С��areaNum
            		target = false;
            	}else {
            		//��list��ѡ��һ����ɾȥ
            		
            	
            	}
            	
            }
        }   
        
        //���list��ȫ����Ϣ
	}
	
}
