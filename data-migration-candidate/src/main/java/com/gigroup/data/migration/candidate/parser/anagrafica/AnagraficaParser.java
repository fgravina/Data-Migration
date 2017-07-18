package com.gigroup.data.migration.candidate.parser.anagrafica;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.gigroup.data.migration.candidate.ExternalSystemReference;
import com.gigroup.data.migration.candidate.NaamahUser;
import com.gigroup.data.migration.candidate.Service;
import com.gigroup.data.migration.candidate.System;
import com.gigroup.data.migration.candidate.anagrafica.Address;
import com.gigroup.data.migration.candidate.anagrafica.Contact;
import com.gigroup.data.migration.candidate.anagrafica.ItalyProfile;
import com.gigroup.data.migration.candidate.anagrafica.Person;
import com.gigroup.data.migration.candidate.anagrafica.PersonCountryBusinessRole;
import com.gigroup.data.migration.candidate.anagrafica.PersonCountryServiceActive;
import com.gigroup.data.migration.candidate.anagrafica.dao.AddressDao;
import com.gigroup.data.migration.candidate.anagrafica.dao.ContactDao;
import com.gigroup.data.migration.candidate.anagrafica.dao.ItalyProfileDao;
import com.gigroup.data.migration.candidate.anagrafica.dao.PersonCountryBusinessRoleDao;
import com.gigroup.data.migration.candidate.anagrafica.dao.PersonCountryServiceActiveDao;
import com.gigroup.data.migration.candidate.anagrafica.dao.PersonDao;
import com.gigroup.data.migration.candidate.dao.ExternalSystemReferenceDao;
import com.gigroup.data.migration.candidate.dao.NaamahUserDao;
import com.gigroup.data.migration.candidate.dao.ServiceDao;
import com.gigroup.data.migration.candidate.dao.SystemDao;
import com.gigroup.data.migration.candidate.util.CountryUtils;
import com.gigroup.data.migration.candidate.util.DataMigrationException;
import com.gigroup.data.migration.candidate.util.DateUtils;
import com.gigroup.data.migration.candidate.util.FileRecoveryUtils;
import com.gigroup.data.migration.candidate.util.LoginNotFoundException;
import com.gigroup.data.migration.candidate.util.SpringApplicationContext;

public class AnagraficaParser {

	private static final transient Logger logger = LoggerFactory.getLogger(AnagraficaParser.class);

	private static Map<String, Service> serviceWorkerMap;
	private static Map<String, System> systemExternalMap;

	public static void parseFileToDatabase(final String filename) {
		final String methodName = "parseFileToDatabase() - ";		
		logger.info(methodName + "filename: " + filename);	

		String otherThanQuote = " [^\"] ";
		String quotedString = String.format(" \" %s* \" ", otherThanQuote);
		String regex = String.format(
				"(?x) " + // enable comments, ignore white spaces
						",                         " + // match a comma
						"(?=                       " + // start positive look
														// ahead
						"  (?:                     " + // start non-capturing
														// group 1
						"    %s*                   " + // match 'otherThanQuote'
														// zero or more times
						"    %s                    " + // match 'quotedString'
						"  )*                      " + // end group 1 and repeat
														// it zero or more times
						"  %s*                     " + // match 'otherThanQuote'
						"  $                       " + // match the end of the
														// string
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
		long counterItalyProfile = 0L;
		long counterPersonCountryBusinessRole = 0L;
		long counterAddress = 0L;
		long counterContact = 0L;
		long counterPersonCountryServicesActive = 0L;
		long counterExternalSystemsReference = 0L;	
		
		try {

			Path filenamePath = Paths.get(filename);
			reader = Files.newBufferedReader(filenamePath);

			// Fill ServiceWorkerMap (SL1, SL2,...)
			fillServiceWorkerMap();

			// Fill SystemExternalMap (RPM, LIFERAY,...)
			fillSystemExternalMap();

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

						LiferayDataAnagraficaCvs liferayData = new LiferayDataAnagraficaCvs(fields);
						logger.debug("Current liferayDataAnagraficaCvs: " + liferayData);

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
							addItalyProfile(userId, liferayData);
							counterItalyProfile++;
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

						// STEP 6
						try {
							addContacts(userId, liferayData);
							counterContact++;
						} catch (DataMigrationException dte) {
							logger.error(methodName + "Error during parsing following line: " + line + " - caused by " + dte.getMessage(), dte);
							isError = true;
						}

						// STEP 7
						try {
							addPersonCountryServicesActive(userId, liferayData);
							counterPersonCountryServicesActive++;
						} catch (DataMigrationException dte) {
							logger.error(methodName + "Error during parsing following line: " + line + " - caused by " + dte.getMessage(), dte);
							isError = true;
						}

						// STEP 8
						try {
							addExternalSystemsReference(String.valueOf(userId), liferayData);
							counterExternalSystemsReference++;
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
		logger.info("counterExternalSystemsReference : " + counterExternalSystemsReference);
		logger.info("counterItalyProfile : " + counterItalyProfile);
		logger.info("counterPersonCountryBusinessRole : " + counterPersonCountryBusinessRole);
		logger.info("counterPersonCountryServicesActive : " + counterPersonCountryServicesActive);
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

	private static Long addNaamahUser(final LiferayDataAnagraficaCvs liferayData) {

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

	private static void addPerson(final Long personId, final LiferayDataAnagraficaCvs liferayData) {

		// Fill Person bean
		Person person = new Person();
		person.setCreationTimestamp(DateUtils.stringToDate(liferayData.getCreateDate()));
		person.setLastUpdateTimestamp(DateUtils.stringToDate(liferayData.getModifiedDate()));
		person.setPersonId(personId);
		person.setFirstname(liferayData.getCnFirstName());
		person.setLastname(liferayData.getCnLastName());
		person.setEmail(liferayData.getCnEmailAddress());
		person.setLoginname(liferayData.getCnEmailAddress());
		person.setBirthday(DateUtils.stringToDate(liferayData.getCnBirthDate()));
		person.setPlaceOfBirth(liferayData.getCnBirthPlace());
		person.setGender(liferayData.getCnGender());
		// Rimappare da Locale in lingua Italiana
		person.setCountryCode(CountryUtils.descriptionToIso3(liferayData.getCnBirthCountry()));
		// person.setCountryCode(liferayData.getCnBirthCountry());
		person.setLoginname(liferayData.getCnEmailAddress());

		// Add NaamahUser
		PersonDao personDao = (PersonDao) SpringApplicationContext.INSTANCE.getBean(PersonDao.BEAN_NAME);
		personDao.addPerson(person);
	}

	private static void addItalyProfile(Long personId, LiferayDataAnagraficaCvs liferayData) {

		// fill ItalyProfile bean
		ItalyProfile italyProfile = new ItalyProfile();
		italyProfile.setPersonId(personId);
		italyProfile.setDriverLicenseA(liferayData.hasDriverLicenseA());
		italyProfile.setDriverLicenseB(liferayData.hasDriverLicenseB());
		italyProfile.setDriverLicenseC(liferayData.hasDriverLicenseC());
		italyProfile.setPrivacyGiGroup("Y".equalsIgnoreCase(liferayData.getCnPrivacyGroup()) ? Boolean.TRUE : Boolean.FALSE);
		italyProfile.setPrivacyMarketing("Y".equalsIgnoreCase(liferayData.getCnPrivacyMarketing()) ? Boolean.TRUE : Boolean.FALSE);
		italyProfile.setPrivacyTraining("Y".equalsIgnoreCase(liferayData.getCnPrivacyTraining()) ? Boolean.TRUE : Boolean.FALSE);
		italyProfile.setTaxCode(liferayData.getCnCode());

		// Add ItalyProfile
		ItalyProfileDao italyProfileDao = (ItalyProfileDao) SpringApplicationContext.INSTANCE.getBean(ItalyProfileDao.BEAN_NAME);
		italyProfileDao.addItalyProfile(italyProfile);
	}

	private static void addAddresses(Long personId, LiferayDataAnagraficaCvs liferayData) {

		addAddress(personId, liferayData.getCnAddress(), liferayData.getCnTown(), liferayData.getCnZipCode(), CountryUtils.descriptionToIso3(liferayData.getCountryDesc()),
				Address.DOMICILE_ADDRESS_TYPE_ID);

		if ("Y".equalsIgnoreCase(liferayData.getResComeDom())) {
			addAddress(personId, liferayData.getCnAddress(), liferayData.getCnTown(), liferayData.getCnZipCode(), CountryUtils.descriptionToIso3(liferayData.getCountryDesc()),
					Address.RESIDENCE_ADDRESS_TYPE_ID);
		} else {
			addAddress(personId, liferayData.getResAddress(), liferayData.getResTown(), liferayData.getResZipCode(), CountryUtils.descriptionToIso3(liferayData.getResCountryDesc()),
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

	private static void addContacts(Long personId, LiferayDataAnagraficaCvs liferayData) {
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

	private static void addPersonCountryBusinessRole(Long personId, LiferayDataAnagraficaCvs liferayData) {

		PersonCountryBusinessRole personCountryBusinessRole = new PersonCountryBusinessRole(personId);

		// Add PersonCountryBusinessRoleDao
		PersonCountryBusinessRoleDao personCountryBusinessRoleDao = (PersonCountryBusinessRoleDao) SpringApplicationContext.INSTANCE.getBean(PersonCountryBusinessRoleDao.BEAN_NAME);
		personCountryBusinessRoleDao.add(personCountryBusinessRole);

	}

	private static void addPersonCountryServicesActive(Long personId, LiferayDataAnagraficaCvs liferayData) {

		if ("Y".equalsIgnoreCase(liferayData.getLavoratore())) {
			addPersonCountryServiceActive(personId, "Y".equalsIgnoreCase(liferayData.getServiceLavoratoreTimesheet()) ? "SL1" : "");
			addPersonCountryServiceActive(personId, "Y".equalsIgnoreCase(liferayData.getServiceLavoratoreCedolini()) ? "SL2" : "");
			addPersonCountryServiceActive(personId, "Y".equalsIgnoreCase(liferayData.getServiceLavoratoreCud()) ? "SL3" : "");
			addPersonCountryServiceActive(personId, "Y".equalsIgnoreCase(liferayData.getServiceLavoratoreConsultaStorico()) ? "SL4" : "");
			addPersonCountryServiceActive(personId, "Y".equalsIgnoreCase(liferayData.getServiceLavoratoreFirma()) ? "SL5" : "");
		}

	}

	private static void addPersonCountryServiceActive(Long personId, String serviceCode) {

		if (StringUtils.hasText(serviceCode) && serviceWorkerMap != null) {
			PersonCountryServiceActive personCountryServiceActive = new PersonCountryServiceActive(personId, serviceWorkerMap.get(serviceCode).getServiceId());
			// Add PersonCountryServiceActiveDao
			PersonCountryServiceActiveDao personCountryServiceActiveDao = (PersonCountryServiceActiveDao) SpringApplicationContext.INSTANCE.getBean(PersonCountryServiceActiveDao.BEAN_NAME);
			personCountryServiceActiveDao.add(personCountryServiceActive);
		}

	}

	private static void addExternalSystemsReference(String girexId, LiferayDataAnagraficaCvs liferayData) {
		addExternalSystemReference(girexId, liferayData.getCnRpmId(), System.RPM_SYSTEM_DESCRIPTION);
		addExternalSystemReference(girexId, liferayData.getCnUserId(), System.LIFERAY_SYSTEM_DESCRIPTION);
	}

	private static void addExternalSystemReference(final String girexId, final String externalSystemObjectId, final String systemDescription) {
		if (StringUtils.hasText(externalSystemObjectId)) {
			// Fill ExternalSystemReference
			ExternalSystemReference externalSystemReference = new ExternalSystemReference(girexId, externalSystemObjectId, systemExternalMap.get(systemDescription).getSystemId());

			// Add NaamahUser
			ExternalSystemReferenceDao externalSystemReferenceDao = (ExternalSystemReferenceDao) SpringApplicationContext.INSTANCE.getBean(ExternalSystemReferenceDao.BEAN_NAME);
			externalSystemReferenceDao.addExternalSystemReference(externalSystemReference);
		}
	}

	private static void fillServiceWorkerMap() {
		ServiceDao serviceDao = (ServiceDao) SpringApplicationContext.INSTANCE.getBean(ServiceDao.BEAN_NAME);
		serviceWorkerMap = serviceDao.getServicesByWorker().stream().collect(Collectors.toMap(Service::getServiceCode, Function.identity()));
	}

	private static void fillSystemExternalMap() {
		SystemDao systemDao = (SystemDao) SpringApplicationContext.INSTANCE.getBean(SystemDao.BEAN_NAME);
		systemExternalMap = systemDao.getAllSystem().stream().collect(Collectors.toMap(System::getDescription, Function.identity()));
	}
}