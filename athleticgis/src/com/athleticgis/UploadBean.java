package com.athleticgis;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.athleticgis.model.Activity;
import com.athleticgis.model.ActivityModel;

@ManagedBean
@SessionScoped
public class UploadBean implements Serializable {
	private static final long serialVersionUID = -290191374294978569L;
	private Part file;
	private String fileContent;
	private String activityName;
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

	public UserInfoBean getUserInfoBean() {
		return userInfoBean;
	}

	public void setUserInfoBean(UserInfoBean userInfoBean) {
		this.userInfoBean = userInfoBean;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String upload() {
		
		
		return null;
	}
	
	public String save() {
		
		if(file != null && file.getSize() > 0 && activityName != null) {
			InputStream in;
		try {
			in = file.getInputStream();
			setFileContent(new Scanner(in)
					.useDelimiter("\\A").next());
			in.close();
		} catch (IOException e) {
			// Error handling
		}
		
		
			Activity a = new Activity();
			a.setDate(new Timestamp(new Date().getTime()));
			a.setName(activityName);
			a.setUserName(userInfoBean.getName());
			activityModel.addActivity(a);
			activityName = null;
			
			return "dashboard?faces-redirect=true";
		} else {
			return null;
		}
	}

	public Part getFile() {
		return file;
	}

	public void setFile(Part file) {
		this.file = file;
	}

	public void validateFile(FacesContext ctx, UIComponent comp, Object value) {
		List<FacesMessage> msgs = new ArrayList<FacesMessage>();
		//kmlObject = value;
		file = (Part) value;
//		if (file.getSize() > 1024) {
//			msgs.add(new FacesMessage("file too big"));
//		}
//		if (!"text/plain".equals(file.getContentType())) {
//			msgs.add(new FacesMessage("not a text file"));
//		}
		if (!msgs.isEmpty()) {
			throw new ValidatorException(msgs);
		}
	}

	public String getFileContent() {
		return fileContent;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}
	
	

}
