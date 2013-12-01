package com.athleticgis.model.activity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.athleticgis.domain.activity.Activity;
import com.athleticgis.domain.activity.ActivityPoint;
import com.athleticgis.domain.user.User;
import com.athleticgis.domain.user.UserRole;
import com.athleticgis.model.Dao;
import com.athleticgis.util.model.EntityManagerUtil;

public class ActivityDao implements Serializable {
	private static final long serialVersionUID = 5546876539130483879L;
	
	//SELECT DISTINCT mag FROM Magazine mag JOIN mag.articles art JOIN art.author auth WHERE auth.firstName = 'John'

	public static List<ActivityPoint> findActivityPointsByActivityId(Long activityId) {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory()
				.createEntityManager();
		TypedQuery<ActivityPoint> query =
		  em.createQuery("SELECT ap FROM ActivityPoint ap where ap.activity.activity_id="+activityId+ " order by ap.time", ActivityPoint.class);
		List<ActivityPoint> activityPoints = query.getResultList();
		em.close();
		return activityPoints;
	}
	
	public static List<Activity> findActivitiesByUserId(Long user_id) {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory()
				.createEntityManager();
		TypedQuery<Activity> query =
		  em.createQuery("SELECT a FROM Activity a where a.user.user_id="+ user_id  + " order by a.date", Activity.class);
		List<Activity> activities = query.getResultList();
		em.close();
		return activities;
	}
	
	public static void persist(Activity entity) {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory()
				.createEntityManager();

		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(entity);
		transaction.commit();
		em.close();
	}

	public static void remove(Long activityId) {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory()
				.createEntityManager();
		Activity a = em.find(Activity.class, activityId);
		em.getTransaction().begin();
		em.remove(a);
		em.getTransaction().commit();
		em.close();
	}

	public static Activity findById(Long id) {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory()
				.createEntityManager();
		Activity a = em.find(Activity.class, id);
		em.close();
		return a;
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
	
	//TODO
	//use findActivitiesByUserIdPaginated as an example
	//need to update this for pagination
	//this is the method that should be being called for an admin
	public static List<Activity> findAllActivities() {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory()
				.createEntityManager();
		TypedQuery<Activity> query =
		  em.createQuery("SELECT a FROM Activity a order by a.date", Activity.class);
		List<Activity> activities = query.getResultList();
		em.close();
		return activities;
	}
	
	public static void mergeActivity(String activityName, Long activityId) {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory()
				.createEntityManager();
		Activity a = em.find(Activity.class, activityId);
		a.setName(activityName);
		em.getTransaction().begin();
		em.merge(a);
		em.getTransaction().commit();
		em.close();
	}
	
	public static Long findActivityCountByUserId(Long user_id) {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory()
				.createEntityManager();
		Query query =
		  em.createQuery("SELECT count(a) FROM Activity a where a.user.user_id="+ user_id);
		Long count = (Long) query.getSingleResult();
		em.close();
		return count;
	}
	
	public static List<Activity> findActivitiesByUserIdPaginated(Long user_id, int start, int max) {
		EntityManager em = EntityManagerUtil.getEntityManagerFactory()
				.createEntityManager();
		TypedQuery<Activity> query =
		  em.createQuery("SELECT a FROM Activity a where a.user.user_id="+ user_id  + " order by a.date desc", Activity.class);
		query.setFirstResult(start);
		query.setMaxResults(max);
		
		List<Activity> activities = query.getResultList();
		em.close();
		return activities;
	}
}
