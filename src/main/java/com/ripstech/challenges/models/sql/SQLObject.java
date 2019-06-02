package com.ripstech.challenges.models.sql;

import java.util.LinkedList;

import com.ripstech.challenges.models.ast.ASTTree;
import com.ripstech.challenges.parser.ParseException;
import com.ripstech.challenges.parser.Tokenizer.TokenInfo;

public interface SQLObject {
	public void parse(LinkedList<TokenInfo> lstTokens) throws ParseException;
	public ASTTree buildTree();
	
}
