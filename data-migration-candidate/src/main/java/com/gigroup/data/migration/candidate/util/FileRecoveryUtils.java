package com.gigroup.data.migration.candidate.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileRecoveryUtils {
	
	private static final transient Logger logger = LoggerFactory.getLogger(FileRecoveryUtils.class);
	
	private static final String PATHNAME_BASE = "C:/Apps/data-migration/recovery/";
	
	/**
	 * 
	 * @param line
	 * @param filenameRecovery
	 */
	public static void createDumpFileRecovery(final String line, String filenameRecovery) {
		final String methodName = "createDumpFileRecovery() - ";
		if (logger.isDebugEnabled()) logger.debug(methodName + "line CSV[" +line+ "] - PATHNAME_BASE  + filenameRecovery: "+PATHNAME_BASE+filenameRecovery );	
		
		BufferedWriter bufferedWriter = null;
		
		try{
			Path filePath = Paths.get(PATHNAME_BASE+filenameRecovery);
			if (!Files.exists(filePath)) {		    
				Files.createFile(filePath);			
			}
						
			bufferedWriter = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8, StandardOpenOption.APPEND);
			bufferedWriter.append(line);
			bufferedWriter.newLine();			
		} catch (IOException ioe) {			
			logger.error(methodName+"Unexcepted exception: " +ioe);
		} finally {
			if(bufferedWriter != null){
				try {
					bufferedWriter.close();
				} catch (IOException ioe) {
					logger.error(methodName+"Unexcepted exception: " +ioe);
				}
			}		
		}
	}
}
