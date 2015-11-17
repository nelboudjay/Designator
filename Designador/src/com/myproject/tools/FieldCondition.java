package com.myproject.tools;

import java.util.HashMap;
import java.util.Map;

public class FieldCondition {
	
	private Map<Integer,Object> fieldConditions; //-1 = Lower, 0 = Equal, 1 = Greater, 2 = Like
	
	public FieldCondition(Map<Integer,Object> fieldConditions) {
		super();
		this.setFieldConditions(fieldConditions);
	}


	public FieldCondition(Object field) {
		super();
		fieldConditions = new HashMap<Integer,Object>();
		fieldConditions.put(0, field);
	}
	
	public FieldCondition(Object field, int condition) {
		super();
		fieldConditions = new HashMap<Integer,Object>();
		fieldConditions.put(condition, field);
	}


	public Map<Integer,Object> getFieldConditions() {
		return fieldConditions;
	}


	public void setFieldConditions(Map<Integer,Object> fieldConditions) {
		this.fieldConditions = fieldConditions;
	}

}
