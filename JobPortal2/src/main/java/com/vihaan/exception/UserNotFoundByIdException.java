package com.vihaan.exception;

import lombok.Data;

@Data
public class UserNotFoundByIdException extends RuntimeException {

	private String message;

	public UserNotFoundByIdException(String message) {
		super();
		this.message = message;
	}
	
}
