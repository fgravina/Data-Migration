package com.gigroup.data.migration.candidate;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class System extends BaseMyGiGroupEntity {

	private static final long serialVersionUID = 1L;
	
	public static final String RPM_SYSTEM_DESCRIPTION = "RPM";
	public static final String LIFERAY_SYSTEM_DESCRIPTION = "LIFERAY";

	private Long systemId;

	private String description;

	public Long getSystemId() {
		return systemId;
	}

	public void setSystemId(Long systemId) {
		this.systemId = systemId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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