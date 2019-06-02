package com.ripstech.challenges.models.sql.statement;

import com.ripstech.challenges.models.sql.EnumModels.StatementType;
import com.ripstech.challenges.models.ast.ASTTree;
import com.ripstech.challenges.models.sql.SQLObjectImpl;

public abstract class SQLStatement extends SQLObjectImpl{

	protected StatementType mType;
	
	public SQLStatement(StatementType type) {
		mType = type;
	}
	
	public ASTTree buildTree() {
		return null;
	}
}
