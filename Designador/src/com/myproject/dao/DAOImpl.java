package com.myproject.dao;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.myproject.tools.FieldCondition;


@Repository
public class DAOImpl implements DAO {

	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void SaveOrUpdateModelData(Object obj) {

		Session session = sessionFactory.getCurrentSession();
		Transaction tx = session.beginTransaction();

		try {
			session.saveOrUpdate(obj);

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		tx.commit();
	}

	@Override
	public void DeleteModelData(Object obj) {
		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		try {
			if (obj != null)
				session.delete(obj);

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		session.getTransaction().commit();
	}

	@Override
	public Object GetUniqueModelData(Class<?> t,
			Map<String, FieldCondition> eqRestrictions) {

		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		Object obj = null;
		try {

			Criteria cr = session.createCriteria(t);

			for (Entry<String, FieldCondition> entry : eqRestrictions.entrySet()) {
				// get key
				String key = entry.getKey();
				// get value
				FieldCondition value = entry.getValue();
				
				switch (value.getCondition()){
					case -1:	cr.add(Restrictions.le(key, value.getField()));
								break;
					case  0:	cr.add(Restrictions.eq(key, value.getField()));
								break;
					case  1:	cr.add(Restrictions.ge(key, value.getField()));
								break;
				}

			}

			obj = (Object) cr.uniqueResult();

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		session.getTransaction().commit();

		return obj;

	}

	@Override
	public List<?> GetModelDataList(Class<?> t,
			Map<String, FieldCondition> eqRestrictions, String attribute, Boolean ascendingOrder) {

		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		List<?> obj = null;
		try {

			Criteria cr = session.createCriteria(t);
			 
			for (Entry<String, FieldCondition> entry : eqRestrictions.entrySet()) {
				// get key
				String key = entry.getKey();
				// get value
				FieldCondition value = entry.getValue();
				
				switch (value.getCondition()){
					case -1:	cr.add(Restrictions.le(key, value.getField()));
								break;
					case  0:	cr.add(Restrictions.eq(key, value.getField()));
								break;
					case  1:	cr.add(Restrictions.ge(key, value.getField()));
								break;
				}

			}

			if(attribute != null){
				
				List<Field> fields = Arrays.asList(t.getDeclaredFields());
				String auxAttribute = attribute;
                if(fields.stream().filter(field -> field.getName().equals(auxAttribute)).count() == 0){
                    
                	for(Field field : fields){
                		List<Field> fieldFields;
                		if (Arrays.asList(field.getAnnotations()).stream().
                				filter(annotation -> annotation.annotationType().getSimpleName().equals("ForeignKey")).count() == 1){
                			
                			fieldFields = Arrays.asList(field.getType().getDeclaredFields());
                			
                			if(fieldFields.stream().filter(fieldField -> fieldField.getName().equals(auxAttribute)).count() == 1){
                				cr.createAlias(field.getName(), "alias");
                				attribute = "alias." + attribute;
                				break;
                			}
                		}		
                	}
                }
                
				if (ascendingOrder)
					cr.addOrder(Order.asc(attribute));
				else
					cr.addOrder(Order.desc(attribute));
			}
			
			obj = cr.list();

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		session.getTransaction().commit();

		return obj;
	}
	
	@Override
	public void DropEvent(String eventName) {

		String sql = "DROP EVENT IF EXISTS " + eventName + ";";

		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		try {

			SQLQuery query = session.createSQLQuery(sql);
			query.executeUpdate();

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		session.getTransaction().commit();
	}

	@Override
	public void CreateEvent(String eventName, String tablesNames, String tableName, String idName,
			String idValue, String interval) {
		String sql = "CREATE EVENT "
				+ eventName
				+ "			ON SCHEDULE AT CURRENT_TIMESTAMP + INTERVAL 1 " + interval 
				+ " DO "
				+ "DELETE " + tablesNames + " FROM " + tableName + " WHERE " + idName + " = '"
				+ idValue + "';";

		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		try {

			SQLQuery query = session.createSQLQuery(sql);
			query.executeUpdate();

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		session.getTransaction().commit();

	}

	@Override
	public void AlterEvent(String eventName, String interval) {
		
		String sql = "ALTER EVENT " + eventName
				+ "			ON SCHEDULE AT CURRENT_TIMESTAMP + INTERVAL 1 " + interval + ";";

		Session session = sessionFactory.getCurrentSession();
		session.beginTransaction();

		try {

			SQLQuery query = session.createSQLQuery(sql);
			query.executeUpdate();

		} catch (HibernateException e) {
			e.printStackTrace();
			session.getTransaction().rollback();
		}

		session.getTransaction().commit();
	}

}
