package com.vihaan.service;

import org.springframework.http.ResponseEntity;

import com.vihaan.dto.EnqueryDto;
import com.vihaan.util.ResponseStructure;

public interface EnqueryService {

	public ResponseEntity<ResponseStructure<EnqueryDto>> createEnquery(EnqueryDto enqueryDto);
	
	public ResponseEntity<ResponseStructure<String>> MailEnquery(String toMail,String subject,String message);
}
