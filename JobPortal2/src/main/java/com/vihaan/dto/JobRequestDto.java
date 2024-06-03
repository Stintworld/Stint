package com.vihaan.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.vihaan.entity.JobLevel;

import lombok.Data;

@Data
public class JobRequestDto {

	private String jobTitle;
	private String jobDiscription;
	private String company;
	private double salary;
	
	private List<String>skills;
	private String companyWebsite;
	private String aboutCompany;
	private String jobLocation;
	private JobLevel jobLevel;
	private String jobType;
	private String jobStatus;
	private LocalDateTime jobCreateDatetime;
	
	
	
}
