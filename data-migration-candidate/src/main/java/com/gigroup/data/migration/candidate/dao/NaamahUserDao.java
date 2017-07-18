package com.gigroup.data.migration.candidate.dao;

import com.gigroup.data.migration.candidate.NaamahUser;
import com.gigroup.data.migration.candidate.util.LoginNotFoundException;

public interface NaamahUserDao extends BaseMyGiGroupDao {
	
	public static String BEAN_NAME = "naamahUserDao";
	
	/**
     * Rende il {@link NaamahUser} persistente.
     * @param loginFailedAttempts
     */
	Long addNaamahUser(final NaamahUser naamahUser);
	
	/**
     * @param login																							
     * @return il {@link NaamahUser} identificato da <code>login</code>.																																																												</code>.
     * @throws LoginNotFoundException se il parametro non identifica alcun {@link NaamahUser}.   
     */
	NaamahUser getNaamahUserByLogin(final String login) throws LoginNotFoundException;

}