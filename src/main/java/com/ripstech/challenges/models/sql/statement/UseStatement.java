package com.ripstech.challenges.models.sql.statement;

import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.ripstech.challenges.models.ast.ASTNode;
import com.ripstech.challenges.models.ast.ASTNodeImpl;
import com.ripstech.challenges.models.ast.ASTTree;
import com.ripstech.challenges.models.sql.EnumModels;
import com.ripstech.challenges.models.sql.SQLObject;
import com.ripstech.challenges.models.sql.catelogobject.Database;
import com.ripstech.challenges.parser.Tokenizer.TokenInfo;

public class UseStatement extends SQLStatement{
	private final static Logger LOGGER = Logger.getLogger(UseStatement.class);
	private LinkedList<TokenInfo> lstTokenInfo;
	private Database db;
	
	public UseStatement() {
		super(EnumModels.StatementType.stmtUse);
	}

	@Override
	public void setBodyContent(LinkedList<TokenInfo> lstTokens) {
		lstTokenInfo = (LinkedList)lstTokens.clone();
	}
	
	@Override
	public void parse(LinkedList<TokenInfo> lstTokens) {
		lstTokenInfo = lstTokens;
		// use statement only has 3 tokens
		if(lstTokenInfo != null && lstTokenInfo.size() == 3){
			db = new Database(lstTokenInfo.get(1).getOrigValue());
			
		}else {
			LOGGER.warn("This USE statement is invalid");
		}
	}
	
	public ASTTree buildTree() {
		ASTTree tree = new ASTTree();
		ASTNode root = new ASTNodeImpl();
		root.setRootNode(true);
		root.setNodeName("USE");
		tree.setRootNode(root);

		if(db!=null && !db.getDatabaseName().isEmpty()) {
			ASTNode dbNode = new ASTNodeImpl();
			dbNode.setNodeName("Database");
			dbNode.setNodeValue(db.getDatabaseName());
			
			dbNode.setParent(root);
			tree.addNode(dbNode, false);
		}
		
		return tree;
	}
	
}
