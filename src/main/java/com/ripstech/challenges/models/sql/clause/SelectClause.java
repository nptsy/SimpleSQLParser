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

public class SelectClause extends SQLClause {

	private LinkedList<Column> lstColumns;

	public SelectClause() {
		super(EnumModels.ClauseType.clsSelect);
		lstColumns = new LinkedList<Column>();
	}

	public void parse2(LinkedList<TokenInfo> lstTokens) throws ParseException{
		lstTokenInfo = lstTokens;
		if (lstTokenInfo != null && lstTokenInfo.size() > 0) {
			StringBuilder colName = new StringBuilder();

			for (int i = 1; i < lstTokenInfo.size(); ++i) {
				TokenInfo tok = lstTokenInfo.get(i);
				if (!tok.getOrigValue().equals(Token.CCOMMA + "")) {
					colName.append(tok.getOrigValue() + " ");
				} else if (!colName.toString().isEmpty()) {
					Column col = new Column(colName.toString());
					lstColumns.add(col);
					colName = new StringBuilder();
				}
			}
			
			if (!colName.toString().isEmpty()) {
				Column col = new Column(colName.toString());
				lstColumns.add(col);
				colName = new StringBuilder();
			}
		}
		
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
					Column col = new Column();
					col.parse(lstsubTokens);
					lstColumns.add(col);
					lstsubTokens = new LinkedList<TokenInfo>();
				}
			}
		}
		
		if(lstsubTokens.size() != 0) {
			Column col = new Column();
			col.parse(lstsubTokens);
			lstColumns.add(col);
			lstsubTokens = new LinkedList<TokenInfo>();
		}
		
	}
	
	
	public ASTTree buildTree() {
		ASTTree tree = new ASTTree();
		ASTNode parent = new ASTNodeImpl();
		parent.setNodeName("COLUMNS");
		parent.setRootNode(true);
		tree.setRootNode(parent);
		
		if(lstColumns!=null && lstColumns.size() >0) {
			for(Column col :lstColumns) {
				tree.addTree(col.buildTree(), true);
			}
		}
		return tree;
	}
}
