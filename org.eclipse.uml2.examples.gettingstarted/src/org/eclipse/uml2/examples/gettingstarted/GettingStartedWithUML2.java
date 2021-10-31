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
package org.eclipse.uml2.examples.gettingstarted;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.uml2.common.util.UML2Util;
import org.eclipse.uml2.uml.AggregationKind;
import org.eclipse.uml2.uml.Association;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.Comment;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Dependency;
import org.eclipse.uml2.uml.Enumeration;
import org.eclipse.uml2.uml.EnumerationLiteral;
import org.eclipse.uml2.uml.Generalization;
import org.eclipse.uml2.uml.LiteralUnlimitedNatural;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.PrimitiveType;
import org.eclipse.uml2.uml.Property;
import org.eclipse.uml2.uml.Stereotype;
import org.eclipse.uml2.uml.Type;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;
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
public class GettingStartedWithUML2 {

	public static boolean DEBUG = true;

	private static File outputDir;
	
	private static Graph G;
	
	private static int level;

	/**
	 * The main program. It expects one argument, which is the local filesystem path
	 * of a directory in which to create the <tt>ExtendedPO2.uml</tt> file.
	 * 
	 * @param the program arguments, which must consist of a single filesystem path
	 */
	public static void main(String[] args) throws Exception {
		level = 0;
		G = new Graph();
		 
		URI uri = URI.createFileURI("E:/papyrus-2021-09-5.2.0-win64/Papyrus/workspace/test/test.uml");
		
		Package p = parseUML(uri);
		
		processComponent(p);
		
		G.showInfo();

	}
	
	protected static Package parseUML(URI uri) {
		
		// get the root package (a model).
		ResourceSetImpl RESOURCE_SET = new ResourceSetImpl();
		UMLResourcesUtil.init(RESOURCE_SET);

		Resource resource = RESOURCE_SET.getResource(uri, true);

		Package p = (Package) EcoreUtil.getObjectByType(resource.getContents(), UMLPackage.Literals.PACKAGE);
		return p;
	}

	protected static void processComponent(Package p) {
		//get all vertexes
 
		for (NamedElement i : p.getMembers()) {
			if (i instanceof Component) {
				Component instance = (Component) i;
				processComponentNode(instance);
			} 
		}
		
		
		//get all edge
		for (NamedElement i : p.getMembers()) {
			if (i instanceof Component) {
				Component instance = (Component) i;
				processComponentEdge(instance);
			}  
	 
		}
		

	}
	
	protected static void processComponentEdge(Component instance)
	{
		for(Dependency den : instance.getClientDependencies())
		{
			String sourceID = getId(den.getClients().get(0).toString());
			String supplierID = getId(den.getSuppliers().get(0).toString());
			
			G.addEdge(sourceID, supplierID);
		}
	}
	
	
	protected static void processComponentNode(Component instance)
	{
		//check annotation
		instance.getClientDependencies();
		
		Node n;
		if (instance.getOwnedComments().size() > 0)
		{
			String info = instance.getOwnedComments().get(0).getBody();
//			out(info);
			JSONObject obj = JSON.parseObject(info);
			
			//Create Node
			if(info.contains("exposure"))
			{ 
				 n = new Surface(getId(instance.toString()), instance.getName(), level);
				 ((Surface)n).setPort(instance.getName());
			}
			else if(info.contains("value")) 
			{
//				out(info); 
				int value  = obj.getIntValue("value");
				n = new Asset(getId(instance.toString()), instance.getName(), level);
				((Asset)n).SetValue(value);
			}else
			{
				 n = new Node(getId(instance.toString()), instance.getName(), level);
			}
			
			
			//add vulnerability info
			if(info.contains("vulnerabilities"))
			{
//				System.out.println(obj.getJSONArray("vulnerabilities").size());
				
				for(Object j: obj.getJSONArray("vulnerabilities")) {
					if(j instanceof JSONObject)
					{
						JSONObject temp = (JSONObject) j;
						int id = temp.getIntValue("VulunerabilityID");
						String name = temp.getString("name");
						int damage = temp.getIntValue("damage");
						String precondition = temp.getString("PreCondition");
						String postcondition = temp.getString("PostCondition");
						int complexity = temp.getIntValue("Complexity");
						Vulnerability v  = new Vulnerability(id, name, damage, precondition, postcondition,complexity);
						n.addVunlerability(v);
					}
				}			
			}	
		}else {
			n = new Node(getId(instance.toString()), instance.getName(), level);
		}
		 
//		n.showNodeInfo();
		//type :  0: surface, 1: Node, 2: Asset
		Vertex v;
		if(n instanceof Surface) {	
		    v = new Vertex(0);
		}else if(n instanceof Asset)
		{
			v = new Vertex(2);
		}else v = new Vertex(1);
		v.itself = n;
		G.addElem(v);
	}
	
	protected void buildGraph() {
//		Node n = new Node();
//		Vertex v = new Vertex();
//		G.AddElem(v);
	}

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
	
	protected static String getId(String str) {		
		String s = str.substring(str.indexOf("@")+1,str.indexOf(" ")); 
		return s;
	}

	protected static void out(String format, Object... args) {
		if (DEBUG) {
			System.out.printf(format, args);
			if (!format.endsWith("%n")) {
				System.out.println();
			}
		}
	}

}
