package com.gigroup.data.migration.candidate.candidatura.dao;

import com.gigroup.data.migration.candidate.candidatura.JobPosting;
import com.gigroup.data.migration.candidate.dao.BaseMyGiGroupDao;

public interface JobPostingDao extends BaseMyGiGroupDao {
	
	public static String BEAN_NAME = "jobPostingDao";	
	 
	JobPosting findByReferenceCode(final String referenceCode);
}