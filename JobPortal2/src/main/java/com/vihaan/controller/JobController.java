package com.vihaan.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vihaan.dto.JobRequestDto;
import com.vihaan.dto.JobResponseDto;
import com.vihaan.entity.Job;
import com.vihaan.service.JobService;
import com.vihaan.util.ResponseStructure;

@RestController
@CrossOrigin
@Validated
public class JobController {

	@Autowired
	private JobService jobService;
	
	@PostMapping("jobs/addjob/{employerId}")
      public ResponseEntity<ResponseStructure<JobResponseDto>> addJob(@RequestBody JobRequestDto jobRequestDto ,@PathVariable Long employerId) {
		return jobService.addJob(jobRequestDto, employerId);	
	}
	 
	@GetMapping("jobs/getalljobs")
	public ResponseEntity<ResponseStructure<List<JobResponseDto>>> getAllJobs() {
		return jobService.getAllJobs();
	}
	
	@GetMapping("jobs/getjobs/{employerId}")
	public ResponseEntity<ResponseStructure<List<JobResponseDto>>> getJobsByEmployerId(@PathVariable long employerId) {
		return jobService.getJobsByEmployerId(employerId);
	}
	
	@GetMapping("jobs/getjobbyjobid/{jobId}")
	public ResponseEntity<ResponseStructure<JobResponseDto>> getJobByJobId(@PathVariable Long jobId) {
		return jobService.getJobByJobId(jobId);
	}
	
	@PostMapping("jobs/addlogo/{jobId}")
	public ResponseEntity<ResponseStructure<String>> addOrgLogo(@PathVariable Long jobId ,@ModelAttribute MultipartFile file) throws IOException {
		return jobService.addOrgLogo(jobId,file);
	}
	
	@GetMapping("jobs/getlogo/{jobId}")
	public ResponseEntity<byte[]> getOrganistionLogo(@PathVariable Long jobId) {
		return jobService.getOrganistionLogo(jobId)	;
	}
	
	@PutMapping("jobs/deletejob/{jobId}")
	public ResponseEntity<ResponseStructure<String>> deleteJob(@PathVariable Long jobId){
		return jobService.deleteJob(jobId);
	}
	@PutMapping("jobs/updatejobstatus/{jobId}")
	public ResponseEntity<ResponseStructure<String>> updateJobStatus(@PathVariable Long jobId){
		return jobService.updateJobStatus(jobId);
	}
}
