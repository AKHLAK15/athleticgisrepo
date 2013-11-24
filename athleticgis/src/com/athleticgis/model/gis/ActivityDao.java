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

	public static List<ActivityPoint> findActivityPointsByActivityId(Long activityId) {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory()
				.createEntityManager();
		
//		TypedQuery<UserRole> query =
//	      em.createQuery("SELECT c FROM Country c", UserRole.class);
//List<UserRole> userRoles = query.getResultList();
		
		TypedQuery<ActivityPoint> query =
		  em.createQuery("SELECT ap FROM ActivityPoint ap where ap.activity.activity_id="+activityId+ " order by ap.time", ActivityPoint.class);
		List<ActivityPoint> activityPoints = query.getResultList();
		em.close();
		return activityPoints;
	}
	
	public static List<Activity> findActivitiesByUserId(Long user_id) {
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
	
	public static void persistActivityAndActivityPoints(Activity a, List<ActivityPoint> activityPoints) {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory()
				.createEntityManager();
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		a.setWaypoints(activityPoints);
		
		//set the timestamp of the activity to the timestamp of the first waypoint
		if(activityPoints != null && !activityPoints.isEmpty()) {
			a.setDate(activityPoints.get(0).getTime());
		}
		
		em.persist(a);
		for(ActivityPoint ap : activityPoints) {
			ap.setActivity(a);
			em.persist(ap);
		}
		
		transaction.commit();
		em.close();
	}
	
	public static List<Activity> findAllActivities() {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory()
				.createEntityManager();
		TypedQuery<Activity> query =
		  em.createQuery("SELECT a FROM Activity a order by a.date", Activity.class);
		List<Activity> activities = query.getResultList();
		em.close();
		return activities;
	}
}
