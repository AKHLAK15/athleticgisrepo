package com.athleticgis.model;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.athleticgis.model.gis.ActivityDao;
import com.athleticgis.model.gis.Activity;
import com.athleticgis.model.user.User;
import com.athleticgis.model.user.UserDao;

public class AthleticgisSessionFacade implements Serializable {
	private static final long serialVersionUID = -6486526059229360289L;
	private UserDao userDao = new UserDao();
	private ActivityDao activityDao = new ActivityDao();
	
	public void persistUser(User u) {
		userDao.persist(u);
	}
	
	public void persistActivity(Activity a) {
		activityDao.persist(a);
	}
	
	public String testFromServlet() {
		return "Hello Matthew!";
	}
	
	public User findUserById(Long id) {
		return userDao.findById(id);
	}
}
