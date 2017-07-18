package com.gigroup.data.migration.candidate.anagrafica.dao;

import java.util.List;

import com.gigroup.data.migration.candidate.NaamahUser;
import com.gigroup.data.migration.candidate.anagrafica.Contact;
import com.gigroup.data.migration.candidate.anagrafica.Person;
import com.gigroup.data.migration.candidate.dao.BaseMyGiGroupDao;

public interface AnagraficaDao extends BaseMyGiGroupDao {
	
	void addAnagrafica(List<NaamahUser> naamahUserList, final List<Person> personList, final List<Contact> contactList);
	
}