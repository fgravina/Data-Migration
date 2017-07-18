package com.gigroup.data.migration.candidate.anagrafica.dao;

import com.gigroup.data.migration.candidate.anagrafica.Address;
import com.gigroup.data.migration.candidate.dao.BaseMyGiGroupDao;;

public interface AddressDao extends BaseMyGiGroupDao {
	
	public static String BEAN_NAME = "addressDao";
	
	/**
     * Rende il {@link Address} persistente.
     * @param loginFailedAttempts
     */
    void addAddress(final Address address);

}
