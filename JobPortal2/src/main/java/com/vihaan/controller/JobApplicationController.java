package com.vihaan.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vihaan.dto.JobApplicationResponseDto;
import com.vihaan.entity.JobApplication;
import com.vihaan.entity.JobApplicationStatus;
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
    public ResponseEntity<ResponseStructure<List<JobApplicationResponseDto>>>getJobApplicationByApplicantId(@PathVariable Long applicantId) {
		return jobApplicationService.getJobApplicationByApplicantId(applicantId);
	}
	@GetMapping("jobapplications/getbyapplicationid/{applicationid}")
	public ResponseEntity<ResponseStructure<JobApplicationResponseDto>> getApplicationById(@PathVariable Long applicationid) {
		return jobApplicationService.getApplicationById(applicationid);
	}
	
	@GetMapping("jobapplications/getbyjobid/{jobId}")
    public ResponseEntity<ResponseStructure<List<JobApplicationResponseDto>>>getJobApplicationByJobId(@PathVariable Long jobId) {
		return jobApplicationService.getApplicationsbyJobId(jobId);
	}
	@PutMapping("jobapplications/updateapplication/{applicationId}")
	public ResponseEntity<ResponseStructure<JobApplicationResponseDto>> updateJobApplication(@PathVariable Long applicationId,@RequestParam JobApplicationStatus applicationStatus,String reason){
		return jobApplicationService.updateJobApplication(applicationId,applicationStatus,reason);
	}
	@GetMapping("jobapplications/getalljobapplications")
	public ResponseEntity<ResponseStructure<List<JobApplicationResponseDto>>> getAllJobApplications(){
		return jobApplicationService.getAllJobApplications();
	}
}
