package com.vihaan.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.vihaan.entity.Job;

public interface JobRepo extends JpaRepository<Job, Long>{

	public Job  findByJobTitle(String jobTitle);
	
	public Job  findByCompany(String company);
	
	@Query("from Job j where j.employer.employerId = ?1")
	public List<Job> findByEmployerId(Long employerId) ;
	
	
}
