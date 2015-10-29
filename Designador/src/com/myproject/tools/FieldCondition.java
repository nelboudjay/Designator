package com.myproject.tools;

public class FieldCondition {
	
	private Object field;
	private int condition; //-1 = Lower, 0 = Equal, 1 = Greater
	
	
	public FieldCondition(Object field, int condition) {
		super();
		this.field = field;
		this.condition = condition;
	}


	public FieldCondition(Object field) {
		super();
		this.field = field;
		this.condition = 0;

	}


	public Object getField() {
		return field;
	}


	public void setField(Object field) {
		this.field = field;
	}


	public int getCondition() {
		return condition;
	}


	public void setCondition(int condition) {
		this.condition = condition;
	}

}
