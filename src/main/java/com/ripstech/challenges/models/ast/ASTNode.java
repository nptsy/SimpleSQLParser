package com.ripstech.challenges.models.ast;

public interface ASTNode {

	// set parent node
	public void setParent(ASTNode n);

	// get parent node
	public ASTNode getParent();

	// add child node to specific index
	public void addChild(ASTNode c, int index);

	// get child node at specific index
	public ASTNode getChild(int index);

	// get number of child nodes
	public int getNumberOfChildNodes();

	// get node type
	public String getNodeType();
	
	public void setRootNode(boolean isRoot);
	public void setNodeValue(String value);
	public void setNodeID(int id);
	public void setNodeName(String name) ;
	
	public String getNodeName();
	public String getNodeValue();
	
	public void printInfo(String indent);
	
}
