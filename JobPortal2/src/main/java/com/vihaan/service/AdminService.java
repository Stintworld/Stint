package com.vihaan.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import com.vihaan.dto.AdminRequestDto;
import com.vihaan.dto.AdminResponseDto;
import com.vihaan.util.ResponseStructure;

public interface AdminService {

	ResponseEntity<ResponseStructure<AdminResponseDto>> addAdmin(AdminRequestDto dto);

	ResponseEntity<ResponseStructure<AdminResponseDto>> adminLogin(String email, String password);

	public ResponseEntity<ResponseStructure<String>> resetpassword( String mail, String newPassword, String confirmPwd);
}