package com.gigroup.data.migration.candidate.anagrafica.dao;

import com.gigroup.data.migration.candidate.anagrafica.PersonCountryBusinessRole;
import com.gigroup.data.migration.candidate.dao.BaseMyGiGroupDao;

public interface PersonCountryBusinessRoleDao extends BaseMyGiGroupDao {
	
	public static String BEAN_NAME = "personCountryBusinessRoleDao";
	
	/**
     * Rende il {@link PersonCountryBusinessRole} persistente.
     * @param loginFailedAttempts
     */
	void add(final PersonCountryBusinessRole personCountryBusinessRole);

	void addUK(final PersonCountryBusinessRole personCountryBusinessRole);
}