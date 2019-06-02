package com.ripstech.challenges.models.sql.catelogobject;

import java.util.LinkedList;

import org.apache.log4j.Logger;

import com.ripstech.challenges.models.ast.ASTNode;
import com.ripstech.challenges.models.ast.ASTNodeImpl;
import com.ripstech.challenges.models.ast.ASTTree;
import com.ripstech.challenges.models.sql.SQLObjectImpl;
import com.ripstech.challenges.parser.Tokenizer.TokenInfo;

public class Database extends SQLObjectImpl{
	private final static Logger LOGGER = Logger.getLogger(Table.class);
	
	private Server server;
	private String databaseName; 
	
	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public Database(String dbName) {
		super();
		databaseName = dbName;
	}
	
	public Database (Server sv, String dbName) {
		super();
		server = sv;
		databaseName = dbName;
	}
	
	public Database() {
		super();
		server = new Server();
		databaseName = "";
	}
	
	public Server getServer() {
		return server;
	}

	public void setServer(Server server) {
		this.server = server;
	}

	public void parse(LinkedList<TokenInfo> lstTokens) {
		
	}
	
	public ASTTree buildTree() {
		ASTTree tree = new ASTTree();
		
		//root node
		ASTNode parent = new ASTNodeImpl();
		parent.setRootNode(true);
		parent.setNodeName("Database");
		parent.setNodeValue(databaseName);
		tree.setRootNode(parent);
		
		if(server!=null && !server.getServerName().isEmpty()) {
			ASTNode node = new ASTNodeImpl();
			node.setParent(parent);
			node.setNodeName("Server");
			node.setNodeValue(server.getServerName());
			tree.addNode(node, false);
		}
		
		return tree;
	}
	
}
