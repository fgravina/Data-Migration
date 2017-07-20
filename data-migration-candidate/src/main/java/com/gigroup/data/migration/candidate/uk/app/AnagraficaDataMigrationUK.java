package com.gigroup.data.migration.candidate.uk.app;

import com.gigroup.data.migration.candidate.parser.anagrafica.AnagraficaParserUK;

public class AnagraficaDataMigrationUK {

	public static void main(String[] args) {
		AnagraficaParserUK.parseFileToDatabase("C:\\Apps\\data-migration\\anagraficaUK.csv");
	}
}