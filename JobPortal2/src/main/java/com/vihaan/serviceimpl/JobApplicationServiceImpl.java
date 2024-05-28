package com.vihaan.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vihaan.dto.JobApplicationResponseDto;
import com.vihaan.entity.Applicant;
import com.vihaan.entity.Job;
import com.vihaan.entity.JobApplication;
import com.vihaan.exception.JobApplicationNotFoundException;
import com.vihaan.exception.UserNotFoundByIdException;
import com.vihaan.repo.ApplicantRepo;
import com.vihaan.repo.JobApplicationRepo;
import com.vihaan.repo.JobRepo;
import com.vihaan.service.JobApplicationService;
import com.vihaan.util.ResponseStructure;

@Service
public class JobApplicationServiceImpl implements JobApplicationService {

	@Autowired
	public ApplicantRepo applicantRepo;
	
	@Autowired
	public JobRepo jobRepo;
	@Autowired
	public JobApplicationRepo jobApplicationRepo;
	@Autowired
	public ModelMapper modelMapper;
	@Override
	public ResponseEntity<ResponseStructure<String>> addJobApplication(Long jobId, long applicantId) {
		       Optional<Job> optional2 = jobRepo.findById(jobId);
		       
		     Optional<Applicant> optional = applicantRepo.findById(applicantId);
		     if (optional.isEmpty()) {
				throw new UserNotFoundByIdException("No applicant with this Id");
			}
		     JobApplication jobApplication= new JobApplication();
		     jobApplication.setApplicant(optional.get());
		     Job job = optional2.get();
		     jobApplication.setJob(job);
		     jobApplication.setJobApplicationDateTime(LocalDateTime.now());
		     jobApplicationRepo.save(jobApplication);
		     List<JobApplication> jobApplications = job.getJobApplications();
		     jobApplications.add(jobApplication);
		     ResponseStructure<String>responseStructure= new ResponseStructure<String>();
		     responseStructure.setData("Application Received");
		     responseStructure.setMessage("Job applied successful");
		     responseStructure.setStatusCode(HttpStatus.CREATED.value());
		return new ResponseEntity<ResponseStructure<String>>(responseStructure,HttpStatus.CREATED);
	}
	@Override
	public ResponseEntity<ResponseStructure<JobApplicationResponseDto>> getJobApplicationByApplicantId(
			Long applicantId) {
		    JobApplication jobApplication = jobApplicationRepo.findByApplicantId(applicantId);
		    if (jobApplication==null) {
				throw new JobApplicationNotFoundException("JobApplication not found by this Applicant Id");
			}
		    JobApplicationResponseDto responseDto = this.modelMapper.map(jobApplication, JobApplicationResponseDto.class);
		    ResponseStructure<JobApplicationResponseDto>structure= new ResponseStructure<JobApplicationResponseDto>();
		    structure.setData(responseDto);
		    structure.setMessage("Application found");
		    structure.setStatusCode(HttpStatus.FOUND.value());
		return new ResponseEntity<ResponseStructure<JobApplicationResponseDto>>(structure,HttpStatus.FOUND);
	}

	
}
