package com.gigroup.data.migration.candidate.jobalert.dao;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.gigroup.data.migration.candidate.jobalert.JobAlert;
import com.gigroup.data.migration.candidate.util.DataMigrationException;

public class JdbcJobAlertDao implements JobAlertDao {
	
	private static final String JOB_ALERT_TABLE_NAME = "JOB_ALERTS";
	private static final String JOB_ALERT_ID_COLUMN_DB = "JOB_ALERT_ID";
	private static final String CITY_COLUMN_DB = "CITY";
	private static final String CONTRACT_TYPE_ID_COLUMN_DB = "CONTRACT_TYPE_ID";
	private static final String DISTANCE_FROM_CITY_COLUMN_DB = "DISTANCE_FROM_CITY";
	private static final String INDUSTRY_SECTOR_ID_COLUMN_DB = "INDUSTRY_SECTOR_ID";
	private static final String JOB_TITLE_ID_COLUMN_DB = "JOB_TITLE_ID";
	private static final String KEYWORDS_COLUMN_DB = "KEYWORDS";
	private static final String LAST_SEND_TIMESTAMP_COLUMN_DB = "LAST_SEND_TS";
	private static final String NAME_COLUMN_DB = "NAME";
	private static final String PROFESSIONAL_AREA_ID_COLUMN_DB = "PROFESSIONAL_AREA_ID";
	private static final String JOB_ALERT_FREQ_ID_COLUMN_DB = "JOB_ALERT_FREQ_ID";	
	private static final String PERSON_ID_COLUMN_DB = "PERSON_ID";
	
	private static final transient Logger logger = LoggerFactory.getLogger(JdbcJobAlertDao.class);

	private SimpleJdbcInsert insertJobAlert;	

	public void addJobAlert(final JobAlert jobAlert) {
		final String methodName = "addJobAlert() - ";
		if (logger.isDebugEnabled()) {
			logger.debug(methodName + "InputParams[jobAlert=" + jobAlert + "]");
		}
		
		SqlParameterSource parameters = new MapSqlParameterSource()					
				.addValue(CITY_COLUMN_DB, jobAlert.getCity())
				.addValue(CONTRACT_TYPE_ID_COLUMN_DB, jobAlert.getContractTypeId())
				.addValue(CREATION_TIMESTAMP_COLUMN_DB, jobAlert.getCreationTimestamp())
				.addValue(CREATION_USER_COLUMN_DB, jobAlert.getCreationUser())				
				.addValue(LAST_UPDATE_TIMESTAMP_COLUMN_DB, jobAlert.getLastUpdateTimestamp())
				.addValue(LAST_UPDATE_USER_COLUMN_DB, jobAlert.getLastUpdateUser())
				.addValue(DISTANCE_FROM_CITY_COLUMN_DB, jobAlert.getDistanceFromCity())
				.addValue(INDUSTRY_SECTOR_ID_COLUMN_DB, jobAlert.getIndustrySectorId())
				.addValue(JOB_TITLE_ID_COLUMN_DB, jobAlert.getJobTitleId())
				.addValue(KEYWORDS_COLUMN_DB, jobAlert.getKeywords())
				.addValue(LAST_SEND_TIMESTAMP_COLUMN_DB, jobAlert.getLastSendTimestamp())
				.addValue(NAME_COLUMN_DB, jobAlert.getName())
				.addValue(PROFESSIONAL_AREA_ID_COLUMN_DB, jobAlert.getProfessionalAreaId())
				.addValue(JOB_ALERT_FREQ_ID_COLUMN_DB, jobAlert.getJobAlertFrequencyId())				
				.addValue(PERSON_ID_COLUMN_DB, jobAlert.getPersonId())
				.addValue(CREATION_USER_COLUMN_DB, LIFERAY_CREATION_USER);

		try {			
			this.insertJobAlert.execute(parameters);
		} catch (DuplicateKeyException dke) {
			//logger.error(methodName +"Job Alert: " + jobAlert + " is already inside database: " + dke);
			throw new DataMigrationException(methodName +"Job Alert: " + jobAlert + " is already inside database: " + dke);
		} catch (Throwable t) {
			//logger.error(methodName +"Unexpected exception: " + t);
			throw new DataMigrationException(methodName +"Unexpected exception: " + t);
		}
	}
	
	// Injection
	public void setDataSource(final DataSource dataSource) {		
		this.insertJobAlert = new SimpleJdbcInsert(dataSource).withTableName(JOB_ALERT_TABLE_NAME).usingGeneratedKeyColumns(JOB_ALERT_ID_COLUMN_DB);
	}	
}