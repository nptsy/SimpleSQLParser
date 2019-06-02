package com.ripstech.challenges.models.ast;

import java.util.LinkedList;
import org.apache.log4j.Logger;


public class ASTNodeImpl implements ASTNode{
	
	private final static Logger LOGGER = Logger.getLogger(ASTNodeImpl.class);
	
	protected ASTNode parent;
	protected LinkedList<ASTNode> llChildNodes;
	protected int nodeID;
	protected String nodeName;
	protected boolean isRootNode;
	protected String nodeValue;
	protected String nodeType = "";
	
	public ASTNodeImpl() {
		llChildNodes = new LinkedList<ASTNode>();
		nodeName = "";
		nodeID = -1;
		isRootNode = false;		
		nodeValue = "";
	}
	
	public void printInfo(String indent) {
		String output = indent + " " + nodeName;
		if(nodeValue!=null && !nodeValue.isEmpty()) {
			output += ": "+nodeValue;
		}
		//LOGGER.info(output);
		System.out.println(output);
		
		if(llChildNodes!=null && llChildNodes.size()>0) {
			for(ASTNode child: llChildNodes) {
				child.printInfo(" " + indent+"-");
			}
		}
	}
	
	public void setNodeValue(String value) {
		nodeValue = value;
	}
	
	public String getNodeValue() {
		return nodeValue;
	}
	
	public void setNodeID(int id) {
		nodeID = id;
	}
	
	public void setNodeName(String name) {
		nodeName = name;
	}
	
	public void setRootNode(boolean isRoot) {
		isRootNode = isRoot;
	}
	
	public void setParent(ASTNode n) {
		parent = n;
	}

	public ASTNode getParent() {
		return parent;
	}

	public void addChild(ASTNode c, int index) {
		if(index >= 0)
			llChildNodes.add(index,c);
		else {
			llChildNodes.addLast(c);
		}
	}

	public ASTNode getChild(int index) {
		ASTNode child = llChildNodes.get(index); 
		return child;
	}

	public int getNumberOfChildNodes() {
		return llChildNodes.size();
	}

	public String getNodeType() {
		return nodeType;
	}
	
	public String getNodeName() {
		return nodeName;
	}
	
}
