package com.gigroup.data.migration.candidate.util;

public class LoginNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LoginNotFoundException(String message) {
		super(message);		
	}
}