package com.myproject.service;

import java.util.List;
import java.util.Map;

import com.myproject.tools.FieldCondition;

public interface GenericService {
		
	Object GetUniqueModelData(Class<?> t, Map<String,FieldCondition> eqRestrictions);
	List<?> GetModelDataList(Class<?> t, Map<String,FieldCondition> eqRestrictions, String attribute, Boolean ascendingOrder);
	void SaveOrUpdateModelData(Object obj);
	void DeleteModelData(Object obj);

	void CreateEvent(String eventName, String tablesNames, String tableName, String idName, String idValue, String interval);
	void DropEvent(String eventName);
	void AlterEvent(String eventName,String interval);




}
