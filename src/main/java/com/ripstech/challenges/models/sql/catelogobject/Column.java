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

public class Column extends SQLObjectImpl {
	
	private final static Logger LOGGER = Logger.getLogger(Column.class);

	private String fieldName;
	private String fieldAliasName;
	private String fieldPrimitiveType;
	
	private Table table;
	
	public Column() {
		super();
		fieldName ="";
		fieldAliasName = "";
		fieldPrimitiveType ="";
	}
	
	public Column(String colName) {
		super();
		fieldName = colName;
	}
	
	public Column(Table tbl, String fName, String fPrimitiveType) {
		super();
		fieldName = fName;
		fieldPrimitiveType = fPrimitiveType;
		table = tbl;
	}

	public void setAliasName(String alias) {
		fieldAliasName = alias;
	}
	
	public void parse(LinkedList<TokenInfo> lstTokens) throws ParseException {
		lstTokenInfo = lstTokens;
		StringBuilder tmpName = new StringBuilder();
		if(lstTokenInfo!=null) {
			if(lstTokenInfo.size() == 1) {
				tmpName.append(lstTokenInfo.get(0).getOrigValue());
			} else if (lstTokenInfo.size() == 2) {
				fieldAliasName = lstTokenInfo.get(1).getOrigValue();
				tmpName.append(lstTokenInfo.get(0).getOrigValue());
			} else if(lstTokenInfo.size() == 3 && 
					lstTokenInfo.get(1).getOrigValue().equalsIgnoreCase(Token.CAS)){
				
				fieldAliasName = lstTokenInfo.get(2).getOrigValue();
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
			throw new ParseException("Invalid column name");
		}
		
		String [] tmpColTblNames = tmpName.toString().split(Pattern.quote("."));
		
		
		if(tmpColTblNames.length > 1) {
			table = new Table();
			table.setTableName(tmpColTblNames[0]);
			
			fieldName = tmpColTblNames[1];
			
		}else if(tmpColTblNames.length == 1){
			fieldName = tmpColTblNames[0];
		} else {
			fieldName = tmpName.toString();
		}
	}
	
	public String getColumnName() {
		return fieldName;
	}
	
	public String getPrimitiveType() {
		return fieldPrimitiveType;
	}
	
	public ASTTree buildTree() {
		ASTTree tree = new ASTTree();
		
		ASTNode parent = new ASTNodeImpl();
		parent.setRootNode(true);
		parent.setNodeName("Column");
		parent.setNodeValue(fieldName);
		tree.setRootNode(parent);

		if(fieldAliasName !=null && !fieldAliasName.isEmpty()) {
			ASTNode node = new ASTNodeImpl();
			node.setParent(parent);
			node.setNodeName("Alias");
			node.setNodeValue(fieldAliasName);
			tree.addNode(node, false);
		}
		
		if(fieldPrimitiveType!=null && !fieldPrimitiveType.isEmpty()) {
			ASTNode node = new ASTNodeImpl();
			node.setParent(parent);
			node.setNodeName("Primitive Type");
			node.setNodeValue(fieldPrimitiveType);
			tree.addNode(node, false);
		}
		
		if(table!=null && !table.getTableName().isEmpty()) {
			ASTNode node = new ASTNodeImpl();
			node.setParent(parent);
			node.setNodeName("Table");
			node.setNodeValue(table.getTableName());
			tree.addNode(node, false);
		}
		
		return tree;
	}
	
}
