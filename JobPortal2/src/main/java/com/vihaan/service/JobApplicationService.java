package com.vihaan.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.vihaan.dto.JobApplicationResponseDto;
import com.vihaan.entity.Job;
import com.vihaan.util.ResponseStructure;

public interface JobApplicationService  {

	public ResponseEntity<ResponseStructure<String>>addJobApplication(@PathVariable Long jobId ,@PathVariable long applicantId);

	public ResponseEntity<ResponseStructure<JobApplicationResponseDto>> getJobApplicationByApplicantId(
			Long applicantId);
}
