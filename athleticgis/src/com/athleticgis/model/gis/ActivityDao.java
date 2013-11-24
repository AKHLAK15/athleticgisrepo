package com.athleticgis.model.gis;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

import com.athleticgis.model.Dao;
import com.athleticgis.model.user.User;
import com.athleticgis.model.user.UserRole;
import com.athleticgis.model.util.EntityManagerUtil;

public class ActivityDao implements Dao<Activity>, Serializable {
	private static final long serialVersionUID = 5546876539130483879L;
	
	//SELECT DISTINCT mag FROM Magazine mag JOIN mag.articles art JOIN art.author auth WHERE auth.firstName = 'John'

	public List<Waypoint> findWaypointsByActivityId(Long activityId) {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory()
				.createEntityManager();
		
//		TypedQuery<UserRole> query =
//	      em.createQuery("SELECT c FROM Country c", UserRole.class);
//List<UserRole> userRoles = query.getResultList();
		
		TypedQuery<Waypoint> query =
		  em.createQuery("SELECT wp FROM Waypoint wp where wp.activity.activity_id="+activityId+ " order by wp.time", Waypoint.class);
		List<Waypoint> waypoints = query.getResultList();
		em.close();
		return waypoints;
	}
	
	public List<Activity> findActivitiesByUserId(Long user_id) {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory()
				.createEntityManager();
		
//		TypedQuery<UserRole> query =
//	      em.createQuery("SELECT c FROM Country c", UserRole.class);
//List<UserRole> userRoles = query.getResultList();
		
		TypedQuery<Activity> query =
		  em.createQuery("SELECT a FROM Activity a where a.user.user_id="+ user_id  + " order by a.date", Activity.class);
		List<Activity> activities = query.getResultList();
		em.close();
		return activities;
	}
	
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
		
		//set the timestamp of the activity to the timestamp of the first waypoint
		if(waypoints != null && !waypoints.isEmpty()) {
			a.setDate(waypoints.get(0).getTime());
		}
		
		em.persist(a);
		for(Waypoint w : waypoints) {
			w.setActivity(a);
			em.persist(w);
		}
		
		transaction.commit();
		em.close();
	}

}
