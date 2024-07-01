package com.vihaan.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Applicant {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long applicantId;
	private String applicantName;
	private String applicantEmail;
	private String applicantPassword;
	private long applicantPhNo;
//	private Gender gender;
//	private String dob;
//	private JobLevel jobLevel;
	private ISDELETED isdeleted;
	
	@OneToMany(mappedBy = "applicant")
	@JsonIgnore
	private List<JobApplication> jobApplications;
	
	@OneToOne
	@JsonIgnore
	private Profile profile;
	
	 @Lob
	 @Basic(fetch = FetchType.LAZY)
	 @Column(length = 100000 ,columnDefinition = "LONGBLOB")
	private byte[] applicantImage;
	 
	 @Lob
	 @Basic(fetch = FetchType.LAZY)
	 @Column(length = 100000 ,columnDefinition = "LONGBLOB")
     private byte[] applicantResume;

	
}
