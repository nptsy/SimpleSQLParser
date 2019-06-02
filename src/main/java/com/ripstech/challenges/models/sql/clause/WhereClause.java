package com.ripstech.challenges.models.sql.clause;

import java.util.LinkedList;

import com.ripstech.challenges.models.ast.ASTNode;
import com.ripstech.challenges.models.ast.ASTNodeImpl;
import com.ripstech.challenges.models.ast.ASTTree;
import com.ripstech.challenges.models.sql.EnumModels;
import com.ripstech.challenges.models.sql.catelogobject.Column;
import com.ripstech.challenges.models.sql.expression.SQLExpression;
import com.ripstech.challenges.parser.ParseException;
import com.ripstech.challenges.parser.Tokenizer.TokenInfo;

public class WhereClause extends SQLClause{

	private SQLExpression sqlExpression;
	
	public WhereClause() {
		super(EnumModels.ClauseType.clsWhere);
	}
	
	public void parse(LinkedList<TokenInfo> inLstTokens) throws ParseException {
		lstTokenInfo = inLstTokens;
		if(lstTokenInfo == null || lstTokenInfo.size() <= 0) {
			return ;
		}
		sqlExpression = new SQLExpression();
		LinkedList<TokenInfo> lstTokens = new LinkedList<TokenInfo>();
		for(int i = 1; i< lstTokenInfo.size(); ++i) {
			lstTokens.add(lstTokenInfo.get(i));
		}
		sqlExpression.parse(lstTokens);
	}
	
	public ASTTree buildTree() {
		ASTTree tree = new ASTTree();
		ASTNode parent = new ASTNodeImpl();
		parent.setNodeName("WHERE");
		parent.setRootNode(true);
		tree.setRootNode(parent);
		
		if(sqlExpression!=null) {
				tree.addTree(sqlExpression.buildTree(), true);
		}
		return tree;
	}
	
	
}
