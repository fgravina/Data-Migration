package com.gigroup.data.migration.candidate.parser.anagrafica;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.util.StringUtils;

public class LiferayDataAnagraficaCsvUK {
	
	private String cnCollaborationPortalId;
	private String cnEmailAddress;
	private String cnFirstName;
	private String cnLastName;
	private String cnBirthPlace;
	private String cnBirthCountry;
	private String cnBirthDate;
	private String cnGender;

	// GBR PROFILE
	private String cnTaxCode;
	private String cnNationality;

	// Contact
	private String cnMobileTelephone;
	private String cnTelephone;

	// Address Domicilio
	private String cnDomAddress;
	private String cnDomZipCode;
	private String cnDomTown;
	private String cnDomCountryDesc;
	 
	// Address Residenza						
	private String cnResComeDom;
	private String cnResAddress;
	private String cnResZipCode;
	private String cnResTown;
	private String cnResCountryDesc;
	// Address ?!?
	private String cnCounty;
	private String cnPostcode;
	 
	// GBR Profile
	private String cnNation;
	private String cnNOKName;
	private String cnNOKContactNumber;
	private String cnNOKRelationship;
	private String cnStatus;
	
	//People - Not defined yet 
	private String cnNationalityCode;
	
	public String getCnNationalityCode() {
		return cnNationalityCode;
	}

	public void setCnNationalityCode(String cnNationalityCode) {
		this.cnNationalityCode = cnNationalityCode;
	}

	public LiferayDataAnagraficaCsvUK(){}
	
	public LiferayDataAnagraficaCsvUK(String[] fields){
		
		this.cnCollaborationPortalId = fields[fieldName.cnCollaborationPortalId.ordinal()];
		this.cnEmailAddress = fields[fieldName.cnEmailAddress.ordinal()];
		this.cnFirstName = StringUtils.hasText(fields[fieldName.cnFirstName.ordinal()]) ? fields[fieldName.cnFirstName.ordinal()].replace("\"", "") : null;
		this.cnLastName = StringUtils.hasText(fields[fieldName.cnLastName.ordinal()]) ? fields[fieldName.cnLastName.ordinal()].replace("\"", "") : null;
		this.cnBirthPlace = fields[fieldName.cnBirthPlace.ordinal()];
		this.cnBirthCountry = fields[fieldName.cnBirthCountry.ordinal()];
		this.cnBirthDate = fields[fieldName.cnBirthDate.ordinal()];
		this.cnGender = fields[fieldName.cnGender.ordinal()];
		
		// Gbr Profile
		this.cnTaxCode = fields[fieldName.cnTaxCode.ordinal()];
		this.cnNationality = fields[fieldName.cnNationality.ordinal()];
		
		// Contact
		this.cnMobileTelephone = fields[fieldName.cnMobileTelephone.ordinal()];
		this.cnTelephone = fields[fieldName.cnTelephone.ordinal()];
		
		
		this.cnMobileTelephone = fields[fieldName.cnMobileTelephone.ordinal()];
		this.cnTelephone = fields[fieldName.cnTelephone.ordinal()];

		this.cnDomAddress = fields[fieldName.cnDomAddress.ordinal()];
		this.cnDomZipCode = fields[fieldName.cnDomZipCode.ordinal()];
		this.cnDomTown = fields[fieldName.cnDomTown.ordinal()];
		this.cnDomCountryDesc = fields[fieldName.cnDomCountryDesc.ordinal()];

		this.cnResComeDom = fields[fieldName.cnResComeDom.ordinal()];
		this.cnResAddress = fields[fieldName.cnResAddress.ordinal()];
		this.cnResZipCode = fields[fieldName.cnResZipCode.ordinal()];
		this.cnResTown = fields[fieldName.cnResTown.ordinal()];
		this.cnResCountryDesc = fields[fieldName.cnResCountryDesc.ordinal()];

		this.cnCounty = fields[fieldName.cnCounty.ordinal()];
		this.cnPostcode = fields[fieldName.cnPostcode.ordinal()];

		this.cnNation = fields[fieldName.cnNation.ordinal()];
		this.cnNOKName = fields[fieldName.cnNOKName.ordinal()];
		this.cnNOKContactNumber = fields[fieldName.cnNOKContactNumber.ordinal()];
		this.cnNOKRelationship = fields[fieldName.cnNOKRelationship.ordinal()];
		this.cnStatus = fields[fieldName.cnStatus.ordinal()];
		
		//People - Not defined yet 
		this.cnNationalityCode = fields[fieldName.cnNationalityCode.ordinal()];
				
	}
	
	public String getCnCollaborationPortalId() {
		return cnCollaborationPortalId;
	}

	public void setCnCollaborationPortalId(String cnCollaborationPortalId) {
		this.cnCollaborationPortalId = cnCollaborationPortalId;
	}

	public String getCnEmailAddress() {
		return cnEmailAddress;
	}

	public void setCnEmailAddress(String cnEmailAddress) {
		this.cnEmailAddress = cnEmailAddress;
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

	public String getCnBirthDate() {
		return cnBirthDate;
	}

	public void setCnBirthDate(String cnBirthDate) {
		this.cnBirthDate = cnBirthDate;
	}

	public String getCnGender() {
		return cnGender;
	}

	public void setCnGender(String cnGender) {
		this.cnGender = cnGender;
	}

	public String getCnTaxCode() {
		return cnTaxCode;
	}

	public void setCnTaxCode(String cnTaxCode) {
		this.cnTaxCode = cnTaxCode;
	}

	public String getCnNationality() {
		return cnNationality;
	}

	public void setCnNationality(String cnNationality) {
		this.cnNationality = cnNationality;
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

	public String getCnDomAddress() {
		return cnDomAddress;
	}

	public void setCnDomAddress(String cnDomAddress) {
		this.cnDomAddress = cnDomAddress;
	}

	public String getCnDomZipCode() {
		return cnDomZipCode;
	}

	public void setCnDomZipCode(String cnDomZipCode) {
		this.cnDomZipCode = cnDomZipCode;
	}

	public String getCnDomTown() {
		return cnDomTown;
	}

	public void setCnDomTown(String cnDomTown) {
		this.cnDomTown = cnDomTown;
	}

	public String getCnDomCountryDesc() {
		return cnDomCountryDesc;
	}

	public void setCnDomCountryDesc(String cnDomCountryDesc) {
		this.cnDomCountryDesc = cnDomCountryDesc;
	}

	public String getCnResComeDom() {
		return cnResComeDom;
	}

	public void setCnResComeDom(String cnResComeDom) {
		this.cnResComeDom = cnResComeDom;
	}

	public String getCnResAddress() {
		return cnResAddress;
	}

	public void setCnResAddress(String cnResAddress) {
		this.cnResAddress = cnResAddress;
	}

	public String getCnResZipCode() {
		return cnResZipCode;
	}

	public void setCnResZipCode(String cnResZipCode) {
		this.cnResZipCode = cnResZipCode;
	}

	public String getCnResTown() {
		return cnResTown;
	}

	public void setCnResTown(String cnResTown) {
		this.cnResTown = cnResTown;
	}

	public String getCnResCountryDesc() {
		return cnResCountryDesc;
	}

	public void setCnResCountryDesc(String cnResCountryDesc) {
		this.cnResCountryDesc = cnResCountryDesc;
	}

	public String getCnCounty() {
		return cnCounty;
	}

	public void setCnCounty(String cnCounty) {
		this.cnCounty = cnCounty;
	}

	public String getCnPostcode() {
		return cnPostcode;
	}

	public void setCnPostcode(String cnPostcode) {
		this.cnPostcode = cnPostcode;
	}

	public String getCnNation() {
		return cnNation;
	}

	public void setCnNation(String cnNation) {
		this.cnNation = cnNation;
	}

	public String getCnNOKName() {
		return cnNOKName;
	}

	public void setCnNOKName(String cnNOKName) {
		this.cnNOKName = cnNOKName;
	}

	public String getCnNOKContactNumber() {
		return cnNOKContactNumber;
	}

	public void setCnNOKContactNumber(String cnNOKContactNumber) {
		this.cnNOKContactNumber = cnNOKContactNumber;
	}

	public String getCnNOKRelationship() {
		return cnNOKRelationship;
	}

	public void setCnNOKRelationship(String cnNOKRelationship) {
		this.cnNOKRelationship = cnNOKRelationship;
	}

	public String getCnStatus() {
		return cnStatus;
	}

	public void setCnStatus(String cnStatus) {
		this.cnStatus = cnStatus;
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
		cnCollaborationPortalId,
		cnEmailAddress,
		cnFirstName,
		cnLastName,
		cnBirthPlace,
		cnBirthCountry,
		cnBirthDate,
		cnGender,

		// GBR PROFILE
		cnTaxCode,
		cnNationality,

		// Contact
		cnMobileTelephone,
		cnTelephone,

		// Address Domicilio
		cnDomAddress,
		cnDomZipCode,
		cnDomTown,
		cnDomCountryDesc,
		 
		// Address Residenza						
		cnResComeDom,
		cnResAddress,
		cnResZipCode,
		cnResTown,
		cnResCountryDesc,
		// Address ?!?
		cnCounty,
		cnPostcode,
		 
		// GBR Profile
		cnNation,
		cnNOKName,
		cnNOKContactNumber,
		cnNOKRelationship,
		cnStatus,
		cnNationalityCode
	}

}