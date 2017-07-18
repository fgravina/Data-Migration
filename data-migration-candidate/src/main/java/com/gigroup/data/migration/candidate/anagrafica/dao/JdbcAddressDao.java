package com.gigroup.data.migration.candidate.anagrafica.dao;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import com.gigroup.data.migration.candidate.anagrafica.Address;
import com.gigroup.data.migration.candidate.util.DataMigrationException;

public class JdbcAddressDao implements AddressDao {
	
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
	
	private static final transient Logger logger = LoggerFactory.getLogger(JdbcAddressDao.class);

	private SimpleJdbcInsert insertAddress;	

	public void addAddress(final Address address) {
		final String methodName = "addAddress() - ";
		if (logger.isDebugEnabled()) {
			logger.debug(methodName + "InputParams[address=" + address + "]");
		}
		
		SqlParameterSource parameters = new MapSqlParameterSource()				
				//.addValue(ADDRESS_ID_COLUMN_DB, address.getAddressId())
				.addValue(ADDRESS_COLUMN_DB, address.getAddress())
				.addValue(CITY_COLUMN_DB, address.getCity())
				.addValue(CREATION_TIMESTAMP_COLUMN_DB, address.getCreationTimestamp())
				.addValue(CREATION_USER_COLUMN_DB, address.getCreationUser())
				.addValue(DUG_COLUMN_DB, address.getDug())
				.addValue(LAST_UPDATE_TIMESTAMP_COLUMN_DB, address.getLastUpdateTimestamp())
				.addValue(LAST_UPDATE_USER_COLUMN_DB, address.getLastUpdateUser())
				.addValue(NUMBER_COLUMN_DB, address.getNumber())
				.addValue(POSTAL_CODE_COLUMN_DB, address.getPostalCode())
				.addValue(PROVINCE_COLUMN_DB, address.getProvince())
				.addValue(ADDRESS_TYPE_ID_COLUMN_DB, address.getAddressTypeId())
				.addValue(COUNTRY_CODE_COLUMN_DB, address.getCountryCode())
				.addValue(PERSON_ID_COLUMN_DB, address.getPersonId())
				.addValue(CREATION_USER_COLUMN_DB, LIFERAY_CREATION_USER);

		try {			
			this.insertAddress.execute(parameters);
		} catch (DuplicateKeyException dke) {
			//logger.error(methodName +"Address: " + address + " is already inside database: " + dke);
			throw new DataMigrationException(methodName +"Address: " + address + " is already inside database: " + dke);
		} catch (Throwable t) {
			//logger.error(methodName +"Unexpected exception: " + t);
			throw new DataMigrationException(methodName +"Unexpected exception: " + t);
		}
	}
	
	// Injection
	public void setDataSource(final DataSource dataSource) {		
		this.insertAddress = new SimpleJdbcInsert(dataSource).withTableName(ADDRESS_TABLE_NAME).usingGeneratedKeyColumns(ADDRESS_ID_COLUMN_DB);
	}	
}