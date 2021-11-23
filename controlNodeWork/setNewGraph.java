package controlNodeWork;

import java.util.ArrayList;
import java.util.List;

import AttackPathGenerator.*;

////将韧性控制点的隔离应用到图中
public class setNewGraph {
	/**
     * 根据id，将出度各连接断开，形成新的图
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
