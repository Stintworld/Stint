package com.vihaan.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vihaan.entity.Applicant;
import java.util.List;
import java.util.Optional;


public interface ApplicantRepo extends JpaRepository<Applicant, Long> {

	Optional<Applicant> findByApplicantEmail(String mail);
	
	 @Query("SELECT a.applicant FROM JobApplication a WHERE a.job.jobId = :jobId")
	 public List<Applicant> getApplicantsByJobId(Long jobId);
}
