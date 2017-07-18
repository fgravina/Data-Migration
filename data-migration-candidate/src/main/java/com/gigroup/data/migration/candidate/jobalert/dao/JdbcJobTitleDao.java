package com.gigroup.data.migration.candidate.jobalert.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.gigroup.data.migration.candidate.jobalert.JobTitle;

public class JdbcJobTitleDao implements JobTitleDao {
	
	private static final String JOB_TITLE_TABLE_NAME = "JOB_TITLES";
	private static final String JOB_TITLE_ID_COLUMN_DB = "JOB_TITLE_ID";
	private static final String CODE_EXT_COLUMN_DB = "CODE_EXT";
	private static final String DESCRIPTION_COLUMN_DB = "DESCRIPTION";
	private static final String VALID_FROM_DATE_COLUMN_DB = "VALID_FROM_DATE";
	private static final String VALID_TO_DATE_COLUMN_DB = "VALID_TO_DATE";
	
	
	private static final transient Logger logger = LoggerFactory.getLogger(JdbcJobTitleDao.class);
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	@Override
	public List<JobTitle> getAll() {		
		final String methodName = "getAll() - ";
				
		final String querySql = "SELECT * FROM " +JOB_TITLE_TABLE_NAME;	

		try {
			return this.namedParameterJdbcTemplate.query(querySql, new JobTitleMapper());				
		} catch (DataAccessException dae) {
			logger.error(methodName + "Data Access to the Database Error: " + dae);
			//throw dae;
		} catch (Throwable t) {
			logger.error(methodName +"Unexpected exception: " + t);
		}
		
		return null;
	}
	
	public static final class JobTitleMapper implements RowMapper<JobTitle> {
		public JobTitle mapRow(ResultSet rs, int rowNum) throws SQLException {

			JobTitle industrySector = new JobTitle();
			industrySector.setJobTitleId(rs.getLong(JOB_TITLE_ID_COLUMN_DB));
			industrySector.setCodeExternal(rs.getString(CODE_EXT_COLUMN_DB));
			industrySector.setDescription(rs.getString(DESCRIPTION_COLUMN_DB));
			industrySector.setValidFromDate(rs.getTimestamp(VALID_FROM_DATE_COLUMN_DB));
			industrySector.setValidToDate(rs.getTimestamp(VALID_TO_DATE_COLUMN_DB));
			return industrySector;
		}
	}
	
	// Injection
	public void setDataSource(final DataSource dataSource) {		
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}	
}