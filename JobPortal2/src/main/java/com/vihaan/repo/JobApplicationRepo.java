package com.vihaan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vihaan.entity.JobApplication;

public interface JobApplicationRepo extends JpaRepository<JobApplication, Long>{

	@Query("from JobApplication j where j.applicant.applicantId=?1")
	public JobApplication findByApplicantId(Long applicantId);
}
