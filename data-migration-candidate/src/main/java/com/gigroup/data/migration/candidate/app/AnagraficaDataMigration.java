package com.gigroup.data.migration.candidate.app;

import org.springframework.util.StringUtils;

import com.gigroup.data.migration.candidate.parser.anagrafica.AnagraficaParser;

public class AnagraficaDataMigration {

	public static void main(String[] args) {
		
		/**
		String filename = args[0];
		
		if(!StringUtils.hasText(filename)){
			filename = "C:\\Apps\\data-migration\\anagrafica.csv";
		}

		AnagraficaParser.parseFileToDatabase(filename);
		*/
		
		AnagraficaParser.parseFileToDatabase("C:\\Apps\\data-migration\\anagrafica.csv");
	}
}