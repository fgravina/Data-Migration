package com.gigroup.data.migration.candidate.parser.jobalert;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.springframework.util.StringUtils;

public class LiferayDataJobAlertCvs {

	private String userId;
	private String createDate;
	private String modifiedDate;
	private String titolo;
	private String provincia;
	private String settore;
	private String area;
	private String job;
	private String descrizione;
	private String frequenza;
	private String ultimoInvio;
	
	private List<String> provinceList;

	public LiferayDataJobAlertCvs() {
	}

	public LiferayDataJobAlertCvs(String[] fields) {

		this.userId = fields[fieldName.userId.ordinal()];
		this.createDate = fields[fieldName.createDate.ordinal()];
		this.modifiedDate = fields[fieldName.modifiedDate.ordinal()];
		this.titolo = fields[fieldName.titolo.ordinal()];
		this.provincia = fields[fieldName.provincia.ordinal()];
		this.settore = fields[fieldName.settore.ordinal()];
		this.area = fields[fieldName.area.ordinal()];
		this.job = fields[fieldName.job.ordinal()];
		this.descrizione = fields[fieldName.descrizione.ordinal()];
		this.frequenza = fields[fieldName.frequenza.ordinal()];
		this.ultimoInvio = fields[fieldName.ultimoInvio.ordinal()];		
		
		this.provinceList = StringUtils.hasText(this.provincia) ? Arrays.asList(this.provincia.replace("\"", "").split(",")) : Collections.emptyList();
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

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getProvincia() {
		return provincia;
	}	

	public List<String> getProvinceList() {
		return provinceList;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getSettore() {
		return settore;
	}

	public void setSettore(String settore) {
		this.settore = settore;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getFrequenza() {
		return frequenza;
	}

	public void setFrequenza(String frequenza) {
		this.frequenza = frequenza;
	}

	public String getUltimoInvio() {
		return ultimoInvio;
	}

	public void setUltimoInvio(String ultimoInvio) {
		this.ultimoInvio = ultimoInvio;
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
		userId, createDate, modifiedDate, titolo, provincia, settore, area, job, descrizione, frequenza, ultimoInvio
	}
}