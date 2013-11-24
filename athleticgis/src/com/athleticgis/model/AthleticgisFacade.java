package com.athleticgis.model;

import java.io.Serializable;
import java.util.List;

import com.athleticgis.model.gis.ActivityDao;
import com.athleticgis.model.gis.Activity;
import com.athleticgis.model.gis.Waypoint;
import com.athleticgis.model.user.User;
import com.athleticgis.model.user.UserDao;

public class AthleticgisFacade implements Serializable {
	private static final long serialVersionUID = -6486526059229360289L;
	//private UserDao userDao = new UserDao();
	//private ActivityDao activityDao = new ActivityDao();
	
	public void persistUser(User u) {
		UserDao userDao = new UserDao();
		userDao.persist(u);
	}
	
	public void persistActivity(Activity a) {
		ActivityDao activityDao = new ActivityDao();
		activityDao.persist(a);
	}
	
	public String testFromServlet() {
		return "Hello Matthew!";
	}
	
	//remove
	public User findUserById(Long id) {
		UserDao userDao = new UserDao();
		return userDao.findById(id);
	}
	
	public void persistActivityAndWaypoints(Activity a, List<Waypoint> waypoints) {
		ActivityDao activityDao = new ActivityDao();
		activityDao.persistActivityAndWaypoints(a, waypoints);
	}
	
	public List<Waypoint> findWaypointsByActivityId(Long activityId) {
		ActivityDao activityDao = new ActivityDao();
		return activityDao.findWaypointsByActivityId(activityId);
	}
	
	public static List<Activity> findActivitiesByUserId(Long user_id) {
		//ActivityDao activityDao = new ActivityDao();
		return ActivityDao.findActivitiesByUserId(user_id);
	}
	
	public User findUserByUsername(String username) {
		UserDao userDao = new UserDao();
		return userDao.findUserByUsername(username);
	}
}
