package com.gigroup.data.migration.candidate.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.gigroup.data.migration.candidate.ExternalSystemReference;
import com.gigroup.data.migration.candidate.util.DataMigrationException;

public class JdbcExternalSystemReferenceDao implements ExternalSystemReferenceDao {
	
	private static final String EXTERNAL_SYSTEM_REFERENCE_TABLE_NAME = "EXTERNAL_SYSTEM_REF";
	private static final String GIREX_ID_COLUMN_DB = "GIREX_ID";
	private static final String TYPE_COLUMN_DB = "TYPE";	
	private static final String EXTERNAL_SYSTEM_OBJ_ID_COLUMN_DB = "EXT_SYS_OBJ_ID";
	private static final String BUSINESS_CODE_COLUMN_DB = "BUSINESS_CODE";
	private static final String COUNTRY_CODE_COLUMN_DB = "COUNTRY_CODE";
	private static final String SYSTEM_ID_COLUMN_DB = "SYSTEM_ID";
	
	
	private static final transient Logger logger = LoggerFactory.getLogger(JdbcExternalSystemReferenceDao.class);
	
	private SimpleJdbcInsert insertExternalSystemReference;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public void addExternalSystemReference(final ExternalSystemReference externalSystemReference) {
		final String methodName = "addExternalSystemReference() - ";
		if (logger.isDebugEnabled()) {
			logger.debug(methodName + "InputParams[externalSystemReference=" + externalSystemReference + "]");
		}
		
				
		try {			
			
			SqlParameterSource parameters = new MapSqlParameterSource()
					.addValue(GIREX_ID_COLUMN_DB, externalSystemReference.getGirexId())
					.addValue(TYPE_COLUMN_DB, externalSystemReference.getType() != null ? externalSystemReference.getType() : PERSON_TYPE)
					.addValue(CREATION_TIMESTAMP_COLUMN_DB, externalSystemReference.getCreationTimestamp())
					.addValue(CREATION_USER_COLUMN_DB, externalSystemReference.getCreationUser())
					.addValue(EXTERNAL_SYSTEM_OBJ_ID_COLUMN_DB, externalSystemReference.getExternalSystemObjectId())
					.addValue(LAST_UPDATE_TIMESTAMP_COLUMN_DB, externalSystemReference.getLastUpdateTimestamp())
					.addValue(LAST_UPDATE_USER_COLUMN_DB, externalSystemReference.getLastUpdateUser())
					.addValue(BUSINESS_CODE_COLUMN_DB, externalSystemReference.getBusinessCode() != null ? externalSystemReference.getBusinessCode() : GI_GROUP_BUSINESS_CODE)
					.addValue(COUNTRY_CODE_COLUMN_DB, externalSystemReference.getCountryCode() != null ? externalSystemReference.getCountryCode() : ITALY_COUNTRY_CODE)
					.addValue(SYSTEM_ID_COLUMN_DB, externalSystemReference.getSystemId())
					.addValue(CREATION_USER_COLUMN_DB, LIFERAY_CREATION_USER);	
			
			this.insertExternalSystemReference.execute(parameters);
		} catch (DuplicateKeyException dke) {
			//logger.error(methodName +"The ExternalSystemReference: " + externalSystemReference + " is already inside database: " + dke);
			throw new DataMigrationException(methodName +"The ExternalSystemReference: " + externalSystemReference + " is already inside database: " + dke);
		} catch (Throwable t) {
			//logger.error(methodName +"Unexpected exception: " + t);
			throw new DataMigrationException(methodName +"Unexpected exception: " + t);
		}
		
	}
	
	@Override
	public ExternalSystemReference findByExtSystemObjectId(String extSystemObjectId) {
		final String methodName = "findByExtSystemObjectId() - ";
		
		final String querySql = "SELECT * FROM " +EXTERNAL_SYSTEM_REFERENCE_TABLE_NAME+ " WHERE COUNTRY_CODE = 'ITA' AND BUSINESS_CODE = 'GIG' AND TYPE = 'PERSON' AND EXT_SYS_OBJ_ID = :extSystemObjectId ORDER BY CREATION_TS DESC";
		SqlParameterSource namedParameters = new MapSqlParameterSource("extSystemObjectId", extSystemObjectId);

		try {
			List<ExternalSystemReference> externalSystemReferenceList = this.namedParameterJdbcTemplate.query(querySql, namedParameters, new ExternalSystemReferenceMapper());
			
			if(externalSystemReferenceList != null && externalSystemReferenceList.size() > 0){
				// return the first record found...
				return externalSystemReferenceList.get(0);
			} else {
				logger.warn(methodName + "ExternalSystemReference with extSystemObjectId [" + extSystemObjectId + "] cannot be found inside database");
			}	
		} catch (DataAccessException dae) {
			logger.error(methodName + "Data Access to the Database Error: " + dae);
			//throw dae;
		} catch (Throwable t) {
			logger.error(methodName +"Unexpected exception: " + t);
		}
		
		return null;
	}
	
	private static final class ExternalSystemReferenceMapper implements RowMapper<ExternalSystemReference> {
		public ExternalSystemReference mapRow(ResultSet rs, int rowNum) throws SQLException {

			ExternalSystemReference externalSystemReference = new ExternalSystemReference();
			
			externalSystemReference.setGirexId(rs.getString(GIREX_ID_COLUMN_DB));
			externalSystemReference.setType(rs.getString(TYPE_COLUMN_DB));
			externalSystemReference.setExternalSystemObjectId(rs.getString(EXTERNAL_SYSTEM_OBJ_ID_COLUMN_DB));
			externalSystemReference.setBusinessCode(rs.getString(BUSINESS_CODE_COLUMN_DB));
			externalSystemReference.setCountryCode(rs.getString(COUNTRY_CODE_COLUMN_DB));
			externalSystemReference.setSystemId(rs.getLong(SYSTEM_ID_COLUMN_DB));
			
			return externalSystemReference;
		}
	}
	
	// Injection
	public void setDataSource(final DataSource dataSource) {
		this.insertExternalSystemReference = new SimpleJdbcInsert(dataSource).withTableName(EXTERNAL_SYSTEM_REFERENCE_TABLE_NAME);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
}