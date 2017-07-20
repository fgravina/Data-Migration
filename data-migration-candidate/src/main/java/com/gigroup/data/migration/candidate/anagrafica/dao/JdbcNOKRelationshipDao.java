package com.gigroup.data.migration.candidate.anagrafica.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.gigroup.data.migration.candidate.NOKRelationship;

public class JdbcNOKRelationshipDao implements NOKRelationshipDao {
	
	private static final String NOK_RELATIONSHIP_TABLE_NAME = "NEXT_OF_KIN_RELATIONSHIPS";
	private static final String NOK_RELATIONSHIP_ID_COLUMN_DB = "NEXT_OF_KIN_RELATIONSHIP_ID";
	private static final String NOK_RELATIONSHIP_DESCRIPTION_COLUMN_DB = "DESCRIPTION";	
	
	private static final transient Logger logger = LoggerFactory.getLogger(JdbcNOKRelationshipDao.class);
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Override
	public NOKRelationship getNokRelationshipByDescription(final String nokRelationshipdescription) throws Exception {
		final String methodName = "getNokRelationshipByDescription() - ";
		if (logger.isDebugEnabled()) {logger.debug(methodName + "InputParams[description = " + nokRelationshipdescription +"]");}
		
		final String querySql = "SELECT TOP 1 * FROM "+ NOK_RELATIONSHIP_TABLE_NAME + " WHERE UPPER("+NOK_RELATIONSHIP_DESCRIPTION_COLUMN_DB+") = :description";		
		SqlParameterSource namedParameters = new MapSqlParameterSource("description", nokRelationshipdescription.toUpperCase());
		
		try {
			return this.namedParameterJdbcTemplate.queryForObject(querySql, namedParameters, new NOKRelationshipMapper());
		} catch (EmptyResultDataAccessException ex) {
			throw new Exception("NOKRelationship with description [" + nokRelationshipdescription + "] cannot be found inside database.");			
		} catch (DataAccessException dae) {
			logger.error(methodName + "Data Access to the Database Error: " + dae);						
			throw dae;			
		}		
	}
	
	private static final class NOKRelationshipMapper implements RowMapper<NOKRelationship> {		
		public NOKRelationship mapRow(ResultSet rs, int rowNum) throws SQLException {
					
			NOKRelationship nokRelationship = new NOKRelationship();
			nokRelationship.setNokRelationshipId(rs.getLong(NOK_RELATIONSHIP_ID_COLUMN_DB));				
			nokRelationship.setDescription(rs.getString(NOK_RELATIONSHIP_DESCRIPTION_COLUMN_DB));			
						
			return nokRelationship;
		}		
	}

	// Injection
	public void setDataSource(final DataSource dataSource) {		
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

}