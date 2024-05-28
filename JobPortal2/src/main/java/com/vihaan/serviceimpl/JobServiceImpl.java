package com.vihaan.serviceimpl;

import java.awt.Image;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.vihaan.dto.EmployerResponseDto;
import com.vihaan.dto.JobRequestDto;
import com.vihaan.dto.JobResponseDto;
import com.vihaan.entity.Applicant;
import com.vihaan.entity.Employer;
import com.vihaan.entity.Job;
import com.vihaan.exception.UserNotFoundByIdException;
import com.vihaan.exception.jobNotFoundException;
import com.vihaan.repo.EmployerRepo;
import com.vihaan.repo.JobRepo;
import com.vihaan.service.JobService;
import com.vihaan.util.ResponseStructure;

@Service
public class JobServiceImpl implements JobService {

	@Autowired
	private JobRepo jobRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private EmployerRepo employerRepo;
	
	@Override
	public ResponseEntity<ResponseStructure<JobResponseDto>> addJob(JobRequestDto requestDto,long employerId) {

		Optional<Employer> optional = employerRepo.findById(employerId);
		if (optional.isEmpty()) {
			throw new UserNotFoundByIdException("Employer not Found");
		}
		Employer employer = optional.get();
                
                Job job = this.modelMapper.map(requestDto, Job.class);
               List<Job> jobs = employer.getJobs();
               jobs.add(job);
               employer.setJobs(jobs);
               job.setEmployer(employer);
               job.setJobCreateDatetime(LocalDateTime.now());
               employerRepo.save(employer);
               jobRepo.save(job);
               JobResponseDto jobResponseDto = this.modelMapper.map(job, JobResponseDto.class);
               ResponseStructure<JobResponseDto>structure= new ResponseStructure<JobResponseDto>();
               structure.setMessage("Job added Successfully");
               structure.setStatusCode(HttpStatus.CREATED.value());
               structure.setData(jobResponseDto);
               
		return new ResponseEntity<ResponseStructure<JobResponseDto>>(structure,HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<JobResponseDto>>> getAllJobs() {
		List<Job> jobs = jobRepo.findAll();
		ArrayList<JobResponseDto>dtos= new ArrayList<JobResponseDto>();
		
		for (Job job : jobs) {
			JobResponseDto jobResponseDto = this.modelMapper.map(job, JobResponseDto.class);
			dtos.add(jobResponseDto);
		}
		ResponseStructure<List<JobResponseDto>>structure= new ResponseStructure<List<JobResponseDto>>();
		structure.setData(dtos);
		structure.setMessage("All jobs fetched here");
		structure.setStatusCode(HttpStatus.FOUND.value());
		return new ResponseEntity<ResponseStructure<List<JobResponseDto>>>(structure,HttpStatus.FOUND);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<JobResponseDto>>> getJobsByEmployerId(long employerId) {
		List<Job> jobs = jobRepo.findByEmployerId(employerId);
		
		List<JobResponseDto>jobResponseDtos= new ArrayList<JobResponseDto>();
		for (Job job : jobs) {
			Employer employer = job.getEmployer();
              JobResponseDto jobResponseDto = this.modelMapper.map(job, JobResponseDto.class);
            jobResponseDtos.add(jobResponseDto);
		
		}
		ResponseStructure<List<JobResponseDto>>structure= new ResponseStructure<List<JobResponseDto>>();
		structure.setData(jobResponseDtos);
		structure.setMessage("Data fetched using Employer Id successfully");
		structure.setStatusCode(HttpStatus.FOUND.value());
		return new ResponseEntity<ResponseStructure<List<JobResponseDto>>>(structure,HttpStatus.FOUND);
	}

	@Override
	public ResponseEntity<ResponseStructure<JobResponseDto>> getJobByJobId(Long jobId) {
		Optional<Job> optionalJob = jobRepo.findById(jobId);
		if (optionalJob.isEmpty()) {
			throw new jobNotFoundException("Job not found by this Id");
		}
		Job job = optionalJob.get();
		JobResponseDto responseDto = this.modelMapper.map(job, JobResponseDto.class);
		ResponseStructure<JobResponseDto> responseStructure= new ResponseStructure<JobResponseDto>();
		responseStructure.setData(responseDto);
		responseStructure.setMessage("Job Found By this Id");
		responseStructure.setStatusCode(HttpStatus.FOUND.value());
		
		return new ResponseEntity<ResponseStructure<JobResponseDto>>(responseStructure, HttpStatus.FOUND); 
		
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> addOrgLogo(Long jobid,MultipartFile file) throws IOException {
		    Optional<Job> optional = jobRepo.findById(jobid);
		    if (optional.isEmpty()) {
				throw new jobNotFoundException("Job Not found by this Id to add logo");
			}
		    Job job = optional.get();
		    job.setOrganisationLogo(file.getBytes());
		    jobRepo.save(job);
		    ResponseStructure<String>structure=new ResponseStructure<String>();
		    structure.setData("Image Added to Job");
		    structure.setMessage("Organisation Logo Added to Job");
		    structure.setStatusCode(HttpStatus.CREATED.value());
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.CREATED);
	}

	@Override
	public ResponseEntity<byte[]> getOrganistionLogo(Long jobId) {
		 Optional<Job> job = jobRepo.findById(jobId);
	        if (job.isPresent()) {
	            byte[] logo = job.get().getOrganisationLogo();
	           
	            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG ).body(logo);
	               
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	}

}
