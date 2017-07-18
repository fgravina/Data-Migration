package com.gigroup.data.migration.candidate.jobalert.dao;

import java.util.List;

import com.gigroup.data.migration.candidate.dao.BaseMyGiGroupDao;
import com.gigroup.data.migration.candidate.jobalert.ProfessionalArea;

public interface ProfessionalAreaDao extends BaseMyGiGroupDao {
	
	public static String BEAN_NAME = "professionalAreaDao";
	
	List<ProfessionalArea> getAll();
}