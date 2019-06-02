package com.ripstech.challenges.models.sql.clause;

import java.util.LinkedList;

import com.ripstech.challenges.models.ast.ASTNode;
import com.ripstech.challenges.models.ast.ASTNodeImpl;
import com.ripstech.challenges.models.ast.ASTTree;
import com.ripstech.challenges.models.sql.EnumModels;
import com.ripstech.challenges.models.sql.catelogobject.Column;
import com.ripstech.challenges.models.sql.catelogobject.Table;
import com.ripstech.challenges.parser.ParseException;
import com.ripstech.challenges.parser.Tokenizer.TokenInfo;
import com.ripstech.challenges.utils.Token;

public class FromClause extends SQLClause{

	private LinkedList<Table> lstTables;
	
	public FromClause() {
		super(EnumModels.ClauseType.clsFrom);
		lstTables = new LinkedList<Table>();
	}

	public void parse(LinkedList<TokenInfo> lstTokens) throws ParseException{
		lstTokenInfo = lstTokens;
		if(lstTokenInfo == null || lstTokenInfo.size() <= 0) {
			return ;
		}
		
		LinkedList<TokenInfo> lstsubTokens = new LinkedList<TokenInfo>();
		
		for(int i = 1; i < lstTokenInfo.size() ; ++i) {
			TokenInfo tok = lstTokenInfo.get(i);
			if(!tok.getOrigValue().equals(Token.CCOMMA+"")) {
				lstsubTokens.addLast(tok);
			}else {
				if(!lstsubTokens.isEmpty()) {
					Table tbl = new Table();
					tbl.parse(lstsubTokens);
					lstTables.add(tbl);
					lstsubTokens = new LinkedList<TokenInfo>();
				}
			}
		}
		
		if(lstsubTokens.size() != 0) {
			Table tbl = new Table();
			tbl.parse(lstsubTokens);
			lstTables.add(tbl);
			lstsubTokens = new LinkedList<TokenInfo>();
		}
	}
	
	public ASTTree buildTree() {
		ASTTree tree = new ASTTree();
		ASTNode parent = new ASTNodeImpl();
		parent.setNodeName("FROM");
		tree.setRootNode(parent);
		parent.setRootNode(true);
		tree.setRootNode(parent);
		
		if(lstTables!=null && lstTables.size() >0) {
			for(Table tbl :lstTables) {
				tree.addTree(tbl.buildTree(), true);
			}
		}
		return tree;
	}
}
