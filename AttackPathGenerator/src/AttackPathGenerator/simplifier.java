package AttackPathGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/*
 * all simplify method implementation.
 * ----attention!!!
 * asset shouldn't be merge
 */
public class simplifier {

	public Graph simplify(Graph G) {
		G = simplifySeqNode(G);
		G = simplifyConcurrentNode(G);  
		G = simplifyExposure(G);
		return G;
	}

	/*
	 * mergeNode for sequence
	 */

	public void mergeSeqNode(Graph G, Vertex v) {
		// can't merge asset
		if (v.getType() == 2)
			return;

		for (Vertex pre : v.getPreV()) {
			pre.rmNextNode(v);
			for (Vertex tmp : v.getNextV()) {
				pre.addNextNode(tmp);
			}
		}

		for (Vertex next : v.getNextV()) {
			next.rmPreNode(v);
			for (Vertex tmp : v.getPreV()) {
				next.addPreNode(tmp);
			}
		}
		G.rmElem(v);
	}

	/*
	 * mergeNode for concurrent
	 */
	public void mergeConcurrentNode(Graph G, Vertex v) {
		// can't merge asset
		if (v.getType() == 2)
			return;
		
		for (Vertex pre : v.getPreV()) {
			pre.rmNextNode(v);
		}

		for (Vertex next : v.getNextV()) {
			next.rmPreNode(v);
		}
		G.rmElem(v);
	}

	/*
	 * 
	 * mergeNode for exposure
	 */
	public void mergeExposureNode(Graph G, Vertex v) {
		mergeSeqNode(G, v);
	}
	
	/*
	 * check two set if they have same elems
	 */
	public boolean cmpSet(Set<Vertex> s1, Set<Vertex> s2) {
		for (Vertex v : s1) {
			if (!s2.contains(v)) {
				return false;
			}
			s2.remove(v);
		}
		if (s2.size() > 0)
			return false;
		return true;
	}
	
	/*
	 * check two node if they have same pre and next nodes 
	 */
	public boolean cmpVertex(Vertex v1, Vertex v2) {

		Set<Vertex> pre1 = new HashSet<Vertex>(v1.getPreV());
		Set<Vertex> pre2 = new HashSet<Vertex>(v2.getPreV());
		Set<Vertex> next1 = new HashSet<Vertex>(v1.getNextV());
		Set<Vertex> next2 = new HashSet<Vertex>(v2.getNextV());

		boolean res = cmpSet(pre1, pre2) && cmpSet(next1, next2);
//		if(res)
//		{
//			out("--start--");
//			out(v1.getItself().getName());
//			out(v2.getItself().getName());
//			out(v1.getNextV().toString());
//			out(v2.getNextV().toString());
//			
//			for(Vertex v: v1.getPreV())
//			{
//				out(v.getItself().getName());
//			}
//			out("----");
//			for(Vertex v: v2.getPreV())
//			{
//				out(v.getItself().getName());
//			}
//			out("--finish--");
//			
//		}
		return res;
	}
	
	public Graph simplifySeqNode(Graph G)
	{
		ArrayList<Vertex> modifiedV = new ArrayList<Vertex>();
		for (Map.Entry<String, Vertex> v : G.getVertexes().entrySet()) {
			Vertex node = v.getValue(); 
			if(node.getPreV().size() == 1)
			{
				modifiedV.add(node);
			}
		}
		for(Vertex node : modifiedV)
		{
			if(node.type == 1)
				mergeSeqNode(G, node);
		}

		return G;
	}
	
	
	public Graph simplifyConcurrentNode(Graph G) {
		unionSetUtil unionSetUtil = new unionSetUtil();
		int parent[] = new int[G.getVertexes().size()];
		unionSetUtil.initParent(parent);

		// for bi-convert between string and int for id
		Map<Vertex, Integer> vertexConvertInt = new HashMap<Vertex, Integer>();
		Map<Integer, Vertex> intConvertVertex = new HashMap<Integer, Vertex>();
		int id = 0;
		for (Map.Entry<String, Vertex> v : G.getVertexes().entrySet()) {
			Vertex node = v.getValue();
			intConvertVertex.put(id, node);
			vertexConvertInt.put(node, id++);
		}

		for (Map.Entry<String, Vertex> v1 : G.getVertexes().entrySet()) {
			Vertex node1 = v1.getValue();

			for (Map.Entry<String, Vertex> v2 : G.getVertexes().entrySet()) {
				{
					Vertex node2 = v2.getValue();
					if (!node1.equals(node2)) {
						if (!node1.getNextV().isEmpty() && !node1.getPreV().isEmpty() && cmpVertex(node1, node2)) {
							unionSetUtil.mergeParent(parent, vertexConvertInt.get(node1), vertexConvertInt.get(node2));
						}
					}
				}
			}
		}
		unionSetUtil.updateParent(parent);

		Set<Integer> eq = new HashSet<Integer>();

		for (int i = 0; i < parent.length; i++) {
			int p = unionSetUtil.getParent(parent, i);
			if (!eq.contains(p))
				eq.add(p);
			else {
				Vertex v = intConvertVertex.get(i);
				if(v.getType() == 1)
					mergeConcurrentNode(G, intConvertVertex.get(i));
			}
		}
		return G;
	}
	
	 

	public Graph simplifyExposure(Graph G) {
		ArrayList<Vertex> surfaces = new ArrayList<Vertex>();
		
		unionSetUtil unionSetUtil = new unionSetUtil();
		// for bi-convert between string and int for id
		Map<Vertex, Integer> vertexConvertInt = new HashMap<Vertex, Integer>();
		Map<Integer, Vertex> intConvertVertex = new HashMap<Integer, Vertex>();
		int id = 0;
		for (Map.Entry<String, Vertex> v1 : G.getVertexes().entrySet()) {
			Vertex node1 = v1.getValue();
			if (node1.getType() == 0) {
				surfaces.add(node1);
				intConvertVertex.put(id, node1);
				vertexConvertInt.put(node1, id++);
			}

		}

		int parent[] = new int[surfaces.size()];
		unionSetUtil.initParent(parent);

		for (Vertex v : surfaces) {
			for (Vertex v1 : v.next_vertexes) {
				if (v1.type == 0) {
					unionSetUtil.mergeParent(parent, vertexConvertInt.get(v), vertexConvertInt.get(v1));
				}
			}
		}

		unionSetUtil.updateParent(parent);

		Map<Integer, Integer> eq = new HashMap<Integer, Integer>();

		for (int i = 0; i < parent.length; i++) {
			int p = unionSetUtil.getParent(parent, i);
			int level = intConvertVertex.get(i).getItself().getLevel();
			if (eq.containsKey(p)) {
				if (level < eq.get(p)) {
					eq.remove(p);
					eq.put(p, level);
				}
			} else {
				eq.put(p, level);
			}
		}

		for (int i = 0; i < parent.length; i++) {
			int p = unionSetUtil.getParent(parent, i);
			int level = intConvertVertex.get(i).getItself().getLevel();

			if (eq.get(p) < level) {
				mergeExposureNode(G, intConvertVertex.get(i));
			}
		}
		return G;
	}

}
