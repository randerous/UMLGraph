package controlNodeWork;

import java.util.ArrayList;
import java.util.List;

import AttackPathGenerator.*;

////�����Կ��Ƶ�ĸ���Ӧ�õ�ͼ��
public class setNewGraph {
	/**
     * ����id�������ȸ����ӶϿ����γ��µ�ͼ
     * @param G
     * @param I
     * @return
     */
    public Graph applyControlNode(Graph G, List<Node> I){
        for(int i = 0; i < I.size(); i++){
            String present = I.get(i).getId();
            G.vertexes.get(present).clearNextNode();
        }

        return G;
    }

}
