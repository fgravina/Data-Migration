package com.gigroup.data.migration.candidate.parser.anagrafica;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.gigroup.data.migration.candidate.NaamahUser;
import com.gigroup.data.migration.candidate.anagrafica.Address;
import com.gigroup.data.migration.candidate.anagrafica.Contact;
import com.gigroup.data.migration.candidate.anagrafica.GbrProfile;
import com.gigroup.data.migration.candidate.anagrafica.Person;
import com.gigroup.data.migration.candidate.anagrafica.PersonCountryBusinessRole;
import com.gigroup.data.migration.candidate.anagrafica.dao.AddressDao;
import com.gigroup.data.migration.candidate.anagrafica.dao.ContactDao;
import com.gigroup.data.migration.candidate.anagrafica.dao.GbrProfileDao;
import com.gigroup.data.migration.candidate.anagrafica.dao.NOKRelationshipDao;
import com.gigroup.data.migration.candidate.anagrafica.dao.PersonCountryBusinessRoleDao;
import com.gigroup.data.migration.candidate.anagrafica.dao.PersonDao;
import com.gigroup.data.migration.candidate.dao.NaamahUserDao;
import com.gigroup.data.migration.candidate.util.CountryUtils;
import com.gigroup.data.migration.candidate.util.DataMigrationException;
import com.gigroup.data.migration.candidate.util.DateUtils;
import com.gigroup.data.migration.candidate.util.FileRecoveryUtils;
import com.gigroup.data.migration.candidate.util.LoginNotFoundException;
import com.gigroup.data.migration.candidate.util.SpringApplicationContext;

public class AnagraficaParserUK {

	private static final transient Logger logger = LoggerFactory.getLogger(AnagraficaParserUK.class);
	
	public static void parseFileToDatabase(final String filename) {
		final String methodName = "parseFileToDatabase() - ";		
		logger.info(methodName + "filename: " + filename);	

		String otherThanQuote = " [^\"] ";
		String quotedString = String.format(" \" %s* \" ", otherThanQuote);
		String regex = String.format(
				"(?x) " + // enable comments, ignore white spaces
						";                         " + // match a comma
						"(?=                       " + // start positive look ahead
						"  (?:                     " + // start non-capturing group 1
						"    %s*                   " + // match 'otherThanQuote' zero or more times
						"    %s                    " + // match 'quotedString'
						"  )*                      " + // end group 1 and repeat it zero or more times
						"  %s*                     " + // match 'otherThanQuote'
						"  $                       " + // match the end of the string
						")                         ", // stop positive look
														// ahead
				otherThanQuote, quotedString, otherThanQuote);

		String line = null;
		BufferedReader reader = null;

		String filenameRecovery = "Anagrafica_Recovery_" + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()) + ".csv";

		Instant start = Instant.now();
		
		boolean isError = false;
		long counterTotal = 0L;
		long counterSuccess = 0L;
		long counterError = 0L;
		long counterPerson = 0L;
		long counterGbrProfile = 0L;
		long counterPersonCountryBusinessRole = 0L;
		long counterAddress = 0L;
		long counterContact = 0L;
		
		try {

			Path filenamePath = Paths.get(filename);
			reader = Files.newBufferedReader(filenamePath);

			start = Instant.now();
			if (logger.isDebugEnabled()) {
				logger.debug(methodName + "Start : " + start.toString());
			}			
			
			while (reader.ready()) {
				counterTotal++;
				line = reader.readLine().trim();
				try {
					logger.debug(methodName + "Current line from CSV: " + line);

					if (!line.isEmpty() && line.split(regex).length > 1) {
						String[] fields = line.split(regex);

						LiferayDataAnagraficaCsvUK liferayData = new LiferayDataAnagraficaCsvUK(fields);
						logger.debug("Current LiferayDataAnagraficaCsvUK: " + liferayData);

						// STEP ONE
						Long userId = addNaamahUser(liferayData);
						logger.debug("Create userId: " + userId);

						// STEP TWO
						try {
							addPerson(userId, liferayData);
							counterPerson++;
						} catch (DataMigrationException dte) {
							logger.error(methodName + "Error during parsing following line: " + line + " - caused by " + dte.getMessage(), dte);
							isError = true;
						}

						// STEP THREE
						try {
							addGbrProfile(userId, liferayData);
							counterGbrProfile++;
						} catch (DataMigrationException dte) {
							logger.error(methodName + "Error during parsing following line: " + line + " - caused by " + dte.getMessage(), dte);
							isError = true;
						}

						// STEP FOUR
						try {
							addPersonCountryBusinessRole(userId, liferayData);
							counterPersonCountryBusinessRole++;
						} catch (DataMigrationException dte) {
							logger.error(methodName + "Error during parsing following line: " + line + " - caused by " + dte.getMessage(), dte);
							isError = true;
						}

						// STEP FIVE
						try {
							addAddresses(userId, liferayData);
							counterAddress++;
						} catch (DataMigrationException dte) {
							logger.error(methodName + "Error during parsing following line: " + line + " - caused by " + dte.getMessage(), dte);
							isError = true;
						}

						// STEP SIX
						try {
							addContacts(userId, liferayData);
							counterContact++;
						} catch (DataMigrationException dte) {
							logger.error(methodName + "Error during parsing following line: " + line + " - caused by " + dte.getMessage(), dte);
							isError = true;
						}
						if(isError){
							FileRecoveryUtils.createDumpFileRecovery(line, filenameRecovery);
							isError = false;
						}
						
						counterSuccess++;
					}
				} catch (DataMigrationException dte) {
					logger.error(methodName + "Error during parsing following line: " + line + " - caused by " + dte.getMessage(), dte);
					FileRecoveryUtils.createDumpFileRecovery(line, filenameRecovery);
					counterError++;
					counterTotal++;
				}
				line = null;

			} // end while

		} catch (Throwable t) {
			logger.error(methodName + "Unexcepted exception at line: " + line + " - caused by " + t.getMessage(), t);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException ioe) {
					logger.error(methodName + "Unexcepted exception: " + ioe);
				}
			}
		}
		
		logger.info("**************************************************");
		logger.info("***************** CONTATORI **********************");
		logger.info("**************************************************");
		logger.info("counterTotal : " + counterTotal);
		logger.info("counterSuccess : " + counterSuccess);
		logger.info("counterError : " + counterError);
		logger.info("counterPerson : " + counterPerson);
		logger.info("counterAddress : " + counterAddress);
		logger.info("counterContact : " + counterContact);
		logger.info("counterGbrProfile : " + counterGbrProfile);
		logger.info("counterPersonCountryBusinessRole : " + counterPersonCountryBusinessRole);
		logger.info("**************************************************");
		logger.info("");
		logger.info("");
		Instant end = Instant.now();
		logger.info("**************************************************");
		logger.info("*************** TEMPO IMPIEGATO ******************");
		logger.info("**************************************************");
		logger.info("Start : " + start.toString());
		logger.info("End : " + end.toString());
		logger.info("Total time : " + ((end.toEpochMilli() - start.toEpochMilli())) + " millisec");
		logger.info("**************************************************");
	}

	private static Long addNaamahUser(final LiferayDataAnagraficaCsvUK liferayData) {
		NaamahUserDao naamahUserDao = (NaamahUserDao) SpringApplicationContext.INSTANCE.getBean(NaamahUserDao.BEAN_NAME);
		try {
			// Check naamahUser is already exist
			if (naamahUserDao.getNaamahUserByLogin(liferayData.getCnEmailAddress()) != null) {
				throw new DataMigrationException("NaamahUser with login[" + liferayData.getCnEmailAddress() + "] is already exist.");
			}
		} catch (LoginNotFoundException lnfe) {
			// ignored because it's possible add new user in Girex
		}		
		// Fill naamahUser bean
		NaamahUser naamahUser = new NaamahUser(liferayData.getCnFirstName() + " " + liferayData.getCnLastName(), liferayData.getCnEmailAddress(), liferayData.getCnEmailAddress());
		// Add NaamahUser
		return naamahUserDao.addNaamahUser(naamahUser);
	}
	
	private static void addPerson(final Long personId, final LiferayDataAnagraficaCsvUK liferayData) {

		// Fill Person bean
		Person person = new Person();
		person.setPersonId(personId);
		person.setFirstname(liferayData.getCnFirstName());
		person.setLastname(liferayData.getCnLastName());
		person.setEmail(liferayData.getCnEmailAddress());
		person.setLoginname(liferayData.getCnEmailAddress());
		person.setBirthday(DateUtils.stringToDate(liferayData.getCnBirthDate()));
		person.setPlaceOfBirth(liferayData.getCnBirthPlace());
		person.setGender(liferayData.getCnGender());
		// Remap from local to english language
		person.setCountryCode(CountryUtils.descriptionToIso3(liferayData.getCnBirthCountry()));

		// Add NaamahUser
		PersonDao personDao = (PersonDao) SpringApplicationContext.INSTANCE.getBean(PersonDao.BEAN_NAME);
		personDao.addPerson(person);
	}
	
	private static void addGbrProfile(Long personId, LiferayDataAnagraficaCsvUK liferayData){
		GbrProfile gbrProfile = new GbrProfile();
		gbrProfile.setPersonId(personId);
		gbrProfile.setCollaborationPortalID( ((liferayData.getCnCollaborationPortalId() != null) && (!liferayData.getCnCollaborationPortalId().isEmpty())) ? Long.parseLong(liferayData.getCnCollaborationPortalId()) : null );
		gbrProfile.setTaxCode(liferayData.getCnCollaborationPortalId());
		gbrProfile.setNationality(liferayData.getCnNationality());
		gbrProfile.setNation(liferayData.getCnNation());
		gbrProfile.setNokName(liferayData.getCnNOKName());
		gbrProfile.setNokContactNumber(liferayData.getCnNOKContactNumber());
		// keep id for NOKRelationship, 'cause we receive description and id needed 
		gbrProfile.setNokReleationship(getId4NOKDescription(liferayData.getCnNOKRelationship()));
		gbrProfile.setStatus(liferayData.getCnStatus());
		// Add NaamahUser
		GbrProfileDao gbrProfileDao = (GbrProfileDao) SpringApplicationContext.INSTANCE.getBean(GbrProfileDao.BEAN_NAME);
		gbrProfileDao.addGbrProfile(gbrProfile);
	}
	
	private static Long getId4NOKDescription(String cnNOKRelationship){
		Long nokRelationshipId = null;
		NOKRelationshipDao nokRelationshipDao = (NOKRelationshipDao) SpringApplicationContext.INSTANCE.getBean(NOKRelationshipDao.BEAN_NAME);
		try {
			if ((cnNOKRelationship != null) && (!cnNOKRelationship.isEmpty())) {
				nokRelationshipId = nokRelationshipDao.getNokRelationshipByDescription(cnNOKRelationship).getNokRelationshipId();
			}
		} catch (Exception e) {
			logger.error("Data Access Error: " + e);	
		}		
		return nokRelationshipId;
	}

	private static void addAddresses(Long personId, LiferayDataAnagraficaCsvUK liferayData) {

		addAddress(personId, liferayData.getCnDomAddress(), liferayData.getCnDomTown(), liferayData.getCnDomZipCode(), CountryUtils.descriptionToIso3(liferayData.getCnDomCountryDesc()),
				Address.DOMICILE_ADDRESS_TYPE_ID);

		if ("Y".equalsIgnoreCase(liferayData.getCnResComeDom())) {
			addAddress(personId, liferayData.getCnDomAddress(), liferayData.getCnDomTown(), liferayData.getCnDomZipCode(), CountryUtils.descriptionToIso3(liferayData.getCnDomCountryDesc()),
					Address.RESIDENCE_ADDRESS_TYPE_ID);
		} else {
			addAddress(personId, liferayData.getCnResAddress(), liferayData.getCnResTown(), liferayData.getCnResZipCode(), CountryUtils.descriptionToIso3(liferayData.getCnResCountryDesc()),
					Address.RESIDENCE_ADDRESS_TYPE_ID);
		}
	}

	private static void addAddress(Long personId, String address, String city, String postalCode, String countryCode, Long addressTypeId) {

		if (StringUtils.hasText(address) || StringUtils.hasText(city) || StringUtils.hasText(postalCode) || StringUtils.hasText(countryCode)) {
			Address addressBean = new Address();
			addressBean.setAddress(StringUtils.hasText(address) ? address.replace("\"", "") : "");
			addressBean.setCity(city);
			addressBean.setPostalCode(postalCode);
			addressBean.setCountryCode(countryCode);
			addressBean.setAddressTypeId(addressTypeId);
			addressBean.setPersonId(personId);

			// Add Address
			AddressDao addressDao = (AddressDao) SpringApplicationContext.INSTANCE.getBean(AddressDao.BEAN_NAME);
			addressDao.addAddress(addressBean);
		}

	}

	private static void addContacts(Long personId, LiferayDataAnagraficaCsvUK liferayData) {
		addContact(personId, liferayData.getCnMobileTelephone(), Contact.MOBILE_CONTACT_TYPE_ID);
		addContact(personId, liferayData.getCnTelephone(), Contact.HOME_CONTACT_TYPE_ID);
	}

	private static void addContact(Long personId, String value, Long contactTypeId) {

		if (StringUtils.hasText(value)) {
			Contact contactMobile = new Contact();
			contactMobile.setValue(value);
			contactMobile.setContactTypeId(contactTypeId);
			contactMobile.setPersonId(personId);

			// Add Address
			ContactDao contactDao = (ContactDao) SpringApplicationContext.INSTANCE.getBean(ContactDao.BEAN_NAME);
			contactDao.addContact(contactMobile);
		}

	}

	private static void addPersonCountryBusinessRole(Long personId, LiferayDataAnagraficaCsvUK liferayData) {

		PersonCountryBusinessRole personCountryBusinessRole = new PersonCountryBusinessRole(personId);

		// Add PersonCountryBusinessRoleDao
		PersonCountryBusinessRoleDao personCountryBusinessRoleDao = (PersonCountryBusinessRoleDao) SpringApplicationContext.INSTANCE.getBean(PersonCountryBusinessRoleDao.BEAN_NAME);
		personCountryBusinessRoleDao.addUK(personCountryBusinessRole);

	}


}