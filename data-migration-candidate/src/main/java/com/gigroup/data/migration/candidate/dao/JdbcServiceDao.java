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

import com.gigroup.data.migration.candidate.Service;

public class JdbcServiceDao implements ServiceDao {
	
	private static final String SERVICE_TABLE_NAME = "SERVICES";
	private static final String SERVICE_ID_COLUMN_DB = "SERVICE_ID";
	private static final String DESCRIPTION_COLUMN_DB = "DESCRIPTION";	
	private static final String SERVICE_CODE_COLUMN_DB = "SERVICE_CODE";
		
	
	private static final transient Logger logger = LoggerFactory.getLogger(JdbcServiceDao.class);
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	@Override
	public List<Service> getServicesByWorker() {		
		final String methodName = "getServicesByWorker() - ";
				
		final String querySql = "SELECT * FROM " +SERVICE_TABLE_NAME+ " WHERE "+SERVICE_CODE_COLUMN_DB +" LIKE 'SL%'";
		//SqlParameterSource namedParameters = new MapSqlParameterSource("description", description);

		try {
			return this.namedParameterJdbcTemplate.query(querySql, new ServiceMapper());				
		} catch (DataAccessException dae) {
			logger.error(methodName + "Data Access to the Database Error: " + dae);
			//throw dae;
		} catch (Throwable t) {
			logger.error(methodName +"Unexpected exception: " + t);
		}
		
		return null;
	}
	
	public static final class ServiceMapper implements RowMapper<Service> {
		public Service mapRow(ResultSet rs, int rowNum) throws SQLException {

			Service service = new Service();
			service.setServiceId(rs.getLong(SERVICE_ID_COLUMN_DB));
			service.setDescription(rs.getString(DESCRIPTION_COLUMN_DB));
			service.setServiceCode(rs.getString(SERVICE_CODE_COLUMN_DB));
			return service;
		}
	}
	
	// Injection
	public void setDataSource(final DataSource dataSource) {		
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
}