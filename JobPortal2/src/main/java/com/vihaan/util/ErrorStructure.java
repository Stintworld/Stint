package com.vihaan.util;

import lombok.Data;

@Data
public class ErrorStructure {

	private int statusCode;
	private String message;
	private String rootCause;
	
	

}
