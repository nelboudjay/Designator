package com.myproject.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.dao.DAO;

@Service
public class GenericServiceImpl implements GenericService {

	@Autowired
	private DAO dao;

	@Override
	@Transactional
	public void SaveOrUpdateModelData(Object obj) {
		dao.SaveOrUpdateModelData(obj);
	}

	@Override
	@Transactional
	public void DeleteModelData(Object obj) {
		dao.DeleteModelData(obj);
	}

	@Override
	@Transactional
	public Object GetUniqueModelData(Class<?> t,
			Map<String, Object> eqRestrictions) {
		return dao.GetUniqueModelData(t, eqRestrictions);
	}

	@Override
	@Transactional
	public 	List<?> GetModelDataList(Class<?> t, Map<String,Object> eqRestrictions,  String attribute, Boolean ascendingOrder){
		return dao.GetModelDataList(t, eqRestrictions, attribute, ascendingOrder);

	}
	
	@Override
	@Transactional
	public void CreateEvent(String eventName, String tableName, String idName,
			String idValue, String interval) {
		dao.CreateEvent(eventName, tableName, idName, idValue, interval);
	}

	@Override
	@Transactional
	public void DropEvent(String eventName) {
		dao.DropEvent(eventName);
	}

	@Override
	@Transactional
	public void AlterEvent(String eventName, String interval) {
		dao.AlterEvent(eventName, interval);
	}
	
	public DAO getdao() {
		return dao;
	}

	public void setdao(DAO dao) {
		this.dao = dao;
	}


	

}
