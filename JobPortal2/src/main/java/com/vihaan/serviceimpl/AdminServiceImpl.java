package com.vihaan.serviceimpl;

import java.util.ArrayList;
import java.util.List;
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
import com.vihaan.entity.ISDELETED;
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
		  Optional<Admin> optional = adminRepo.findByAdminEmail(dto.getAdminEmail());
		  if (optional.isPresent()&&optional.get().getDeleteCondition()==ISDELETED.FALSE) {
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
		   Optional<Admin> optional = adminRepo.findByAdminEmail(email);
		   if (optional.isEmpty()||optional.get().getDeleteCondition()==ISDELETED.TRUE) {
				throw new EmailNotFoundException("Admin details not found");
				
			}
		   Admin admin = optional.get();
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
		Optional<Admin> optional = adminRepo.findByAdminEmail(mail);
		   if (optional.isEmpty()||optional.get().getDeleteCondition()==ISDELETED.TRUE) {
				throw new EmailNotFoundException("Admin details not found");
				
			}
	        if (!newPassword.equals(confirmPwd)) {
				throw new ForbiddenOperationException("NewPassword and Confirm password mismatch");
			}
	        Admin admin = optional.get();
	        String encodedPassword = passwordEncoder.encode(newPassword);
	        admin.setAdminPassword(encodedPassword);
	        adminRepo.save(admin);
	        ResponseStructure<String>structure= new ResponseStructure<String>();
	        structure.setData("reset password successful");
	        structure.setMessage("password reset done");
	        structure.setStatusCode(HttpStatus.OK.value());
	        return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.OK);
	}
	@Override
	public ResponseEntity<ResponseStructure<List<AdminResponseDto>>> getAllAdmins() {
		List<Admin> admins = adminRepo.findAll();
		List<AdminResponseDto>responseDtos= new ArrayList<AdminResponseDto>();
		for (Admin admin : admins) {
			AdminResponseDto responseDto = this.modelMapper.map(admin, AdminResponseDto.class);
			responseDtos.add(responseDto);
		}
		ResponseStructure<List<AdminResponseDto>>structure= new ResponseStructure<List<AdminResponseDto>>();
		structure.setData(responseDtos);
		structure.setMessage("All Admins fetched successfully");
		structure.setStatusCode(HttpStatus.OK.value());
		
return new ResponseEntity<ResponseStructure<List<AdminResponseDto>>>(structure,HttpStatus.OK);
	}
	@Override
	public ResponseEntity<ResponseStructure<String>> deleteAdmin(Long adminHeadId, Long adminId) {

           if (adminHeadId!=1) {
			throw new ForbiddenOperationException("Only AdminHead can Delete Admin");
		}
           Optional<Admin> optional = adminRepo.findById(adminId);
           if (optional.isEmpty()||optional.get().getDeleteCondition()==ISDELETED.TRUE) {
			throw new UserNotFoundByIdException("Admin with this id not found");
		}
           if (adminId==1) {
			 throw new ForbiddenOperationException("Admin Head cannot delete his account himself");
		}
           Admin admin = optional.get();
        	admin.setDeleteCondition(ISDELETED.TRUE);
           adminRepo.save(admin);
           ResponseStructure<String>structure= new ResponseStructure<String>();
           structure.setData("Admin Data deleted ");
           structure.setMessage("Admin Data deleted successfully");
           structure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.OK);
	}
	
	@Override
	public ResponseEntity<ResponseStructure<String>> deleteAdmin(Long adminId) {
		 Optional<Admin> optional = adminRepo.findById(adminId);
         if (optional.isEmpty()||optional.get().getDeleteCondition()==ISDELETED.TRUE) {
			throw new UserNotFoundByIdException("Admin with this id not found");
		}
         if (adminId==1) {
			 throw new ForbiddenOperationException("Admin Head cannot delete his account himself");
		}
         Admin admin = optional.get();
      	admin.setDeleteCondition(ISDELETED.TRUE);
         adminRepo.save(admin);
         ResponseStructure<String>structure= new ResponseStructure<String>();
         structure.setData("Admin Data deleted ");
         structure.setMessage("Admin Data deleted successfully");
         structure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.OK);
	}

	
}
