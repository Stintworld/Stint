package com.vihaan.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Otp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long otpId;
	private String gmail;
	private int otpNumber;

	
	
}
