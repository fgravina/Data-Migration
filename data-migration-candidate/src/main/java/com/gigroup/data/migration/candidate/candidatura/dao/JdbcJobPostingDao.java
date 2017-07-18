package com.gigroup.data.migration.candidate.candidatura.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.gigroup.data.migration.candidate.candidatura.JobPosting;
import com.gigroup.data.migration.candidate.util.DataMigrationException;

public class JdbcJobPostingDao implements JobPostingDao {
	
	private static final String JOB_POSTING_TABLE_NAME = "JOB_POSTING";
	private static final String JOB_POSTING_ID_COLUMN_DB = "JOB_POSTING_ID";
	private static final String OFFER_ID_COLUMN_DB = "OFFER_ID";	
	private static final String REFERENCE_CODE_COLUMN_DB = "REFERENCE_CODE";
	private static final String TITLE_COLUMN_DB = "TITLE";	
	
	private static final transient Logger logger = LoggerFactory.getLogger(JdbcJobPostingDao.class);	
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

		
	@Override
	public JobPosting findByReferenceCode(final String referenceCode) {
		final String methodName = "findByReferenceCode() - ";
		
		final String querySql = "SELECT "+JOB_POSTING_ID_COLUMN_DB + ", "
										 +OFFER_ID_COLUMN_DB + ", "
										 +REFERENCE_CODE_COLUMN_DB + ", "
										 +TITLE_COLUMN_DB
							 + " FROM " +JOB_POSTING_TABLE_NAME
							 + " WHERE "+REFERENCE_CODE_COLUMN_DB+" = :referenceCode";
		
		SqlParameterSource namedParameters = new MapSqlParameterSource("referenceCode", referenceCode);

		try {
			List<JobPosting> jobPostingList = this.namedParameterJdbcTemplate.query(querySql, namedParameters, new JobPostingMapper());
			
			if(jobPostingList != null && jobPostingList.size() > 0){
				// return the first record found...
				return jobPostingList.get(0);
			} else {
				logger.warn(methodName + "JobPosting with jobPostingList [" + jobPostingList + "] cannot be found inside database");
			}	
		} catch (DataAccessException dae) {
			//logger.error(methodName + "Data Access to the Database Error: " + dae);
			throw new DataMigrationException(methodName + "Data Access to the Database Error: " + dae);
		} catch (Throwable t) {
			//logger.error(methodName +"Unexpected exception: " + t);
			throw new DataMigrationException(methodName +"Unexpected exception: " + t);
		}
		
		return null;
	}
	
	private static final class JobPostingMapper implements RowMapper<JobPosting> {
		public JobPosting mapRow(ResultSet rs, int rowNum) throws SQLException {

			JobPosting jobPosting = new JobPosting();			
			jobPosting.setJobPostingId(rs.getLong(JOB_POSTING_ID_COLUMN_DB));
			jobPosting.setOfferId(rs.getString(OFFER_ID_COLUMN_DB));
			jobPosting.setReferenceCode(rs.getString(REFERENCE_CODE_COLUMN_DB));
			jobPosting.setTitle(rs.getString(TITLE_COLUMN_DB));
			
			return jobPosting;
		}
	}
	
	// Injection
	public void setDataSource(final DataSource dataSource) {		
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
}