package com.ripstech.challenges.models.sql;

public class EnumModels {
	public enum StatementType {
		stmtSelect, stmtImport,
		stmtInsert, stmtDelete,stmtUse
		// stmtCreate,
	};
	
	
	public enum ClauseType{
		clsWhere, clsGroup,
		clsOrder, clsHaving,
		clsSelect, clsFrom,
	};
}
