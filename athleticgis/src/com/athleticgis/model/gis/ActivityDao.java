package com.athleticgis.model.gis;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.athleticgis.model.Dao;
import com.athleticgis.model.user.User;
import com.athleticgis.model.user.UserRole;
import com.athleticgis.model.util.EntityManagerUtil;

public class ActivityDao implements Dao<Activity>, Serializable {
	private static final long serialVersionUID = 5546876539130483879L;

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
	
	public void persistActivityAndWaypoints(Activity a, List<Waypoint> waypoints) {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory()
				.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		a.setWaypoints(waypoints);
		em.persist(a);
		for(Waypoint w : waypoints) {
			w.setActivity(a);
			em.persist(w);
		}
		
		transaction.commit();
		em.close();
	}

}
