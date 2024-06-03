package com.vihaan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vihaan.dto.EmployerRequestDto;
import com.vihaan.dto.EmployerResponseDto;
import com.vihaan.dto.LoginDto;
import com.vihaan.service.EmployerService;
import com.vihaan.util.ResponseStructure;

@RestController
@CrossOrigin
@Validated
public class EmployerController {
	
	@Autowired
	private EmployerService  employerService;
	
	@PostMapping("/employer/add")
	public ResponseEntity<ResponseStructure<EmployerResponseDto>> addEmployer(@RequestBody @Validated EmployerRequestDto employerRequestDto){
		return employerService.addEmployer(employerRequestDto);
	}
	
	@GetMapping("/employer/login")
	public ResponseEntity<ResponseStructure> userLogin(@RequestBody @Validated LoginDto dto) {
		return employerService.employerLogin(dto);
	}
	
	@GetMapping("employer/forgotpwd")
	public ResponseEntity<ResponseStructure> sendForgotPwdLink(@RequestParam String toMail) {
		String resetToken=employerService.generateResetToken();
		return employerService.sendForgotPwdLink(toMail,resetToken);
	}
	
	@PutMapping("employer/resetpwd")
	public ResponseEntity<ResponseStructure<String>> resetPassword(@RequestParam String mail,@RequestParam String newPassword,@RequestParam String confirmPwd) {
		return employerService.resetPassword(mail, newPassword,confirmPwd);
	}
	
	@GetMapping("employer/FetchById/{id}")
	public ResponseEntity<ResponseStructure<EmployerResponseDto>> FindEmployerById(@PathVariable Long id) {
		return employerService.findUserById(id);
	}
	
	@PutMapping("employer/update/{EmployerId}")
	public ResponseEntity<ResponseStructure<EmployerResponseDto>>updateEmployer(@RequestBody EmployerRequestDto requestDto,@PathVariable Long EmployerId) {
		return employerService.updateEmployer(requestDto, EmployerId);
	}
	
	@DeleteMapping("employer/delete/{id}/{password}")
	public ResponseEntity<ResponseStructure<String>> deleteEmployer(@PathVariable long id,@PathVariable String password) {
		return employerService.deleteEmployer(id, password);
	}
	
	
}
