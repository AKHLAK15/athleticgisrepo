package com.athleticgis.model.activity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.athleticgis.domain.activity.Activity;
import com.athleticgis.domain.activity.ActivityPoint;
import com.athleticgis.domain.activity.MyMap;
import com.athleticgis.domain.activity.MyMapMarker;
import com.athleticgis.domain.user.User;
import com.athleticgis.domain.user.UserRole;
import com.athleticgis.model.Dao;
import com.athleticgis.util.model.EntityManagerUtil;

public class ActivityDao implements Serializable {
	private static final long serialVersionUID = 5546876539130483879L;
	
	//SELECT DISTINCT mag FROM Magazine mag JOIN mag.articles art JOIN art.author auth WHERE auth.firstName = 'John'

	public static List<ActivityPoint> findActivityPointsByActivityId(Long activityId) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		TypedQuery<ActivityPoint> query =
		  em.createQuery("SELECT ap FROM ActivityPoint ap where ap.activity.activity_id="+activityId+ " order by ap.time", ActivityPoint.class);
		List<ActivityPoint> activityPoints = query.getResultList();
		
		em.close();
		return activityPoints;
	}
	
	public static List<Activity> findActivitiesByUserId(Long user_id) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		TypedQuery<Activity> query =
		  em.createQuery("SELECT a FROM Activity a where a.user.user_id="+ user_id  + " order by a.date", Activity.class);
		List<Activity> activities = query.getResultList();
	
		em.close();
		return activities;
	}
	
	public static void persist(Activity entity) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();
		em.close();
	}

	public static void remove(Long activityId) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		Activity a = em.find(Activity.class, activityId);
		em.remove(a);
		em.getTransaction().commit();
		em.close();
	}

	public static Activity findById(Long id) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		Activity a = em.find(Activity.class, id);
		
		em.close();
		return a;
	}
	
	public static void persistActivityAndActivityPoints(Activity a, List<ActivityPoint> activityPoints) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
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
		
		em.getTransaction().commit();
		em.close();
	}
	
	//TODO
	//use findActivitiesByUserIdPaginated as an example
	//need to update this for pagination
	//this is the method that should be being called for an admin
	public static List<Activity> findAllActivities() {
		EntityManager em = EntityManagerUtil.getEntityManager();
		TypedQuery<Activity> query =
		  em.createQuery("SELECT a FROM Activity a order by a.date", Activity.class);
		List<Activity> activities = query.getResultList();
		em.close();
		return activities;
	}
	
	public static void mergeActivity(String activityName, Long activityId) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		Activity a = em.find(Activity.class, activityId);
		a.setName(activityName);
		
		em.merge(a);
		em.getTransaction().commit();
		em.close();
	}
	
	public static Long findActivityCountByUserId(Long user_id) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		Query query =
		  em.createQuery("SELECT count(a) FROM Activity a where a.user.user_id="+ user_id);
		Long count = (Long) query.getSingleResult();
		em.close();
		return count;
	}
	
	/**
	 * 
	 * @param user_id
	 * @param start
	 * @param max
	 * @return
	 */
	public static List<Activity> findActivitiesByUserIdPaginated(Long user_id, int start, int max) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		TypedQuery<Activity> query =
		  em.createQuery("SELECT a FROM Activity a where a.user.user_id="+ user_id  + " order by a.date desc", Activity.class);
		query.setFirstResult(start);
		query.setMaxResults(max);
		List<Activity> activities = query.getResultList();
		em.close();
		return activities;
	}
	
	/**
	 * This method persists a user created myMap along with its markers.
	 * 
	 * @param myMap
	 * @param myMapMarkers
	 */
	public static void persistMapAndMyMapMarkers(MyMap myMap, List<MyMapMarker> myMapMarkers) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		myMap.setMyMapMarkers(myMapMarkers);
		
		//set myMap date to the first marker date.
		if(myMapMarkers != null && !myMapMarkers.isEmpty()) {
			myMap.setDate(myMapMarkers.get(0).getTime());
		}
		
		em.persist(myMap);
		for(MyMapMarker mm : myMapMarkers) {
			mm.setMyMap(myMap);
			em.persist(mm);
		}
		
		em.getTransaction().commit();
		em.close();
	}
	
	/**
	 * Count the number of maps created by user_id.
	 * 
	 * @param user_id
	 * @return
	 */
	public static Long findMyMapCountByUserId(Long user_id) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		Query query =
		  em.createQuery("SELECT count(mm) FROM MyMap mm where mm.user.user_id="+ user_id);
		Long count = (Long) query.getSingleResult();
	
		em.close();
		return count;
	}
	
	/**
	 * This method selects a subset of all maps for a given user.
	 * 
	 * @param user_id
	 * @param start
	 * @param max
	 * @return
	 */
	public static List<MyMap> findMyMapsByUserIdPaginated(Long user_id, int start, int max) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		TypedQuery<MyMap> query =
		  em.createQuery("SELECT mm FROM MyMap mm where mm.user.user_id="+ user_id  + " order by mm.date desc", MyMap.class);
		query.setFirstResult(start);
		query.setMaxResults(max);	
		List<MyMap> myMaps = query.getResultList();
	
		em.close();
		return myMaps;
	}
	
	/**
	 * removes a map and its associated markers.
	 * 
	 * @param mymap_id
	 */
	public static void removeMyMap(Long mymap_id) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		MyMap mm = em.find(MyMap.class, mymap_id);
		em.remove(mm);
		em.getTransaction().commit();
		em.close();
	}
	
	/**
	 * get MyMap entity for given id.
	 * 
	 * @param id
	 * @return
	 */
	public static MyMap findMyMapById(Long id) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		MyMap mm = em.find(MyMap.class, id);
		em.close();
		return mm;
	}
	
	/**
	 * update myMap name.
	 * 
	 * @param myMapName
	 * @param mymap_id
	 */
	public static void mergeMyMap(String myMapName, Long mymap_id) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		em.getTransaction().begin();
		MyMap mm = em.find(MyMap.class, mymap_id);
		mm.setName(myMapName);
		em.merge(mm);
		em.getTransaction().commit();
		em.close();
	}
	
	/**
	 * finds MapMarkers for a given map id
	 * 
	 * @param mymap_id
	 * @return
	 */
	public static List<MyMapMarker> findMyMapMarkersByMymap_id(Long mymap_id) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		TypedQuery<MyMapMarker> query =
		  em.createQuery("SELECT m FROM MyMapMarker m where m.myMap.mymap_id="+mymap_id+ " order by m.time", MyMapMarker.class);
		List<MyMapMarker> myMapMarkers = query.getResultList();
		
		em.close();
		return myMapMarkers;
	}
	
	/**
	 * Counts all activities for admin user.
	 * 
	 * @return
	 */
	public static Long findActivityCount() {
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		Query query =
		  em.createQuery("SELECT count(a) FROM Activity a");
		Long count = (Long) query.getSingleResult();
		
		em.close();
		return count;
	}
	
	/**
	 * find all activity records for admin user.
	 * 
	 * @param start
	 * @param max
	 * @return
	 */
	public static List<Activity> findActivitiesPaginated(int start, int max) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		TypedQuery<Activity> query =
		  em.createQuery("SELECT a FROM Activity a order by a.date desc", Activity.class);
		query.setFirstResult(start);
		query.setMaxResults(max);
		
		List<Activity> activities = query.getResultList();
		
		em.close();
		return activities;
	}
	
	/**
	 * Count the total number of user defined maps.
	 * 
	 * @return
	 */
	public static Long findMyMapCount() {
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		Query query =
		  em.createQuery("SELECT count(mm) FROM MyMap mm");
		Long count = (Long) query.getSingleResult();
		
		em.close();
		return count;
	}
	
	/**
	 * This method selects a subset of all maps for a given user.
	 * 
	 * @param start
	 * @param max
	 * @return
	 */
	public static List<MyMap> findMyMapsPaginated(int start, int max) {
		EntityManager em = EntityManagerUtil.getEntityManager();
		
		TypedQuery<MyMap> query =
		  em.createQuery("SELECT mm FROM MyMap mm order by mm.date desc", MyMap.class);
		query.setFirstResult(start);
		query.setMaxResults(max);
		
		List<MyMap> myMaps = query.getResultList();
		
		em.close();
		return myMaps;
	}
}
