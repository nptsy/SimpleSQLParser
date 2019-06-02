package com.ripstech.challenges.models.sql.clause;
import com.ripstech.challenges.models.sql.EnumModels.ClauseType;
import com.ripstech.challenges.models.sql.SQLObjectImpl;

public abstract class SQLClause extends SQLObjectImpl{
	protected ClauseType mType;
	
	public SQLClause(ClauseType type) {
		mType = type;
	}
}
