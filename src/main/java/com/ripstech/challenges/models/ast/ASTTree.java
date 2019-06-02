package com.ripstech.challenges.models.ast;

import org.apache.log4j.Logger;

public class ASTTree {

	private final static Logger LOGGER = Logger.getLogger(ASTTree.class);
	
	//private LinkedList<ASTNode> lstNodes;
	private ASTNode rootNode;
	
	public ASTTree(){
		//lstNodes = new LinkedList<ASTNode>();
	}
	
//	public LinkedList<ASTNode> getListChildNodes(){
//		return lstNodes;
//	}
	
	public void printASTTree() {
		if(rootNode!=null) {
			rootNode.printInfo(" ");
		}
	}
		
	public void addNode(ASTNode node, boolean isRoot) {
//		lstNodes.add(node);
		
		if(isRoot) {
			rootNode = node;
		} else {
			rootNode.addChild(node, -1);
		}
	}

	public void setRootNode(ASTNode root){
		rootNode = root;
		rootNode.setParent(null);
	}
	
	public ASTNode getRootNode(){
		if(rootNode !=null)
			return rootNode;
//		
//		for(ASTNode node :lstNodes) {
//			if(node.getParent() == null)
//				return node;
//		}
//		
		return null;
	}
	
	public void addTree(ASTTree tree, boolean isRequireRoot) {
		ASTNode root = tree.getRootNode();
		if(root!=null){
			if(isRequireRoot) {
				root.setParent(rootNode);	
			} 
			rootNode.addChild(root, 0);
		}
	}
}	
