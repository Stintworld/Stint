package com.vihaan.util;

import lombok.Data;

@Data
public class ResponseStructure <A>{	
	private int statusCode;
	private String message;
	private A data;
	
}
