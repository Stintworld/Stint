package com.vihaan.dto;

import java.util.List;

import com.vihaan.entity.Gender;
import com.vihaan.entity.JobLevel;

import lombok.Data;

@Data
public class ProfileDto {

	
	private String firstName;
	private String lastName;
	private String email;
	private long phNo;
	private String dob;
	private Gender gender;
	private String applicantLocation;
	private String school_10th_Name;
	private String school_10th_YOP;
	private String school_10th_Perc;
	
	private String school_12th_Name;
	private String school_12th_YOP;
	private String school_12th_Perc;
	
	private String bTechCollegeName;
	private String bTechCollegeYOP;
	private String bTechCollegePerc;
	private String bTechDeptName;
	
	private String mTechCollegeName;
	private String mTechCollegeYOP;
	private String mTechCollegePerc;
	private String mTechDeptName;
	
	private JobLevel jobLevel;
	
	private Integer yearOfExp;

	private List<String>skills;
	
}
