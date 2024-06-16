package com.vihaan.serviceimpl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.vihaan.dto.AdminRequestDto;
import com.vihaan.dto.AdminResponseDto;
import com.vihaan.entity.Admin;
import com.vihaan.exception.EmailNotFoundException;
import com.vihaan.exception.ForbiddenOperationException;
import com.vihaan.exception.PasswordMissMatchException;
import com.vihaan.exception.UserNotFoundByIdException;
import com.vihaan.exception.UserWithSameEmailExist;
import com.vihaan.repo.AdminRepo;
import com.vihaan.service.AdminService;
import com.vihaan.util.ResponseStructure;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
	private AdminRepo adminRepo;
    @Autowired
    private ModelMapper modelMapper;
	@Autowired
    private PasswordEncoder passwordEncoder;
	@Override
	public ResponseEntity<ResponseStructure<AdminResponseDto>> addAdmin(AdminRequestDto dto) {
		  Admin admin = adminRepo.findByAdminEmail(dto.getAdminEmail());
		  if (admin!=null) {
			throw new UserWithSameEmailExist("Admin with same email exist");
		}
		  Admin admin2= new Admin();
		 admin2.setAdminEmail(dto.getAdminEmail());
		 admin2.setAdminName(dto.getAdminName());
		 admin2.setAdminPhoneNo(dto.getAdminPhoneNo());
		 System.out.println(dto.getAdminPassword());
		 String encodedPassword = passwordEncoder.encode(dto.getAdminPassword());
		 admin2.setAdminPassword(encodedPassword);
		 Admin admin3 = adminRepo.save(admin2);
		  AdminResponseDto adminResponseDto = this.modelMapper.map(admin3, AdminResponseDto.class);
		  ResponseStructure<AdminResponseDto>structure= new ResponseStructure<AdminResponseDto>();
		  structure.setData(adminResponseDto);
		  structure.setMessage("Admin Data Added uccessfully");
		  structure.setStatusCode(HttpStatus.CREATED.value());
		return new ResponseEntity<ResponseStructure<AdminResponseDto>>(structure,HttpStatus.CREATED);
	}
	@Override
	public ResponseEntity<ResponseStructure<AdminResponseDto>> adminLogin(String email, String password) {
		   Admin admin = adminRepo.findByAdminEmail(email);
		   if (admin==null) {
				throw new EmailNotFoundException("Admin details not found");
				
			}
	        if (!passwordEncoder.matches(password, admin.getAdminPassword())) {
				throw new PasswordMissMatchException("Wrong Password ,please try again");
			}
	        AdminResponseDto responseDto = this.modelMapper.map(admin, AdminResponseDto.class);
	        ResponseStructure<AdminResponseDto>structure= new ResponseStructure<AdminResponseDto>();
	        structure.setData(responseDto);
	        structure.setMessage("Login Successfull");
	        structure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<AdminResponseDto>>(structure,HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<ResponseStructure<String>> resetpassword(String mail, String newPassword,
			String confirmPwd) {
		Admin admin = adminRepo.findByAdminEmail(mail);
		   if (admin==null) {
				throw new EmailNotFoundException("Admin details not found");
				
			}
	        if (!newPassword.equals(confirmPwd)) {
				throw new ForbiddenOperationException("NewPassword and Confirm password mismatch");
			}
	        String encodedPassword = passwordEncoder.encode(newPassword);
	        admin.setAdminPassword(encodedPassword);
	        adminRepo.save(admin);
	        ResponseStructure<String>structure= new ResponseStructure<String>();
	        structure.setData("reset password successful");
	        structure.setMessage("password reset done");
	        structure.setStatusCode(HttpStatus.OK.value());
	        return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.OK);
	}

}
