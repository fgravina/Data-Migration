package com.gigroup.data.migration.candidate.jobalert;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.gigroup.data.migration.candidate.BaseMyGiGroupEntity;

public class JobAlertFrequency extends BaseMyGiGroupEntity {

	private static final long serialVersionUID = 1L;

	private int jobAlertFrequencyId;

	private String description;

	private int numberDays;

	public int getJobAlertFrequencyId() {
		return jobAlertFrequencyId;
	}

	public void setJobAlertFrequencyId(int jobAlertFrequencyId) {
		this.jobAlertFrequencyId = jobAlertFrequencyId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getNumberDays() {
		return numberDays;
	}

	public void setNumberDays(int numberDays) {
		this.numberDays = numberDays;
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