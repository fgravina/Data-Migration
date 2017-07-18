package com.gigroup.data.migration.candidate.candidatura.dao;

import com.gigroup.data.migration.candidate.candidatura.AppliedPosition;
import com.gigroup.data.migration.candidate.dao.BaseMyGiGroupDao;

public interface AppliedPositionDao extends BaseMyGiGroupDao {
	
	public static String BEAN_NAME = "appliedPositionDao";
	
	/**
     * Rende il {@link AppliedPosition} persistente.
     * @param loginFailedAttempts
     */
    void addAppliedPosition(final AppliedPosition appliedPosition);
}