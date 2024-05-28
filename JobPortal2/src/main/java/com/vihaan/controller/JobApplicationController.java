package com.vihaan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vihaan.dto.JobApplicationResponseDto;
import com.vihaan.service.JobApplicationService;
import com.vihaan.util.ResponseStructure;

@RestController
@CrossOrigin
@Validated
public class JobApplicationController {

	@Autowired
	private JobApplicationService jobApplicationService;
	
	@PostMapping("jobapplications/add/{jobId}/{applicantId}")
	public ResponseEntity<ResponseStructure<String>> addJobApplication(@PathVariable long jobId,@PathVariable long applicantId ) {
		return jobApplicationService.addJobApplication(jobId, applicantId);
	}
	
	@GetMapping("jobapplications/getbyapplicantid/{applicantId}")
    public ResponseEntity<ResponseStructure<JobApplicationResponseDto>>getJobApplicationByApplicantId(@PathVariable Long applicantId) {
		return jobApplicationService.getJobApplicationByApplicantId(applicantId);
	}
}
