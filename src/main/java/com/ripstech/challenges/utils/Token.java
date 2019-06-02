package com.ripstech.challenges.utils;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

//-------------------------- Token ----------------------------
//
//This class encapsulates definitions related to tokens.
//This class is never instantiated.
//
//Harry Porter -- 01/15/03
//

public class Token {

	private static Token instance;
	private Set<String> setSymbols;
	private Set<String> setSQLKeyWords;

	public Token getInstance() {
		if (instance == null) {
			instance = new Token();
		}

		return instance;
	}

	//
	// Constructor -- This makes creating instances impossible.
	//
	private Token() {

		// set of symbols
		setSymbols = new HashSet<String>();
		setSymbols.add("+");
		setSymbols.add("-");
		setSymbols.add("*");
		setSymbols.add("/");
		setSymbols.add(">");
		setSymbols.add("<");
		setSymbols.add("=");
		setSymbols.add(":");
		setSymbols.add(";");
		setSymbols.add(",");
		setSymbols.add(".");
		setSymbols.add("(");
		setSymbols.add(")");
		setSymbols.add("[");
		setSymbols.add("]");
		setSymbols.add("{");
		setSymbols.add("}");
		setSymbols.add(":=");
		setSymbols.add(">=");
		setSymbols.add("<=");
		setSymbols.add("<>");

		// set of SQL key words
		// String[] lines = strContent.split("\\r?\\n");
		setSQLKeyWords = new HashSet<String>();
	}

	private void loadSQLKeyWords() {
		LinkedList<String> lines = FileIO.readLines("./sql-keywords.txt");
		if (lines.size() > 0) {
			for (String line : lines) {
				line = line.trim();
				if (line.startsWith("\\") || line.startsWith("/") || line.startsWith("%") || line.startsWith("#")) {
					continue;
				}
				setSQLKeyWords.add(line);
			}
		}
	}
	
	public final static String 
	FNOW = "NOW", 
	FCAST = "CAST",
	FSUM = "SUM", 
	FMIN = "MIN", 
	FCOUNT = "COUNT", 
	FMAX = "MAX", 
	FAVRG = "AVG", 
	FSTDDEV = "STDDEV", 
	FVAR = "VAR";
	
	// Token keywords
	public final static String 
	CUSE = "USE",
	CAS = "AS", 
	CSELECT = "SELECT",
	CFROM = "FROM",
	CWHERE = "WHERE",
	CIS = "IS",
	CNULL = "NULL",
	CORDER = "ORDER", 
	CBY = "BY",
	CINSERT ="INSERT",
	CINTO = "INTO",
	CVALUES = "VALUES",
	CDELETE = "DELETE",
	CAND = "AND",
	COR = "OR",
	CIN = "IN",
	CNOT = "NOT",
	CDESC = "DESC",
	CASC = "CASC";
	
	
	
	//
	// Token kinds
	//
	// This list must match the "stringOf" array.
	//
	public final static char
			CPLUS = '+',
			CMINUS = '-',
			CSTAR = '*', // *
			CSLASH = '/', // /
			CLESS = '<', // <
			CGREATER = '>', // >
			CEQUAL = '=', // =
			CCOLON = ':', // :
			CSEMI = ';', // ;
			CCOMMA = ',', // ,
			CPERIOD = '.', // .
			CLPAREN = '(', // (
			CRPAREN = ')', // )
			CLBRACK = '[', // [
			CRBRACK = ']', // ]
			CLBRACE = '{', // {
			CRBRACE = '}', // }
			CDQUOTE = '"',
			CSQUOTE = '\'';
	
	public final static int
	
	AND = 0, ARRAY = 1, BEGIN = 2, BY = 3, DIV = 4, DO = 5, ELSE = 6, ELSEIF = 7, END = 8, EXIT = 9, FOR = 10, IF = 11,
			IS = 12, LOOP = 13, MOD = 14, NOT = 15, OF = 16, OR = 17, PROCEDURE = 18, PROGRAM = 19, READ = 20,
			RECORD = 21, RETURN = 22, THEN = 23, TO = 24, TYPE = 25, VAR = 26, WHILE = 27, WRITE = 28,

			ID = 29, INTEGER = 30, REAL = 31, STRING = 32,

			PLUS = 33, // +
			MINUS = 34, // -
			STAR = 35, // *
			SLASH = 36, // /
			LESS = 37, // <
			GREATER = 38, // >
			EQUAL = 39, // =
			COLON = 40, // :
			SEMI = 41, // ;
			COMMA = 42, // ,
			PERIOD = 43, // .
			LPAREN = 44, // (
			RPAREN = 45, // )
			LBRACK = 46, // [
			RBRACK = 47, // ]
			LBRACE = 48, // {
			RBRACE = 49, // }
			ASSIGN = 50, // :=
			LEQ = 51, // <=
			GEQ = 52, // >=
			NEQ = 53, // <>

			EOF = 54;

	final static String[] stringOf = { "AND", "ARRAY", "BEGIN", "BY", "DIV", "DO", "ELSE", "ELSEIF", "END", "EXIT",
			"FOR", "IF", "IS", "LOOP", "MOD", "NOT", "OF", "OR", "PROCEDURE", "PROGRAM", "READ", "RECORD", "RETURN",
			"THEN", "TO", "TYPE", "VAR", "WHILE", "WRITE",

			"ID", "INTEGER", "REAL", "STRING",

			"+", "-", "*", "/", "<", ">", "=", ":", ";", ",", ".", "(", ")", "[", "]", "{", "}", ":=", "<=", ">=", "<>",

			"END-OF-FILE" };

}
