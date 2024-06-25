package com.vihaan.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class EnqueryDto {

	private Long EnqueryId;
	private String fromName;
	private String fromEmail;
	private LocalDateTime enqueryCreatedDateTime;
	private String subject;
	private String message;
	
}
