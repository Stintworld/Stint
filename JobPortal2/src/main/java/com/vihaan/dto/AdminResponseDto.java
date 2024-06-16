package com.vihaan.dto;

import lombok.Data;

@Data
public class AdminResponseDto {

	private Long adminId;
	private String adminName;
	private String adminEmail;
    private Long adminPhoneNo;
}
