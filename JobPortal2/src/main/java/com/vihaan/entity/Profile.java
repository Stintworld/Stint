package com.vihaan.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
@Entity
@Data
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long profileId;
	private String firstName;
	private String lastName;
	private String email;
	private long phNo;
	private String dob;
	private Gender gender;
	
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
