package com.vihaan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vihaan.dto.AdminRequestDto;
import com.vihaan.dto.AdminResponseDto;
import com.vihaan.service.AdminService;
import com.vihaan.util.ResponseStructure;



@RestController
@CrossOrigin
@Validated
public class AdminController {

	@Autowired
	private AdminService adminService;
	
	@PostMapping("admins/signup")
	public ResponseEntity<ResponseStructure<AdminResponseDto>> addAdmin(@RequestBody AdminRequestDto dto) {
		return adminService.addAdmin(dto);
	}
	@GetMapping("admins/login")
	public ResponseEntity<ResponseStructure<AdminResponseDto>> adminLogin(@RequestHeader String email,@RequestHeader String password) {
		return adminService.adminLogin(email,password);
	}
	
	@PutMapping("admins/resetpwd")
	public ResponseEntity<ResponseStructure<String>> resetpassword(@RequestParam String mail,@RequestParam String newPassword,@RequestParam String confirmPwd) {
		return adminService.resetpassword(mail,newPassword,confirmPwd);
	}
	@GetMapping("admins/getAllAdmins")
	public ResponseEntity<ResponseStructure<List<AdminResponseDto>>> getAllAdmins(){
		return adminService.getAllAdmins();
	}
	@PutMapping("admins/deletebyadminhead/{adminHeadId}/{adminId}")
	public ResponseEntity<ResponseStructure<String>> deleteAdmin(@PathVariable Long adminHeadId,@PathVariable Long adminId){
		return adminService.deleteAdmin(adminHeadId, adminId);
	}
	@PutMapping("admins/deletebyadmin/{adminId}")
	public ResponseEntity<ResponseStructure<String>> deleteAdmin(@PathVariable Long adminId){
		return adminService.deleteAdmin(adminId);
	}
	
	
}
