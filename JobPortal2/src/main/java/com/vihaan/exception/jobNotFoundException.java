package com.vihaan.exception;

public class jobNotFoundException extends RuntimeException {

	private String message;

	public jobNotFoundException(String message) {
		super();
		this.message = message;
	}
	
}
