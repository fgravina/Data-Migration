package com.gigroup.data.migration.candidate.parser.candidatura;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class LiferayDataCandidaturaCvs {

	private String userId;
	private String createDate;
	private String offerId;
	private String codiceRiferimento;

	public LiferayDataCandidaturaCvs() {
	}

	public LiferayDataCandidaturaCvs(String[] fields) {

		this.userId = fields[fieldName.userId.ordinal()];
		this.createDate = fields[fieldName.createDate.ordinal()];
		this.offerId = fields[fieldName.offerId.ordinal()];
		this.codiceRiferimento = fields[fieldName.codiceRiferimento.ordinal()];
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getOfferId() {
		return offerId;
	}

	public void setOfferId(String offerId) {
		this.offerId = offerId;
	}

	public String getCodiceRiferimento() {
		return codiceRiferimento;
	}

	public void setCodiceRiferimento(String codiceRiferimento) {
		this.codiceRiferimento = codiceRiferimento;
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

	private enum fieldName {
		userId, createDate, offerId, codiceRiferimento
	}
}