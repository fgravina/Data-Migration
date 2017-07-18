package com.gigroup.data.migration.candidate.parser.candidatura;

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

import com.gigroup.data.migration.candidate.ExternalSystemReference;
import com.gigroup.data.migration.candidate.candidatura.AppliedPosition;
import com.gigroup.data.migration.candidate.candidatura.JobPosting;
import com.gigroup.data.migration.candidate.candidatura.dao.AppliedPositionDao;
import com.gigroup.data.migration.candidate.candidatura.dao.JobPostingDao;
import com.gigroup.data.migration.candidate.dao.ExternalSystemReferenceDao;
import com.gigroup.data.migration.candidate.util.DateUtils;
import com.gigroup.data.migration.candidate.util.FileRecoveryUtils;
import com.gigroup.data.migration.candidate.util.SpringApplicationContext;


public class CandidaturaParser {	 
	
	private static final transient Logger logger = LoggerFactory.getLogger(CandidaturaParser.class);
	
	public static void parseFileToDatabase(final String filename) {
		final String methodName = "parseFileToDatabase() - ";
		logger.info(methodName+"filename: " + filename);				
				
		Instant start = Instant.now();
		if(logger.isDebugEnabled()){logger.debug(methodName+"Start : " + start.toString());}
		
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
		
		String filenameRecovery = "Candidatura_Recovery_" + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()) + ".csv";
		
		long counterTotal = 0L;
		long counterSuccess = 0L;
		long counterError = 0L;
				
		try {			
			
			Path filenamePath = Paths.get(filename);			
			reader = Files.newBufferedReader(filenamePath);			
						
			while(reader.ready()) {
				counterTotal++;
				line = reader.readLine().trim();
				try {
					logger.debug(methodName+"Current line from CSV: " + line);
					
					if(!line.isEmpty() && line.split(regex).length > 1) {
						String[] fields = line.split(regex);
						
						LiferayDataCandidaturaCvs liferayDataCandidaturaCvs = new LiferayDataCandidaturaCvs(fields);
						logger.debug("liferayDataCandidaturaCvs: " + liferayDataCandidaturaCvs);						
						
						addAppliedPosition(liferayDataCandidaturaCvs);
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
	

	private static void addAppliedPosition(LiferayDataCandidaturaCvs liferayDataCandidatura) {
		
		if(StringUtils.hasText(liferayDataCandidatura.getUserId()) && StringUtils.hasText(liferayDataCandidatura.getCodiceRiferimento())){
			
			// Get ExternalSystemReference By extSystemObjectId for retrieve girexId/personId
			ExternalSystemReferenceDao externalSystemReferenceDao = (ExternalSystemReferenceDao)SpringApplicationContext.INSTANCE.getBean(ExternalSystemReferenceDao.BEAN_NAME);
			ExternalSystemReference externalSystemReferenceFound = externalSystemReferenceDao.findByExtSystemObjectId(liferayDataCandidatura.getUserId());
			
			// Get JobPosting By extSystemObjectId for retrieve jobPostingId
			JobPostingDao jobPostingDao = (JobPostingDao)SpringApplicationContext.INSTANCE.getBean(JobPostingDao.BEAN_NAME);
			JobPosting jobPostingFound = jobPostingDao.findByReferenceCode(liferayDataCandidatura.getCodiceRiferimento());		
			
			AppliedPosition appliedPosition = new AppliedPosition();		
			appliedPosition.setPersonId(externalSystemReferenceFound!= null ? Long.valueOf(externalSystemReferenceFound.getGirexId()) : 0L);
			appliedPosition.setCreationTimestamp(DateUtils.stringToDate(liferayDataCandidatura.getCreateDate()));		
			appliedPosition.setJobPostingId(jobPostingFound != null ? jobPostingFound.getJobPostingId() : 0L);
		    
		    // Add AppliedPosition
			AppliedPositionDao appliedPositionDao = (AppliedPositionDao)SpringApplicationContext.INSTANCE.getBean(AppliedPositionDao.BEAN_NAME);
			appliedPositionDao.addAppliedPosition(appliedPosition);
		}		
	}	
}