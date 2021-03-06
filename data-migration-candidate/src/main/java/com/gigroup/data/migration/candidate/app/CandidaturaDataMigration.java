package com.gigroup.data.migration.candidate.app;

import org.springframework.util.StringUtils;

import com.gigroup.data.migration.candidate.parser.candidatura.CandidaturaParser;

public class CandidaturaDataMigration {

	public static void main(String[] args) {
		
		String filename = args[0];
		
		if(!StringUtils.hasText(filename)){
			filename = "C:\\Apps\\data-migration\\candidatura.csv";
		}
			
		CandidaturaParser.parseFileToDatabase(filename);
	}
}