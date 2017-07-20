package com.gigroup.data.migration.candidate;

public class NOKRelationship {

	private Long nokRelationshipId;
	private String description;
	
	public NOKRelationship(){}
	
	public NOKRelationship(String description) {
		this.description = description;
	}

	public Long getNokRelationshipId() {
		return nokRelationshipId;
	}

	public void setNokRelationshipId(Long nokRelationshipId) {
		this.nokRelationshipId = nokRelationshipId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	

}
