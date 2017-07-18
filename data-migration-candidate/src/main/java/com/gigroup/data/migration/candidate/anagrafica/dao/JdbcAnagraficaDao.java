package com.gigroup.data.migration.candidate.anagrafica.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.util.StringUtils;

import com.gigroup.data.migration.candidate.NaamahUser;
import com.gigroup.data.migration.candidate.anagrafica.Address;
import com.gigroup.data.migration.candidate.anagrafica.Contact;
import com.gigroup.data.migration.candidate.anagrafica.Person;
import com.gigroup.data.migration.candidate.util.DataMigrationException;

public class JdbcAnagraficaDao implements AnagraficaDao {
	
	private static final String NAAMAH_USER_TABLE_NAME = "AS_USER";
	private static final String USER_ID_COLUMN_DB = "USID";
	
	private static final String ADDRESS_TABLE_NAME = "ADDRESSES";
	private static final String ADDRESS_ID_COLUMN_DB = "ADDRESS_ID";
	private static final String ADDRESS_COLUMN_DB = "ADDRESS";
	private static final String CITY_COLUMN_DB = "CITY";
	private static final String DUG_COLUMN_DB = "DUG";
	private static final String NUMBER_COLUMN_DB = "NUMBER";
	private static final String POSTAL_CODE_COLUMN_DB = "POSTAL_CODE";
	private static final String PROVINCE_COLUMN_DB = "PROVINCE";
	private static final String ADDRESS_TYPE_ID_COLUMN_DB = "ADDRESS_TYPE_ID";
	private static final String COUNTRY_CODE_COLUMN_DB = "COUNTRY_CODE";
	private static final String PERSON_ID_COLUMN_DB = "PERSON_ID";
	
	private static final transient Logger logger = LoggerFactory.getLogger(JdbcAnagraficaDao.class);

	private JdbcTemplate jdbcTemplate;	

	@Override
	public void addAnagrafica(List<NaamahUser> naamahUserList, List<Person> personList, List<Contact> contactList) {
		final String methodName = "addAddress() - ";
		if (logger.isDebugEnabled()) {
			logger.debug(methodName + "InputParams[personList=" + personList + "]");
		}
		
		//SqlParameterSource parameters = new MapSqlParameterSource()				
				//.addValue(ADDRESS_ID_COLUMN_DB, address.getAddressId())
				

		try {			
			//this.insertAddress.execute(parameters);
			
			Long userId = this.nextLongValue()-1;
			
			String naamahUserSql = "INSERT INTO AS_USER(USID, CHMP, MAIL, PASW, ENID, GRID, WORK, LOGI, NAME, OTHE, NTEL, type, FAILED_LOGIN, FORCE_CHANGE, HASH_PASSWORD, LAST_LOGIN, SALT, USER_STATE_ID) " 
			+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?); ";
			
			String personSql = "INSERT INTO PEOPLE(PERSON_ID, BIRTHDAY, COUNTRY_CODE, DISPLAYNAME, EMAIL, GENDER, LASTNAME, LOGINNAME, NAME, PLACE_OF_BIRTH) " 
					+ " values(?,?,?,?,?,?,?,?,?,?); ";
			
			String sqlMultiTable = naamahUserSql +
						 personSql;

				int[] arr = jdbcTemplate.batchUpdate(sqlMultiTable, new BatchPreparedStatementSetter() {

				    @Override
				    public void setValues(PreparedStatement ps, int i) throws SQLException {
				    	
				    	// Increment userId
				    	Long currentUserId = userId +1L;
				    	
				    	NaamahUser naamahUser = naamahUserList.get(i);
				    	
				    	ps.setLong(1, currentUserId);				    	
				    	ps.setString(2, naamahUser.getCustomHomepage());
				    	ps.setString(3, StringUtils.hasText(naamahUser.getEmail()) && naamahUser.getEmail().length() > 100 ? naamahUser.getEmail().substring(0, 99) : naamahUser.getEmail());
				    	ps.setString(4,naamahUser.getPassword());
				    	ps.setInt(5,1);
				    	ps.setInt(6, 6);
				    	ps.setString(7,naamahUser.getJobDescription());
				    	ps.setString(8,StringUtils.hasText(naamahUser.getLogin()) && naamahUser.getLogin().length() > 40 ? naamahUser.getLogin().substring(0, 39) : naamahUser.getLogin());
				    	ps.setString(9,StringUtils.hasText(naamahUser.getName()) && naamahUser.getName().length() > 40 ? naamahUser.getName().substring(0, 39) : naamahUser.getName());
				    	ps.setString(10,naamahUser.getOther());
				    	ps.setString(11,naamahUser.getTelephoneNumber());					
				    	ps.setInt(12, 0);
				    	ps.setInt(13,naamahUser.getFailedLogin());
				    	ps.setBoolean(14,naamahUser.getForceChange());
				    	ps.setString(15,null);
				    	ps.setDate(16, null);
				    	ps.setString(17, null);				    	
				    	ps.setInt(18,0);		
				    	
				        Person person = personList.get(i);
				        Contact contact = contactList.get(i);
				        ps.setString(1, person.getFirstname());
				       
				        ps.setString(2, contact.getValue());
				    }

				    @Override
				    public int getBatchSize() {
				        return naamahUserList.size();
				    }
				});
			
		} catch (DuplicateKeyException dke) {
			//logger.error(methodName +"Address: " + address + " is already inside database: " + dke);
			//throw new DataMigrationException(methodName +"Address: " + address + " is already inside database: " + dke);
		} catch (Throwable t) {
			//logger.error(methodName +"Unexpected exception: " + t);
			//throw new DataMigrationException(methodName +"Unexpected exception: " + t);
		}
	}
	
	
	private synchronized Long nextLongValue() {
		final String methodName = "nextLongValue() - ";
		
		try{
			String sqlCount = "SELECT MAX("+USER_ID_COLUMN_DB+") + 1 FROM "+ NAAMAH_USER_TABLE_NAME;
			Long nextValue = this.jdbcTemplate.queryForObject(sqlCount, Long.class);
			return nextValue != null ? nextValue : 1L;
		} catch (EmptyResultDataAccessException ex) {
			logger.warn(methodName+"The table "+NAAMAH_USER_TABLE_NAME+" is EMPTY" +ex);
			return 1L;		
		} catch (DataAccessException dae) {
			logger.error(methodName+"Data Access to the Database Error: " +dae);
			throw new RuntimeException(dae);
		}		
	}
	
	
	// Injection
//	public void setDataSource(final DataSource dataSource) {		
//		this.insertAddress = new SimpleJdbcInsert(dataSource).withTableName(ADDRESS_TABLE_NAME).usingGeneratedKeyColumns(ADDRESS_ID_COLUMN_DB);
//	}	
	
	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
}