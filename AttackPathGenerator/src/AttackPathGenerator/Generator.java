/*
 * Copyright (c) 2014, 2018 CEA and others.
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 *   Christian W. Damus (CEA) - initial API and implementation
 *   Kenn Hussey - 535301
 *
 */
package AttackPathGenerator;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Artifact;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.CommunicationPath;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Deployment;
import org.eclipse.uml2.uml.Device;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.ExecutionEnvironment;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Relationship;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.internal.impl.DependencyImpl;
import org.eclipse.uml2.uml.resource.UMLResource;
import org.eclipse.uml2.uml.resources.util.UMLResourcesUtil;
import org.eclipse.uml2.uml.Package;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
/**
 * A Java program that may be run stand-alone (with the required EMF and UML2
 * bundle JARs on the classpath) to create the example model illustrated in the
 * <em>Getting Started with UML2</em> article on the Wiki.
 * 
 * @see http://wiki.eclipse.org/MDT/UML2/Getting_Started_with_UML2
 */
public class Generator {

	public static boolean DEBUG = true;

	private static File outputDir;
	
	private static Graph G;
	
	private static int level;

 
	public static void main(String[] args) throws Exception {
		level = 0;
		G = new Graph();
		 
		URI uri = URI.createFileURI("C:/Users/randerous/Desktop/»ªÎª/UMLGraph/test/test.uml");
//		URI uri = URI.createFileURI("D:/×ÀÃæ/a3.uml");
		Package p = parseUML(uri);
		
		
		
		processNode(p);
		processEdge(p);
		
//		G.showInfo();
		AttackPath paths = new AttackPath();
		paths.genPath(G);
		paths.showInfo();
				

	}
	
	// parse UML to get root package
	protected static Package parseUML(URI uri) {
		// get the root package (a model).
		ResourceSetImpl RESOURCE_SET = new ResourceSetImpl();
		UMLResourcesUtil.init(RESOURCE_SET);

		Resource resource = RESOURCE_SET.getResource(uri, true);

		Package p = (Package) EcoreUtil.getObjectByType(resource.getContents(), UMLPackage.Literals.PACKAGE);
		return p;
	}
	
	
	//parse component diagram
	protected static void processNode(Package p) {
		
		//get all vertexes
		for (NamedElement i : p.getMembers()) {
			
			if (i instanceof Component) {
				Component instance = (Component) i; 
				processComponentNode(instance);
			}
			
			if (i instanceof Interface)
			{
				Interface instance = (Interface) i;
				createNode(instance.getOwnedComments(), getId(instance.toString()), instance.getName(), level);
			}
			
			if(i instanceof Package) 
			{
				Package instance = (Package) i;
				createNode(instance.getOwnedComments(), getId(instance.toString()), instance.getName(), level);
			}
			
			if(i instanceof Model) 
			{
				Model instance = (Model) i;
				createNode(instance.getOwnedComments(), getId(instance.toString()), instance.getName(), level);
			}
			
			if(i instanceof org.eclipse.uml2.uml.Node)
			{
				org.eclipse.uml2.uml.Node instance = (org.eclipse.uml2.uml.Node) i;
				createNode(instance.getOwnedComments(), getId(instance.toString()), instance.getName(), level);
			}
			
			if(i instanceof Device) 
			{
				Device instance = (Device) i;
				createNode(instance.getOwnedComments(), getId(instance.toString()), instance.getName(), level);
			}
			
			if(i instanceof ExecutionEnvironment) 
			{
				ExecutionEnvironment instance = (ExecutionEnvironment) i;
				createNode(instance.getOwnedComments(), getId(instance.toString()), instance.getName(), level);
			}
			
			if(i instanceof Artifact) 
			{
				Artifact instance = (Artifact) i;
				createNode(instance.getOwnedComments(), getId(instance.toString()), instance.getName(), level);
			}
			
		}
	}
	
	protected static void processEdge(Package p) {
		
 
	
//		processCommunicationPath(p.)
		//get all edge
		
		for (NamedElement i : p.getMembers()) {
			if (i instanceof Component) {
				Component instance = (Component) i; 
				processDependency(instance.getClientDependencies());
				processGeneralization(getId(instance.toString()),instance.getGeneralizations());
				processAssociation(instance.getAssociations());
			} 
			
			if (i instanceof Interface) {
				Interface instance = (Interface) i; 
				processDependency(instance.getClientDependencies());
				processGeneralization(getId(instance.toString()),instance.getGeneralizations());
				processAssociation(instance.getAssociations());
			}  
			
			if (i instanceof Package) {
				Package instance = (Package) i; 
				processDependency(instance.getClientDependencies());
			}  
			
			if (i instanceof Model) {
				Model instance = (Model) i; 
				processDependency(instance.getClientDependencies()); 
			}
			
			if(i instanceof org.eclipse.uml2.uml.Node)
			{
				org.eclipse.uml2.uml.Node instance = (org.eclipse.uml2.uml.Node) i;
				processDependency(instance.getClientDependencies());
				processGeneralization(getId(instance.toString()),instance.getGeneralizations());
				processAssociation(instance.getAssociations());
				processCommunicationPath(instance.getCommunicationPaths());
				processDeployment(getId(instance.toString()),instance.getDeployments());
			}
			
			
			if(i instanceof Device) 
			{
				Device instance = (Device) i; 
				processDependency(instance.getClientDependencies());
				processGeneralization(getId(instance.toString()),instance.getGeneralizations());
				processAssociation(instance.getAssociations());
				processCommunicationPath(instance.getCommunicationPaths());
				processDeployment(getId(instance.toString()),instance.getDeployments());
			}
			
			if(i instanceof ExecutionEnvironment) 
			{
				ExecutionEnvironment instance = (ExecutionEnvironment) i;
				processDependency(instance.getClientDependencies());
				processGeneralization(getId(instance.toString()),instance.getGeneralizations());
				processAssociation(instance.getAssociations());
				processCommunicationPath(instance.getCommunicationPaths());
				processDeployment(getId(instance.toString()),instance.getDeployments());
			}
			
			if(i instanceof Artifact) 
			{
				Artifact instance = (Artifact) i; 	 
				processDependency(instance.getClientDependencies());
				processGeneralization(getId(instance.toString()),instance.getGeneralizations());
				processAssociation(instance.getAssociations()); 
			}
			
	 
		}
		

	}
	
	
	//parse connections in Component diagram
	protected static void processDependency(EList<Dependency> dependencies)
	{
		for(Dependency i : dependencies)
		{
			String sourceID = getId(i.getClients().get(0).toString());
			String destID = getId(i.getSuppliers().get(0).toString());			
			G.addEdge(sourceID, destID);
		}
	}
	
	protected static void processGeneralization(String sourceID, EList<Generalization> generalizations)
	{
		for(Generalization i : generalizations)
		{ 
			String  destID = getId(i.getGeneral().toString());			
			G.addEdge(sourceID, destID);
			G.addEdge(destID, sourceID);
		}
	}
	
	protected static void processAssociation(EList<Association> associations)
	{
		for(Association i : associations)
		{ 
			String sourceID = getId(i.getMembers().get(0).toString());
			String destID = getId(i.getMembers().get(1).toString());			
			G.addEdge(sourceID, destID);
			G.addEdge(destID, sourceID);
		}
	}
	
	protected static void processCommunicationPath(EList<CommunicationPath> communicationPaths)
	{
		for(CommunicationPath i : communicationPaths)
		{			
			String sourceID = getId(i.getMembers().get(0).toString());
			String destID = getId(i.getMembers().get(1).toString());			
			G.addEdge(sourceID, destID);
			G.addEdge(destID, sourceID);
		}
	}
	
	protected static void processDeployment(String sourceID,EList<Deployment> deployments)
	{
		for(Deployment i : deployments)
		{			
			String destID = getId(i.getTargets().get(0).toString());			
			G.addEdge(sourceID, destID);
			G.addEdge(destID, sourceID);
		}
	}
	
	
	
	
	//parse Nodes in component diagram
	protected static void processComponentNode(Component instance)
	{
		//check annotation		
		 createNode(instance.getOwnedComments(), getId(instance.toString()), instance.getName(), level);

		
	}
	
	
	protected void buildGraph() {
//		Node n = new Node();
//		Vertex v = new Vertex();
//		G.AddElem(v);
	}
	
	
	
	//parse comments to create Node
	protected static void createNode(EList<Comment> comments, String id, String name, int level)
	{
		Node node;
		if (comments.size() > 0)
		{
			String info = comments.get(0).getBody();
//			out(info);
			JSONObject obj = JSON.parseObject(info);
			
			//Create Node
			if(info.contains("exposure"))
			{ 
				 node = new Surface(id, name, level); 
				 ((Surface)node).setPort(name);
			}
			else if(info.contains("value")) 
			{
//				out(info); 
				int value  = obj.getIntValue("value");
				node = new Asset(id, name, level);
				((Asset)node).SetValue(value);
			}else
			{
				 node = new Node(id, name, level);
			}
			
			
			//add vulnerability info
			if(info.contains("vulnerabilities"))
			{
//				System.out.println(obj.getJSONArray("vulnerabilities").size());	
				for(Object j: obj.getJSONArray("vulnerabilities")) {
					if(j instanceof JSONObject)
					{
						JSONObject temp = (JSONObject) j;
						Vulnerability v  = new Vulnerability(
								temp.getIntValue("VulunerabilityID"), 
								temp.getString("name"), 
								temp.getIntValue("damage"), 
								temp.getString("PreCondition"),
								temp.getString("PostCondition"),
								temp.getIntValue("Complexity"));
//						v.showInfo();
						node.addVunlerability(v);
					}
				}			
			}	
		}else {
			node = new Node(id, name, level);
		}
		
		//type :  0: surface, 1: Node, 2: Asset
		Vertex v;
				if(node instanceof Surface) {	
				    v = new Vertex(0);
				}else if(node instanceof Asset)
				{
					v = new Vertex(2);
				}else v = new Vertex(1);
				v.itself = node;
				G.addElem(v);
 
	}
	
	//general iterator for EMF
	protected void iterator(Resource resource) {
		for (TreeIterator<EObject> i = resource.getAllContents(); i.hasNext();) {

			EObject current = i.next();
			if (!(current instanceof NamedElement)) {
				i.prune();
			} else {
				NamedElement asNamed = (NamedElement) current;
				System.out.println(asNamed.getQualifiedName() + " : " + asNamed.eClass().getName());
			}
		}

	}
	
	//tools for parse identifier for Node
	protected static String getId(String str) {		
		String s = str.substring(str.indexOf("@")+1,str.indexOf(" ")); 
		return s;
	}
	
	//tools to simplify system.out, & for debug
	protected static void out(String format, Object... args) {
		if (DEBUG) {
			System.out.printf(format, args);
			if (!format.endsWith("%n")) {
				System.out.println();
			}
		}
	}

}
