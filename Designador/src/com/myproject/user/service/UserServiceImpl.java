package com.myproject.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myproject.user.dao.UserDAO;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDAO userDAO;

	@Override
	@Transactional
	public void SaveOrUpdateModelData(Object obj) {
		userDAO.SaveOrUpdateModelData(obj);
	}

	@Override
	@Transactional
	public void DeleteModelData(Object obj) {
		userDAO.DeleteModelData(obj);
	}

	@Override
	@Transactional
	public Object GetUniqueModelData(Class<?> t,
			Map<String, Object> eqRestrictions) {
		return userDAO.GetUniqueModelData(t, eqRestrictions);
	}

	@Override
	@Transactional
	public 	List<?> GetModelDataList(Class<?> t, Map<String,Object> eqRestrictions){
		return userDAO.GetModelDataList(t, eqRestrictions);

	}
	
	@Override
	@Transactional
	public void CreateEvent(String eventName, String tableName, String idName,
			String idValue, String interval) {
		userDAO.CreateEvent(eventName, tableName, idName, idValue, interval);
	}

	@Override
	@Transactional
	public void DropEvent(String eventName) {
		userDAO.DropEvent(eventName);
	}

	@Override
	@Transactional
	public void AlterEvent(String eventName, String interval) {
		userDAO.AlterEvent(eventName, interval);
	}
	
	public UserDAO getUserDAO() {
		return userDAO;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}


	

}
