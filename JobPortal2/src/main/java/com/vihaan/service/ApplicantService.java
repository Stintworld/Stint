package com.vihaan.service;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.multipart.MultipartFile;

import com.razorpay.RazorpayException;
import com.vihaan.dto.ApplicantRequestDto;
import com.vihaan.dto.ApplicantResponseDto;
import com.vihaan.dto.LoginDto;
import com.vihaan.dto.ProfileDto;
import com.vihaan.entity.Profile;
import com.vihaan.util.ResponseStructure;
import com.vihaan.util.paymentLinkResponse;

public interface ApplicantService {

	public ResponseEntity<ResponseStructure<ApplicantResponseDto>> addApplicant(@RequestBody ApplicantRequestDto requestDto);
	
	public ResponseEntity<ResponseStructure<ApplicantResponseDto>> addApplicantImage (@PathVariable Long applicantId, @ModelAttribute MultipartFile file)throws IOException;
	
	public ResponseEntity<ResponseStructure<String>> addApplicantResume (@PathVariable Long applicantId, @ModelAttribute MultipartFile file)throws IOException;
	public ResponseEntity<ResponseStructure<ApplicantResponseDto>> applicantLogin(@RequestHeader String emailId,@RequestHeader String password);
	
	public ResponseEntity<ResponseStructure<ApplicantResponseDto>> FetchApplicantById(Long id);

public	ResponseEntity<byte[]> getApplicantImage(Long applicantId);
	
public	ResponseEntity<byte[]> getApplicantResume(Long applicantId);

public ResponseEntity<paymentLinkResponse> createPaymentLink(@PathVariable Long applicantId) throws RazorpayException ;

public ResponseEntity<ResponseStructure<ApplicantResponseDto>> getApplicantById(Long applicantId);


}
