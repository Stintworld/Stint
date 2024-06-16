package com.vihaan.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AdminRequestDto {

	@NotBlank(message = "Admin Name Mandatory")
	private String adminName;
	@NotBlank(message = "Admin  Email Mandatory")
	private String adminEmail;
	@NotBlank(message = "Admin Password Mandatory")
	private String adminPassword;
	@NotBlank(message = "Admin phone number Mandatory")
    private Long adminPhoneNo;
    
}
