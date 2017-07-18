package com.gigroup.data.migration.candidate.anagrafica;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.gigroup.data.migration.candidate.BaseMyGiGroupEntity;

public class PersonCountryServiceActive extends BaseMyGiGroupEntity {

	private static final long serialVersionUID = 1L;

	private Long personId;

	private String countryCode;

	private String businessCode;

	private Long serviceId;

	private String claimsCode;

	public PersonCountryServiceActive(final Long personId, final Long serviceId) {
		this.personId = personId;
		this.serviceId = serviceId;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public Long getServiceId() {
		return serviceId;
	}

	public void setServiceId(Long serviceId) {
		this.serviceId = serviceId;
	}

	public String getClaimsCode() {
		return claimsCode;
	}

	public void setClaimsCode(String claimsCode) {
		this.claimsCode = claimsCode;
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