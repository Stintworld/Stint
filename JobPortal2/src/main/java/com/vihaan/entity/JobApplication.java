package com.vihaan.entity;

import java.time.LocalDateTime;

import org.springframework.context.annotation.Lazy;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class JobApplication {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long jobApplicationId;
	
	
	private LocalDateTime jobApplicationDateTime;

	@ManyToOne
	@JoinColumn
	private Job job;

	@ManyToOne
	@JoinColumn
	@Lazy
	private Applicant applicant;
    private JobApplicationStatus jobApplicationStatus;
	private String reasonForRejection;
	
}
