package com.ripstech.challenges.models.sql.expression;

import java.util.HashSet;
import java.util.Set;

import com.ripstech.challenges.models.ast.ASTNode;
import com.ripstech.challenges.models.ast.ASTNodeImpl;
import com.ripstech.challenges.models.ast.ASTTree;
import com.ripstech.challenges.models.sql.SQLObjectImpl;
import com.ripstech.challenges.utils.Token;

public class SQLFunction extends SQLObjectImpl{
	
	private static Set<String> setFunctions;
	
	private String funcName;
	
	static {
		setFunctions = new HashSet<String>();
		setFunctions.add(Token.FNOW);
		setFunctions.add(Token.FCAST);
		setFunctions.add(Token.FMIN);
		setFunctions.add(Token.FMAX);
		setFunctions.add(Token.FAVRG);
		setFunctions.add(Token.FCOUNT);
	}
	
	public SQLFunction(String fName) {
		super();
		funcName = fName;
	}
	
	public void setFunctionName(String name) {
		funcName = name;
	}
	
	public static boolean isAFunction(String token) {
		return setFunctions.contains(token);
	}

	public ASTTree buildTree() {
		ASTTree tree = new ASTTree();
		//root node
		ASTNode parent = new ASTNodeImpl();
		parent.setNodeName("Function");
		parent.setNodeValue(funcName);
		parent.setRootNode(true);
		tree.setRootNode(parent);
		return tree;
	}
	
}
