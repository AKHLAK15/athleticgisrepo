package com.athleticgis.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import com.athleticgis.UserInfoBean;

@ManagedBean
@SessionScoped
public class ActivityModel implements Serializable {
	private static final long serialVersionUID = -6259289389198829073L;
	private List<Activity> activities;
	@ManagedProperty(value = "#{userInfoBean}")
    private UserInfoBean userInfoBean;
	private int numVisits = 0;
	
	

	Long activityNdx = 5L;
	
	public UserInfoBean getUserInfoBean() {
		return userInfoBean;
	}

	public void setUserInfoBean(UserInfoBean userInfoBean) {
		this.userInfoBean = userInfoBean;
	}

	public List<Activity> getActivities() {
		// hard coded
		if(numVisits == 0) {
				activities = new ArrayList<Activity>();

				if (userInfoBean.getAdmin()) {
					Activity a = new Activity();
					a.setDate(new Timestamp((new Date()).getTime()));
					a.setName("Tuesday Night World Championships");
					a.setUserName("admin");
					a.setActivityId(1L);
					activities.add(a);
				}

				Activity a1 = new Activity();
				a1.setDate(new Timestamp((new Date()).getTime()));
				a1.setName("Run Around The Park");
				a1.setUserName("cs402@uwlax.uwl");
				a1.setActivityId(2L);
				activities.add(a1);
		}
		numVisits++;
		
		
		
		return activities;
	}

	public void setActivities(List<Activity> activities) {
		this.activities = activities;
	}
	
	public void addActivity(Activity a) {
		if(activities != null) {
			a.setActivityId(activityNdx++);
			activities.add(a);
		}
	}
	
	public String removeActivity() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String aId = params.get("activityId");
		Long id = Long.parseLong(aId);
		
		int ndx = -1;
		for(int i = 0; i < activities.size(); i++) {
			if(id.equals(activities.get(i).getActivityId())) {
				ndx = i;
			}
		}
		activities.remove(ndx);
		return null;
	}
	
	public void updateActivity(Long activityId, String name) {
		for(int i = 0; i < activities.size(); i++) {
			if(activityId.equals(activities.get(i).getActivityId())) {
				activities.get(i).setName(name);
			}
		}
	}
	
	public Activity findActivity(Long activityId) {
		Activity a = null;
		for(int i = 0; i < activities.size(); i++) {
			if(activityId.equals(activities.get(i).getActivityId())) {
				a = activities.get(i);
			}
		}
		return a;
	}

}
