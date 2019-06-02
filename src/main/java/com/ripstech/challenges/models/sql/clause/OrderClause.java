package com.ripstech.challenges.models.sql.clause;

import java.util.LinkedList;

import com.ripstech.challenges.models.ast.ASTNode;
import com.ripstech.challenges.models.ast.ASTNodeImpl;
import com.ripstech.challenges.models.ast.ASTTree;
import com.ripstech.challenges.models.sql.EnumModels;
import com.ripstech.challenges.models.sql.catelogobject.Column;
import com.ripstech.challenges.parser.ParseException;
import com.ripstech.challenges.parser.Tokenizer.TokenInfo;
import com.ripstech.challenges.utils.Token;

public class OrderClause extends SQLClause{

	private String orderType = Token.CASC;
	private LinkedList<Column> lstColumns;
	
	
	public OrderClause() {
		super(EnumModels.ClauseType.clsOrder);
		lstColumns = new LinkedList<Column>();
	}

	@Override
	public void parse(LinkedList<TokenInfo> lstTokens) throws ParseException{
		lstTokenInfo = lstTokens;
		if(lstTokenInfo.size() > 2) {
			StringBuilder colName = new StringBuilder();
			for(int i = 2 ; i < lstTokens.size(); ++i) {
				TokenInfo tok = lstTokenInfo.get(i);
				if (!tok.getOrigValue().equals(Token.CCOMMA + "") && 
						!tok.getOrigValue().equals(Token.CASC) && 
						!tok.getOrigValue().equals(Token.CDESC)) {
					colName.append(tok.getOrigValue() + " ");
				} else if(tok.getOrigValue().equals(Token.CASC)){
					orderType =  Token.CASC;					
				}else if(tok.getOrigValue().equals(Token.CDESC)) {
					orderType =  Token.CDESC;
				}else if(!colName.toString().isEmpty()) {
					Column col = new Column(colName.toString());
					lstColumns.add(col);
					colName = new StringBuilder();
				}
			}
			
			if (!colName.toString().isEmpty()) {
				Column col = new Column(colName.toString().trim());
				lstColumns.add(col);
				colName = new StringBuilder();
			}
		}
	}
	
	public ASTTree buildTree() {
		ASTTree tree = new ASTTree();
		ASTNode parent = new ASTNodeImpl();
		parent.setNodeName("ORDER BY");
		parent.setRootNode(true);
		tree.setRootNode(parent);
		tree.setRootNode(parent);
		
		if(lstColumns!=null && lstColumns.size() >0) {
			for(Column col :lstColumns) {
				tree.addTree(col.buildTree(), true);
			}
		}
		return tree;
	}
	
}
