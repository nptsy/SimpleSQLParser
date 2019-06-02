package com.ripstech.challenges.models.sql.expression;

import java.util.LinkedList;
import java.util.Stack;

import com.ripstech.challenges.models.ast.ASTNode;
import com.ripstech.challenges.models.ast.ASTNodeImpl;
import com.ripstech.challenges.models.ast.ASTTree;
import com.ripstech.challenges.models.sql.SQLObject;
import com.ripstech.challenges.models.sql.SQLObjectImpl;
import com.ripstech.challenges.parser.ParseException;
import com.ripstech.challenges.parser.Tokenizer.TokenInfo;
import com.ripstech.challenges.utils.Token;

public class SQLExpression extends SQLObjectImpl{

	private LinkedList<SQLObject> lstSubExpressions;
	
	public SQLExpression() {
		super();
		lstSubExpressions = new LinkedList<SQLObject>();
	}

	public void parse(LinkedList<TokenInfo> inLstTokens) throws ParseException {
		
		lstTokenInfo = inLstTokens;
		
		LinkedList<TokenInfo> lstTokens = new LinkedList<TokenInfo>();
		
		for(int i = 0; i < lstTokenInfo.size() ; ++i) {
			TokenInfo tok = lstTokenInfo.get(i);
			if(tok.getOrigValue().equalsIgnoreCase(Token.CAND)) {
				createSubExpression(lstTokens);
				createOperator(Token.CAND);
				lstTokens = new LinkedList<TokenInfo>();
			} else if(tok.getOrigValue().equalsIgnoreCase(Token.COR)) {
				createSubExpression(lstTokens);
				createOperator(Token.COR);
				lstTokens = new LinkedList<TokenInfo>();
			} else if(tok.getOrigValue().equalsIgnoreCase(Token.CNOT)) {
				createSubExpression(lstTokens);
				createOperator(Token.CNOT);
				lstTokens = new LinkedList<TokenInfo>();
			} else if(tok.getOrigValue().equalsIgnoreCase(Token.CIN)) {
				createSubExpression(lstTokens);
				createOperator(Token.CIN);
				lstTokens = new LinkedList<TokenInfo>();
			} else if(tok.getOrigValue().equalsIgnoreCase(Token.CIS)) {
				createSubExpression(lstTokens);
				createOperator(Token.CIS);
				lstTokens = new LinkedList<TokenInfo>();
			} else if(tok.getOrigValue().equalsIgnoreCase(Token.CLPAREN +"")) {
				   Stack<TokenInfo> tmpStack = new Stack<TokenInfo>();	
				   tmpStack.push(tok);
				   
				   while(!tmpStack.isEmpty() && i < lstTokenInfo.size()){
					   i++;
					   tok = lstTokens.get(i);
					   lstTokens.add(tok);
					   
					   if(tok.getOrigValue().equalsIgnoreCase(Token.CRPAREN +"")) {
						   tmpStack.pop();
					   }else if(tok.getOrigValue().equalsIgnoreCase(Token.CLPAREN +"")) {
						   tmpStack.push(tok);
					   }
				   }
				   
				   if(i == lstTokenInfo.size() && !tmpStack.isEmpty()) {
					   throw new ParseException("Invalid expressions");
				   }
				   lstTokens.remove(lstTokens.size() -1);
				   createSubExpression(lstTokens);
				   
			} else {
				lstTokens.add(tok);
			}
		}	
		
		if(lstTokens.size() > 0) {
			createSubExpression(lstTokens);
		}
	}
	
	private void createSubExpression(LinkedList<TokenInfo> lstTokens) {
		if(lstTokens.size() > 0) {
			SQLExpression expr = new SQLExpression();
			expr.setBodyContent(lstTokens);
			lstSubExpressions.add(expr);
			lstTokens = new LinkedList<TokenInfo>();
		}
	}
	
	private void createOperator(String operator) {
		SQLOperator op = new SQLOperator(operator);
		lstSubExpressions.add(op);
	}
	
	private String getStrExprBasedTokens() {
		StringBuilder strExpr = new StringBuilder();
		if(lstTokenInfo!=null && lstTokenInfo.size()>0) {
			for(TokenInfo tok : lstTokenInfo) {
				strExpr.append(tok.getOrigValue());
			}
		}
		
		return strExpr.toString();
	}
	
	
	public ASTTree buildTree() {
		ASTTree tree = new ASTTree();
		ASTNode root = null;
		
		if(lstSubExpressions!=null && lstSubExpressions.size() >0) {
			int iRootPos = -1;
			for(int i = 0 ; i < lstSubExpressions.size(); ++i) {
				SQLObject obj = lstSubExpressions.get(i);
				if(obj instanceof SQLOperator) {
					iRootPos = i;
					SQLOperator op = (SQLOperator)obj;
					root = new ASTNodeImpl();
					root.setNodeName(op.getOperator());
					root.setRootNode(true);
					tree.setRootNode(root);
					break;
				}
			}
			
			boolean isRootExist = iRootPos == -1 ? false: true;
			
			for(int i = 0 ; i < lstSubExpressions.size(); i++) {
				if(i == iRootPos)
					continue;
				
				SQLObject obj = lstSubExpressions.get(i);
				if(obj instanceof SQLOperator) {
					SQLOperator op = (SQLOperator)obj;
					ASTNode node = new ASTNodeImpl();
					node.setNodeName(op.getOperator());
					tree.addNode(node, false);
				}else if(obj instanceof SQLExpression){
					SQLExpression op = (SQLExpression)obj;
					ASTNode node = new ASTNodeImpl();
					node.setNodeName(op.getStrExprBasedTokens());
					tree.addNode(node, !isRootExist);
					isRootExist = true;
				}
			}
		}
		
		return tree;
	}
	
}
