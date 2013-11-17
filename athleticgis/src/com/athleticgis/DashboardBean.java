package com.athleticgis;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.athleticgis.model.Activity;
import com.athleticgis.model.ActivityModel;

import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
public class DashboardBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ManagedProperty(value = "#{userInfoBean}")
    private UserInfoBean userInfoBean;
	
	@ManagedProperty(value = "#{activityModel}")
    private ActivityModel activityModel;
	
	public ActivityModel getActivityModel() {
		return activityModel;
	}

	public void setActivityModel(ActivityModel activityModel) {
		this.activityModel = activityModel;
	}

	public void setActivities(List<Activity> activities) {
		activityModel.setActivities(activities);
	}
	
	public UserInfoBean getUserInfoBean() {
		return userInfoBean;
	}

	public void setUserInfoBean(UserInfoBean userInfoBean) {
		this.userInfoBean = userInfoBean;
	}

	public List<Activity> getActivities() {
		return activityModel.getActivities();
	}
	
	
//	public void fileUploadListener(FileUploadEvent event) throws Exception {
//		UploadedFile item = event.getUploadedFile();
//	    File file = new File();
//	    file.setLength(item.getData().length);
//	    file.setName(item.getName());
//	    file.setData(item.getData());
//	    files.add(file);
//	    uploadsAvailable--;
//	}
//	public void setActivityName(List<Activity> activities) {
//		this.activities = activities;
//	}
}
