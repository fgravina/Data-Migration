package com.gigroup.data.migration.candidate.app;

import org.springframework.util.StringUtils;

import com.gigroup.data.migration.candidate.parser.jobalert.JobAlertParser;

public class JobAlertDataMigration {

	public static void main(String[] args) {		
			
		String filename = args[0];
		
		if(!StringUtils.hasText(filename)){
			filename = "C:\\Apps\\data-migration\\candidatura.csv";
		}
		
		JobAlertParser.parseFileToDatabase(filename);
	}
}