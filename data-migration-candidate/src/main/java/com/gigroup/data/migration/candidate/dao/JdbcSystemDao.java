package com.gigroup.data.migration.candidate.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.gigroup.data.migration.candidate.System;

public class JdbcSystemDao implements SystemDao {
	
	private static final String SYSTEM_TABLE_NAME = "SYSTEMS";
	private static final String SYSTEM_ID_COLUMN_DB = "SYSTEM_ID";
	private static final String DESCRIPTION_COLUMN_DB = "DESCRIPTION";	
	
	private static final transient Logger logger = LoggerFactory.getLogger(JdbcSystemDao.class);
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	@Override
	public List<System> getAllSystem() {		
		final String methodName = "getAllSystem() - ";
				
		final String querySql = "SELECT * FROM " +SYSTEM_TABLE_NAME;	

		try {
			return this.namedParameterJdbcTemplate.query(querySql, new SystemMapper());				
		} catch (DataAccessException dae) {
			logger.error(methodName + "Data Access to the Database Error: " + dae);
			//throw dae;
		} catch (Throwable t) {
			logger.error(methodName +"Unexpected exception: " + t);
		}
		
		return null;
	}
	
	public static final class SystemMapper implements RowMapper<System> {
		public System mapRow(ResultSet rs, int rowNum) throws SQLException {

			System system = new System();
			system.setSystemId(rs.getLong(SYSTEM_ID_COLUMN_DB));
			system.setDescription(rs.getString(DESCRIPTION_COLUMN_DB));
			return system;
		}
	}
	
	// Injection
	public void setDataSource(final DataSource dataSource) {		
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}	
}