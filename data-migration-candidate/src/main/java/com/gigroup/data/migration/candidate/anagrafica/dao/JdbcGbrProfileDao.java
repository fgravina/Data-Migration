package com.gigroup.data.migration.candidate.anagrafica.dao;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.gigroup.data.migration.candidate.anagrafica.GbrProfile;
import com.gigroup.data.migration.candidate.util.DataMigrationException;

public class JdbcGbrProfileDao implements GbrProfileDao {
	
	private static final String GBR_PROFILE_TABLE_NAME = "GBR_PROFILES";
	private static final String PERSON_ID_COLUMN_DB = "PERSON_ID";
	private static final String TAX_CODE_COLUMN_DB = "TAX_CODE";
	private static final String NOK_CONTACT_NUMBER_COLUMN_DB = "NOK_CONTACT_N";
	private static final String NOK_NAME_NUMBER_COLUMN_DB = "NOK_NAME";
	private static final String NOK_RELATIONSHIP_COLUMN_DB = "NOK_RELATIONSHIP";
	
	private static final transient Logger logger = LoggerFactory.getLogger(JdbcGbrProfileDao.class);

	private SimpleJdbcInsert insertGbrProfile;	

	public void addGbrProfile(final GbrProfile gbrProfile) {
		final String methodName = "addGbrProfile() - ";
		if (logger.isDebugEnabled()) {
			logger.debug(methodName + "InputParams[gbrProfile=" + gbrProfile + "]");
		}
		SqlParameterSource parameters = new MapSqlParameterSource()				
				.addValue(PERSON_ID_COLUMN_DB, gbrProfile.getPersonId())
				.addValue(TAX_CODE_COLUMN_DB, gbrProfile.getTaxCode())	
				.addValue(NOK_CONTACT_NUMBER_COLUMN_DB, gbrProfile.getNokContactNumber())	
				.addValue(NOK_NAME_NUMBER_COLUMN_DB, gbrProfile.getNokName())	
				.addValue(NOK_RELATIONSHIP_COLUMN_DB, gbrProfile.getNokReleationship())
				.addValue(CREATION_USER_COLUMN_DB, LIFERAY_CREATION_USER);
		try {			
			this.insertGbrProfile.execute(parameters);
		} catch (DuplicateKeyException dke) {
			throw new DataMigrationException(methodName +"GbrProfile: " + gbrProfile + " is already inside database: " + dke);
		} catch (Throwable t) {
			throw new DataMigrationException(methodName +"Unexpected exception: " + t);
		}
	}
	
	// Injection
	public void setDataSource(final DataSource dataSource) {		
		this.insertGbrProfile = new SimpleJdbcInsert(dataSource).withTableName(GBR_PROFILE_TABLE_NAME);	
	}	
}