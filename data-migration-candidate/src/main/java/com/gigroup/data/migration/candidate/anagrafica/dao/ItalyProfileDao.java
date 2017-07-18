package com.gigroup.data.migration.candidate.anagrafica.dao;

import com.gigroup.data.migration.candidate.anagrafica.ItalyProfile;
import com.gigroup.data.migration.candidate.dao.BaseMyGiGroupDao;

public interface ItalyProfileDao extends BaseMyGiGroupDao {
	
	public static String BEAN_NAME = "italyProfileDao";
	
	/**
     * Rende il {@link ItalyProfile} persistente.
     * @param loginFailedAttempts
     */
    void addItalyProfile(final ItalyProfile italyProfile);

}
