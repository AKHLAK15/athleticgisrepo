package com.athleticgis.view;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.athleticgis.model.gis.Activity;
import com.athleticgis.model.AthleticgisFacade;

import java.io.Serializable;
import java.util.List;

@ManagedBean
@SessionScoped
public class DashboardBean implements Serializable {
	private static final long serialVersionUID = 1L;
	List<Activity> activities;
	
	@ManagedProperty(value = "#{userInfoBean}")
    private UserInfoBean userInfoBean;
	
	
	public UserInfoBean getUserInfoBean() {
		return userInfoBean;
	}

	public void setUserInfoBean(UserInfoBean userInfoBean) {
		this.userInfoBean = userInfoBean;
	}

	public List<Activity> getActivities() {
		//return activityModel.getActivities();
		//AthleticgisFacade athleticgisFacade = new AthleticgisFacade();
		//hard coded user_id use UserInfo
		//fix, show all for admin, show particular for non admin
		if(activities == null || activities.size() == 0) {
			if(!userInfoBean.getIsAdmin()) {
				activities = AthleticgisFacade.findActivitiesByUserId(userInfoBean.getUser_id());
			} else {
				activities = AthleticgisFacade.findAllActivities();
			}
		}
		return activities;
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
