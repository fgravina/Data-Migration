package com.gigroup.data.migration.candidate.anagrafica.dao;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.gigroup.data.migration.candidate.anagrafica.Contact;
import com.gigroup.data.migration.candidate.util.DataMigrationException;

public class JdbcContactDao implements ContactDao {
	
	private static final String CONTACT_TABLE_NAME = "CONTACTS";
	private static final String CONTACT_ID_COLUMN_DB = "CONTACT_ID";
	private static final String CONTACT_VALUE_COLUMN_DB = "CONTACT_VALUE";
	private static final String CONTACT_TYPE_ID_COLUMN_DB = "CONTACT_TYPE_ID";	
	private static final String PERSON_ID_COLUMN_DB = "PERSON_ID";
	
	private static final transient Logger logger = LoggerFactory.getLogger(JdbcContactDao.class);

	private SimpleJdbcInsert insertContact;	

	public void addContact(final Contact contact) {
		final String methodName = "addContact() - ";
		if (logger.isDebugEnabled()) {
			logger.debug(methodName + "InputParams[contact=" + contact + "]");
		}
		
		SqlParameterSource parameters = new MapSqlParameterSource()				
				//.addValue(CONTACT_ID_COLUMN_DB, contact.getContactId())				
				.addValue(CREATION_TIMESTAMP_COLUMN_DB, contact.getCreationTimestamp())
				.addValue(CREATION_USER_COLUMN_DB, contact.getCreationUser())				
				.addValue(LAST_UPDATE_TIMESTAMP_COLUMN_DB, contact.getLastUpdateTimestamp())
				.addValue(LAST_UPDATE_USER_COLUMN_DB, contact.getLastUpdateUser())
				.addValue(CONTACT_VALUE_COLUMN_DB, contact.getValue())
				.addValue(CONTACT_TYPE_ID_COLUMN_DB, contact.getContactTypeId())				
				.addValue(PERSON_ID_COLUMN_DB, contact.getPersonId())
				.addValue(CREATION_USER_COLUMN_DB, LIFERAY_CREATION_USER);

		try {
			//this.insertPerson.setGeneratedKeyName(PERSON_ID_COLUMN_DB);
			this.insertContact.execute(parameters);
		} catch (DuplicateKeyException dke) {
			//logger.error(methodName +"Contact: " + contact + " is already inside database: " + dke);
			throw new DataMigrationException(methodName +"Contact: " + contact + " is already inside database: " + dke);
		} catch (Throwable t) {
			//logger.error(methodName +"Unexpected exception: " + t);
			throw new DataMigrationException(methodName +"Unexpected exception: " + t);
		}
	}
	
	// Injection
	public void setDataSource(final DataSource dataSource) {		
		this.insertContact = new SimpleJdbcInsert(dataSource).withTableName(CONTACT_TABLE_NAME).usingGeneratedKeyColumns(CONTACT_ID_COLUMN_DB);
	}	
}