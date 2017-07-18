package com.gigroup.data.migration.candidate.jobalert;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.gigroup.data.migration.candidate.BaseMyGiGroupEntity;

public class JobAlert extends BaseMyGiGroupEntity {

	private static final long serialVersionUID = 1L;

	private Long jobAlertId;

	private String name;

	private int jobAlertFrequencyId;

	private Date lastSendTimestamp;

	private Long personId;

	// -------------------------------

	private String keywords;

	private String city;

	private Integer distanceFromCity; // in km

	private String professionalAreaId;

	private String industrySectorId;

	private String contractTypeId;

	private String jobTitleId;

	public Long getJobAlertId() {
		return jobAlertId;
	}

	public void setJobAlertId(Long jobAlertId) {
		this.jobAlertId = jobAlertId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getJobAlertFrequencyId() {
		return jobAlertFrequencyId;
	}

	public void setJobAlertFrequencyId(int jobAlertFrequencyId) {
		this.jobAlertFrequencyId = jobAlertFrequencyId;
	}

	public Date getLastSendTimestamp() {
		return lastSendTimestamp;
	}

	public void setLastSendTimestamp(Date lastSendTimestamp) {
		this.lastSendTimestamp = lastSendTimestamp;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getDistanceFromCity() {
		return distanceFromCity;
	}

	public void setDistanceFromCity(Integer distanceFromCity) {
		this.distanceFromCity = distanceFromCity;
	}

	public String getProfessionalAreaId() {
		return professionalAreaId;
	}

	public void setProfessionalAreaId(String professionalAreaId) {
		this.professionalAreaId = professionalAreaId;
	}

	public String getIndustrySectorId() {
		return industrySectorId;
	}

	public void setIndustrySectorId(String industrySectorId) {
		this.industrySectorId = industrySectorId;
	}

	public String getContractTypeId() {
		return contractTypeId;
	}

	public void setContractTypeId(String contractTypeId) {
		this.contractTypeId = contractTypeId;
	}

	public String getJobTitleId() {
		return jobTitleId;
	}

	public void setJobTitleId(String jobTitleId) {
		this.jobTitleId = jobTitleId;
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