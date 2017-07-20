package com.gigroup.data.migration.candidate.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.util.StringUtils;

import com.gigroup.data.migration.candidate.NaamahUser;
import com.gigroup.data.migration.candidate.util.DataMigrationException;
import com.gigroup.data.migration.candidate.util.LoginNotFoundException;

public class JdbcNaamahUserDao implements NaamahUserDao {
	
	private static final String NAAMAH_USER_TABLE_NAME = "AS_USER";
	private static final String USER_ID_COLUMN_DB = "USID";
	private static final String CHMP_COLUMN_DB = "CHMP";	
	private static final String MAIL_COLUMN_DB = "MAIL";
	private static final String PASW_COLUMN_DB = "PASW";
	private static final String ENID_COLUMN_DB = "ENID";
	private static final String GRID_COLUMN_DB = "GRID";
	private static final String WORK_COLUMN_DB = "WORK";
	private static final String LOGI_COLUMN_DB = "LOGI";
	private static final String NAME_COLUMN_DB = "NAME";
	private static final String OTHE_COLUMN_DB = "OTHE";
	private static final String NTEL_COLUMN_DB = "NTEL";
	private static final String TYPE_COLUMN_DB = "type";
	private static final String FAILED_LOGIN_COLUMN_DB = "FAILED_LOGIN";
	private static final String FORCE_CHANGE_COLUMN_DB = "FORCE_CHANGE";
	private static final String HASH_PASSWORD_COLUMN_DB = "HASH_PASSWORD";
	private static final String LAST_LOGIN_COLUMN_DB = "LAST_LOGIN";
	private static final String SALT_COLUMN_DB = "SALT";
	private static final String USER_STATE_ID_COLUMN_DB = "USER_STATE_ID";
	
	private static final transient Logger logger = LoggerFactory.getLogger(JdbcNaamahUserDao.class);
	
	private SimpleJdbcInsert insertNaamahUser;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	private static Long userIdCounter = 40L;

	@Override
	public Long addNaamahUser(NaamahUser naamahUser) {
		final String methodName = "addNaamahUser() - ";
		if (logger.isDebugEnabled()) {
			logger.debug(methodName + "InputParams[naamahUser=" + naamahUser + "]");
		}
		
		Long userId = naamahUser.getUserId();
		
		try {
			if(userId == null){				
				userId = this.nextLongValue();
				//userId = userIdCounter++;				
			}			
			
			SqlParameterSource parameters = new MapSqlParameterSource()				
					.addValue(CHMP_COLUMN_DB, naamahUser.getCustomHomepage())
					.addValue(MAIL_COLUMN_DB, StringUtils.hasText(naamahUser.getEmail()) && naamahUser.getEmail().length() > 100 ? naamahUser.getEmail().substring(0, 99) : naamahUser.getEmail())
					.addValue(PASW_COLUMN_DB, naamahUser.getPassword())
					.addValue(ENID_COLUMN_DB, naamahUser.getEnvironmentId() > 0 ? naamahUser.getEnvironmentId() : 1)
					.addValue(GRID_COLUMN_DB, naamahUser.getGroupId() >  0 ? naamahUser.getGroupId() : 6)
					.addValue(WORK_COLUMN_DB, naamahUser.getJobDescription())
					.addValue(LOGI_COLUMN_DB, StringUtils.hasText(naamahUser.getLogin()) && naamahUser.getLogin().length() > 40 ? naamahUser.getLogin().substring(0, 39) : naamahUser.getLogin())
					.addValue(NAME_COLUMN_DB, StringUtils.hasText(naamahUser.getName()) && naamahUser.getName().length() > 40 ? naamahUser.getName().substring(0, 39) : naamahUser.getName())
					.addValue(OTHE_COLUMN_DB, naamahUser.getOther())
					.addValue(NTEL_COLUMN_DB, naamahUser.getTelephoneNumber())					
					.addValue(TYPE_COLUMN_DB, 0)
					.addValue(FAILED_LOGIN_COLUMN_DB, naamahUser.getFailedLogin())
					.addValue(FORCE_CHANGE_COLUMN_DB, naamahUser.getForceChange())
					.addValue(HASH_PASSWORD_COLUMN_DB, naamahUser.getHashPassword())
					.addValue(LAST_LOGIN_COLUMN_DB, naamahUser.getLastLogin())
					.addValue(SALT_COLUMN_DB, naamahUser.getSalt())							
					//.addValue(USER_STATE_ID_COLUMN_DB, naamahUser.getUserState());
					.addValue(USER_STATE_ID_COLUMN_DB, 0);								
			this.insertNaamahUser.execute(parameters);
		} catch (DuplicateKeyException dke) {
			//logger.error(methodName +"Person with email [" + naamahUser.getEmail() + "] is already inside database: " + dke);
			throw new DataMigrationException(methodName +"Person with email [" + naamahUser.getEmail() + "] is already inside database: " + dke);
		} catch (Throwable t) {
			//logger.error(methodName +"Unexpected exception: " + t);
			throw new DataMigrationException(methodName +"Unexpected exception: " + t);
		}
		
		return userId;
	}
	
	private synchronized Long nextLongValue() {
		final String methodName = "nextLongValue() - ";
		
		try{
			String sqlCount = "SELECT MAX("+USER_ID_COLUMN_DB+") + 1 FROM "+ NAAMAH_USER_TABLE_NAME;
			Long nextValue = this.insertNaamahUser.getJdbcTemplate().queryForObject(sqlCount, Long.class);
			return nextValue != null ? nextValue : 1L;
		} catch (EmptyResultDataAccessException ex) {
			logger.warn(methodName+"The table "+NAAMAH_USER_TABLE_NAME+" is EMPTY" +ex);
			return 1L;		
		} catch (DataAccessException dae) {
			logger.error(methodName+"Data Access to the Database Error: " +dae);
			throw new RuntimeException(dae);
		}		
	}
	
	
	@Override
	public NaamahUser getNaamahUserByLogin(final String login) throws LoginNotFoundException {
		final String methodName = "getNaamahUserByLogin() - ";
		if (logger.isDebugEnabled()) {logger.debug(methodName + "InputParams[login=" + login +"]");}
		
		final String querySql = "SELECT TOP 1 * FROM "+ NAAMAH_USER_TABLE_NAME + " WHERE UPPER("+LOGI_COLUMN_DB+") = :login";		
		SqlParameterSource namedParameters = new MapSqlParameterSource("login", login.toUpperCase());
		
		try {
			return this.namedParameterJdbcTemplate.queryForObject(querySql, namedParameters, new NaamahUserMapper());
		} catch (EmptyResultDataAccessException ex) {
			//logger.warn(methodName+"NaamahUser with login [" + login + "] cannot be found inside database: " +ex);
			throw new LoginNotFoundException("NaamahUser with login [" + login + "] cannot be found inside database.");			
		} catch (DataAccessException dae) {
			logger.error(methodName+"Data Access to the Database Error: " +dae);						
			throw dae;			
		}		
	}
	
	private static final class NaamahUserMapper implements RowMapper<NaamahUser> {		
		public NaamahUser mapRow(ResultSet rs, int rowNum) throws SQLException {
					
			NaamahUser naamahUser = new NaamahUser();
			naamahUser.setUserId(rs.getLong(USER_ID_COLUMN_DB));				
			naamahUser.setEmail(rs.getString(MAIL_COLUMN_DB));			
			naamahUser.setLogin(rs.getString(LOGI_COLUMN_DB));			
			naamahUser.setName(rs.getString(NAME_COLUMN_DB));			
						
			return naamahUser;
		}		
	}
	
	// Injection
	public void setDataSource(final DataSource dataSource) {		
		this.insertNaamahUser = new SimpleJdbcInsert(dataSource).withTableName(NAAMAH_USER_TABLE_NAME).usingGeneratedKeyColumns(USER_ID_COLUMN_DB);
		this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}
}