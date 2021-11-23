package CriticalNodesGenerator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import AttackPathGenerator.*;

public class CriticalNodes {
	    Graph G;
	    int n;
	    String[] vertexes;//��Ÿ��ڵ�id
	    LinkedList<Vertex> Vertexes=new LinkedList();

	    public CriticalNodes(Graph g) {
	        this.G = g;
	        this.n = this.G.vertexes.size();
	        this.vertexes = new String[n];


	        int i = 0;
	        for (Map.Entry<String, Vertex> v : G.vertexes.entrySet()) {
	            this.vertexes[i++] = new String(v.getKey());
	            Vertexes.add(v.getValue());
	        }
	    }

	    public List<Node> getCriticalNodes(){
	        List<Node> res=new ArrayList();
	        LinkedList<String> G0=new LinkedList();
	        String minDegreeNode=G0.get(0);
	        for(int i=0;i<n;i++){
	            G0.add(vertexes[i]);
	        }

	        while(!G0.isEmpty()){
	            //��G0�ж���С�Ľڵ�
	            for(String nodeId:G0){
	                if(G.vertexes.get(nodeId).getNext_vertexes().size()<
	                        G.vertexes.get(minDegreeNode).getNext_vertexes().size())
	                    minDegreeNode=nodeId;
	            }

	            //���ѡ��minDegreeNode��һ���ڽӽڵ㣬����res
	            int rand=(int)Math.random()*G.vertexes.get(minDegreeNode).getNext_vertexes().size();
	            int i=0;
//	            Node controlledNode=G.vertexes.get(minDegreeNode).getNext_vertexes().get(rand);
	            Node controlledNode=null;
	            for(Vertex v:G.vertexes.get(minDegreeNode).getNext_vertexes()) {
	            	if(i==rand) controlledNode=v.getNode();
	            	i++;
	            }
	            res.add(controlledNode);


	            //ɾȥ��minDegreeNode�����Ľڵ�
	            for(Vertex v: G.vertexes.get(controlledNode.getId()).getNext_vertexes()){
	                G0.remove(v.getNode());
	            }

	        }
	        return res;
	    }

	    public List<Node> GetTwoNodesInOnePath(ArrayList<ArrayList<Vertex>> pathset){
	    	/*
	    	 * @Vertexes:��ѡ��Ľڵ�
	    	 * @pathSet:·������ÿ��·���ı�ʶΪindex����
	    	 */
	        List<Integer> pathState=new ArrayList();//pathState[i]==2������Ϊi��·����ǰ��״̬Ϊ2�������ڸ�·����ѡ���������ڵ���
	        for(int i=0;i<pathset.size();i++)
	        	pathState.add(0);
	        ArrayList<Node> res=new ArrayList();
	        int count=pathset.size();//·����

	        for(Vertex v:Vertexes) {
	        	v.InitPaths(pathset);
	        }

	        while(count!=0){
	            Vertex p=Vertexes.getFirst();
	            for(Vertex q:Vertexes){
	                if(q.pathNums>p.pathNums) p=q;
	            }
	            res.add(p.getNode());
	            ArrayList<Integer> myPaths=p.getPaths();
	            for(int each:myPaths){
	                int k=pathState.get(each);
	                if(k==2) continue;
	                k++;
	                pathState.set(each,k); 
	                if(k==2) {
	                   count--;
	                   for(Vertex v:pathset.get(each)){
	                       v.pathNums--;
	                   }
	                }
	            }
	            Vertexes.remove(p);
	        }
	        return res;

	    }	    
}
