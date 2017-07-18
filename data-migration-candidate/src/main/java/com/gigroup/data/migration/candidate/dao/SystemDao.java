package com.gigroup.data.migration.candidate.dao;

import java.util.List;
import com.gigroup.data.migration.candidate.System;

public interface SystemDao extends BaseMyGiGroupDao {
	
	public static String BEAN_NAME = "systemDao";
	
	List<System> getAllSystem();
}