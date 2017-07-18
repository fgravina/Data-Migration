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

import com.gigroup.data.migration.candidate.jobalert.IndustrySector;

public class JdbcIndustrySectorDao implements IndustrySectorDao {
	
	private static final String INDUSTRY_SECTOR_TABLE_NAME = "INDUSTRY_SECTORS";
	private static final String INDUSTRY_SECTOR_ID_COLUMN_DB = "INDUSTRY_SECTOR_ID";
	private static final String CODE_EXT_COLUMN_DB = "CODE_EXT";
	private static final String DESCRIPTION_COLUMN_DB = "DESCRIPTION";
	private static final String VALID_FROM_DATE_COLUMN_DB = "VALID_FROM_DATE";
	private static final String VALID_TO_DATE_COLUMN_DB = "VALID_TO_DATE";
	
	
	private static final transient Logger logger = LoggerFactory.getLogger(JdbcIndustrySectorDao.class);
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	@Override
	public List<IndustrySector> getAll() {		
		final String methodName = "getAll() - ";
					
		final String querySql = "SELECT * FROM " +INDUSTRY_SECTOR_TABLE_NAME;	

		try {
			return this.namedParameterJdbcTemplate.query(querySql, new IndustrySectorMapper());				
		} catch (DataAccessException dae) {
			logger.error(methodName + "Data Access to the Database Error: " + dae);
			//throw dae;
		} catch (Throwable t) {
			logger.error(methodName +"Unexpected exception: " + t);
		}
		
		return null;
	}
	
	public static final class IndustrySectorMapper implements RowMapper<IndustrySector> {
		public IndustrySector mapRow(ResultSet rs, int rowNum) throws SQLException {

			IndustrySector industrySector = new IndustrySector();
			industrySector.setIndustrySectorId(rs.getLong(INDUSTRY_SECTOR_ID_COLUMN_DB));
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