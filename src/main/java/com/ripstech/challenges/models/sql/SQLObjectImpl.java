package com.ripstech.challenges.models.sql;

import java.util.LinkedList;

import com.ripstech.challenges.models.ast.ASTTree;
import com.ripstech.challenges.parser.ParseException;
import com.ripstech.challenges.parser.Tokenizer.TokenInfo;

public abstract class SQLObjectImpl implements SQLObject {
	protected LinkedList<TokenInfo> lstTokenInfo;

	public void parse(LinkedList<TokenInfo> lstTokens) throws ParseException{
	}

	public void setBodyContent(LinkedList<TokenInfo> lstTokens) {
		lstTokenInfo = lstTokens;
	}
	
	public ASTTree buildTree() {
		return null;
	}
	
}
