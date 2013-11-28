package com.athleticgis.view.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.athleticgis.domain.activity.Activity;
import com.athleticgis.model.AthleticgisFacade;

public class ActivityDataModel extends LazyDataModel<Activity> {
	private static final long serialVersionUID = 5024184940806000630L;

	private List<Activity> activities;
	private Long user_id;

	public ActivityDataModel(Long user_id) {
		this.user_id = user_id;
	}

	@Override
	public Activity getRowData(String activityId) {
		Long id = Long.valueOf(activityId);
		
		for (Activity activity : activities) {
			if (id.equals(activity.getActivity_id()))
				return activity;
		}

		return null;
	}

	@Override
	public Object getRowKey(Activity activity) {
		return activity.getActivity_id();
	}

	@Override
	public List<Activity> load(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, String> filters) {
		//	List<Activity> data = new ArrayList<Activity>();

//		// filter
//		for (Activity activity : datasource) {
//			boolean match = true;
//
//			for (Iterator<String> it = filters.keySet().iterator(); it
//					.hasNext();) {
//				try {
//					String filterProperty = it.next();
//					String filterValue = filters.get(filterProperty);
//					String fieldValue = String.valueOf(activity.getClass()
//							.getField(filterProperty).get(activity));
//
//					if (filterValue == null
//							|| fieldValue.startsWith(filterValue)) {
//						match = true;
//					} else {
//						match = false;
//						break;
//					}
//				} catch (Exception e) {
//					match = false;
//				}
//			}
//
//			if (match) {
//				data.add(activity);
//			}
//		}

//		// sort
//		if (sortField != null) {
//			Collections.sort(data, new LazySorter(sortField, sortOrder));
//		}
		
		System.out.println("Loading....");
		
		activities = AthleticgisFacade.findActivitiesByUserIdPaginated(user_id, first, pageSize);

		// set the total number of activities
		Integer dataSize = AthleticgisFacade.findActivityCountByUserId(user_id).intValue(); 
		this.setRowCount(dataSize);
		
		this.setPageSize(pageSize);

		return activities;
	}

}
