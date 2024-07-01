package com.vihaan.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vihaan.entity.Employer;

public interface EmployerRepo extends JpaRepository<Employer, Long> {

		public	Optional<Employer> findByEmployerEmail(String email);

}
