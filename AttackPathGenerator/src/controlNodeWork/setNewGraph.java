package controlNodeWork;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import AttackPathGenerator.*;

////�����Կ��Ƶ�ĸ���Ӧ�õ�ͼ��
public class setNewGraph {
	/**
     * ����id������ȸ����ӶϿ����γ��µ�ͼ
     * @param G
     * @param I
     * @return
     */
    public Graph applyControlNode(Graph G, List<Node> I){
    	
    	
    	for(int i = 0; i < I.size(); i++){
    		
    		String present = I.get(i).getID();
    		if(!G.vertexes.containsKey(present))continue;
    		
            List<String> s = new ArrayList();
            
            //
            for(int j = 0; j < G.getVertexes().get(present).getPreV().size(); j ++) {
            	
            }

            
            for(int j = 0; j < s.size(); j ++) {
            	if( G.vertexes.get(s.get(j)).getNextV().size() != 0) {
    				if(G.vertexes.get(s.get(j)).getNextV().contains(present))
    				G.vertexes.get(s.get(j)).getNextV().remove(present);
    			}
            }
            	
            G.vertexes.get(present).getNextV().clear();       
        }
        return G;
    }
}
