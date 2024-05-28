package com.vihaan.dto;

import com.vihaan.entity.Gender;
import com.vihaan.entity.JobLevel;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ApplicantRequestDto {

	@NotBlank(message = "Applicant Name Mandatory")
	private String applicantName;
	
	@NotBlank(message = "Applicant mail Mandatory")
	@Email(message = "Email shoul be valid")
	private String applicantEmail;
	
	@NotBlank(message = "password Mandatory")
	private String applicantPassword;
	
	@NotBlank(message = "Phone number Mandatory")
	@Min(value = 6000000000l ,message = "Phone Number cannot start with below `6`!!!")
	@Max(value = 9999999999l,message = "Phone number cannot exceed `10` Digits!!!")
	private long applicantPhNo;
	
	@NotBlank(message = "Gender Mandatory")
	private Gender gender;
	
	@NotBlank(message = "DOB Mandatory")
	private String dOB;
	
	private JobLevel jobLevel;

	
	
}
