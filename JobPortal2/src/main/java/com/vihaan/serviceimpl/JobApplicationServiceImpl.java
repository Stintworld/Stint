package com.vihaan.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import com.vihaan.entity.JobApplicationStatus;
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
		     jobApplication.setJobApplicationStatus(JobApplicationStatus.SUBMITTED);
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
	public ResponseEntity<ResponseStructure<List<JobApplicationResponseDto>>> getJobApplicationByApplicantId(
			Long applicantId) {
		    List<JobApplication> jobApplications = jobApplicationRepo.findByApplicantId(applicantId);
		    ArrayList<JobApplicationResponseDto>responseDtos= new ArrayList<JobApplicationResponseDto>();
		    if (jobApplications.isEmpty()) {
				throw new JobApplicationNotFoundException("JobApplication not found by this Applicant Id");
			}
		  for (JobApplication jobApplication : jobApplications) {
			   JobApplicationResponseDto responseDto = this.modelMapper.map(jobApplication, JobApplicationResponseDto.class);
			   responseDto.setCompany(jobApplication.getJob().getCompany());
			  responseDto.setCompanyWebsite(jobApplication.getJob().getCompanyWebsite());
			  responseDto.setSkills(jobApplication.getJob().getSkills());
			  responseDto.setEmployerName(jobApplication.getJob().getEmployer().getEmployerName());
			  responseDto.setEmployerEmail(jobApplication.getJob().getEmployer().getEmployerEmail());
			  responseDto.setEmployerPhNo(jobApplication.getJob().getEmployer().getEmployerPhNo());
			   responseDto.setJobSerial(jobApplication.getJob().getJobId());
			   responseDtos.add(responseDto);
		}
		    ResponseStructure<List<JobApplicationResponseDto>>structure= new ResponseStructure<List<JobApplicationResponseDto>>();
		    structure.setData(responseDtos);
		    structure.setMessage("Application found");
		    structure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<List<JobApplicationResponseDto>>>(structure,HttpStatus.OK);
	}
	@Override
	public ResponseEntity<ResponseStructure<JobApplicationResponseDto>> getApplicationById(Long applicationid) {
		    Optional<JobApplication> optional = jobApplicationRepo.findById(applicationid);
		    if (optional.isEmpty()) {
				throw  new JobApplicationNotFoundException("No Job Application by this Id");
			}
		    JobApplication jobApplication = optional.get();
		    JobApplicationResponseDto responseDto = this.modelMapper.map(jobApplication, JobApplicationResponseDto.class);
		    responseDto.setCompany(jobApplication.getJob().getCompany());
			  responseDto.setCompanyWebsite(jobApplication.getJob().getCompanyWebsite());
			  responseDto.setSkills(jobApplication.getJob().getSkills());
			  responseDto.setEmployerName(jobApplication.getJob().getEmployer().getEmployerName());
			  responseDto.setEmployerEmail(jobApplication.getJob().getEmployer().getEmployerEmail());
			  responseDto.setEmployerPhNo(jobApplication.getJob().getEmployer().getEmployerPhNo());
		    responseDto.setJobSerial(jobApplication.getJob().getJobId());
		    ResponseStructure<JobApplicationResponseDto>structure=new  ResponseStructure<JobApplicationResponseDto>();
		    structure.setData(responseDto);
		    structure.setMessage("JobApplication Fetched successfully");
		    structure.setStatusCode(HttpStatus.OK.value());
		    
		return new ResponseEntity<ResponseStructure<JobApplicationResponseDto>>(structure,HttpStatus.OK);
	}
	@Override
	public ResponseEntity<ResponseStructure<List<JobApplicationResponseDto>>> getApplicationsbyJobId(Long jobId) {
		     List<JobApplication> jobApplications= jobApplicationRepo.findByjobId(jobId);
		     ArrayList<JobApplicationResponseDto>responseDtos=new ArrayList<JobApplicationResponseDto>();
		     for (JobApplication jobApplication : jobApplications) {
				JobApplicationResponseDto responseDto = this.modelMapper.map(jobApplication, JobApplicationResponseDto.class);
				 responseDto.setCompany(jobApplication.getJob().getCompany());
				  responseDto.setCompanyWebsite(jobApplication.getJob().getCompanyWebsite());
				  responseDto.setSkills(jobApplication.getJob().getSkills());
				  responseDto.setEmployerName(jobApplication.getJob().getEmployer().getEmployerName());
				  responseDto.setEmployerEmail(jobApplication.getJob().getEmployer().getEmployerEmail());
				  responseDto.setEmployerPhNo(jobApplication.getJob().getEmployer().getEmployerPhNo());
				responseDto.setJobSerial(jobApplication.getJob().getJobId());
				responseDtos.add(responseDto);
			}
		     ResponseStructure<List<JobApplicationResponseDto>>structure= new ResponseStructure<List<JobApplicationResponseDto>>();
		     structure.setData(responseDtos);
		     structure.setMessage("Application fetched ");
		     structure.setStatusCode(HttpStatus.OK.value());
		     return new ResponseEntity<ResponseStructure<List<JobApplicationResponseDto>>>(structure,HttpStatus.OK);
	}

	public ResponseEntity<ResponseStructure<JobApplicationResponseDto>> updateJobApplication(Long applicationId,JobApplicationStatus applicationStatus,String reason) {
		  Optional<JobApplication> optional = jobApplicationRepo.findById(applicationId);
		  if (optional.isEmpty()) {
		   throw new	JobApplicationNotFoundException("Application not found by this Id"); 
		}
		  JobApplication jobApplication = optional.get();
		  jobApplication.setJobApplicationStatus(applicationStatus);
		  jobApplication.setReasonForRejection(reason);
		  JobApplication jobApplication2 = jobApplicationRepo.save(jobApplication);
		  JobApplicationResponseDto responseDto = this.modelMapper.map(jobApplication2, JobApplicationResponseDto.class);
		  responseDto.setCompany(jobApplication.getJob().getCompany());
		  responseDto.setCompanyWebsite(jobApplication2.getJob().getCompanyWebsite());
		  responseDto.setSkills(jobApplication2.getJob().getSkills());
		  responseDto.setEmployerName(jobApplication2.getJob().getEmployer().getEmployerName());
		  responseDto.setEmployerEmail(jobApplication2.getJob().getEmployer().getEmployerEmail());
		  responseDto.setEmployerPhNo(jobApplication2.getJob().getEmployer().getEmployerPhNo());
		responseDto.setJobSerial(jobApplication2.getJob().getJobId());
		  ResponseStructure<JobApplicationResponseDto>structure= new ResponseStructure<JobApplicationResponseDto>();
		  structure.setData(responseDto);
		  structure.setMessage("Job Application Updated successfully");
		  structure.setStatusCode(HttpStatus.OK.value());
		  return new ResponseEntity<ResponseStructure<JobApplicationResponseDto>>(structure,HttpStatus.OK);
		
	}
	
}
