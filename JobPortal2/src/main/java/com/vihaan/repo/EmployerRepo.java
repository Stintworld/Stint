package com.vihaan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vihaan.entity.Employer;

public interface EmployerRepo extends JpaRepository<Employer, Long> {

		public	Employer findByEmployerEmail(String email);

}
