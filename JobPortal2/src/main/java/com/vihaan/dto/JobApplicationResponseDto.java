package com.vihaan.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.vihaan.entity.JobApplicationStatus;

import lombok.Data;

@Data
public class JobApplicationResponseDto {

	
     private long jobApplicationId;
	private LocalDateTime jobApplicationDateTime;
	private long jobSerial;
	private String jobTitle;
	private String jobDiscription;
	private String company;
	private double salary;
	 private JobApplicationStatus jobApplicationStatus;
	 
	private List<String>skills;
	private String companyWebsite;
	private String jobLocation;
	private String jobLevel;
	private String jobType;
	private String jobStatus; 
	
	private String employerName;
	private String employerEmail;
	private String employerPhNo;
}
