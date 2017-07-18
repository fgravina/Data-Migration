package com.gigroup.data.migration.candidate.jobalert.dao;

import java.util.List;

import com.gigroup.data.migration.candidate.dao.BaseMyGiGroupDao;
import com.gigroup.data.migration.candidate.jobalert.JobAlertFrequency;

public interface JobAlertFrequencyDao extends BaseMyGiGroupDao {
	
	public static String BEAN_NAME = "jobAlertFrequencyDao";
	
	List<JobAlertFrequency> getAll();
}