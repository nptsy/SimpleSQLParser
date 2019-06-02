package com.ripstech.challenges.models.sql.expression;

import com.ripstech.challenges.models.sql.SQLObjectImpl;

public class SQLOperator extends SQLObjectImpl{
	private String operatorName;
	
	public SQLOperator(String opName) {
		super();
		operatorName = opName;
	}
	
	public String getOperator() {
		return operatorName;
	}
	
	
}

