package EvaluationWork;

import java.util.List;

import AttackPathGenerator.AttackPath;
import AttackPathGenerator.Graph;
import AttackPathGenerator.Node;
import controlNodeWork.setNewGraph;

public class totalEvaluation {
	public void showEvaluationResult(Graph G, AttackPath A, List<Node> I){
        //��ͨͼ�ܽڵ���
        int nodeNum = G.vertexes.size();
        float no = nodeNum;

        //��¶������
        surfaceNum s = new surfaceNum();
        s.countInitialSurfaceNum(G);
        int suNu = s.getSurfaceNumber();

        //����·������
        attackPathNum a = new attackPathNum();
        a.countInitialAttackPathNumber(A);
        int atPaNu = a.getAttackPathNumber();

        //���Կ��Ƶ�����
        controlNodeNum c = new controlNodeNum();
        c.countControlNodeNumber(I);
        int coNoNu = c.getControlNodeNumber();

        //���Կ��Ƶ�����ͨͼ�ܽ�����ı�ֵ
        float propotion = coNoNu/no;

        //����������
        connectAreaNum con = new connectAreaNum();
        //��ʼ����������
        con.setConnectNum(G);
        int coArNu = con.getConnectNum();
        //�������Ե��ĸ���������
		setNewGraph set = new setNewGraph();
		G = set.applyControlNode(G, I);
		con.setConnectNum(G);
		int coArNuNext = con.getConnectNum();

        System.out.println("���������£�\n"
        		+ "�򻯺����ͨͼ�ܽڵ�����" + nodeNum + "\n"
                + "��¶��������" + suNu + "\n"
                + "δ�����ǰ�Ĺ���·��������" + atPaNu + "\n"
                + "�趨�����Կ��Ƶ�������" + coNoNu + "\n"
                + "����Ϻ�Ĺ���·��������" + 0 + "\n"
                + "���Կ��Ƶ�����ͨͼ�ܽ�����ı�ֵ��" + propotion + "\n"
                + "�����Կ��Ƶ�ǰϵͳ�ĸ�����������" + coArNu + "\n"
                + "�������Կ��Ƶ��ϵͳ�ĸ�����������" + coArNuNext + "\n"
        );
    }
}
