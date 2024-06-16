package com.vihaan.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vihaan.entity.Admin;

public interface AdminRepo extends JpaRepository<Admin, Long> {

	public Admin findByAdminEmail(String email) ;
}
