package com.gigroup.data.migration.candidate.anagrafica.dao;

import java.sql.Types;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.gigroup.data.migration.candidate.anagrafica.ItalyProfile;
import com.gigroup.data.migration.candidate.util.DataMigrationException;

public class JdbcItalyProfileDao implements ItalyProfileDao {
	
	private static final String ITALY_PROFILE_TABLE_NAME = "ITA_PROFILES";
	private static final String CAR_AVAILABLE_COLUMN_DB = "CAR_AVAILABLE";
	private static final String DRIVER_LICENSE_A_COLUMN_DB = "DRIVER_LICENSE_A";
	private static final String DRIVER_LICENSE_B_COLUMN_DB = "DRIVER_LICENSE_B";
	private static final String DRIVER_LICENSE_C_COLUMN_DB = "DRIVER_LICENSE_C";
	private static final String LEGALLY_PROTECTED_STATUS_COLUMN_DB = "LEGALLY_PROTECTED_STATUS";
	private static final String NO_TAX_CODE_COLUMN_DB = "NO_TAX_CODE";
	private static final String PRIVACY_GI_GROUP_COLUMN_DB = "PRIVACY_GI_GROUP";
	private static final String PRIVACY_MARKETING_COLUMN_DB = "PRIVACY_MARKETING";
	private static final String PRIVACY_TRAINING_COLUMN_DB = "PRIVACY_TRAINING";
	private static final String TAX_CODE_COLUMN_DB = "TAX_CODE";	
	private static final String PERSON_ID_COLUMN_DB = "PERSON_ID";
	
	private static final transient Logger logger = LoggerFactory.getLogger(JdbcItalyProfileDao.class);

	private SimpleJdbcInsert insertItalyProfile;	

	public void addItalyProfile(final ItalyProfile italyProfile) {
		final String methodName = "addItalyProfile() - ";
		if (logger.isDebugEnabled()) {
			logger.debug(methodName + "InputParams[italyProfile=" + italyProfile + "]");
		}
		
		SqlParameterSource parameters = new MapSqlParameterSource()				
				.addValue(CAR_AVAILABLE_COLUMN_DB, italyProfile.getCarAvailable())				
				.addValue(CREATION_TIMESTAMP_COLUMN_DB, italyProfile.getCreationTimestamp(), Types.TIMESTAMP)
				.addValue(CREATION_USER_COLUMN_DB, italyProfile.getCreationUser())				
				.addValue(LAST_UPDATE_TIMESTAMP_COLUMN_DB, italyProfile.getLastUpdateTimestamp(), Types.TIMESTAMP)
				.addValue(LAST_UPDATE_USER_COLUMN_DB, italyProfile.getLastUpdateUser())
				.addValue(DRIVER_LICENSE_A_COLUMN_DB, italyProfile.getDriverLicenseA())
				.addValue(DRIVER_LICENSE_B_COLUMN_DB, italyProfile.getDriverLicenseB())
				.addValue(DRIVER_LICENSE_C_COLUMN_DB, italyProfile.getDriverLicenseC())	
				.addValue(LEGALLY_PROTECTED_STATUS_COLUMN_DB, italyProfile.getLegallyProtectedStatus())	
				.addValue(NO_TAX_CODE_COLUMN_DB, italyProfile.getNoTaxCode())	
				.addValue(PRIVACY_GI_GROUP_COLUMN_DB, italyProfile.getPrivacyGiGroup())	
				.addValue(PRIVACY_MARKETING_COLUMN_DB, italyProfile.getPrivacyMarketing())	
				.addValue(PRIVACY_TRAINING_COLUMN_DB, italyProfile.getPrivacyTraining())
				.addValue(TAX_CODE_COLUMN_DB, italyProfile.getTaxCode())	
				.addValue(PERSON_ID_COLUMN_DB, italyProfile.getPersonId())
				.addValue(CREATION_USER_COLUMN_DB, LIFERAY_CREATION_USER);

		try {			
			this.insertItalyProfile.execute(parameters);
		} catch (DuplicateKeyException dke) {
			//logger.error(methodName +"ItalyProfile: " + italyProfile + " is already inside database: " + dke);
			throw new DataMigrationException(methodName +"ItalyProfile: " + italyProfile + " is already inside database: " + dke);
		} catch (Throwable t) {
			//logger.error(methodName +"Unexpected exception: " + t);
			throw new DataMigrationException(methodName +"Unexpected exception: " + t);
		}
	}
	
	// Injection
	public void setDataSource(final DataSource dataSource) {		
		this.insertItalyProfile = new SimpleJdbcInsert(dataSource).withTableName(ITALY_PROFILE_TABLE_NAME);	
	}	
}