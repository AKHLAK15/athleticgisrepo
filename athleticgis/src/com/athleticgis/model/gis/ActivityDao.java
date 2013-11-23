package com.athleticgis.model.gis;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.athleticgis.model.Dao;
import com.athleticgis.model.util.EntityManagerUtil;

public class ActivityDao implements Dao<Activity> {

	@Override
	public void persist(Activity entity) {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory()
				.createEntityManager();

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(entity);
		transaction.commit();
		em.close();
	}

	@Override
	public void remove(Activity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public Activity findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
