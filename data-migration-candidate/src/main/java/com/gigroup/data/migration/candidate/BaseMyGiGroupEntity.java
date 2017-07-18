package com.gigroup.data.migration.candidate;

import java.io.Serializable;
import java.util.Date;

import com.gigroup.data.migration.candidate.util.DateUtils;

public abstract class BaseMyGiGroupEntity implements Serializable {
	
	public static final String CREATION_TIMESTAMP_COLUMN_DB = "CREATION_TS";	
	public static final String CREATION_USER_COLUMN_DB = "CREATION_USER";
	public static final String LAST_UPDATE_TIMESTAMP_COLUMN_DB = "LAST_UPDATE_TS";
	public static final String LAST_UPDATE_USER_COLUMN_DB = "LAST_UPDATE_USER";
	
	private static final long serialVersionUID = 1L;

	private String creationUser;

	private Date creationTimestamp;

	private String lastUpdateUser;

	private Date lastUpdateTimestamp;
	
	public BaseMyGiGroupEntity(){
		this.creationTimestamp = DateUtils.addDays(new Date(), -1);
		this.lastUpdateTimestamp = DateUtils.addDays(new Date(), -1);
	}

	public String getCreationUser() {
		return creationUser;
	}

	public void setCreationUser(String creationUser) {
		this.creationUser = creationUser;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public String getLastUpdateUser() {
		return lastUpdateUser;
	}

	public void setLastUpdateUser(String lastUpdateUser) {
		this.lastUpdateUser = lastUpdateUser;
	}

	public Date getLastUpdateTimestamp() {
		return lastUpdateTimestamp;
	}

	public void setLastUpdateTimestamp(Date lastUpdateTimestamp) {
		this.lastUpdateTimestamp = lastUpdateTimestamp;
	}
}