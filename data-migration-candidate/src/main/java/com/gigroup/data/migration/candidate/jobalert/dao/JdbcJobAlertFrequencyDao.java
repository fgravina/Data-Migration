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

import com.gigroup.data.migration.candidate.jobalert.JobAlertFrequency;

public class JdbcJobAlertFrequencyDao implements JobAlertFrequencyDao {
	
	private static final String JOB_ALERT_FREQUENCY_TABLE_NAME = "JOB_ALERT_FREQUENCIES";
	private static final String JOB_ALERT_FREQ_ID_COLUMN_DB = "JOB_ALERT_FREQ_ID";
	private static final String DESCRIPTION_COLUMN_DB = "DESCRIPTION";
	private static final String NUMBER_DAYS_COLUMN_DB = "NUMBER_DAYS";
	
	private static final transient Logger logger = LoggerFactory.getLogger(JdbcJobAlertFrequencyDao.class);
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	@Override
	public List<JobAlertFrequency> getAll() {		
		final String methodName = "getAll() - ";
				
		final String querySql = "SELECT * FROM " +JOB_ALERT_FREQUENCY_TABLE_NAME;	

		try {
			return this.namedParameterJdbcTemplate.query(querySql, new JobAlertFrequencyMapper());				
		} catch (DataAccessException dae) {
			logger.error(methodName + "Data Access to the Database Error: " + dae);
			//throw dae;
		} catch (Throwable t) {
			logger.error(methodName +"Unexpected exception: " + t);
		}
		
		return null;
	}
	
	public static final class JobAlertFrequencyMapper implements RowMapper<JobAlertFrequency> {
		public JobAlertFrequency mapRow(ResultSet rs, int rowNum) throws SQLException {

			JobAlertFrequency jobbAlertFrequency = new JobAlertFrequency();
			jobbAlertFrequency.setJobAlertFrequencyId(rs.getInt(JOB_ALERT_FREQ_ID_COLUMN_DB));
			jobbAlertFrequency.setDescription(rs.getString(DESCRIPTION_COLUMN_DB));
			jobbAlertFrequency.setNumberDays(rs.getInt(NUMBER_DAYS_COLUMN_DB));
			return jobbAlertFrequency;
		}
	}
	
	// Injection
	public void setDataSource(final DataSource dataSource) {		
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}	
}