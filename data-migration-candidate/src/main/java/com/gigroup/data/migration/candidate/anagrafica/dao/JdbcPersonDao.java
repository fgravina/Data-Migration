package com.gigroup.data.migration.candidate.anagrafica.dao;

import java.sql.Types;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.gigroup.data.migration.candidate.anagrafica.Person;
import com.gigroup.data.migration.candidate.util.DataMigrationException;


public class JdbcPersonDao implements PersonDao {
	
	private static final String PERSON_TABLE_NAME = "PEOPLE";
	private static final String PERSON_ID_COLUMN_DB = "PERSON_ID";
	private static final String BIRTHDAY_COLUMN_DB = "BIRTHDAY";
	private static final String COUNTRY_CODE_COLUMN_DB = "COUNTRY_CODE";	
	private static final String DISPLAYNAME_COLUMN_DB = "DISPLAYNAME";	
	private static final String EMAIL_COLUMN_DB = "EMAIL";
	private static final String GENDER_COLUMN_DB = "GENDER";
	private static final String LASTNAME_COLUMN_DB = "LASTNAME";
	private static final String LOGINNAME_COLUMN_DB = "LOGINNAME";	
	private static final String FIRSTNAME_COLUMN_DB = "NAME";	
	private static final String PLACE_OF_BIRTH_COLUMN_DB = "PLACE_OF_BIRTH";
	private static final String NATIONALITY_CODE = "NATIONALITY_CODE";
	
	
	private static final transient Logger logger = LoggerFactory.getLogger(JdbcPersonDao.class);

	private SimpleJdbcInsert insertPerson;	

	public void addPerson(Person person) {
		final String methodName = "addPerson() - ";
		if (logger.isDebugEnabled()) {
			logger.debug(methodName + "InputParams[person=" + person + "]");
		}
		
		SqlParameterSource parameters = new MapSqlParameterSource()				
				.addValue(PERSON_ID_COLUMN_DB, person.getPersonId())
				.addValue(BIRTHDAY_COLUMN_DB, person.getBirthday())
				.addValue(COUNTRY_CODE_COLUMN_DB, person.getCountryCode())
				.addValue(DISPLAYNAME_COLUMN_DB, person.getFirstname() + " " + person.getLastname())
				.addValue(EMAIL_COLUMN_DB, person.getEmail())
				.addValue(GENDER_COLUMN_DB, person.getGender())
				.addValue(LASTNAME_COLUMN_DB, person.getLastname())
				.addValue(LOGINNAME_COLUMN_DB, person.getLoginname())
				.addValue(FIRSTNAME_COLUMN_DB, person.getFirstname())				
				.addValue(PLACE_OF_BIRTH_COLUMN_DB, person.getPlaceOfBirth())				
				.addValue(CREATION_TIMESTAMP_COLUMN_DB, person.getCreationTimestamp(), Types.TIMESTAMP)
				.addValue(CREATION_USER_COLUMN_DB, person.getCreationUser())
				.addValue(LAST_UPDATE_TIMESTAMP_COLUMN_DB, person.getLastUpdateTimestamp(), Types.TIMESTAMP)
				.addValue(LAST_UPDATE_USER_COLUMN_DB, person.getLastUpdateUser())
				.addValue(NATIONALITY_CODE, person.getNationalityCode())
				.addValue(CREATION_USER_COLUMN_DB, LIFERAY_CREATION_USER);

		try {			
			this.insertPerson.execute(parameters);
		} catch (DuplicateKeyException dke) {
			//logger.error(methodName +"Person with email [" + person.getEmail() + "] is already inside database: " + dke);
			throw new DataMigrationException(methodName +"Person with email [" + person.getEmail() + "] is already inside database: " + dke);			
		} catch (Throwable t) {
			//logger.error(methodName +"Unexpected exception: " + t);
			throw new DataMigrationException(methodName +"Unexpected exception: " + t);
		}
	}
	
	// Injection
	public void setDataSource(final DataSource dataSource) {		
		this.insertPerson = new SimpleJdbcInsert(dataSource).withTableName(PERSON_TABLE_NAME);	
	}	
}