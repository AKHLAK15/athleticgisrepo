package com.athleticgis.view;

import java.io.Serializable;
import java.util.Map;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;

import com.athleticgis.domain.activity.Activity;
import com.athleticgis.model.AthleticgisFacade;

@ManagedBean
@RequestScoped
public class ViewMyMapBean implements Serializable {
	private static final long serialVersionUID = -4430301657324644250L;
	@ManagedProperty(value = "#{request.getParameter('mymap_id')}")
    private String mymap_id;
	
	@ManagedProperty(value = "#{userInfoBean}")
    private UserInfoBean userInfoBean;
	private HtmlInputText inputTextMyMapName;
	
	/**
	 * @return the userInfoBean
	 */
	public UserInfoBean getUserInfoBean() {
		return userInfoBean;
	}

	/**
	 * @param userInfoBean the userInfoBean to set
	 */
	public void setUserInfoBean(UserInfoBean userInfoBean) {
		this.userInfoBean = userInfoBean;
	}

	public HtmlInputText getInputTextMyMapName() {
		return inputTextMyMapName;
	}

	public void setInputTextMyMapName(HtmlInputText inputTextMyMapName) {
		
		if(mymap_id != null) {
			inputTextMyMapName.setValue(AthleticgisFacade.findMyMapById(Long.parseLong(mymap_id)).getName());
		}
		this.inputTextMyMapName = inputTextMyMapName;
	}

	public String getMymap_id() {
		return mymap_id;
	}

	public void setMymap_id(String mymap_id) {
		this.mymap_id = mymap_id;
	}
	
	public String updateActivity() {
		Map<String,String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		String mId = params.get("mymap_id");
		Long id = Long.parseLong(mId);
		AthleticgisFacade.mergeMyMap(inputTextMyMapName.getValue().toString(), id);
		return "mymaps?faces-redirect=true";
	}
}
