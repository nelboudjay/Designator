package com.myproject.dao;

import java.util.List;
import java.util.Map;

public interface DAO {
			
	Object GetUniqueModelData(Class<?> t, Map<String,Object> eqRestrictions);
	List<?> GetModelDataList(Class<?> t, Map<String,Object> eqRestrictions, String attribute, Boolean ascendingOrder);
	void SaveOrUpdateModelData(Object obj);
	void DeleteModelData(Object obj);
	
	void CreateEvent(String eventName, String tableName, String idName, String idValue, String interval);
	void DropEvent(String eventName);
	void AlterEvent(String eventName,String interval);

	
	

}
