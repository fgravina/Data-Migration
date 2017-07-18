package com.gigroup.data.migration.candidate.jobalert.dao;

import java.util.List;

import com.gigroup.data.migration.candidate.dao.BaseMyGiGroupDao;
import com.gigroup.data.migration.candidate.jobalert.JobTitle;

public interface JobTitleDao extends BaseMyGiGroupDao {
	
	public static String BEAN_NAME = "jobTitleDao";
	
	List<JobTitle> getAll();
}