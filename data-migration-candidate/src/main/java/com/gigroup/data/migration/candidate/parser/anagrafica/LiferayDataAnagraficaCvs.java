package com.gigroup.data.migration.candidate.parser.anagrafica;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.util.StringUtils;

public class LiferayDataAnagraficaCvs {
	
	private String cnUserId;
	private String cnRpmId;
	private String createDate;
	private String modifiedDate;
	private String cnFirstName;
	private String cnLastName;
	private String cnGender;
	private String cnBirthDate;
	private String cnBirthPlace;
	private String cnBirthCountry;
	private String cnCode;
	private String cnEmailAddress;
	
	// Contact
	private String cnMobileTelephone;
	private String cnTelephone;
 
	// Address Domicilio
	private String cnAddress;
	private String cnZipCode;
	private String cnTown;
	private String countryDesc;
	 
	// Address Residenza						
	private String resComeDom;
	private String resAddress;
	private String resZipCode;
	private String resTown;
	private String resCountryDesc;
	 
	// ItalyProfile						
	private String cnPrivacyTraining;
	private String cnPrivacyMarketing;
	private String cnPrivacyGroup;
	private String cnLicense;
	private List<String> driverLicenseList;
	
	// PersonCountryServiceActive
	private String lavoratore;
	private String serviceLavoratoreTimesheet;
	private String serviceLavoratoreCedolini;
	private String serviceLavoratoreCud;
	private String serviceLavoratoreConsultaStorico;
	private String serviceLavoratoreFirma;
	
	public LiferayDataAnagraficaCvs(){}
	
	public LiferayDataAnagraficaCvs(String[] fields){
		
		this.cnUserId = fields[fieldName.cnUserId.ordinal()];
		this.cnRpmId = fields[fieldName.cnRpmId.ordinal()];
		this.createDate = fields[fieldName.createDate.ordinal()];
		this.modifiedDate = fields[fieldName.modifiedDate.ordinal()];
		this.cnFirstName = StringUtils.hasText(fields[fieldName.cnFirstName.ordinal()]) ? fields[fieldName.cnFirstName.ordinal()].replace("\"", "") : null;
		this.cnLastName = StringUtils.hasText(fields[fieldName.cnLastName.ordinal()]) ? fields[fieldName.cnLastName.ordinal()].replace("\"", "") : null;
		this.cnGender = fields[fieldName.cnGender.ordinal()];
		this.cnBirthDate = fields[fieldName.cnBirthDate.ordinal()];
		this.cnBirthPlace = fields[fieldName.cnBirthPlace.ordinal()];
		this.cnBirthCountry = fields[fieldName.cnBirthCountry.ordinal()];
		this.cnCode = fields[fieldName.cnCode.ordinal()];
		this.cnEmailAddress = fields[fieldName.cnEmailAddress.ordinal()];
		
		// Contact
		this.cnMobileTelephone = fields[fieldName.cnMobileTelephone.ordinal()];
		this.cnTelephone = fields[fieldName.cnTelephone.ordinal()];
		
		// Address Domicilio
		this.cnAddress = fields[fieldName.cnAddress.ordinal()];
		this.cnZipCode = fields[fieldName.cnZipCode.ordinal()];
		this.cnTown = fields[fieldName.cnTown.ordinal()];
		this.countryDesc = fields[fieldName.countryDesc.ordinal()];
		
		// Address Residenza						
		this.resComeDom = fields[fieldName.resComeDom.ordinal()];
		this.resAddress = fields[fieldName.resAddress.ordinal()];
		this.resZipCode = fields[fieldName.resZipCode.ordinal()];
		this.resTown = fields[fieldName.resTown.ordinal()];
		this.resCountryDesc = fields[fieldName.resCountryDesc.ordinal()];
		
		// Address ItalyProfile						
		this.cnPrivacyTraining = fields[fieldName.cnPrivacyTraining.ordinal()];
		this.cnPrivacyMarketing = fields[fieldName.cnPrivacyMarketing.ordinal()];
		this.cnPrivacyGroup = fields[fieldName.cnPrivacyGroup.ordinal()];
		this.cnLicense = fields[fieldName.cnLicense.ordinal()];
		this.driverLicenseList = StringUtils.hasText(this.cnLicense) ? Arrays.asList(this.cnLicense.replace("\"", "").split(",")) : Collections.emptyList();
		
		// PersonCountryServiceActive
		this.lavoratore = fields[fieldName.lavoratore.ordinal()];
		this.serviceLavoratoreTimesheet = fields[fieldName.serviceLavoratoreTimesheet.ordinal()];
		this.serviceLavoratoreCedolini = fields[fieldName.serviceLavoratoreCedolini.ordinal()];
		this.serviceLavoratoreCud = fields[fieldName.serviceLavoratoreCud.ordinal()];
		this.serviceLavoratoreConsultaStorico = fields[fieldName.serviceLavoratoreConsultaStorico.ordinal()];
		this.serviceLavoratoreFirma = fields[fieldName.serviceLavoratoreFirma.ordinal()];
		
		
	}
	
	public String getCnUserId() {
		return cnUserId;
	}

	public void setCnUserId(String cnUserId) {
		this.cnUserId = cnUserId;
	}

	public String getCnRpmId() {
		return cnRpmId;
	}

	public void setCnRpmId(String cnRpmId) {
		this.cnRpmId = cnRpmId;
	}	

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCnFirstName() {
		return cnFirstName;
	}

	public void setCnFirstName(String cnFirstName) {
		this.cnFirstName = cnFirstName;
	}

	public String getCnLastName() {
		return cnLastName;
	}

	public void setCnLastName(String cnLastName) {
		this.cnLastName = cnLastName;
	}

	public String getCnGender() {
		return cnGender;
	}

	public void setCnGender(String cnGender) {
		this.cnGender = cnGender;
	}

	public String getCnBirthDate() {
		return cnBirthDate;
	}

	public void setCnBirthDate(String cnBirthDate) {
		this.cnBirthDate = cnBirthDate;
	}

	public String getCnBirthPlace() {
		return cnBirthPlace;
	}

	public void setCnBirthPlace(String cnBirthPlace) {
		this.cnBirthPlace = cnBirthPlace;
	}

	public String getCnBirthCountry() {
		return cnBirthCountry;
	}

	public void setCnBirthCountry(String cnBirthCountry) {
		this.cnBirthCountry = cnBirthCountry;
	}

	public String getCnCode() {
		return cnCode;
	}

	public void setCnCode(String cnCode) {
		this.cnCode = cnCode;
	}

	public String getCnEmailAddress() {
		return cnEmailAddress;
	}

	public void setCnEmailAddress(String cnEmailAddress) {
		this.cnEmailAddress = cnEmailAddress;
	}

	public String getCnMobileTelephone() {
		return cnMobileTelephone;
	}

	public void setCnMobileTelephone(String cnMobileTelephone) {
		this.cnMobileTelephone = cnMobileTelephone;
	}

	public String getCnTelephone() {
		return cnTelephone;
	}

	public void setCnTelephone(String cnTelephone) {
		this.cnTelephone = cnTelephone;
	}

	public String getCnAddress() {
		return cnAddress;
	}

	public void setCnAddress(String cnAddress) {
		this.cnAddress = cnAddress;
	}

	public String getCnZipCode() {
		return cnZipCode;
	}

	public void setCnZipCode(String cnZipCode) {
		this.cnZipCode = cnZipCode;
	}

	public String getCnTown() {
		return cnTown;
	}

	public void setCnTown(String cnTown) {
		this.cnTown = cnTown;
	}

	public String getCountryDesc() {
		return countryDesc;
	}

	public void setCountryDesc(String countryDesc) {
		this.countryDesc = countryDesc;
	}

	public String getResComeDom() {
		return resComeDom;
	}

	public void setResComeDom(String resComeDom) {
		this.resComeDom = resComeDom;
	}

	public String getResAddress() {
		return resAddress;
	}

	public void setResAddress(String resAddress) {
		this.resAddress = resAddress;
	}

	public String getResZipCode() {
		return resZipCode;
	}

	public void setResZipCode(String resZipCode) {
		this.resZipCode = resZipCode;
	}

	public String getResTown() {
		return resTown;
	}

	public void setResTown(String resTown) {
		this.resTown = resTown;
	}

	public String getResCountryDesc() {
		return resCountryDesc;
	}

	public void setResCountryDesc(String resCountryDesc) {
		this.resCountryDesc = resCountryDesc;
	}

	public String getCnPrivacyTraining() {
		return cnPrivacyTraining;
	}

	public void setCnPrivacyTraining(String cnPrivacyTraining) {
		this.cnPrivacyTraining = cnPrivacyTraining;
	}

	public String getCnPrivacyMarketing() {
		return cnPrivacyMarketing;
	}

	public void setCnPrivacyMarketing(String cnPrivacyMarketing) {
		this.cnPrivacyMarketing = cnPrivacyMarketing;
	}

	public String getCnPrivacyGroup() {
		return cnPrivacyGroup;
	}

	public void setCnPrivacyGroup(String cnPrivacyGroup) {
		this.cnPrivacyGroup = cnPrivacyGroup;
	}

	public String getCnLicense() {
		return cnLicense;
	}
	
	public Boolean hasDriverLicenseA() {
		return this.driverLicenseList.contains("YE02");
	}
	
	public Boolean hasDriverLicenseB() {
		return this.driverLicenseList.contains("YE03");
	}
	
	public Boolean hasDriverLicenseC() {
		return this.driverLicenseList.contains("YE04");
	}

	public void setCnLicense(String cnLicense) {
		this.cnLicense = cnLicense;
	}	

	public String getLavoratore() {
		return lavoratore;
	}

	public void setLavoratore(String lavoratore) {
		this.lavoratore = lavoratore;
	}

	public String getServiceLavoratoreTimesheet() {
		return serviceLavoratoreTimesheet;
	}

	public void setServiceLavoratoreTimesheet(String serviceLavoratoreTimesheet) {
		this.serviceLavoratoreTimesheet = serviceLavoratoreTimesheet;
	}

	public String getServiceLavoratoreCedolini() {
		return serviceLavoratoreCedolini;
	}

	public void setServiceLavoratoreCedolini(String serviceLavoratoreCedolini) {
		this.serviceLavoratoreCedolini = serviceLavoratoreCedolini;
	}

	public String getServiceLavoratoreCud() {
		return serviceLavoratoreCud;
	}

	public void setServiceLavoratoreCud(String serviceLavoratoreCud) {
		this.serviceLavoratoreCud = serviceLavoratoreCud;
	}

	public String getServiceLavoratoreConsultaStorico() {
		return serviceLavoratoreConsultaStorico;
	}

	public void setServiceLavoratoreConsultaStorico(String serviceLavoratoreConsultaStorico) {
		this.serviceLavoratoreConsultaStorico = serviceLavoratoreConsultaStorico;
	}

	public String getServiceLavoratoreFirma() {
		return serviceLavoratoreFirma;
	}

	public void setServiceLavoratoreFirma(String serviceLavoratoreFirma) {
		this.serviceLavoratoreFirma = serviceLavoratoreFirma;
	}
	
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
	private enum fieldName {
		cnUserId,
		cnRpmId,
		createDate,
		modifiedDate,
		cnFirstName,
		cnLastName,
		cnGender,
		cnBirthDate,
		cnBirthPlace,
		cnBirthCountry,
		cnCode,
		cnEmailAddress,
		cnMobileTelephone,
		cnTelephone,
		
		// Address domicilio
		cnAddress,
		cnZipCode,
		cnTown,
		countryDesc,

		// Address residenza						
		resComeDom,
		resAddress,
		resZipCode,
		resTown,
		resCountryDesc,

		// ItalyProfile						
		cnPrivacyTraining,
		cnPrivacyMarketing,
		cnPrivacyGroup,
		cnLicense,		

		// PersonCountryServiceActive
		lavoratore,
		serviceLavoratoreTimesheet,
		serviceLavoratoreCedolini,
		serviceLavoratoreCud,
		serviceLavoratoreConsultaStorico,
		serviceLavoratoreFirma
	}
}