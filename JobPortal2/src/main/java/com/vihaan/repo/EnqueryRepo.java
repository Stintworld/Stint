package com.vihaan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vihaan.entity.Enquery;

public interface EnqueryRepo extends JpaRepository<Enquery, Long> {

	
}
