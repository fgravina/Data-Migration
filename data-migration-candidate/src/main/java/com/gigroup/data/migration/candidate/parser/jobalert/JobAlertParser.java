package com.gigroup.data.migration.candidate.parser.jobalert;

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
import com.gigroup.data.migration.candidate.dao.ExternalSystemReferenceDao;
import com.gigroup.data.migration.candidate.jobalert.IndustrySector;
import com.gigroup.data.migration.candidate.jobalert.JobAlert;
import com.gigroup.data.migration.candidate.jobalert.JobAlertFrequency;
import com.gigroup.data.migration.candidate.jobalert.JobTitle;
import com.gigroup.data.migration.candidate.jobalert.ProfessionalArea;
import com.gigroup.data.migration.candidate.jobalert.dao.IndustrySectorDao;
import com.gigroup.data.migration.candidate.jobalert.dao.JobAlertDao;
import com.gigroup.data.migration.candidate.jobalert.dao.JobAlertFrequencyDao;
import com.gigroup.data.migration.candidate.jobalert.dao.JobTitleDao;
import com.gigroup.data.migration.candidate.jobalert.dao.ProfessionalAreaDao;
import com.gigroup.data.migration.candidate.util.DateUtils;
import com.gigroup.data.migration.candidate.util.FileRecoveryUtils;
import com.gigroup.data.migration.candidate.util.ProvinceUtils;
import com.gigroup.data.migration.candidate.util.SpringApplicationContext;


public class JobAlertParser {	 
	
	private static final transient Logger logger = LoggerFactory.getLogger(JobAlertParser.class);
	
	private static Map<String, IndustrySector> industrySectorMap;
	private static Map<String, ProfessionalArea> professionalAreaMap;
	private static Map<String, JobTitle> jobTitleMap;
	private static Map<Integer, JobAlertFrequency> jobAlertFrequencyMap;
	
	public static void parseFileToDatabase(final String filename) {
		final String methodName = "parseFileToDatabase() - ";
		logger.info(methodName+"filename: " + filename);			
		
		String otherThanQuote = " [^\"] ";
	    String quotedString = String.format(" \" %s* \" ", otherThanQuote);
	    String regex = String.format("(?x) "+ // enable comments, ignore white spaces
	             ",                         "+ // match a comma
	             "(?=                       "+ // start positive look ahead
	             "  (?:                     "+ //   start non-capturing group 1
	             "    %s*                   "+ //     match 'otherThanQuote' zero or more times
	             "    %s                    "+ //     match 'quotedString'
	             "  )*                      "+ //   end group 1 and repeat it zero or more times
	             "  %s*                     "+ //   match 'otherThanQuote'
	             "  $                       "+ // match the end of the string
	             ")                         ", // stop positive look ahead
	             otherThanQuote, quotedString, otherThanQuote);
				
		String line = null;		
		BufferedReader reader = null;
		
		String filenameRecovery = "JobAlert_Recovery_" + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()) + ".csv";
		long counterTotal = 0L;
		long counterSuccess = 0L;
		long counterError = 0L;
		
		Instant start = null;
		
		try {			
			
			Path filenamePath = Paths.get(filename);			
			reader = Files.newBufferedReader(filenamePath);
			
			// Fill IndustrySectorMap with codeExt as key (MM)
			fillIndustrySectorMap();
			
			// Fill ProfessionalAreaMap with codeExt as key (ZPA)
			fillProfessionalAreaMap();
			
			// Fill JobTitleMap with codeExt as key (L08I)
			fillJobTitleMap();
			
			// Fill JobAlertFrequencyMap with numberDays as key (1)
			fillJobAlertFrequencyMap();
			
			start = Instant.now();
			if(logger.isDebugEnabled()){logger.debug(methodName+"Start : " + start.toString());}
						
			while(reader.ready()) {
				counterTotal++;
				line = reader.readLine().trim();
				try {
					logger.debug(methodName+"Current line from CSV: " + line);
					
					if(!line.isEmpty() && line.split(regex).length > 1) {
						String[] fields = line.split(regex);
						
						LiferayDataJobAlertCvs liferayDataJobAlertCvs = new LiferayDataJobAlertCvs(fields);
						logger.debug("liferayDataJobAlertCvs: " + liferayDataJobAlertCvs);						
						
						addJobAlert(liferayDataJobAlertCvs);
						counterSuccess++;
						
					}
				} catch (Exception e) {					
					logger.error(methodName+"Error during parsing following line: " + line + " - caused by " + e.getMessage(), e);
					FileRecoveryUtils.createDumpFileRecovery(line, filenameRecovery);
					counterError++;
				}
				line = null;						
				
			} // end while			
			
		} catch (Throwable t) {			
			logger.error(methodName+"Unexcepted exception at line: " + line + ", exception: "+t);			
		} finally {
			if(reader != null){
				try {
					reader.close();
				} catch (IOException ioe) {
					logger.error(methodName+"Unexcepted exception: " +ioe);
				}
			}			
		}		
		
		logger.info("**************************************************");
		logger.info("***************** CONTATORI **********************");
		logger.info("**************************************************");
		logger.info("counterTotal : " + counterTotal);
		logger.info("counterSuccess : " + counterSuccess);
		logger.info("counterError : " + counterError);
		logger.info("**************************************************");
		logger.info("");
		logger.info("");
		
		Instant end = Instant.now();
		logger.info("**************************************************");
		logger.info("*************** TEMPO IMPIEGATO ******************");
		logger.info("**************************************************");
		logger.info(methodName+"Start : " + start.toString());
		logger.info(methodName+"End : " + end.toString());
		logger.info(methodName+"Total time : " + ((end.toEpochMilli() - start.toEpochMilli())) + " millisec" );	
		logger.info("**************************************************");	    		
	}

	private static void addJobAlert(LiferayDataJobAlertCvs liferayDataJobAlert) {
		
		if(StringUtils.hasText(liferayDataJobAlert.getUserId())) {
			// Get ExternalSystemReference By extSystemObjectId for retrieve girexId/personId
			ExternalSystemReferenceDao externalSystemReferenceDao = (ExternalSystemReferenceDao)SpringApplicationContext.INSTANCE.getBean(ExternalSystemReferenceDao.BEAN_NAME);
			ExternalSystemReference externalSystemReferenceFound = externalSystemReferenceDao.findByExtSystemObjectId(liferayDataJobAlert.getUserId());
			
			JobAlert jobAlert = new JobAlert();
			jobAlert.setPersonId(externalSystemReferenceFound != null ? Long.valueOf(externalSystemReferenceFound.getGirexId()) : 0L);
			jobAlert.setCreationTimestamp(DateUtils.stringToDate(liferayDataJobAlert.getCreateDate()));
			jobAlert.setLastUpdateTimestamp(DateUtils.stringToDate(liferayDataJobAlert.getModifiedDate()));
			jobAlert.setName(liferayDataJobAlert.getTitolo());
			jobAlert.setKeywords(liferayDataJobAlert.getDescrizione());
			jobAlert.setLastSendTimestamp(StringUtils.hasText(liferayDataJobAlert.getUltimoInvio()) ? DateUtils.stringToDate(liferayDataJobAlert.getUltimoInvio()) : null);
			
			// DISTANCE_FROM_CITY: 50 km
			jobAlert.setDistanceFromCity(50000); // in metri
			
			// "Split con , - Si prende la prima provincia e diventa la città (MI,MB,LC,VA)
			if(liferayDataJobAlert.getProvinceList() != null && liferayDataJobAlert.getProvinceList().size() >= 1) {
				String provinciaSigla = liferayDataJobAlert.getProvinceList().get(0);
				jobAlert.setCity(ProvinceUtils.siglaToCity(provinciaSigla));
			}
			
			// Ricalcolato da INDUSTRY_SECTORS.CODE_EXT (MM)
			if(industrySectorMap != null && StringUtils.hasText(liferayDataJobAlert.getSettore()))
				jobAlert.setIndustrySectorId(Long.toString(industrySectorMap.get(liferayDataJobAlert.getSettore()).getIndustrySectorId()));
			
			// Ricalcolato da PROFESSIONAL_AREAS.CODE_EXT (ZPA)
			if(professionalAreaMap != null && StringUtils.hasText(liferayDataJobAlert.getArea()))
				jobAlert.setProfessionalAreaId(Long.toString(professionalAreaMap.get(liferayDataJobAlert.getArea()).getProfessionalAreaId()));
			
			// Ricalcolato da JOB_TITLES.CODE_EXT (L08I)
			if(jobTitleMap != null && StringUtils.hasText(liferayDataJobAlert.getJob()))
				jobAlert.setJobTitleId(Long.toString(jobTitleMap.get(liferayDataJobAlert.getJob()).getJobTitleId()));
			
			// Ricalcolata da JOB_ALERT_FREQUENCIES.NUMBER_DAYS (1)
			if(jobAlertFrequencyMap != null && StringUtils.hasText(liferayDataJobAlert.getFrequenza()))
				jobAlert.setJobAlertFrequencyId(jobAlertFrequencyMap.get(Integer.parseInt(liferayDataJobAlert.getFrequenza())).getJobAlertFrequencyId());			 
		    
		    // Add JobAlert
			JobAlertDao jobAlertDao = (JobAlertDao)SpringApplicationContext.INSTANCE.getBean(JobAlertDao.BEAN_NAME);
			jobAlertDao.addJobAlert(jobAlert);
		}
	}
	
	private static void fillIndustrySectorMap() {
		IndustrySectorDao industrySectorDao = (IndustrySectorDao) SpringApplicationContext.INSTANCE.getBean(IndustrySectorDao.BEAN_NAME);
		industrySectorMap = industrySectorDao.getAll().stream().collect(Collectors.toMap(IndustrySector::getCodeExternal, Function.identity()));		
	}
	
	private static void fillProfessionalAreaMap() {
		ProfessionalAreaDao professionalAreaDao = (ProfessionalAreaDao) SpringApplicationContext.INSTANCE.getBean(ProfessionalAreaDao.BEAN_NAME);
		professionalAreaMap = professionalAreaDao.getAll().stream().collect(Collectors.toMap(ProfessionalArea::getCodeExternal, Function.identity()));		
	}
	
	private static void fillJobTitleMap() {
		JobTitleDao jobTitleDao = (JobTitleDao) SpringApplicationContext.INSTANCE.getBean(JobTitleDao.BEAN_NAME);
		jobTitleMap = jobTitleDao.getAll().stream().collect(Collectors.toMap(JobTitle::getCodeExternal, Function.identity()));		
	}
	
	private static void fillJobAlertFrequencyMap() {
		JobAlertFrequencyDao industrySectorDao = (JobAlertFrequencyDao) SpringApplicationContext.INSTANCE.getBean(JobAlertFrequencyDao.BEAN_NAME);
		jobAlertFrequencyMap = industrySectorDao.getAll().stream().collect(Collectors.toMap(JobAlertFrequency::getNumberDays, Function.identity()));		
	}
}