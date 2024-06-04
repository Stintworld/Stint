package com.vihaan.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vihaan.entity.JobApplication;

public interface JobApplicationRepo extends JpaRepository<JobApplication, Long>{

	@Query("from JobApplication j where j.applicant.applicantId=?1")
	public List<JobApplication> findByApplicantId(Long applicantId);
	
	@Query("from JobApplication j where j.job.jobId=?1")
	public List<JobApplication> findByjobId(Long jobId);
}
