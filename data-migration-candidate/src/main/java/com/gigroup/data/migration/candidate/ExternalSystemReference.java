package com.gigroup.data.migration.candidate;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class ExternalSystemReference extends BaseMyGiGroupEntity {

	private static final long serialVersionUID = 1L;	

	private Long systemId;

	private String girexId;

	private String type;

	private String countryCode;

	private String businessCode;

	private String externalSystemObjectId;
	
	public ExternalSystemReference(){}
	
	public ExternalSystemReference(final String girexId, final String externalSystemObjectId, final Long systemId){
		this.girexId = girexId;
		this.externalSystemObjectId = externalSystemObjectId;
		this.systemId = systemId;
	}

	public Long getSystemId() {
		return systemId;
	}

	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}

	public String getGirexId() {
		return girexId;
	}

	public void setGirexId(String girexId) {
		this.girexId = girexId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getExternalSystemObjectId() {
		return externalSystemObjectId;
	}

	public void setExternalSystemObjectId(String externalSystemObjectId) {
		this.externalSystemObjectId = externalSystemObjectId;
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