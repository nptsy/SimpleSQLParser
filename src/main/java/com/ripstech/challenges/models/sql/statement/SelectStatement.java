package com.ripstech.challenges.models.sql.statement;

import java.util.Arrays;
import java.util.LinkedList;

import com.ripstech.challenges.models.ast.ASTNode;
import com.ripstech.challenges.models.ast.ASTNodeImpl;
import com.ripstech.challenges.models.ast.ASTTree;
import com.ripstech.challenges.models.sql.EnumModels;
import com.ripstech.challenges.models.sql.SQLObject;
import com.ripstech.challenges.models.sql.clause.SQLClause;
import com.ripstech.challenges.models.sql.clause.SQLClauseFactory;
import com.ripstech.challenges.parser.ParseException;
import com.ripstech.challenges.parser.Tokenizer.TokenInfo;

public class SelectStatement extends SQLStatement{

	private LinkedList<SQLObject> lstSQLClauses;
	
	public SelectStatement() {
		super(EnumModels.StatementType.stmtSelect);
		lstSQLClauses = new LinkedList<SQLObject>();
	}
	
	public void parse(LinkedList<TokenInfo> lstTokens) throws ParseException{
		lstTokenInfo = lstTokens;
		if(lstTokenInfo != null && lstTokenInfo.size() > 0) {
			SQLClauseFactory clsFact = new SQLClauseFactory();
			lstSQLClauses = clsFact.process(lstTokenInfo);
		}
	}
	
	public ASTTree buildTree() {
		ASTTree tree = new ASTTree();
		ASTNode root = new ASTNodeImpl();
		root.setRootNode(true);
		root.setNodeName("SELECT");
		tree.setRootNode(root);
		
		for(SQLObject sqlObj : lstSQLClauses) {
			tree.addTree(sqlObj.buildTree(), true);
		}
		
		return tree;
	}
}
