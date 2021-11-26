package EvaluationWork;

import java.util.List;

import AttackPathGenerator.AttackPath;
import AttackPathGenerator.Graph;
import AttackPathGenerator.Node;
import controlNodeWork.setNewGraph;

public class totalEvaluation {
	public void showEvaluationResult(Graph G, AttackPath A, List<Node> I){
        //连通图总节点数
        int nodeNum = G.vertexes.size();
        float no = nodeNum;

        //暴露面数量
        surfaceNum s = new surfaceNum();
        s.countInitialSurfaceNum(G);
        int suNu = s.getSurfaceNumber();

        //攻击路径数量
        attackPathNum a = new attackPathNum();
        a.countInitialAttackPathNumber(A);
        int atPaNu = a.getAttackPathNumber();

        //韧性控制点数量
        controlNodeNum c = new controlNodeNum();
        c.countControlNodeNumber(I);
        int coNoNu = c.getControlNodeNumber();

        //韧性控制点与连通图总结点数的比值
        float propotion = coNoNu/no;

        //连通域数量
        connectAreaNum con = new connectAreaNum();
        //初始连通域数量
        con.setConnectNum(G);
        int coArNu = con.getConnectNum();
        //加入韧性点后的连通域数量
		setNewGraph set = new setNewGraph();
		set.applyControlNode(G, I);
		con.setConnectNum(G);
		int coArNuNext = con.getConnectNum();

        System.out.println("连通图总节点数：" + nodeNum + "\n"
                + "暴露面数量：" + suNu + "\n"
                + "攻击路径数量" + atPaNu + "\n"
                + "韧性控制点数量" + coNoNu + "\n"
                + "韧性控制点与连通图总结点数的比值" + propotion + "\n"
                + "初始连通域数量" + coArNu + "\n"
                + "加入韧性点后的连通域数量" + coArNuNext + "\n"
        );
    }
}
