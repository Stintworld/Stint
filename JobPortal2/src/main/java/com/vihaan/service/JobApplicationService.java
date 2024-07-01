package com.vihaan.service;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.vihaan.dto.JobApplicationResponseDto;
import com.vihaan.entity.Job;
import com.vihaan.entity.JobApplication;
import com.vihaan.entity.JobApplicationStatus;
import com.vihaan.util.ResponseStructure;

public interface JobApplicationService  {

	public ResponseEntity<ResponseStructure<String>>addJobApplication(@PathVariable Long jobId ,@PathVariable long applicantId);

	public ResponseEntity<ResponseStructure<List<JobApplicationResponseDto>>> getJobApplicationByApplicantId(
			Long applicantId);

	public ResponseEntity<ResponseStructure<JobApplicationResponseDto>> getApplicationById(Long applicationid);
	
	public ResponseEntity<ResponseStructure<List<JobApplicationResponseDto>>> getApplicationsbyJobId(Long jobId) ;

	public ResponseEntity<ResponseStructure<JobApplicationResponseDto>> updateJobApplication(Long applicationId,
			JobApplicationStatus applicationStatus,String reason);
	
	public ResponseEntity<ResponseStructure<List<JobApplicationResponseDto>>> getAllJobApplications() ;
}
