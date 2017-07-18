package com.gigroup.data.migration.candidate;

import java.util.Date;

public class NaamahUser {

	public final static Long NEW = -1L;

	Long userId;

	String name;

	String telephoneNumber;

	String email;

	String login;

	String password;

	String jobDescription;

	int groupId;

	String customHomepage;

	UserType type;

	int environmentId;

	String other;

	// private List<Structure> structures;

	private byte[] hashPassword;

	private byte[] salt;

	private Boolean forceChange;

	private Date lastLogin;

	private Integer failedLogin;

	// todo: attenzione che la tabella USER_STATE è su mygigroup ma se usiamo
	// gli utenti di naamah deve essere spostata
	private UserState userState;
	
	public NaamahUser(){}
	
	public NaamahUser(String name, String email, String login) {
		//this.userId = NEW;
		this.name = name;
		//this.telephoneNumber = telephoneNumber;
		this.email = email;
		this.login = login;
		this.password = "defaultPwd";
		// setPassword(password);
		//this.groupId = groupId;
		// this.structures = new ArrayList<>();
		//this.environmentId = environmentId;
		this.type = UserType.naamah;
		this.forceChange = true;
		this.setFailedLogin(0);
		//this.lastLogin = new Date(java.lang.System.currentTimeMillis());
		this.userState = UserState.ACTIVE;
	}	

	public NaamahUser(String name, String telephoneNumber, String email, String login, int environmentId, int groupId) {
		//this.userId = NEW;
		this.name = name;
		this.telephoneNumber = telephoneNumber;
		this.email = email;
		this.login = login;
		this.password = "defaultPwd";
		// setPassword(password);
		this.groupId = groupId;
		// this.structures = new ArrayList<>();
		this.environmentId = environmentId;
		this.type = UserType.naamah;
		this.forceChange = true;
		this.setFailedLogin(0);
		this.lastLogin = new Date(java.lang.System.currentTimeMillis());
		this.userState = UserState.ACTIVE;
	}	

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	public String getCustomHomepage() {
		return customHomepage;
	}

	public void setCustomHomepage(String customHomepage) {
		this.customHomepage = customHomepage;
	}

	public UserType getType() {
		return type;
	}

	public void setType(UserType type) {
		this.type = type;
	}

	public int getEnvironmentId() {
		return environmentId;
	}

	public void setEnvironmentId(int environmentId) {
		this.environmentId = environmentId;
	}

	public String getOther() {
		return other;
	}

	public void setOther(String other) {
		this.other = other;
	}

	public byte[] getHashPassword() {
		return hashPassword;
	}

	public void setHashPassword(byte[] hashPassword) {
		this.hashPassword = hashPassword;
	}

	public byte[] getSalt() {
		return salt;
	}

	public void setSalt(byte[] salt) {
		this.salt = salt;
	}

	public Boolean getForceChange() {
		return forceChange;
	}

	public void setForceChange(Boolean forceChange) {
		this.forceChange = forceChange;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Integer getFailedLogin() {
		return failedLogin;
	}

	public void setFailedLogin(Integer failedLogin) {
		this.failedLogin = failedLogin;
	}

	public UserState getUserState() {
		return userState;
	}

	public void setUserState(UserState userState) {
		this.userState = userState;
	}

	public enum UserType {
		naamah, ldap, facebook, gmail, twitter

	}

	public enum UserState {
		ACTIVE, BLOCKED, SUSPENDED;

		public static UserState getFromMask(int mask) {
			for (UserState current : UserState.values()) {
				if (current.ordinal() == mask) {
					return current;
				}
			}
			return null;
		}
	}
}
