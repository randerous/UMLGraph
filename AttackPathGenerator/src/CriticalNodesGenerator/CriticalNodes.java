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
	    String[] vertexes;//存放各节点id
	    LinkedList<Vertex> Vertexes=new LinkedList();//待选择的节点集，除去暴露面和资产

	    public CriticalNodes(Graph g) {
	        this.G = g;
	        this.n = this.G.vertexes.size();
	        this.vertexes = new String[n];


	        int i = 0;
	        for (Map.Entry<String, Vertex> v : G.vertexes.entrySet()) {
	            this.vertexes[i++] = new String(v.getKey());
//	            if(v.getValue().getType()==1)//将传播节点存入待选择节点集
	            	Vertexes.add(v.getValue());
	        }
	    }

	    public List<Node> getCriticalNodes(){
	    	/*
	    	 * 考虑图是一个连通图
	    	 * @return：针对图选的控制点
	    	 */
	        List<Node> res=new ArrayList();
	        LinkedList<String> G0=new LinkedList();
	        //初始化图
//	        for(int i=0;i<n;i++){
//	        	G0.add(vertexes[i]);
//	        }
//	        
	        for(Vertex v:Vertexes) {
	        	G0.add(v.getItself().getID());
	        }
	        String minDegreeNode=G0.get(0);
	        Vertex minV=G.vertexes.get(minDegreeNode);
	        int minD=minV.getNextV().size()+minV.getPreV().size();

	        while(!G0.isEmpty()){
	            //找G0中度最小的节点
	            for(String nodeId:G0){
	            	Vertex v=G.vertexes.get(nodeId);
	            	int D=v.getNextV().size()+v.getPreV().size();
	                if(D<minD){
	                    minDegreeNode=nodeId;
	                    minV=v;
	                    minD=D;
	                }
	            }
	            
	            if(minD==0) {
	            	res.add(minV.getItself());
	            	Vertexes.remove(minV);
	            	continue;
	            }

	            //随机选择minDegreeNode的一个邻接节点，加入res
	            int rand=(int)Math.random()*minD;
//	            int i=0;
	            List<Vertex> adjVertexes=new ArrayList<Vertex>(minV.getPreV());
	            adjVertexes.addAll(minV.getNextV());
	            Node controlledNode=adjVertexes.get(rand).getItself();
//	            Node controlledNode=G.vertexes.get(minDegreeNode).getNext_vertexes().get(rand);
//	            Node controlledNode=null;
//	            for(Vertex v:G.vertexes.get(minDegreeNode).getNextV()) {
//	            	if(i==rand) controlledNode=v.getItself();
//	            	i++;
//	            }
	            res.add(controlledNode);


	            //删去与minDegreeNode相连的节点
	            for(Vertex v: adjVertexes){
	                G0.remove(v.getItself());
	            }
	        }
	        return res;
	    }

	    public List<Node> GetTwoNodesInOnePath(ArrayList<ArrayList<Vertex>> pathset){
	    	/*
	    	 * @Vertexes:待选择的节点
	    	 * @pathSet:路径集，每条路径的标识为index索引
	    	 * @return:控制点集
	    	 */
	        List<Integer> pathState=new ArrayList();//pathState[i]==2，索引为i的路径当前的状态为2，即已在该路径上选择了两个节点了
	        for(int i=0;i<pathset.size();i++)
	        	pathState.add(0);
	        ArrayList<Node> res=new ArrayList();
	        int count=pathset.size();//路径数

	        for(Vertex v:Vertexes) {
	        	v.InitPaths(pathset);
	        }

	        while(count!=0){
	            Vertex p=Vertexes.getFirst();
	            for(Vertex q:Vertexes){
	                if(q.pathNums>p.pathNums) p=q;
	            }
	            res.add(p.getItself());
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

	    public List<Node> GetTwoNodesInOnePath2(ArrayList<ArrayList<Vertex>> pathset){
	    	/*
	    	 * 在算法CriticalNodes的基础上找
	    	 * @Vertexes:待选择的节点
	    	 * @pathSet:路径集，每条路径的标识为index索引
	    	 * @return:控制点集
	    	 */
	    	List<Node> res=getCriticalNodes();
	    	List<Integer> pathState=new ArrayList();//pathState[i]==2，索引为i的路径当前的状态为2，即已在该路径上选择了两个节点了
	        for(int i=0;i<pathset.size();i++)
	        	pathState.add(0);
	        int count=pathset.size();//未满足条件的路径数

	        for(Vertex v:Vertexes) {
	        	v.InitPaths(pathset);
	        }
	    	
	        for(Node n:res) {
	        	Vertex v=G.vertexes.get(n.getID());
	        	Vertexes.remove(v);
	        	for(int path:v.getPaths()) {
	        		int k=pathState.get(path);
	        		if(k==2) continue;
	        		else {
	        			k++;
	        			pathState.set(path, k);
	        			if(k==2) {
	        				count--;
	        				for(Vertex vv:pathset.get(path)){
	 	                       vv.pathNums--;
	 	                   }
	        			}
	        		}
	        	}        	
	        }
	        
	        if(count==0)//已满足条件
	        	return res;
	        else {
	        	while(count!=0) {
	        		Vertex p=Vertexes.getFirst();
		            for(Vertex q:Vertexes){
		                if(q.pathNums>p.pathNums) p=q;
		            }
		            res.add(p.getItself());
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
	        }
	        
	    	return res;
	    	
	    }
}
