package com.gigroup.data.migration.candidate.candidatura.dao;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.gigroup.data.migration.candidate.candidatura.AppliedPosition;
import com.gigroup.data.migration.candidate.util.DataMigrationException;

public class JdbcAppliedPositionDao implements AppliedPositionDao {
	
	private static final String APPLIED_POSITION_TABLE_NAME = "APPLIED_POSITION";
	private static final String APPLIED_POSITION_ID_COLUMN_DB = "APPLIED_POSITION_ID";
	private static final String APPLIED_POSITION_TIMESTAMP_COLUMN_DB = "APPLIED_POSITION_TS";
	private static final String COVER_LETTER_COLUMN_DB = "COVER_LETTER";
	private static final String JOB_POSTING_ID_COLUMN_DB = "JOB_POSTING_ID";
	private static final String APPLIED_POSITION_STATUS_ID_COLUMN_DB = "APPLIED_POSITION_STATUS_ID";	
	private static final String PERSON_ID_COLUMN_DB = "PERSON_ID";
	
	private static final transient Logger logger = LoggerFactory.getLogger(JdbcAppliedPositionDao.class);

	private SimpleJdbcInsert insertAddress;	

	public void addAppliedPosition(final AppliedPosition appliedPosition) {
		final String methodName = "addAppliedPosition() - ";
		if (logger.isDebugEnabled()) {
			logger.debug(methodName + "InputParams[appliedPosition=" + appliedPosition + "]");
		}
		
		SqlParameterSource parameters = new MapSqlParameterSource()					
				.addValue(APPLIED_POSITION_TIMESTAMP_COLUMN_DB, appliedPosition.getAppliedPositionTimestamp())
				.addValue(COVER_LETTER_COLUMN_DB, appliedPosition.getCoverLetter())
				.addValue(CREATION_TIMESTAMP_COLUMN_DB, appliedPosition.getCreationTimestamp())
				.addValue(CREATION_USER_COLUMN_DB, appliedPosition.getCreationUser())				
				.addValue(LAST_UPDATE_TIMESTAMP_COLUMN_DB, appliedPosition.getLastUpdateTimestamp())
				.addValue(LAST_UPDATE_USER_COLUMN_DB, appliedPosition.getLastUpdateUser())
				.addValue(JOB_POSTING_ID_COLUMN_DB, appliedPosition.getJobPostingId())
				.addValue(APPLIED_POSITION_STATUS_ID_COLUMN_DB, appliedPosition.getAppliedPositionStatusId() != null ? appliedPosition.getAppliedPositionStatusId() : APPLIED_POSITION_STATUS_ID)			
				.addValue(PERSON_ID_COLUMN_DB, appliedPosition.getPersonId())
				.addValue(CREATION_USER_COLUMN_DB, LIFERAY_CREATION_USER);

		try {			
			this.insertAddress.execute(parameters);
		} catch (DuplicateKeyException dke) {
			//logger.error(methodName +"AppliedPosition: " + appliedPosition + " is already inside database: " + dke);
			throw new DataMigrationException(methodName +"AppliedPosition: " + appliedPosition + " is already inside database: " + dke);
		} catch (Throwable t) {
			//logger.error(methodName +"Unexpected exception: " + t);
			throw new DataMigrationException(methodName +"Unexpected exception: " + t);
		}
	}
	
	// Injection
	public void setDataSource(final DataSource dataSource) {		
		this.insertAddress = new SimpleJdbcInsert(dataSource).withTableName(APPLIED_POSITION_TABLE_NAME).usingGeneratedKeyColumns(APPLIED_POSITION_ID_COLUMN_DB);
	}	
}