package com.ripstech.challenges.models.sql.clause;

import java.util.HashMap;
import java.util.LinkedList;

import com.ripstech.challenges.models.sql.SQLObject;
import com.ripstech.challenges.parser.ParseException;
import com.ripstech.challenges.parser.Tokenizer.TokenInfo;
import com.ripstech.challenges.utils.Token;

public class SQLClauseFactory {
	private LinkedList<SQLObject> lstSQLClauses;
	private HashMap<String, String> mMapSQLObjects;
	
	public SQLClauseFactory() {
		lstSQLClauses = new LinkedList<SQLObject>();
		mMapSQLObjects = new HashMap<String, String>();
		mMapSQLObjects.put(Token.CWHERE, WhereClause.class.getCanonicalName());
		mMapSQLObjects.put(Token.CORDER, OrderClause.class.getCanonicalName());
		mMapSQLObjects.put(Token.CSELECT, SelectClause.class.getCanonicalName());
		mMapSQLObjects.put(Token.CFROM, FromClause.class.getCanonicalName());
	}
	
	
	public LinkedList<SQLObject> process(LinkedList<TokenInfo> lstTokens){
		if(lstTokens.size() > 0) {

			LinkedList<TokenInfo> clause = new LinkedList<TokenInfo>();
			for(int i = lstTokens.size() - 1 ; i >= 0  ;i--) {
				TokenInfo tok = lstTokens.get(i);
				if(tok.getOrigValue().equalsIgnoreCase(Token.CSEMI+""))
					continue;
				
				clause.add(0, tok);
				
				if(mMapSQLObjects.containsKey(tok.getOrigValue())) {
					SQLObject obj = createClause(clause);
					if(obj !=null ) {
						lstSQLClauses.add(obj);
					}
					clause = new LinkedList<TokenInfo>();
				}
			}
		}
		return lstSQLClauses;
	}
	
	private SQLObject createClause(LinkedList<TokenInfo> lstTokens) {
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
