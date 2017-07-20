package com.gigroup.data.migration.candidate.anagrafica.dao;

import com.gigroup.data.migration.candidate.anagrafica.GbrProfile;
import com.gigroup.data.migration.candidate.anagrafica.ItalyProfile;
import com.gigroup.data.migration.candidate.dao.BaseMyGiGroupDao;

public interface GbrProfileDao extends BaseMyGiGroupDao {
	
	public static String BEAN_NAME = "gbrProfileDao";
	
	/**
     * Rende il {@link ItalyProfile} persistente.
     * @param loginFailedAttempts
     */
    void addGbrProfile(final GbrProfile gbrProfile);

}
