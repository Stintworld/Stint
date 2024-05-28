package com.vihaan.exception;

public class UserWithSameEmailExist extends RuntimeException {

	private	String message;

	public UserWithSameEmailExist(String message) {
		super();
		this.message = message;
	}
	
}
