package com.gigroup.data.migration.candidate.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

public class ProvinceUtils {
	
	private static Map<String, String> provinceMap;
	
	static {
		provinceMap = new HashMap<>();
		
		provinceMap.put("AL","Alessandria");
		provinceMap.put("AG","Agrigento");
		provinceMap.put("AN","Ancona");
		provinceMap.put("AO","Aosta");
		provinceMap.put("AR","Arezzo");
		provinceMap.put("AP","Ascoli Piceno");
		provinceMap.put("AT","Asti");
		provinceMap.put("AV","Avellino");
		provinceMap.put("BA","Bari");
		provinceMap.put("BT","Barletta-Andria-Trani");
		provinceMap.put("BL","Belluno");
		provinceMap.put("BN","Benevento");
		provinceMap.put("BG","Bergamo");
		provinceMap.put("BI","Biella");
		provinceMap.put("BO","Bologna");
		provinceMap.put("BZ","Bolzano");
		provinceMap.put("BS","Brescia");
		provinceMap.put("BR","Brindisi");
		provinceMap.put("CA","Cagliari");
		provinceMap.put("CL","Caltanissetta");
		provinceMap.put("CB","Campobasso");
		provinceMap.put("CI","Carbonia-Iglesias");
		provinceMap.put("CE","Caserta");
		provinceMap.put("CT","Catania");
		provinceMap.put("CZ","Catanzaro");
		provinceMap.put("CH","Chieti");
		provinceMap.put("CO","Como");
		provinceMap.put("CS","Cosenza");
		provinceMap.put("CR","Cremona");
		provinceMap.put("KR","Crotone");
		provinceMap.put("CN","Cuneo");
		provinceMap.put("EN","Enna");
		provinceMap.put("FM","Fermo");
		provinceMap.put("FE","Ferrara");
		provinceMap.put("FI","Firenze");
		provinceMap.put("FG","Foggia");
		provinceMap.put("FC","Forlì-Cesena");
		provinceMap.put("FR","Frosinone");
		provinceMap.put("GE","Genova");
		provinceMap.put("GO","Gorizia");
		provinceMap.put("GR","Grosseto");
		provinceMap.put("IM","Imperia");
		provinceMap.put("IS","Isernia");
		provinceMap.put("SP","La Spezia");
		provinceMap.put("AQ","L'Aquila");
		provinceMap.put("LT","Latina");
		provinceMap.put("LE","Lecce");
		provinceMap.put("LC","Lecco");
		provinceMap.put("LI","Livorno");
		provinceMap.put("LO","Lodi");
		provinceMap.put("LU","Lucca");
		provinceMap.put("MC","Macerata");
		provinceMap.put("MN","Mantova");
		provinceMap.put("MS","Massa-Carrara");
		provinceMap.put("MT","Matera");
		provinceMap.put("ME","Messina");
		provinceMap.put("MI","Milano");
		provinceMap.put("MO","Modena");
		provinceMap.put("MB","Monza e della Brianza");
		provinceMap.put("NA","Napoli");
		provinceMap.put("NO","Novara");
		provinceMap.put("NU","Nuoro");
		provinceMap.put("OT","Olbia-Tempio");
		provinceMap.put("OR","Oristano");
		provinceMap.put("PD","Padova");
		provinceMap.put("PA","Palermo");
		provinceMap.put("PR","Parma");
		provinceMap.put("PV","Pavia");
		provinceMap.put("PG","Perugia");
		provinceMap.put("PU","Pesaro e Urbino");
		provinceMap.put("PE","Pescara");
		provinceMap.put("PC","Piacenza");
		provinceMap.put("PI","Pisa");
		provinceMap.put("PT","Pistoia");
		provinceMap.put("PN","Pordenone");
		provinceMap.put("PZ","Potenza");
		provinceMap.put("PO","Prato");
		provinceMap.put("RG","Ragusa");
		provinceMap.put("RA","Ravenna");
		provinceMap.put("RC","Reggio Calabria");
		provinceMap.put("RE","Reggio Emilia");
		provinceMap.put("RI","Rieti");
		provinceMap.put("RN","Rimini");
		provinceMap.put("RM","Roma");
		provinceMap.put("RO","Rovigo");
		provinceMap.put("SA","Salerno");
		provinceMap.put("VS","Medio Campidano");
		provinceMap.put("SS","Sassari");
		provinceMap.put("SV","Savona");
		provinceMap.put("SI","Siena");
		provinceMap.put("SR","Siracusa");
		provinceMap.put("SO","Sondrio");
		provinceMap.put("TA","Taranto");
		provinceMap.put("TE","Teramo");
		provinceMap.put("TR","Terni");
		provinceMap.put("TO","Torino");
		provinceMap.put("OG","Ogliastra");
		provinceMap.put("TP","Trapani");
		provinceMap.put("TN","Trento");
		provinceMap.put("TV","Treviso");
		provinceMap.put("TS","Trieste");
		provinceMap.put("UD","Udine");
		provinceMap.put("VA","Varese");
		provinceMap.put("VE","Venezia");
		provinceMap.put("VB","Verbano-Cusio-Ossola");
		provinceMap.put("VC","Vercelli");
		provinceMap.put("VR","Verona");
		provinceMap.put("VV","Vibo Valentia");
		provinceMap.put("VI","Vicenza");
		provinceMap.put("VT","Viterbo");
		provinceMap.put("FU","Fiume");
		provinceMap.put("PL","Pola");
		provinceMap.put("ZA","Zara");
	}
	
	public static String siglaToCity(final String sigla){
		if(!StringUtils.hasText(sigla))
			return null;
		
		return provinceMap.get(sigla.toUpperCase());
	}
}