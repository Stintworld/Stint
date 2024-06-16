package com.vihaan.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.vihaan.dto.JobRequestDto;
import com.vihaan.dto.JobResponseDto;
import com.vihaan.entity.Job;
import com.vihaan.util.ResponseStructure;

public interface JobService {

	
	public ResponseEntity<ResponseStructure<JobResponseDto>> addJob(JobRequestDto requestDto,long employerId);
	public ResponseEntity<ResponseStructure<List<JobResponseDto>>> getAllJobs();
	
	public ResponseEntity<ResponseStructure<List<JobResponseDto>>> getJobsByEmployerId(@PathVariable long employerId);
	public ResponseEntity<ResponseStructure<JobResponseDto>> getJobByJobId(Long jobId);
	public ResponseEntity<ResponseStructure<String>> addOrgLogo(Long jobid, MultipartFile file) throws IOException ;
	public ResponseEntity<byte[]> getOrganistionLogo(Long jobId);
	
	public ResponseEntity<ResponseStructure<String>> deleteJob(Long jobId);
	
}
