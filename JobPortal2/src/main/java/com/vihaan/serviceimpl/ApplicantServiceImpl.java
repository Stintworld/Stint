package com.vihaan.serviceimpl;

import java.io.IOException;
import java.util.Optional;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.vihaan.dto.ApplicantRequestDto;
import com.vihaan.dto.ApplicantResponseDto;
import com.vihaan.dto.LoginDto;
import com.vihaan.dto.ProfileDto;
import com.vihaan.entity.Applicant;
import com.vihaan.entity.ISDELETED;
import com.vihaan.entity.Profile;
import com.vihaan.exception.EmailNotFoundException;
import com.vihaan.exception.PasswordMissMatchException;
import com.vihaan.exception.UserNotFoundByIdException;
import com.vihaan.exception.UserWithSameEmailExist;
import com.vihaan.repo.ApplicantRepo;
import com.vihaan.repo.ProfileRepo;
import com.vihaan.service.ApplicantService;
import com.vihaan.util.ResponseStructure;
import com.vihaan.util.paymentLinkResponse;

@Service
public class ApplicantServiceImpl implements ApplicantService{

	@Autowired
	private ApplicantRepo applicantRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ProfileRepo profileRepo;
	@Override
	public ResponseEntity<ResponseStructure<ApplicantResponseDto>> addApplicant(ApplicantRequestDto requestDto) {
		      Applicant applicant = applicantRepo.findByApplicantEmail(requestDto.getApplicantEmail());
		      if (applicant!=null) {
				throw new UserWithSameEmailExist("Applicant With Same Email Exist");
			}
		      Applicant applicant2= new Applicant();
		      applicant2.setApplicantEmail(requestDto.getApplicantEmail());
		      applicant2.setApplicantName(requestDto.getApplicantName());
		      String encodedPassword = passwordEncoder.encode(requestDto.getApplicantPassword());
		      applicant2.setApplicantPassword(encodedPassword);
		      applicant2.setApplicantPhNo(requestDto.getApplicantPhNo());
		      applicant2.setDob(requestDto.getDOB());
		      applicant2.setGender(requestDto.getGender());
		      applicant2.setIsdeleted(ISDELETED.FALSE);
		      applicant2.setJobLevel(requestDto.getJobLevel());
		      Profile profile= new Profile();
		      profile.setEmail(applicant2.getApplicantEmail());
		      profile.setPhNo(applicant2.getApplicantPhNo());
		      profile.setFirstName(applicant2.getApplicantName());
		      profileRepo.save(profile);
		      applicant2.setProfile(profile);
		      
		      Applicant applicant3 = applicantRepo.save(applicant2);
		      ApplicantResponseDto responseDto = this.modelMapper.map(applicant3, ApplicantResponseDto.class);
		      ResponseStructure<ApplicantResponseDto>responseStructure= new ResponseStructure<ApplicantResponseDto>();
		      responseStructure.setData(responseDto);
		      responseStructure.setMessage("Applicant SignUp Successful");
		      responseStructure.setStatusCode(HttpStatus.CREATED.value());
		return new ResponseEntity<ResponseStructure<ApplicantResponseDto>>(responseStructure,HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<ResponseStructure<ApplicantResponseDto>> addApplicantImage( Long applicantId,
			MultipartFile file) throws IOException {
		 Optional<Applicant> optional = applicantRepo.findById(applicantId);
		 if (optional.isEmpty()) {
			throw new UserNotFoundByIdException("User not found by this id");
		}
		 Applicant applicant = optional.get();
	      
	      applicant.setApplicantImage(file.getBytes());
	      Applicant applicant3 = applicantRepo.save(applicant);
	      ApplicantResponseDto responseDto = this.modelMapper.map(applicant3, ApplicantResponseDto.class);
	      ResponseEntity<byte[]> applicantImage = getApplicantImage(applicantId);
	      byte[] imageBody = applicantImage.getBody();
	      responseDto.setApplicantImage(imageBody);
	      ResponseStructure<ApplicantResponseDto>responseStructure= new ResponseStructure<ApplicantResponseDto>();
	      responseStructure.setData(responseDto);
	      responseStructure.setMessage("Applicant Image Added Successful");
	      responseStructure.setStatusCode(HttpStatus.CREATED.value());
		    
		return new ResponseEntity<ResponseStructure<ApplicantResponseDto>>(responseStructure,HttpStatus.CREATED);
	}
	
	@Override
	 public ResponseEntity<byte[]> getApplicantImage(Long applicantId) {
	        Optional<Applicant> applicant = applicantRepo.findById(applicantId);
	        if (applicant.isPresent()) {
	            byte[] image = applicant.get().getApplicantImage();
	           
	            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG ).body(image);
	               
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

	 @Override
	 public ResponseEntity<byte[]> getApplicantResume(Long applicantId) {
	        Optional<Applicant> applicant = applicantRepo.findById(applicantId);
	        if (applicant.isPresent()) {
	            byte[] resume = applicant.get().getApplicantResume();
	           
	            return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF ).body(resume);
	               
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	 
	@Override
	public ResponseEntity<ResponseStructure<ApplicantResponseDto>> applicantLogin(LoginDto loginDto) {
		        Applicant applicant = applicantRepo.findByApplicantEmail(loginDto.getEmailId());
		        if (applicant==null) {
					throw new EmailNotFoundException("Applicant details not found");
					
				}
		        if (!passwordEncoder.matches(loginDto.getPassword(), applicant.getApplicantPassword())) {
					throw new PasswordMissMatchException("Wrong Password ,please try again");
				}
		        ApplicantResponseDto responseDto = this.modelMapper.map(applicant, ApplicantResponseDto.class);
		        ResponseEntity<byte[]> applicantImage = getApplicantImage(applicant.getApplicantId());
			      byte[] imageBody = applicantImage.getBody();
			      responseDto.setApplicantImage(imageBody);
		        ResponseStructure<ApplicantResponseDto>responseStructure= new ResponseStructure<ApplicantResponseDto>();
		        responseStructure.setData(responseDto);
		        responseStructure.setMessage("Login Successfull");
		        responseStructure.setStatusCode(HttpStatus.OK.value());
		        return new ResponseEntity<ResponseStructure<ApplicantResponseDto>>(responseStructure,HttpStatus.OK);
		 
	}
	
	@Override
	public ResponseEntity<ResponseStructure<String>> addApplicantResume(Long applicantId, MultipartFile file)
			throws IOException {
		Optional<Applicant> optional = applicantRepo.findById(applicantId);
		 if (optional.isEmpty()) {
			throw new UserNotFoundByIdException("User not found by this id");
		}
		 Applicant applicant = optional.get();
	      applicant.setApplicantResume(file.getBytes());
	      applicantRepo.save(applicant);
	      ResponseStructure<String>structure= new ResponseStructure<String>();
	      structure.setData("Resume Uploaded To DB Successfully");
	      structure.setMessage("Resume Uploaded ");
	      structure.setStatusCode(HttpStatus.CREATED.value());
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.CREATED);
	}
	
	@Override
	public ResponseEntity<ResponseStructure<ApplicantResponseDto>> FetchApplicantById(Long id) {
		
		return null;
	}

	@Override
	public ResponseEntity<paymentLinkResponse> createPaymentLink(Long applicantId) throws RazorpayException {
		
		Optional<Applicant> optional = applicantRepo.findById(applicantId);
		if (optional.isEmpty()) {
		    throw new UserNotFoundByIdException("Applicant ot found to accept amount fom him");
		    
		}
		Applicant applicant2 = optional.get();
		 RazorpayClient razorpayClient = new RazorpayClient("rzp_test_kLQyG6uNdfEtHq", "LxnhqKi8SlVrMlaIB6kwuhTf");
		
		 JSONObject paymentLinkRequest = new JSONObject();
		 paymentLinkRequest.put("amount", 100);
		 paymentLinkRequest.put("currency", "INR");
		 
		 JSONObject customer= new JSONObject();
		 customer.put("name",applicant2.getApplicantName());
		 customer.put("email", applicant2.getApplicantEmail());
//		 customer.put("contact", applicant2.getApplicantPhNo());
		
		 paymentLinkRequest.put("customer", customer);
		 JSONObject notify= new JSONObject();
		 notify.put("sms", true);
		 notify.put("email", true);
		 
		 paymentLinkRequest.put("notify", notify);
		 
		 paymentLinkRequest.put("callback_url", "");
		 
		 paymentLinkRequest.put("callback_method", "get");
		 
		 PaymentLink paymentLink = razorpayClient.paymentLink.create(paymentLinkRequest);
		 
		 String paymentLinkId=paymentLink.get("id");
		 String paymentLinkUrl=paymentLink.get("short_url");
		 
		 paymentLinkResponse linkResponse= new paymentLinkResponse();
		 linkResponse.setPaymentLinkId(paymentLinkId);
		 linkResponse.setPaymentLinkURL(paymentLinkUrl);
		return new ResponseEntity<paymentLinkResponse>(linkResponse,HttpStatus.CREATED);
	}

	

	

	

	

	

}
