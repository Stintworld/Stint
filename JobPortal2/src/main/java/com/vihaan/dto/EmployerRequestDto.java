package com.vihaan.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmployerRequestDto {

	@NotBlank(message = "Employer Name is mandatory")
	private String employerName;
	
	@NotBlank(message = "Employer Email is mandatory")
	@Email(message = "email should be valid")
	private String employerEmail;
	
	@NotBlank(message = "phone number is required")
	@Min(value = 6000000000l ,message = "Phone Number cannot start with below `6`!!!")
	@Max(value = 9999999999l,message = "Phone number cannot exceed `10` Digits!!!")
	private String employerPhNo;
	@NotBlank(message = "orgName is required")
	private String organisation;
	@NotBlank(message = "orgLocation  is required")
	private String orgLocation;
	
	@NotBlank(message = "Password is required")
	private String employerPassword;

	
	
}
