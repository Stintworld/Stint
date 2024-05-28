package com.vihaan.exception;

import lombok.Data;


public class JobApplicationNotFoundException extends RuntimeException {

	private String message;

	public JobApplicationNotFoundException(String message) {
		super();
		this.message = message;
	}
	
}
