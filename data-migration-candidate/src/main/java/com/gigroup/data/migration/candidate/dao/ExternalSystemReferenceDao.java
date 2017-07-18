package com.gigroup.data.migration.candidate.dao;

import com.gigroup.data.migration.candidate.ExternalSystemReference;

public interface ExternalSystemReferenceDao extends BaseMyGiGroupDao {
	
	public static String BEAN_NAME = "externalSystemReferenceDao";
	
	/**
     * Rende il {@link ExternalSystemReference} persistente.
     * @param loginFailedAttempts
     */
	void addExternalSystemReference(final ExternalSystemReference externalSystemReference);
	
	ExternalSystemReference findByExtSystemObjectId(final String extSystemObjectId);

}