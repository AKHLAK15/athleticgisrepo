package com.athleticgis.model;

import java.io.Serializable;
import java.util.List;

import com.athleticgis.domain.activity.Activity;
import com.athleticgis.domain.activity.ActivityPoint;
import com.athleticgis.domain.user.User;
import com.athleticgis.model.activity.ActivityDao;
import com.athleticgis.model.user.UserDao;

public class AthleticgisFacade implements Serializable {
	private static final long serialVersionUID = -6486526059229360289L;
	//private UserDao userDao = new UserDao();
	//private ActivityDao activityDao = new ActivityDao();
	
	public void persistUser(User u) {
		UserDao userDao = new UserDao();
		userDao.persist(u);
	}
	
	public static void persistActivity(Activity a) {
		ActivityDao.persist(a);
	}
	
	public static void removeActivity(Long activityId) {
		ActivityDao.remove(activityId);
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
	
	public static List<Activity> findAllActivities() {
		return ActivityDao.findAllActivities();
	}
	
	public static void mergeActivity(String activityName, Long activityId) {
		ActivityDao.mergeActivity(activityName, activityId);
	}
	
	public static Activity findActivityById(Long id) {
		return ActivityDao.findById(id);
	}
	
	public static Long findActivityCountByUserId(Long user_id) {
		return ActivityDao.findActivityCountByUserId(user_id);
	}
	
	public static List<Activity> findActivitiesByUserIdPaginated(Long user_id, int start, int max) {
		return ActivityDao.findActivitiesByUserIdPaginated(user_id, start, max);
	}
	
//	public static void updateTheme(String theme, Long user_id) {
//		UserDao.updateTheme(theme, user_id);
//	}
	
	public static void updateUserTheme(Long user_id, String theme) {
		UserDao.updateUserTheme(user_id, theme);
	}
}
