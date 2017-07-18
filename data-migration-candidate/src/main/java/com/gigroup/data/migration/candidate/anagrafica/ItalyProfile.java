package com.gigroup.data.migration.candidate.anagrafica;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.gigroup.data.migration.candidate.BaseMyGiGroupEntity;

public class ItalyProfile extends BaseMyGiGroupEntity {
	
	private static final long serialVersionUID = 1L;

	private Long personId;

	private String taxCode;

	private Boolean noTaxCode;

	private Boolean privacyTraining;

	private Boolean privacyMarketing;

	private Boolean privacyGiGroup;

	private Boolean legallyProtectedStatus;

	private Boolean carAvailable;

	private Boolean driverLicenseA;

	private Boolean driverLicenseB;

	private Boolean driverLicenseC;

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}

	public Boolean getNoTaxCode() {
		return noTaxCode;
	}

	public void setNoTaxCode(Boolean noTaxCode) {
		this.noTaxCode = noTaxCode;
	}

	public Boolean getPrivacyTraining() {
		return privacyTraining;
	}

	public void setPrivacyTraining(Boolean privacyTraining) {
		this.privacyTraining = privacyTraining;
	}

	public Boolean getPrivacyMarketing() {
		return privacyMarketing;
	}

	public void setPrivacyMarketing(Boolean privacyMarketing) {
		this.privacyMarketing = privacyMarketing;
	}

	public Boolean getPrivacyGiGroup() {
		return privacyGiGroup;
	}

	public void setPrivacyGiGroup(Boolean privacyGiGroup) {
		this.privacyGiGroup = privacyGiGroup;
	}

	public Boolean getLegallyProtectedStatus() {
		return legallyProtectedStatus;
	}

	public void setLegallyProtectedStatus(Boolean legallyProtectedStatus) {
		this.legallyProtectedStatus = legallyProtectedStatus;
	}

	public Boolean getCarAvailable() {
		return carAvailable;
	}

	public void setCarAvailable(Boolean carAvailable) {
		this.carAvailable = carAvailable;
	}

	public Boolean getDriverLicenseA() {
		return driverLicenseA;
	}

	public void setDriverLicenseA(Boolean driverLicenseA) {
		this.driverLicenseA = driverLicenseA;
	}

	public Boolean getDriverLicenseB() {
		return driverLicenseB;
	}

	public void setDriverLicenseB(Boolean driverLicenseB) {
		this.driverLicenseB = driverLicenseB;
	}

	public Boolean getDriverLicenseC() {
		return driverLicenseC;
	}

	public void setDriverLicenseC(Boolean driverLicenseC) {
		this.driverLicenseC = driverLicenseC;
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
}