package com.gigroup.data.migration.candidate.candidatura;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.gigroup.data.migration.candidate.BaseMyGiGroupEntity;

public class AppliedPosition extends BaseMyGiGroupEntity {

	private static final long serialVersionUID = 1L;

	private Long appliedPositionId;

	private Long appliedPositionStatusId;

	private Long jobPostingId;

	private Date appliedPositionTimestamp;

	private String coverLetter;

	private Long personId;

	public Long getAppliedPositionId() {
		return appliedPositionId;
	}

	public void setAppliedPositionId(Long appliedPositionId) {
		this.appliedPositionId = appliedPositionId;
	}

	public Long getAppliedPositionStatusId() {
		return appliedPositionStatusId;
	}

	public void setAppliedPositionStatusId(Long appliedPositionStatusId) {
		this.appliedPositionStatusId = appliedPositionStatusId;
	}

	public Long getJobPostingId() {
		return jobPostingId;
	}

	public void setJobPostingId(Long jobPostingId) {
		this.jobPostingId = jobPostingId;
	}

	public Date getAppliedPositionTimestamp() {
		return appliedPositionTimestamp;
	}

	public void setAppliedPositionTimestamp(Date appliedPositionTimestamp) {
		this.appliedPositionTimestamp = appliedPositionTimestamp;
	}

	public String getCoverLetter() {
		return coverLetter;
	}

	public void setCoverLetter(String coverLetter) {
		this.coverLetter = coverLetter;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
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