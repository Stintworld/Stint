package com.vihaan.service;

import org.springframework.http.ResponseEntity;

import com.vihaan.util.ResponseStructure;

public interface OtpService  {

	public int generateOtp();
	
	public ResponseEntity<ResponseStructure<String>> sendOtpMail(String toMail ) ;
	
	
	public ResponseEntity<ResponseStructure<String>> verifyOtp(String mail, int otp) ;
}
