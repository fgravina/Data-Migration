package com.gigroup.data.migration.candidate.anagrafica;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.gigroup.data.migration.candidate.BaseMyGiGroupEntity;

public class Contact extends BaseMyGiGroupEntity {

	private static final long serialVersionUID = 1L;
	
	public static final Long MOBILE_CONTACT_TYPE_ID = 1L;
	public static final Long HOME_CONTACT_TYPE_ID = 2L;

	private Long contactId;

	private Long contactTypeId;

	private String value;

	private Long personId;	
	

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	public Long getContactTypeId() {
		return contactTypeId;
	}

	public void setContactTypeId(Long contactTypeId) {
		this.contactTypeId = contactTypeId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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