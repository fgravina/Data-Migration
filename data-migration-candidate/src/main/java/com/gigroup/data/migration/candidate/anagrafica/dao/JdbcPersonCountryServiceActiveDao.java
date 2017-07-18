package com.gigroup.data.migration.candidate.anagrafica.dao;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.gigroup.data.migration.candidate.anagrafica.PersonCountryServiceActive;
import com.gigroup.data.migration.candidate.util.DataMigrationException;

public class JdbcPersonCountryServiceActiveDao implements PersonCountryServiceActiveDao {
	
	private static final String PERSON_COUNTRY_SERVICE_ACTIVE_TABLE_NAME = "PPL_CTRY_SERV_ACT";
	private static final String BUSINESS_CODE_COLUMN_DB = "BUSINESS_CODE";
	private static final String CLAIMS_CODE_COLUMN_DB = "CLAIMS_CODE";
	private static final String COUNTRY_CODE_COLUMN_DB = "COUNTRY_CODE";	
	private static final String PERSON_ID_COLUMN_DB = "PERSON_ID";
	private static final String SERVICE_ID_COLUMN_DB = "SERVICE_ID";
	
	
	private static final transient Logger logger = LoggerFactory.getLogger(JdbcPersonCountryServiceActiveDao.class);
	
	private SimpleJdbcInsert insertPersonCountryServiceActive;

	@Override
	public void add(final PersonCountryServiceActive personCountryServiceActive) {
		final String methodName = "add() - ";
		if (logger.isDebugEnabled()) {
			logger.debug(methodName + "InputParams[personCountryServiceActive=" + personCountryServiceActive + "]");
		}
		
				
		try {			
			
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue(BUSINESS_CODE_COLUMN_DB, personCountryServiceActive.getBusinessCode() != null ? personCountryServiceActive.getBusinessCode() : GI_GROUP_BUSINESS_CODE)
					.addValue(COUNTRY_CODE_COLUMN_DB, personCountryServiceActive.getCountryCode() != null ? personCountryServiceActive.getCountryCode() : ITALY_COUNTRY_CODE)
					.addValue(CLAIMS_CODE_COLUMN_DB, personCountryServiceActive.getClaimsCode() != null ? personCountryServiceActive.getClaimsCode() : WRITING_CLAIMS_CODE)
					.addValue(CREATION_TIMESTAMP_COLUMN_DB, personCountryServiceActive.getCreationTimestamp())
					.addValue(CREATION_USER_COLUMN_DB, personCountryServiceActive.getCreationUser())
					.addValue(LAST_UPDATE_TIMESTAMP_COLUMN_DB, personCountryServiceActive.getLastUpdateTimestamp())
					.addValue(LAST_UPDATE_USER_COLUMN_DB, personCountryServiceActive.getLastUpdateUser())
					.addValue(PERSON_ID_COLUMN_DB, personCountryServiceActive.getPersonId())
					.addValue(SERVICE_ID_COLUMN_DB, personCountryServiceActive.getServiceId())
					.addValue(CREATION_USER_COLUMN_DB, LIFERAY_CREATION_USER);
			
			this.insertPersonCountryServiceActive.execute(parameters);
		} catch (DuplicateKeyException dke) {
			//logger.error(methodName +"The PersonCountryServiceActive: " + personCountryServiceActive + " is already inside database: " + dke);
			throw new DataMigrationException(methodName +"The PersonCountryServiceActive: " + personCountryServiceActive + " is already inside database: " + dke);
		} catch (Throwable t) {
			//logger.error(methodName +"Unexpected exception: " + t);
			throw new DataMigrationException(methodName +"Unexpected exception: " + t);
		}
		
	}
	
	// Injection
	public void setDataSource(final DataSource dataSource) {
		this.insertPersonCountryServiceActive = new SimpleJdbcInsert(dataSource).withTableName(PERSON_COUNTRY_SERVICE_ACTIVE_TABLE_NAME);	
	}
}