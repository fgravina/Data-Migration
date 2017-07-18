package com.gigroup.data.migration.candidate.anagrafica.dao;

import com.gigroup.data.migration.candidate.anagrafica.PersonCountryServiceActive;
import com.gigroup.data.migration.candidate.dao.BaseMyGiGroupDao;

public interface PersonCountryServiceActiveDao extends BaseMyGiGroupDao {
	
	public static String BEAN_NAME = "personCountryServiceActiveDao";
	
	/**
     * Rende il {@link PersonCountryServiceActive} persistente.
     * @param loginFailedAttempts
     */
	void add(final PersonCountryServiceActive personCountryServiceActive);

}