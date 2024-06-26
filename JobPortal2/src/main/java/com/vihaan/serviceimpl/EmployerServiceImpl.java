package com.vihaan.serviceimpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import com.vihaan.dto.EmployerRequestDto;
import com.vihaan.dto.EmployerResponseDto;
import com.vihaan.dto.LoginDto;
import com.vihaan.entity.Employer;
import com.vihaan.entity.ISDELETED;
import com.vihaan.exception.EmailNotFoundException;
import com.vihaan.exception.ForbiddenOperationException;
import com.vihaan.exception.PasswordMissMatchException;
import com.vihaan.exception.UserNotFoundByIdException;
import com.vihaan.exception.UserWithSameEmailExist;
import com.vihaan.repo.EmployerRepo;
import com.vihaan.service.EmployerService;
import com.vihaan.util.ResponseStructure;
@Service
public class EmployerServiceImpl implements EmployerService{

	@Autowired
	private EmployerRepo employerRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JavaMailSender javaMailSender;
	@Autowired
	private ModelMapper modelMapper;
	@Override
	public ResponseEntity<ResponseStructure<EmployerResponseDto>> addEmployer(EmployerRequestDto employerRequestDto) {
		Optional<Employer> optional = employerRepo.findByEmployerEmail(employerRequestDto.getEmployerEmail());
		if (optional.isPresent()&&optional.get().getDeleteStatus()==ISDELETED.FALSE) {
			throw new UserWithSameEmailExist("Employer Email is already in Database");
		}
		
		Employer employer2 = this.modelMapper.map(employerRequestDto, Employer.class);
		String encodedPassword = passwordEncoder.encode(employerRequestDto.getEmployerPassword());
		employer2.setEmployerPassword(encodedPassword);
		employer2.setDeleteStatus(ISDELETED.FALSE);
		Employer employer3 = employerRepo.save(employer2);
		EmployerResponseDto employerResponseDto = this.modelMapper.map(employer3, EmployerResponseDto.class);
		ResponseStructure<EmployerResponseDto>responseStructure= new  ResponseStructure<EmployerResponseDto>();
		responseStructure.setData(employerResponseDto);
		responseStructure.setMessage("Welcome to Job Portal SignUp Successfull");
		responseStructure.setStatusCode(HttpStatus.CREATED.value());
//    	signupmail(employer3.getEmployerEmail(), employer3.getEmployerName());
		return new ResponseEntity<ResponseStructure<EmployerResponseDto>>(responseStructure,HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure> employerLogin( String emailId, String password) {
		Optional<Employer> optional = employerRepo.findByEmployerEmail(emailId);
		Employer employer = optional.get();
		if (optional.isEmpty()||employer.getDeleteStatus()==ISDELETED.TRUE) {
			throw new EmailNotFoundException("Email not Found in DB");
		}
		 else if (!passwordEncoder.matches(password, employer.getEmployerPassword())) {
				throw  new PasswordMissMatchException("Password is Not Matching");
			}
		  else if (employer.getDeleteStatus()==ISDELETED.TRUE) {
				throw  new  EmailNotFoundException("Employer Not Found");
			}

		
		EmployerResponseDto employerResponseDto = this.modelMapper.map(employer, EmployerResponseDto.class);
		ResponseStructure<EmployerResponseDto>responseStructure= new  ResponseStructure<EmployerResponseDto>();
		responseStructure.setData(employerResponseDto);
		responseStructure.setMessage("login Successfull");
		responseStructure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure>(responseStructure,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> signupmail(String toMail, String userName) {
		SimpleMailMessage mailMessage = new  SimpleMailMessage();
		mailMessage.setTo(toMail);
		mailMessage.setSubject("Welcome");
		mailMessage.setText("Dear "+userName+" your account successfully created ,WELCOME TO JOB PORTAL ");
		javaMailSender.send(mailMessage);
		ResponseStructure<String> responseStructure= new ResponseStructure<String>();
		responseStructure.setData(mailMessage.getText());
		responseStructure.setMessage(mailMessage.getText());
		responseStructure.setStatusCode(HttpStatus.OK.value());
		return  new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.OK);
	}

	@Override
	public String generateResetToken() {
		String resetToken=UUID.randomUUID().toString();
		return resetToken;
	}

	@Override
	public String createPwdResetLink(String resetToken) {
		// TODO Auto-generated method stub
		return "http://localhost:3000/reset/";
	}

	@Override
	public ResponseEntity<ResponseStructure> sendForgotPwdLink(String toMail, String resetToken) {
		Optional<Employer> optional = employerRepo.findByEmployerEmail(toMail);
		if( optional.isEmpty()&&optional.get().getDeleteStatus()==ISDELETED.TRUE) {
			throw new  EmailNotFoundException("Email not found in Database");
		}
		Employer employer = optional.get();
		SimpleMailMessage mailMessage= new  SimpleMailMessage();
		mailMessage.setTo(toMail);
		mailMessage.setSubject("Link to Reset Password");
		mailMessage.setText("Reset the password using  below link "+createPwdResetLink(resetToken)+toMail);
		
		javaMailSender.send(mailMessage);
		ResponseStructure<String> responseStructure= new ResponseStructure<String>();
		responseStructure.setData("Mail sent to reset password");
		responseStructure.setMessage("Mail sent to reset password");
		responseStructure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure>(responseStructure,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> resetPassword(String email, String newPassword,
			String confirmPwd) {
		Optional<Employer> optional = employerRepo.findByEmployerEmail(email);
		if (optional.isEmpty() ||optional.get().getDeleteStatus()==ISDELETED.TRUE) {
			throw new  EmailNotFoundException("Email not found in Database");
		}
		Employer employer = optional.get();
		if (!newPassword.equals(confirmPwd)) {
			throw new PasswordMissMatchException("password should match with confirm password");
		}
		if (passwordEncoder.matches(newPassword, employer.getEmployerPassword())) {
			throw new ForbiddenOperationException("you are trying to reset with same password ");
		}
		employer.setEmployerPassword(passwordEncoder.encode(newPassword));
		employerRepo.save(employer);
		ResponseStructure<String>responseStructure= new ResponseStructure<String>();
		responseStructure.setData("Password reset successfully");
		responseStructure.setMessage("Dear "+employer.getEmployerName()+" your password reset successfully");
		responseStructure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<ResponseStructure<EmployerResponseDto>> findUserById(Long userId) {
		Optional<Employer> optionalEmployer = employerRepo.findById(userId);
		if (optionalEmployer.isEmpty()||optionalEmployer.get().getDeleteStatus()==ISDELETED.TRUE) {
			throw new UserNotFoundByIdException("Employer not Found By this ID");
		}
		Employer employer = optionalEmployer.get();

		
		EmployerResponseDto employerResponseDto = this.modelMapper.map(employer, EmployerResponseDto.class);
		ResponseStructure<EmployerResponseDto>responseStructure= new  ResponseStructure<EmployerResponseDto>();
		responseStructure.setData(employerResponseDto);
		responseStructure.setMessage("Employer found by this Id");
		responseStructure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<EmployerResponseDto>>(responseStructure,HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> updateEmployer(EmployerRequestDto requestDTO,
			Long id) {
		 Optional<Employer> optional = employerRepo.findById(id);
		 if (optional.isEmpty()||optional.get().getDeleteStatus()==ISDELETED.TRUE) {
			throw new UserNotFoundByIdException("Employer Not Found in DB to Update");
		}
		 Employer employer = optional.get();
		 employer.setEmployerName(requestDTO.getEmployerName());
		 employer.setEmployerPhNo(requestDTO.getEmployerPhNo());
		 employer.setOrganisation(requestDTO.getOrganisation());
		 employer.setOrgLocation(requestDTO.getOrgLocation());
		 Employer employer2 = employerRepo.save(employer);

		 
		 EmployerResponseDto employerResponseDto = this.modelMapper.map(employer2, EmployerResponseDto.class);
			ResponseStructure<String>responseStructure= new  ResponseStructure<String>();
			responseStructure.setData("Employer data Updated");
			responseStructure.setMessage("Employer Data Updated");
			responseStructure.setStatusCode(HttpStatus.OK.value());
			return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> deleteEmployer(long EmployerId, String password) {
		Optional<Employer> optional = employerRepo.findById(EmployerId);
		if (optional.isEmpty()||optional.get().getDeleteStatus()==ISDELETED.TRUE) {
			throw new UserNotFoundByIdException("Employer Not Found By this id");
		}
		Employer employer = optional.get();
		if (!passwordEncoder.matches(password, employer.getEmployerPassword())) {
			throw new PasswordMissMatchException("deletion failed due to password mismatching ");
		}
		employer.setDeleteStatus(ISDELETED.TRUE);
		Employer employer2 = employerRepo.save(employer);
		ResponseStructure<String>responseStructure= new  ResponseStructure<String>();
		responseStructure.setData("Employer Deleted successfully");
		responseStructure.setMessage("Employer Data Deleted");
		responseStructure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<ResponseStructure<List<EmployerResponseDto>>> getAllEmployers() {
		List<Employer> employers = employerRepo.findAll();
		List<EmployerResponseDto>responseDtos=new ArrayList<EmployerResponseDto>();
		for (Employer employer : employers) {
			if (employer.getDeleteStatus()==ISDELETED.FALSE) {
				EmployerResponseDto responseDto = this.modelMapper.map(employer, EmployerResponseDto.class);
				responseDtos.add(responseDto);
			}
			
		}
		ResponseStructure<List<EmployerResponseDto>>structure= new ResponseStructure<List<EmployerResponseDto>>();
		structure.setData(responseDtos);
		structure.setMessage("All employers data fetched successfully");
		structure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<List<EmployerResponseDto>>>(structure,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> deleteEmployer(long userid) {
		Optional<Employer> optional = employerRepo.findById(userid);
		 if (optional.isEmpty()||optional.get().getDeleteStatus()==ISDELETED.TRUE) {
		   throw new UserNotFoundByIdException("Employer Not Found By this Id");
		}
		 Employer employer = optional.get();
		 employer.setDeleteStatus(ISDELETED.TRUE);
		 employerRepo.save(employer);
		 ResponseStructure<String>structure= new ResponseStructure<String>();
		 structure.setData("Employer Data deleted ");
		 structure.setMessage("Employer Data deleted successfully ");
		 structure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.OK);
	}

}
