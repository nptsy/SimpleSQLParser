package com.ripstech.challenges.models.sql.statement;

import java.util.LinkedList;
import java.util.Stack;

import org.apache.log4j.Logger;

import com.ripstech.challenges.models.ast.ASTNode;
import com.ripstech.challenges.models.ast.ASTNodeImpl;
import com.ripstech.challenges.models.ast.ASTTree;
import com.ripstech.challenges.models.sql.EnumModels;
import com.ripstech.challenges.models.sql.SQLObject;
import com.ripstech.challenges.models.sql.catelogobject.Column;
import com.ripstech.challenges.models.sql.catelogobject.Table;
import com.ripstech.challenges.models.sql.expression.SQLConstantValue;
import com.ripstech.challenges.models.sql.expression.SQLFunction;
import com.ripstech.challenges.parser.ParseException;
import com.ripstech.challenges.parser.Tokenizer.TokenInfo;
import com.ripstech.challenges.utils.Token;

public class InsertStatement extends SQLStatement {

	private final static Logger LOGGER = Logger.getLogger(InsertStatement.class);
	
	private Table table;
	private LinkedList<Column> lstColumns;
	private SQLStatement stmtSelectForValues;
	private LinkedList<LinkedList<SQLObject>> lstRowValues;

	public InsertStatement() {
		super(EnumModels.StatementType.stmtInsert);
		lstColumns = new LinkedList<Column>();
		lstRowValues = new LinkedList<LinkedList<SQLObject>>();
	}

	public void parse(LinkedList<TokenInfo> lstTokens) throws ParseException {
		lstTokenInfo = lstTokens;
		if (lstTokenInfo != null && lstTokenInfo.size() > 2) {

			TokenInfo insertCmd = lstTokens.get(0);
			TokenInfo intoCmd = lstTokens.get(1);

			if (insertCmd.getOrigValue().equalsIgnoreCase(Token.CINSERT)
					&& intoCmd.getOrigValue().equalsIgnoreCase(Token.CINTO)) {

				int i = 2;
				TokenInfo tok = lstTokens.get(i);
				LinkedList<TokenInfo> tmpLstTokens = new LinkedList<TokenInfo>();
				while (!tok.getOrigValue().equalsIgnoreCase(Token.CSELECT)
						&& !tok.getOrigValue().equalsIgnoreCase(Token.CVALUES)) {
					tmpLstTokens.add(tok);
					i++;
					tok = lstTokens.get(i);
				}
				tok = tmpLstTokens.get(tmpLstTokens.size() - 1);
				if (tok.getOrigValue().equals(Token.CLPAREN + "")) {
					while (tok.getOrigValue().equals(Token.CLPAREN + "")) {
						i--;
						tmpLstTokens.remove(tmpLstTokens.size() - 1);
						tok = tmpLstTokens.get(tmpLstTokens.size() - 1);
					}
				}

				// parse table and columns
				parseTableAndColumns(tmpLstTokens);

				// parse value or select statement
				tok = lstTokens.get(i);
				tmpLstTokens = new LinkedList<TokenInfo>();
				if (tok.getOrigValue().equals(Token.CLPAREN + "")) {
					Stack<TokenInfo> tmpStack = new Stack<TokenInfo>();
					tmpStack.push(tok);
					while (!tmpStack.isEmpty() && i < lstTokens.size()) {
						i++;
						tok = lstTokens.get(i);
						lstTokens.add(tok);
						if (tok.getOrigValue().equals(Token.CRPAREN + "")) {
							tmpStack.pop();
						} else if (tok.getOrigValue().equals(Token.CLPAREN + "")) {
							tmpStack.push(tok);
						}
					}

					if (i == lstTokenInfo.size() && !tmpStack.isEmpty()) {
						throw new ParseException("Invalid expressions");
					}
					lstTokens.remove(lstTokens.size() - 1);
					parseStatement(lstTokens);

				} else if (tok.getOrigValue().equals(Token.CVALUES + "")) {
					i++;
					while (i < lstTokens.size()) {
						
						tok = lstTokens.get(i);
						if (tok.getOrigValue().equals(Token.CSEMI + "")) {
							break;
						}
						tmpLstTokens.add(tok);
						i++;
					}

					parseValues(tmpLstTokens);
				} else if (tok.getOrigValue().equals(Token.CSELECT)) {
					tmpLstTokens.add(tok);
					i++;
					while (i < lstTokens.size()) {
						i++;
						tok = lstTokens.get(i);
						if (tok.getOrigValue().equals(Token.CSEMI + "")) {
							break;
						}
						tmpLstTokens.add(tok);
					}

					parseStatement(lstTokens);
				}
			} else {
				throw new ParseException("Invalid Insert statement");
			}
		}
	}

	private void parseStatement(LinkedList<TokenInfo> lstTokens) throws ParseException {
		
	}

	private void parseValues(LinkedList<TokenInfo> lstTokens) throws ParseException {
		if(lstTokens.size() > 0 ) {
			
			LinkedList<TokenInfo> tmpLstTokens = new LinkedList<TokenInfo>();
			for(int i = 0 ; i < lstTokens.size(); ++i) {
				TokenInfo tok = lstTokens.get(i);
				
				if(tok.getOrigValue().equals(Token.CLPAREN+"")) {
					Stack<TokenInfo> tmpStack = new Stack<TokenInfo>();
					tmpStack.add(tok);
					while(!tmpStack.isEmpty() && i < lstTokens.size()) {
						i++;
						tok = lstTokens.get(i);
						tmpLstTokens.add(tok);
						if (tok.getOrigValue().equals(Token.CRPAREN + "")) {
							tmpStack.pop();
						} else if (tok.getOrigValue().equals(Token.CLPAREN + "")) {
							tmpStack.push(tok);
						}
					}
					
					if (i == lstTokens.size() && !tmpStack.isEmpty()) {
						throw new ParseException("Invalid expressions");
					}
					tmpLstTokens.remove(tmpLstTokens.size() - 1);
					parseARow(tmpLstTokens);
					tmpLstTokens = new LinkedList<TokenInfo>();
				}else if(tok.getOrigValue().equals(Token.CCOMMA+"")) {
					continue;
				}else {
					LOGGER.error("Unknown error in parsing values");
				}
			}
		}
	}

	private void parseARow(LinkedList<TokenInfo> lstTokens) throws ParseException {
		if(lstTokens.size() > 0 ) {
			LinkedList<SQLObject> row = new LinkedList<SQLObject>();
			for(int i = 0 ; i < lstTokens.size(); ++i) {
				TokenInfo tok = lstTokens.get(i);
				
				if(tok.getOrigValue().equals(Token.CSEMI+"") ||tok.getOrigValue().equals(Token.CCOMMA+"")) {
					continue;
				}
				
				if(SQLFunction.isAFunction(tok.getOrigValue())) {
					String fName = tok.getOrigValue();
					SQLFunction func = new SQLFunction(tok.getOrigValue());
					if((i < lstTokens.size() - 1) && lstTokens.get(i+1).getOrigValue().equals(Token.CLPAREN +"")) {
						StringBuilder params = new StringBuilder();
						i++;
						Stack<String> tmpStack = new Stack<String>();
						tmpStack.push("(");
						params.append("(");
						while(!tmpStack.isEmpty() && i < lstTokens.size()) {
							i++;
							tok = lstTokens.get(i);
							params.append(tok.getOrigValue()+" ");
							
							if(tok.getOrigValue().equals(Token.CLPAREN+"")) {
								tmpStack.push("(");
							}else if(tok.getOrigValue().equals(Token.CRPAREN+"")) {
								tmpStack.pop();
							}
						}
						
						if(i == lstTokens.size() && !tmpStack.isEmpty()) {
							throw new ParseException("Invalid parements of inner functions");
						}
						
						func.setFunctionName(fName+" "+params.toString());
					}
					
					row.add(func);
				}else {
					SQLConstantValue constVal = new SQLConstantValue(tok.getOrigValue());
					row.add(constVal);
				}
			}
			
			if(row.size() != lstColumns.size()) {
				throw new ParseException("Not maching the number of insert values");
			}
			
			lstRowValues.add(row);
		}
	}
	
	private void parseTableAndColumns(LinkedList<TokenInfo> lstTokens) throws ParseException {

		if (lstTokens.size() > 0) {
			table = new Table();
			table.setTableName(lstTokens.get(0).getOrigValue());

			LinkedList<TokenInfo> tmpLstToken = new LinkedList<TokenInfo>();
			
			for (int i = 1; i < lstTokens.size(); ++i) {
				TokenInfo tok = lstTokens.get(i);
				if (!tok.getOrigValue().equals(Token.CCOMMA+"") && !tok.getOrigValue().equals(Token.CLPAREN+"")
						&& !tok.getOrigValue().equals(Token.CRPAREN+"") && !tok.getOrigValue().equals(Token.CSEMI+"")) {

//					Column col = new Column(tok.getOrigValue());
//					lstColumns.add(col);
					tmpLstToken.add(tok);
				}else {
					if(tmpLstToken.size() > 0) {
						Column col = new Column();
						col.parse(tmpLstToken);
						lstColumns.add(col);
						tmpLstToken = new LinkedList<TokenInfo>();
					}
				}
			}
			
			
			if(tmpLstToken.size() > 0) {
				Column col = new Column();
				col.parse(tmpLstToken);
				tmpLstToken = new LinkedList<TokenInfo>();
			}
		}
	}
	
	public ASTTree buildTree() {
		ASTTree tree = new ASTTree();
		
		ASTNode root = new ASTNodeImpl();
		root.setRootNode(true);
		root.setNodeName("INSERT INTO");
		tree.setRootNode(root);
		
		if(table!=null) {
			ASTNode tblNode = new ASTNodeImpl();
			tblNode.setNodeName("Table");
			tblNode.setNodeValue(table.getTableName());
			tblNode.setParent(root);
			tree.addNode(tblNode, false);
			
			if(lstColumns !=null && lstColumns.size() >0) {
				for(Column col: lstColumns) {
					ASTNode childNode = new ASTNodeImpl();
					childNode.setNodeName("Column");
					childNode.setNodeValue(col.getColumnName());
					childNode.setParent(tblNode);
					tblNode.addChild(childNode, -1);
					//tree.addNode(childNode, false);
				}
			}
		}

		if(lstRowValues!=null && lstRowValues.size() > 0) {
			ASTNode valNode = new ASTNodeImpl();
			valNode.setNodeName("VALUES");
			
			for(int i = 0 ; i < lstRowValues.size(); ++i) {
				LinkedList<SQLObject> lstValues = lstRowValues.get(i);
				
				ASTNode recNode = new ASTNodeImpl();
				recNode.setNodeName("Record: " +(i+1));
				recNode.setParent(valNode);
				valNode.addChild(recNode, -1);
				//tree.addNode(recNode);
				
				for(SQLObject values: lstValues) {
					ASTTree valTree = values.buildTree();
					ASTNode valRootNode = valTree.getRootNode();
					valRootNode.setParent(recNode);
					recNode.addChild(valRootNode, -1);
					//tree.addTree(valTree, false);
				}
			}
			
			valNode.setParent(root);
			tree.addNode(valNode, false);
		}
		
		if(stmtSelectForValues!= null) {
			ASTTree selectTree = stmtSelectForValues.buildTree();
			tree.addTree(selectTree, true);
		}
		
		return tree;
	}
	
}
