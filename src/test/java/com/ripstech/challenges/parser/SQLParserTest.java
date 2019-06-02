package com.ripstech.challenges.parser;

import java.util.LinkedList;

import com.ripstech.challenges.models.ast.ASTTree;
import com.ripstech.challenges.models.sql.SQLObject;
import com.ripstech.challenges.models.sql.statement.DeleteStatement;
import com.ripstech.challenges.models.sql.statement.InsertStatement;
import com.ripstech.challenges.models.sql.statement.SelectStatement;

import junit.framework.TestCase;

public class SQLParserTest extends TestCase{

	private String selectStmt1 = "SELECT column1, column2 FROM table1, table2 WHERE column2='value';";
	private String selectStmt2 = "SELECT column1, column2 FROM table1, table2 WHERE column2='value;";
	
	private String selectStmt3 = "SELECT D.DepartmentName FROM Department AS D;";
	private String selectStmt4 = "SELECT d.DepartmentId Id, d.DepartmentName AS Name FROM Department d;";
	
	private String insertStmt1 = "INSERT INTO employee (id, name, dept, age, salary, location) VALUES (105, 'Srinath', 'Aeronautics', 27, 33000, 'Frankfurt');";
	private String insertStmt2 = "INSERT INTO employee (id, name, dept, age, salary, location) VALUES (105, 'Srinath', 'Aeronautics', 27, 33000, 'Frankfurt'), "
			+ "(102, 'Tobias', 'Manager', 42, 60000, 'Paderborn'), "
			+ "(108, 'MannK', 'IT', 32, 50000, 'Berlin');";
	
	
	private String deleteStmt1 = "DELETE FROM employee WHERE id = 100;";
	private String deleteStmt2 = "DELETE FROM employee;";
	private String deleteStmt3 = "DELETE FROM database2.logs WHERE id < 1000;";
	
	
	private SQLParser parser; 
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		parser = new SQLParser();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		parser = null;
	}
	
	
	public void testParseDeleteStmt2() {
		// test valid sql
		boolean isSuccessfull = parser.parseStringSQL(deleteStmt2);
		assertTrue(isSuccessfull);
		
		LinkedList<SQLObject> lstSQLStatements = parser.getListStatements();
		assertEquals(lstSQLStatements.size(), 1);
		assertTrue(lstSQLStatements.get(0) instanceof DeleteStatement);
		
		
		DeleteStatement deleteStmt = (DeleteStatement) lstSQLStatements.get(0); 
		ASTTree tree = deleteStmt.buildTree();
		assertNotNull(tree);
		tree.printASTTree();
	}
	
	
	public void testParseDeleteStmt3() {
		// test valid sql
		boolean isSuccessfull = parser.parseStringSQL(deleteStmt3);
		assertTrue(isSuccessfull);
		
		LinkedList<SQLObject> lstSQLStatements = parser.getListStatements();
		assertEquals(lstSQLStatements.size(), 1);
		assertTrue(lstSQLStatements.get(0) instanceof DeleteStatement);
		
		
		DeleteStatement deleteStmt = (DeleteStatement) lstSQLStatements.get(0); 
		ASTTree tree = deleteStmt.buildTree();
		assertNotNull(tree);
		tree.printASTTree();
	}
	
	public void testParseDeleteStmt() {
		// test valid sql
		boolean isSuccessfull = parser.parseStringSQL(deleteStmt1);
		assertTrue(isSuccessfull);
		
		LinkedList<SQLObject> lstSQLStatements = parser.getListStatements();
		assertEquals(lstSQLStatements.size(), 1);
		assertTrue(lstSQLStatements.get(0) instanceof DeleteStatement);
		
		
		DeleteStatement deleteStmt = (DeleteStatement) lstSQLStatements.get(0); 
		ASTTree tree = deleteStmt.buildTree();
		assertNotNull(tree);
		tree.printASTTree();
	}
	
	public void testParseInsertStmt() {
		// test valid sql
		boolean isSuccessfull = parser.parseStringSQL(insertStmt1);
		assertTrue(isSuccessfull);
		
		LinkedList<SQLObject> lstSQLStatements = parser.getListStatements();
		assertEquals(lstSQLStatements.size(), 1);
		assertTrue(lstSQLStatements.get(0) instanceof InsertStatement);
		
		
		InsertStatement insertStmt = (InsertStatement) lstSQLStatements.get(0); 
		ASTTree tree = insertStmt.buildTree();
		assertNotNull(tree);
		tree.printASTTree();
	}
	
	public void testParseInsertStmt2() {
		// test valid sql
		boolean isSuccessfull = parser.parseStringSQL(insertStmt2);
		assertTrue(isSuccessfull);
		
		LinkedList<SQLObject> lstSQLStatements = parser.getListStatements();
		assertEquals(lstSQLStatements.size(), 1);
		assertTrue(lstSQLStatements.get(0) instanceof InsertStatement);
		
		
		InsertStatement insertStmt = (InsertStatement) lstSQLStatements.get(0); 
		ASTTree tree = insertStmt.buildTree();
		assertNotNull(tree);
		tree.printASTTree();
	}
	
	public void testParseAsiasName() {
		// test valid sql
		boolean isSuccessfull = parser.parseStringSQL(selectStmt3);
		assertTrue(isSuccessfull);
		
		LinkedList<SQLObject> lstSQLStatements = parser.getListStatements();
		assertEquals(lstSQLStatements.size(), 1);
		assertTrue(lstSQLStatements.get(0) instanceof SelectStatement);
		
		SelectStatement stmtSelect = (SelectStatement) lstSQLStatements.get(0); 
		ASTTree tree = stmtSelect.buildTree();
		assertNotNull(tree);
		tree.printASTTree();
		
		
		
		isSuccessfull = parser.parseStringSQL(selectStmt4);
		assertTrue(isSuccessfull);
		
		lstSQLStatements = parser.getListStatements();
		assertEquals(lstSQLStatements.size(), 1);
		assertTrue(lstSQLStatements.get(0) instanceof SelectStatement);
		
		stmtSelect = (SelectStatement) lstSQLStatements.get(0); 
		tree = stmtSelect.buildTree();
		assertNotNull(tree);
		tree.printASTTree();
	}
	
	public void testParseInvalidSQL() {
		// test invalid sql
		boolean	isSuccessfull = parser.parseStringSQL(selectStmt2);
		assertFalse(isSuccessfull);
	}
	
	public void testParseSimpleSelectStatement() {
		
		// test valid sql
		boolean isSuccessfull = parser.parseStringSQL(selectStmt1);
		assertTrue(isSuccessfull);
		
		LinkedList<SQLObject> lstSQLStatements = parser.getListStatements();
		assertEquals(lstSQLStatements.size(), 1);
		assertTrue(lstSQLStatements.get(0) instanceof SelectStatement);
		
		SelectStatement stmtSelect = (SelectStatement) lstSQLStatements.get(0); 
		ASTTree tree = stmtSelect.buildTree();
		assertNotNull(tree);
		tree.printASTTree();
	}
	
	
	

}
