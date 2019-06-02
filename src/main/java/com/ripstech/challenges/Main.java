package com.ripstech.challenges;

import org.apache.log4j.Logger;

import com.ripstech.challenges.models.ast.ASTTree;
import com.ripstech.challenges.parser.SQLParser;


public class Main {

	private final static Logger LOGGER = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {
		
		System.out.println("Usage: mvn exec:java -Dexec.args=\"<file-path>\"");
		System.out.println("Example: mvn exec:java -Dexec.args=\"\\\"D:\\test folder\\operations.sql\\\"\"");
		
		if(args!=null && args.length == 1){
			SQLParser parser = new SQLParser();
			boolean isSuccessfull = parser.parseSQL(args[0]);
			if(isSuccessfull) {
				parser.printASTTree();
			} else {
				LOGGER.info("Failed to parse SQL statement in the file "+ args[0]);
			}
		}
	}
}


