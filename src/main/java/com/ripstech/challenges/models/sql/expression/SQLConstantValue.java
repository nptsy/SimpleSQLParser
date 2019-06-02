package com.ripstech.challenges.models.sql.expression;

import com.ripstech.challenges.models.ast.ASTNode;
import com.ripstech.challenges.models.ast.ASTNodeImpl;
import com.ripstech.challenges.models.ast.ASTTree;
import com.ripstech.challenges.models.sql.SQLObjectImpl;

public class SQLConstantValue extends SQLObjectImpl{
	private String value; 
	
	public SQLConstantValue(String val) {
		super();
		value =val;
	}
	
	public ASTTree buildTree() {
		ASTTree tree = new ASTTree();
		//root node
		ASTNode parent = new ASTNodeImpl();
		parent.setNodeName("Const Value");
		parent.setNodeValue(value);
		parent.setRootNode(true);
		tree.setRootNode(parent);
		return tree;
	}
	
}
