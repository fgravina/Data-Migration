package com.gigroup.data.migration.candidate.anagrafica.dao;

import com.gigroup.data.migration.candidate.NOKRelationship;
import com.gigroup.data.migration.candidate.dao.BaseMyGiGroupDao;

public interface NOKRelationshipDao extends BaseMyGiGroupDao {
	
	public static String BEAN_NAME = "NOKRelationshipDao";
	/**
	 * 
	 * @param nokRelationshipdescription description of 'next of kind relationship'
	 * @return id of 'next of kind relationship'
	 * @throws Exception
	 */
	NOKRelationship getNokRelationshipByDescription(final String nokRelationshipdescription) throws Exception;

}