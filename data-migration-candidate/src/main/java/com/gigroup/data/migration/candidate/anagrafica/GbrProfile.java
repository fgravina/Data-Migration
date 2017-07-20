package com.gigroup.data.migration.candidate.anagrafica;

import com.gigroup.data.migration.candidate.BaseMyGiGroupEntity;

public class GbrProfile extends BaseMyGiGroupEntity {
	
	private static final long serialVersionUID = 1L;

	private Long personId;
	private Long CollaborationPortalID;
	private String taxCode;
	private String nationality;
	private String nation;
	private String nokName;
	private String nokContactNumber;
	private Long nokReleationship;
	private String status;
	
	
	public Long getPersonId() {
		return personId;
	}
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	public Long getCollaborationPortalID() {
		return CollaborationPortalID;
	}
	public void setCollaborationPortalID(Long collaborationPortalID) {
		CollaborationPortalID = collaborationPortalID;
	}
	public String getTaxCode() {
		return taxCode;
	}
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getNokName() {
		return nokName;
	}
	public void setNokName(String nokName) {
		this.nokName = nokName;
	}
	public String getNokContactNumber() {
		return nokContactNumber;
	}
	public void setNokContactNumber(String nokContactNumber) {
		this.nokContactNumber = nokContactNumber;
	}
	public Long getNokReleationship() {
		return nokReleationship;
	}
	public void setNokReleationship(Long nokReleationship) {
		this.nokReleationship = nokReleationship;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

}