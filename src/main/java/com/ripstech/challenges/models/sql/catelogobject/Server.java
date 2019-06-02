package com.ripstech.challenges.models.sql.catelogobject;

import java.util.LinkedList;

import com.ripstech.challenges.models.ast.ASTNode;
import com.ripstech.challenges.models.ast.ASTNodeImpl;
import com.ripstech.challenges.models.ast.ASTTree;
import com.ripstech.challenges.models.sql.SQLObjectImpl;
import com.ripstech.challenges.parser.ParseException;
import com.ripstech.challenges.parser.Tokenizer.TokenInfo;

public class Server extends SQLObjectImpl{
	
	private String serverName;
	private String instanceName;
	private String simpleName;
	
	public Server() {
		serverName = "";
		instanceName = "";
		simpleName = "";
	}
	
	
	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getSimpleName() {
		return simpleName;
	}

	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}

	public void setBodyContent(LinkedList<TokenInfo> lstTokens) {
	}


	public void parse(LinkedList<TokenInfo> lstTokens) throws ParseException{
	}

	public ASTTree buildTree() {
		ASTTree tree = new ASTTree();
		
		//root node
		ASTNode parent = new ASTNodeImpl();
		parent.setRootNode(true);
		parent.setNodeName("Server");
		parent.setNodeValue(serverName);
		tree.setRootNode(parent);
		
		if(instanceName!=null && !instanceName.isEmpty()) {
			ASTNode node = new ASTNodeImpl();
			node.setParent(parent);
			node.setNodeName("Instance name");
			node.setNodeValue(instanceName);	
			tree.addNode(node, false);
		}
		
		if(simpleName!= null && !simpleName.isEmpty()) {
			ASTNode node = new ASTNodeImpl();
			node.setParent(parent);
			node.setNodeName("Simple Name");
			node.setNodeValue(simpleName);
			tree.addNode(node, false);
		}
		
		return tree;
	}
	
	
	
}
