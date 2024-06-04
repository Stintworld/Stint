package com.vihaan.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vihaan.entity.Job;

import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
public class EmployerResponseDto {

	private long employerId;
	private String employerName;
	private String employerEmail;
	private String employerPhNo;
	private String organisation;
	private String orgLocation;
	private List<Job>jobs;
	
}
