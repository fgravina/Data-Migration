package com.gigroup.data.migration.candidate.anagrafica.dao;

import com.gigroup.data.migration.candidate.anagrafica.Person;
import com.gigroup.data.migration.candidate.dao.BaseMyGiGroupDao;

public interface PersonDao extends BaseMyGiGroupDao {
	
	public static String BEAN_NAME = "personDao";
	
	/**
     * Rende il {@link person} persistente.
     * @param loginFailedAttempts
     */
    void addPerson(final Person person);

}