package org.eclipse.uml2.examples.gettingstarted;

public class Asset extends Node{
	int Value;
	
	public Asset(String id, String name, int level) {
		super(id, name, level);
		// TODO Auto-generated constructor stub
	}
	public void SetValue(int value) {
		this.Value = value;
	}
	
}
