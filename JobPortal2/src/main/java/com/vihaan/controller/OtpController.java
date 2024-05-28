package com.vihaan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vihaan.service.OtpService;
import com.vihaan.util.ResponseStructure;

@RestController
@Validated
@CrossOrigin
public class OtpController {

	@Autowired
	private OtpService otpService;
	
	@PostMapping("otps/sendotpmail")
	public ResponseEntity<ResponseStructure<String>> sendEmailOtp(@RequestParam String emailId) {
		return otpService.sendOtpMail(emailId);
	}
	
	@GetMapping("otps/verifyotp")
	public ResponseEntity<ResponseStructure<String>> verifyOtp(@RequestParam String mail,@RequestParam Integer otp){
		return otpService.verifyOtp(mail, otp);
		
	}
}
