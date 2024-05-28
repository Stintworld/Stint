package com.vihaan.serviceimpl;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.vihaan.entity.Otp;
import com.vihaan.exception.ForbiddenOperationException;
import com.vihaan.repo.OtpRepo;
import com.vihaan.service.OtpService;
import com.vihaan.util.ResponseStructure;

@Service
public class OtpServiceImpl implements OtpService{

	@Autowired
    private	JavaMailSender javaMailSender;
	@Autowired
	private OtpRepo otpRepo;
	
	@Override
	public int generateOtp() {
		Random random= new Random();
		int nextInt = 100000+random.nextInt(900000);
		System.out.println(nextInt);
		return nextInt;
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> sendOtpMail(String toMail) {
		 Integer generatedOtp = generateOtp();
		
		    Otp otp2 = otpRepo.findByGmail(toMail);
		    Otp otp= new Otp();
		    if (otp2!=null) {
				otp2.setOtpNumber(generatedOtp);
				Long otpId = otp2.getOtpId();
				otp.setOtpId(otpId);
				otp.setGmail(toMail);
				otp.setOtpNumber(generatedOtp);
			}
		    else {
		    	
				 otp.setGmail(toMail);
				 otp.setOtpNumber(generatedOtp);
			}
		 otpRepo.save(otp);
		SimpleMailMessage mailMessage = new  SimpleMailMessage();
		mailMessage.setTo(toMail);
		mailMessage.setSubject("OTP VERIFICATION");
		mailMessage.setText("Dear "+ generatedOtp+" is the OTP for accessing Stint");
		javaMailSender.send(mailMessage);
		ResponseStructure<String> responseStructure= new ResponseStructure<String>();
		responseStructure.setData(mailMessage.getText());
		responseStructure.setMessage(mailMessage.getText());
		responseStructure.setStatusCode(HttpStatus.OK.value());
		return  new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> verifyOtp(String mail, int otp) {
		      Otp otp2 = otpRepo.findByGmail(mail);
		      if (otp2==null) {
				throw new ForbiddenOperationException("Email otp not yet sent to user");
			}
		      if (otp2.getOtpNumber()!=otp) {
		    	  throw new ForbiddenOperationException("Wrong OTP please enter valid otp");
			}
		      ResponseStructure<String>structure= new ResponseStructure<String>();
		      structure.setData("OTP verified ");
		      structure.setMessage("OTP verified Successfully ");
		      structure.setStatusCode(HttpStatus.OK.value());
		      
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.OK);
	}

	
}
