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

import com.gigroup.data.migration.candidate.jobalert.ProfessionalArea;

public class JdbcProfessionalAreaDao implements ProfessionalAreaDao {
	
	private static final String PROFESSIONAL_AREA_TABLE_NAME = "PROFESSIONAL_AREAS";
	private static final String PROFESSIONAL_AREA_ID_COLUMN_DB = "PROFESSIONAL_AREA_ID";
	private static final String CODE_EXT_COLUMN_DB = "CODE_EXT";
	private static final String DESCRIPTION_COLUMN_DB = "DESCRIPTION";
	private static final String VALID_FROM_DATE_COLUMN_DB = "VALID_FROM_DATE";
	private static final String VALID_TO_DATE_COLUMN_DB = "VALID_TO_DATE";
	
	
	private static final transient Logger logger = LoggerFactory.getLogger(JdbcProfessionalAreaDao.class);
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;


	@Override
	public List<ProfessionalArea> getAll() {		
		final String methodName = "getAll() - ";
				
		final String querySql = "SELECT * FROM " +PROFESSIONAL_AREA_TABLE_NAME;	

		try {
			return this.namedParameterJdbcTemplate.query(querySql, new ProfessionalAreaMapper());				
		} catch (DataAccessException dae) {
			logger.error(methodName + "Data Access to the Database Error: " + dae);
			//throw dae;
		} catch (Throwable t) {
			logger.error(methodName +"Unexpected exception: " + t);
		}
		
		return null;
	}
	
	public static final class ProfessionalAreaMapper implements RowMapper<ProfessionalArea> {
		public ProfessionalArea mapRow(ResultSet rs, int rowNum) throws SQLException {

			ProfessionalArea professionalArea = new ProfessionalArea();
			professionalArea.setProfessionalAreaId(rs.getLong(PROFESSIONAL_AREA_ID_COLUMN_DB));
			professionalArea.setCodeExternal(rs.getString(CODE_EXT_COLUMN_DB));
			professionalArea.setDescription(rs.getString(DESCRIPTION_COLUMN_DB));
			professionalArea.setValidFromDate(rs.getTimestamp(VALID_FROM_DATE_COLUMN_DB));
			professionalArea.setValidToDate(rs.getTimestamp(VALID_TO_DATE_COLUMN_DB));
			return professionalArea;
		}
	}
	
	// Injection
	public void setDataSource(final DataSource dataSource) {		
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}	
}