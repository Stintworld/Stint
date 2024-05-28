package com.vihaan.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Employer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long employerId;
	private String employerName;
	private String employerEmail;
	private String employerPhNo;
	private String employerPassword;
	
	
	private ISDELETED deleteStatus;
	@OneToMany(mappedBy = "employer")
	@JsonIgnore
	private List<Job>jobs;
	
}
