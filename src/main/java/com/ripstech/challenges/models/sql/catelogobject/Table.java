package com.ripstech.challenges.models.sql.catelogobject;

import java.util.LinkedList;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.ripstech.challenges.models.ast.ASTNode;
import com.ripstech.challenges.models.ast.ASTNodeImpl;
import com.ripstech.challenges.models.ast.ASTTree;
import com.ripstech.challenges.models.sql.SQLObjectImpl;
import com.ripstech.challenges.parser.ParseException;
import com.ripstech.challenges.parser.Tokenizer.TokenInfo;
import com.ripstech.challenges.utils.Token;

public class Table extends SQLObjectImpl{

	private final static Logger LOGGER = Logger.getLogger(Table.class);
	private Database db;
	private String tableName;
	private String tableAliasName;

	public Table() {
		super();
		tableName = "";
	}

	public Table(Database database, String tblName) {
		super();
		db = database;
		tableName = tblName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Database getDb() {
		return db;
	}

	public void setDb(Database db) {
		this.db = db;
	}

	public void setBodyContent(LinkedList<TokenInfo> lstTokens) {
		super.setBodyContent(lstTokens);
	}

	public void parse(LinkedList<TokenInfo> lstTokens) throws ParseException{
		lstTokenInfo = lstTokens;
		StringBuilder tmpName = new StringBuilder();
		if(lstTokenInfo!=null) {
			if(lstTokenInfo.size() == 1) {
				tmpName.append(lstTokenInfo.get(0).getOrigValue());
			} else if (lstTokenInfo.size() == 2) {
				tableAliasName = lstTokenInfo.get(1).getOrigValue();
				tmpName.append(lstTokenInfo.get(0).getOrigValue());
			} else if(lstTokenInfo.size() == 3 && 
					lstTokenInfo.get(1).getOrigValue().equalsIgnoreCase(Token.CAS)){
				
				tableAliasName = lstTokenInfo.get(2).getOrigValue();
				tmpName.append(lstTokenInfo.get(0).getOrigValue());
			} else {
				for(TokenInfo tok: lstTokenInfo) {
					tmpName.append(tok.getOrigValue());
				}
			}
		}
		
		int index = tmpName.toString().indexOf('.');
		boolean moreThanOnce = index != -1 && index != tmpName.toString().lastIndexOf('.');
		if(moreThanOnce) {
			throw new ParseException("Invalid table name");
		}
		
		String [] tmpTblServernames = tmpName.toString().split(Pattern.quote("."));
		
		
		if(tmpTblServernames.length > 1) {
			db = new Database();
			db.setDatabaseName(tmpTblServernames[0]);
			
			tableName = tmpTblServernames[1];	
		}else if(tmpTblServernames.length == 1){
			tableName = tmpTblServernames[0];
		} else {
			tableName = tmpName.toString();
		}
		
	}
	
	public ASTTree buildTree() {
		ASTTree tree = new ASTTree();
		
		//root node
		ASTNode parent = new ASTNodeImpl();
		parent.setRootNode(true);
		parent.setNodeName("Table");
		parent.setNodeValue(tableName);
		tree.setRootNode(parent);
		
		
		if(tableAliasName!=null && !tableAliasName.isEmpty()) {
			ASTNode node = new ASTNodeImpl();
			node.setParent(parent);
			node.setNodeName("Alias");
			node.setNodeValue(tableAliasName);
			tree.addNode(node, false);
		}
		
		if(db!= null && !db.getDatabaseName().isEmpty()) {
			ASTNode node = new ASTNodeImpl();
			node.setParent(parent);
			node.setNodeName("Database");
			node.setNodeValue(db.getDatabaseName());
			tree.addNode(node, false);
		}
		
		return tree;
	}
	
}
