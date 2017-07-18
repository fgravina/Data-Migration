package com.gigroup.data.migration.candidate.dao;

import java.util.List;

import com.gigroup.data.migration.candidate.Service;

public interface ServiceDao extends BaseMyGiGroupDao {
	
	public static String BEAN_NAME = "serviceDao";
	
	List<Service> getServicesByWorker();

}