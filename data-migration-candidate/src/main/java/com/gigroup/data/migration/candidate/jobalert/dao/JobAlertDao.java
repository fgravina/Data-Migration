package com.gigroup.data.migration.candidate.jobalert.dao;

import com.gigroup.data.migration.candidate.dao.BaseMyGiGroupDao;
import com.gigroup.data.migration.candidate.jobalert.JobAlert;

public interface JobAlertDao extends BaseMyGiGroupDao {
	
	public static String BEAN_NAME = "jobAlertDao";
	
	/**
     * Rende il {@link JobAlert} persistente.
     * @param loginFailedAttempts
     */
    void addJobAlert(final JobAlert jobAlert);
}