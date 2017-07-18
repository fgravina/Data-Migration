package com.gigroup.data.migration.candidate.dao;

public interface BaseMyGiGroupDao {
	
	public static final String CREATION_TIMESTAMP_COLUMN_DB = "CREATION_TS";	
	public static final String CREATION_USER_COLUMN_DB = "CREATION_USER";
	public static final String LAST_UPDATE_TIMESTAMP_COLUMN_DB = "LAST_UPDATE_TS";
	public static final String LAST_UPDATE_USER_COLUMN_DB = "LAST_UPDATE_USER";
	
	public static final String GI_GROUP_BUSINESS_CODE = "GIG";
	public static final String ITALY_COUNTRY_CODE = "ITA";
	public static final String PERSON_TYPE = "PERSON";
	public static final String WRITING_CLAIMS_CODE = "SCR";
	
	public static final Long APPLIED_POSITION_STATUS_ID = 1L;
	
	public static final String CANDIDATE_ROLE_CODE = "CAND";
	public static final String LIFERAY_CREATION_USER = "liferay";
}