package com.gigroup.data.migration.candidate.anagrafica;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.gigroup.data.migration.candidate.BaseMyGiGroupEntity;


public class Address extends BaseMyGiGroupEntity {
	
	private static final long serialVersionUID = 1L;
	
	public static final Long DOMICILE_ADDRESS_TYPE_ID = 1L;
	public static final Long RESIDENCE_ADDRESS_TYPE_ID = 2L;

	private Long addressId;	

	private Long addressTypeId;

	private String countryCode;

	private String province;

	private String postalCode;

	private String city;

	private String dug;

	private String address;

	private String number;
	
	private Long personId;

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}	

	public Long getAddressTypeId() {
		return addressTypeId;
	}

	public void setAddressTypeId(Long addressTypeId) {
		this.addressTypeId = addressTypeId;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDug() {
		return dug;
	}

	public void setDug(String dug) {
		this.dug = dug;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
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