package com.ripstech.challenges.models.sql.statement;

import java.util.HashMap;
import java.util.LinkedList;

import com.ripstech.challenges.models.sql.SQLObject;
import com.ripstech.challenges.parser.ParseException;
import com.ripstech.challenges.parser.Tokenizer.TokenInfo;
import com.ripstech.challenges.utils.Token;

public class SQLStatementFactory {
	
	private LinkedList<SQLObject> lstSQLObjects;
	private HashMap<String, String> mMapSQLObjects;
	
	
	public SQLStatementFactory() {
		lstSQLObjects = new LinkedList<SQLObject>();
		mMapSQLObjects = new HashMap<String, String>();
		mMapSQLObjects.put(Token.CUSE, UseStatement.class.getCanonicalName());
		mMapSQLObjects.put(Token.CSELECT, SelectStatement.class.getCanonicalName());
		mMapSQLObjects.put(Token.CINSERT, InsertStatement.class.getCanonicalName());
		mMapSQLObjects.put(Token.CDELETE, DeleteStatement.class.getCanonicalName());
	}
	
	public LinkedList<SQLObject> process(LinkedList<TokenInfo> lstTokenInfos) throws ParseException{
		
		if(lstTokenInfos.size() > 0) {

			LinkedList<TokenInfo> statement = new LinkedList<TokenInfo>();
			for(int i = 0; i < lstTokenInfos.size() ;++i) {
				TokenInfo tok = lstTokenInfos.get(i);
				statement.add(tok);
				
				if(tok.getOrigValue().equalsIgnoreCase(Token.CSEMI+"")) {
					SQLObject obj = createStatement(statement);
					if(obj !=null ) {
						lstSQLObjects.add(obj);
					}
					statement = new LinkedList<TokenInfo>();
				}
			}
			
			if(statement.size() != 0 ) {
				throw new ParseException("Incomple statement");
			}
			
		}
		
		return lstSQLObjects;
	}
	
	private SQLObject createStatement(LinkedList<TokenInfo> lstTokens) {
		SQLObject obj = null;
		if(lstTokens.size() > 0) {
			TokenInfo head = lstTokens.get(0);
			String className = mMapSQLObjects.get(head.getOrigValue());
			if(className !=null && className.length() > 0) {
				try {
					Class c = Class.forName(className);
					obj = (SQLObject)c.newInstance();
					obj.parse(lstTokens);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		return obj;
	}
}



