package com.athleticgis.model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import com.athleticgis.model.gis.ActivityDao;
import com.athleticgis.model.gis.Activity;
import com.athleticgis.model.user.User;
import com.athleticgis.model.user.UserDao;

@ManagedBean
@SessionScoped
public class AthleticgisSessionFacade {
	private UserDao userDao = new UserDao();
	private ActivityDao activityDao = new ActivityDao();
	
	public void persistUser(User u) {
		userDao.persist(u);
	}
	
	public void persistActivity(Activity a) {
		activityDao.persist(a);
	}
}
