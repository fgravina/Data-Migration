package com.gigroup.data.migration.candidate.anagrafica.dao;

import com.gigroup.data.migration.candidate.anagrafica.Contact;
import com.gigroup.data.migration.candidate.dao.BaseMyGiGroupDao;

public interface ContactDao extends BaseMyGiGroupDao {
	
	public static String BEAN_NAME = "contactDao";
	
	/**
     * Rende il {@link Contact} persistente.
     * @param loginFailedAttempts
     */
    void addContact(final Contact contact);

}
