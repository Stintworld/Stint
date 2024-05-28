package com.vihaan.entity;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long jobId;
	private String jobTitle;
	
	@Size(max = 10000)
	private String jobDiscription;
	private String company;
	private double salary;
	
	private List<String>skills;
	private String companyWebsite;
	
	private String jobLocation;
	private JobLevel jobLevel;
	private String jobType;
	
	private String aboutCompany;
	private LocalDateTime jobCreateDatetime;
	
	private String jobStatus;  //Hiring or Stopped
	
	@OneToMany(mappedBy = "job")
	@JsonIgnore
	private List<JobApplication> jobApplications;
		
	@ManyToOne
	@JoinColumn
	@Basic(fetch = FetchType.LAZY)
	private Employer employer;

	 @Lob
	 @Basic(fetch = FetchType.LAZY)
	 @Column(length = 100000 ,columnDefinition = "LONGBLOB")
	private byte[] organisationLogo;


	
}
