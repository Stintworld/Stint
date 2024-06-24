package com.vihaan.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vihaan.entity.Gender;
import com.vihaan.entity.ISDELETED;
import com.vihaan.entity.JobApplication;
import com.vihaan.entity.JobLevel;

import lombok.Data;

@Data
public class ApplicantResponseDto {
	private long applicantId;
	private String applicantName;
	private String applicantEmail;
	private long applicantPhNo;
//	private Gender gender;
//	private String dOB;
//	private JobLevel jobLevel;	
	private List<JobApplicationResponseDto>applications;
	
	private byte[] applicantImage;
	
	 private byte[] applicantResume;

	
}
