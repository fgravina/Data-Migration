package com.gigroup.data.migration.candidate.anagrafica.dao;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.gigroup.data.migration.candidate.anagrafica.PersonCountryBusinessRole;
import com.gigroup.data.migration.candidate.util.DataMigrationException;

public class JdbcPersonCountryBusinessRoleDao implements PersonCountryBusinessRoleDao {
	
	private static final String PERSON_COUNTRY_BUSINESS_ROLE_TABLE_NAME = "PPL_CTRY_BUS_ROLES";
	private static final String BUSINESS_CODE_COLUMN_DB = "BUSINESS_CODE";
	private static final String COUNTRY_CODE_COLUMN_DB = "COUNTRY_CODE";	
	private static final String PERSON_ID_COLUMN_DB = "PERSON_ID";
	private static final String ROLE_CODE_COLUMN_DB = "ROLE_CODE";
	
	
	private static final transient Logger logger = LoggerFactory.getLogger(JdbcPersonCountryBusinessRoleDao.class);
	
	private SimpleJdbcInsert insertPersonCountryBusinessRole;

	@Override
	public void add(final PersonCountryBusinessRole personCountryBusinessRole) {
		final String methodName = "add() - ";
		if (logger.isDebugEnabled()) {
			logger.debug(methodName + "InputParams[personCountryBusinessRole=" + personCountryBusinessRole + "]");
		}
		
				
		try {			
			
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue(BUSINESS_CODE_COLUMN_DB, personCountryBusinessRole.getBusinessCode() != null ? personCountryBusinessRole.getBusinessCode() : GI_GROUP_BUSINESS_CODE)
					.addValue(COUNTRY_CODE_COLUMN_DB, personCountryBusinessRole.getCountryCode() != null ? personCountryBusinessRole.getCountryCode() : ITALY_COUNTRY_CODE)
					.addValue(CREATION_TIMESTAMP_COLUMN_DB, personCountryBusinessRole.getCreationTimestamp())
					.addValue(CREATION_USER_COLUMN_DB, personCountryBusinessRole.getCreationUser())
					.addValue(LAST_UPDATE_TIMESTAMP_COLUMN_DB, personCountryBusinessRole.getLastUpdateTimestamp())
					.addValue(LAST_UPDATE_USER_COLUMN_DB, personCountryBusinessRole.getLastUpdateUser())
					.addValue(PERSON_ID_COLUMN_DB, personCountryBusinessRole.getPersonId())
					.addValue(ROLE_CODE_COLUMN_DB, personCountryBusinessRole.getRoleCode() != null ? personCountryBusinessRole.getRoleCode() : CANDIDATE_ROLE_CODE)
					.addValue(CREATION_USER_COLUMN_DB, LIFERAY_CREATION_USER);
			
			this.insertPersonCountryBusinessRole.execute(parameters);
		} catch (DuplicateKeyException dke) {
			//logger.error(methodName +"The PersonCountryBusinessRole: " + personCountryBusinessRole + " is already inside database: " + dke);
			throw new DataMigrationException(methodName +"The PersonCountryBusinessRole: " + personCountryBusinessRole + " is already inside database: " + dke);
		} catch (Throwable t) {
			//logger.error(methodName +"Unexpected exception: " + t);
			throw new DataMigrationException(methodName +"Unexpected exception: " + t);
		}
		
	}
	
	@Override
	public void addUK(final PersonCountryBusinessRole personCountryBusinessRole) {
		final String methodName = "add() - ";
		if (logger.isDebugEnabled()) {
			logger.debug(methodName + "InputParams[personCountryBusinessRole=" + personCountryBusinessRole + "]");
		}
		try {			
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue(PERSON_ID_COLUMN_DB, personCountryBusinessRole.getPersonId())
					.addValue(BUSINESS_CODE_COLUMN_DB, personCountryBusinessRole.getBusinessCode() != null ? personCountryBusinessRole.getBusinessCode() : GI_GROUP_BUSINESS_CODE)
					.addValue(COUNTRY_CODE_COLUMN_DB, personCountryBusinessRole.getCountryCode() != null ? personCountryBusinessRole.getCountryCode() : GBR_COUNTRY_CODE)
					.addValue(CREATION_TIMESTAMP_COLUMN_DB, personCountryBusinessRole.getCreationTimestamp())
					.addValue(CREATION_USER_COLUMN_DB, personCountryBusinessRole.getCreationUser())
					.addValue(LAST_UPDATE_TIMESTAMP_COLUMN_DB, personCountryBusinessRole.getLastUpdateTimestamp())
					.addValue(LAST_UPDATE_USER_COLUMN_DB, personCountryBusinessRole.getLastUpdateUser())
					.addValue(ROLE_CODE_COLUMN_DB, personCountryBusinessRole.getRoleCode() != null ? personCountryBusinessRole.getRoleCode() : CANDIDATE_ROLE_CODE)
					.addValue(CREATION_USER_COLUMN_DB, LIFERAY_CREATION_USER);
			this.insertPersonCountryBusinessRole.execute(parameters);
		} catch (DuplicateKeyException dke) {
			throw new DataMigrationException(methodName +"The PersonCountryBusinessRole: " + personCountryBusinessRole + " is already inside database: " + dke);
		} catch (Throwable t) {
			throw new DataMigrationException(methodName +"Unexpected exception: " + t);
		}
	}
	
	// Injection
	public void setDataSource(final DataSource dataSource) {
		this.insertPersonCountryBusinessRole = new SimpleJdbcInsert(dataSource).withTableName(PERSON_COUNTRY_BUSINESS_ROLE_TABLE_NAME);	
	}
}
