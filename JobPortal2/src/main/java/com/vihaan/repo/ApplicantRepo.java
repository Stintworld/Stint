package com.vihaan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vihaan.entity.Applicant;
import java.util.List;


public interface ApplicantRepo extends JpaRepository<Applicant, Long> {

	Applicant findByApplicantEmail(String mail);
	

}
