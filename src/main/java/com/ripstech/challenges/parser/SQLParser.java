package com.ripstech.challenges.parser;

import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.ripstech.challenges.models.ast.ASTTree;
import com.ripstech.challenges.models.sql.SQLObject;
import com.ripstech.challenges.models.sql.statement.SQLStatementFactory;
import com.ripstech.challenges.parser.Tokenizer.TokenInfo;
import com.ripstech.challenges.utils.FileIO;

public class SQLParser {
	private final static Logger LOGGER = Logger.getLogger(SQLParser.class);
	
	private LinkedList<SQLObject> lstStatements;
	
	public SQLParser() {
	}
	
	public void printASTTree() {
		if(lstStatements!=null && lstStatements.size()>0) {
			LOGGER.info("AST parse tree:");
			System.out.println("");
			for(SQLObject stmt : lstStatements) {
				ASTTree tree = stmt.buildTree();
				tree.printASTTree();
				System.out.println("");
			}
		}else {
			LOGGER.info("There is no AST parse tree!");
		}
	}
	
	public boolean parseStringSQL(String sql) {
		
		try {
			Tokenizer tokenizer = new Tokenizer();
			
			LinkedList<TokenInfo> lstTokens = tokenizer.tokenize(sql);
			
			SQLStatementFactory factory = new SQLStatementFactory();
			
			lstStatements = factory.process(lstTokens);
		} catch (ParseException e) {
			LOGGER.error("Parsed failed - " + e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean parseSQL(String inFilePath) {
		
		LinkedList<String> lstLines = FileIO.readLines(inFilePath);
		Tokenizer tokenizer = new Tokenizer();
		try {
			LinkedList<TokenInfo> lstTokens = tokenizer.tokenize(lstLines);
			
			SQLStatementFactory factory = new SQLStatementFactory();
			
			lstStatements = factory.process(lstTokens);
			
		} catch (ParseException e) {
			LOGGER.error("Parsed failed: " + e.getMessage());
			return false;
		}
		return true;
	}
	
	public LinkedList<SQLObject> getListStatements(){
		return lstStatements;
	}
	
}
