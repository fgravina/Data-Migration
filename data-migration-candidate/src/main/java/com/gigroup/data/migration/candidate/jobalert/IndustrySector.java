package com.gigroup.data.migration.candidate.jobalert;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.gigroup.data.migration.candidate.BaseMyGiGroupEntity;

public class IndustrySector extends BaseMyGiGroupEntity {

	private static final long serialVersionUID = 1L;

	private Long industrySectorId;

	private String codeExternal;

	private String description;

	private Date validFromDate;

	private Date validToDate;

	public Long getIndustrySectorId() {
		return industrySectorId;
	}

	public void setIndustrySectorId(Long industrySectorId) {
		this.industrySectorId = industrySectorId;
	}

	public String getCodeExternal() {
		return codeExternal;
	}

	public void setCodeExternal(String codeExternal) {
		this.codeExternal = codeExternal;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getValidFromDate() {
		return validFromDate;
	}

	public void setValidFromDate(Date validFromDate) {
		this.validFromDate = validFromDate;
	}

	public Date getValidToDate() {
		return validToDate;
	}

	public void setValidToDate(Date validToDate) {
		this.validToDate = validToDate;
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