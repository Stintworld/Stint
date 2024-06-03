package com.vihaan.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vihaan.dto.EmployerRequestDto;
import com.vihaan.dto.EmployerResponseDto;
import com.vihaan.dto.LoginDto;
import com.vihaan.util.ResponseStructure;


public interface EmployerService {

	public ResponseEntity<ResponseStructure<EmployerResponseDto>> addEmployer(EmployerRequestDto employerRequestDto) ;
	
	public ResponseEntity<ResponseStructure> employerLogin(LoginDto loginDto) ;
	
	// mail pwd:gjth ejqh mrti vjnh
	public ResponseEntity<ResponseStructure<String>> signupmail(String toMail ,String userName) ;
	
	public String  generateResetToken();
	
	public String createPwdResetLink(String resetToken) ;

	ResponseEntity<ResponseStructure> sendForgotPwdLink(String toMail, String resetToken);
	
	public ResponseEntity<ResponseStructure<String>> resetPassword(String email,String newPassword,String confirmPwd) ;
	
	public ResponseEntity<ResponseStructure<EmployerResponseDto>> findUserById(Long userId);
	public ResponseEntity<ResponseStructure<EmployerResponseDto>> updateEmployer( EmployerRequestDto requestDTO,
			 Long id) ;
	
	public ResponseEntity<ResponseStructure<String>> deleteEmployer(long userid,String password);
}
