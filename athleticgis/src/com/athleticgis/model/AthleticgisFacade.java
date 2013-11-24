package com.athleticgis.model;

import java.io.Serializable;
import java.util.List;

import com.athleticgis.model.gis.ActivityDao;
import com.athleticgis.model.gis.Activity;
import com.athleticgis.model.gis.ActivityPoint;
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
	
	public static void persistActivityAndActivityPoints(Activity a, List<ActivityPoint> activityPoints) {
		ActivityDao.persistActivityAndActivityPoints(a, activityPoints);
	}
	
	public static List<ActivityPoint> findActivityPointsByActivityId(Long activityId) {
		return ActivityDao.findActivityPointsByActivityId(activityId);
	}
	
	public static List<Activity> findActivitiesByUserId(Long user_id) {
		return ActivityDao.findActivitiesByUserId(user_id);
	}
	
	public static User findUserByUsername(String username) {
		return UserDao.findUserByUsername(username);
	}
}
