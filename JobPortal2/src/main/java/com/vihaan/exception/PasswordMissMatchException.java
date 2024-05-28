package com.vihaan.exception;

import lombok.Data;

@Data
public class PasswordMissMatchException extends RuntimeException {

	private String message;

	public PasswordMissMatchException(String message) {
		super();
		this.message = message;
	}

	
}
