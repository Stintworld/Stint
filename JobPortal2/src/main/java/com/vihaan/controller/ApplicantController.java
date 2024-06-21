package com.vihaan.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.razorpay.RazorpayException;
import com.vihaan.dto.ApplicantRequestDto;
import com.vihaan.dto.ApplicantResponseDto;
import com.vihaan.dto.LoginDto;
import com.vihaan.service.ApplicantService;
import com.vihaan.util.ResponseStructure;
import com.vihaan.util.paymentLinkResponse;

@RestController
@CrossOrigin
@Validated
public class ApplicantController {
	
	@Autowired
	private ApplicantService applicantService;
	
	
	
	@PostMapping("applicants/signup")
	public ResponseEntity<ResponseStructure<ApplicantResponseDto>> addApplicant(@RequestBody ApplicantRequestDto requestDto) {
		return applicantService.addApplicant(requestDto);
	}
	
	@PostMapping("applicants/addimage/{applicantId}")
	public ResponseEntity<ResponseStructure<ApplicantResponseDto>> addApplicantImage(@PathVariable Long applicantId,@ModelAttribute MultipartFile image) throws IOException {
		return applicantService.addApplicantImage(applicantId, image);
	}
	
	@PostMapping("applicants/addresume/{applicantId}")
	public ResponseEntity<ResponseStructure<String>> addApplicantResume(@PathVariable Long applicantId,@ModelAttribute MultipartFile resume) throws IOException {
		return applicantService.addApplicantResume(applicantId, resume);
	}
	
	@GetMapping("applicants/findImage/{applicantId}")
	public ResponseEntity<byte[]> getApplicantImage(@PathVariable  Long applicantId){
		return applicantService.getApplicantImage(applicantId);
	}
	
	@GetMapping("applicants/findResume/{applicantId}")
	public ResponseEntity<byte[]> getApplicantResume(@PathVariable  Long applicantId){
		return applicantService.getApplicantResume(applicantId);
	}
	
	@GetMapping("applicants/login")
	public ResponseEntity<ResponseStructure<ApplicantResponseDto>> applicantLogin(@RequestHeader String emailId,@RequestHeader String password) {
		  return applicantService.applicantLogin(emailId,password);
	}
	
	@PostMapping("applicants/payment/{applicantid}")
	public ResponseEntity<paymentLinkResponse> createPaymentLink(@PathVariable Long applicantid) throws RazorpayException {
		return applicantService.createPaymentLink(applicantid);
	}
	
	@GetMapping("applicants/getApplicantById/{applicantId}")
	public ResponseEntity<ResponseStructure<ApplicantResponseDto>> getApplicantById(@PathVariable Long applicantId) {
		return applicantService.getApplicantById(applicantId);
	}
	@PutMapping("applicants/resetpwd")
	public ResponseEntity<ResponseStructure<String>> resetPassword(@RequestParam String mail,@RequestParam String newPassword,@RequestParam String confirmPwd) {
		return applicantService.resetPassword(mail,newPassword,confirmPwd);
	}
	@GetMapping("applicants/getbyjobid/{jobId}")
	public ResponseEntity<ResponseStructure<List<ApplicantResponseDto>>> getApplicantListByJobId(@PathVariable Long jobId) {
		return applicantService.getApplicantListByJobId(jobId);
	}
	
	@GetMapping("applicants/getallapplicants")
	public ResponseEntity<ResponseStructure<List<ApplicantResponseDto>>> getAllApplicants(){
		return applicantService.getAllApplicants();
	}
}
