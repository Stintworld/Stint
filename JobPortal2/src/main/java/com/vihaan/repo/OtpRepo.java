package com.vihaan.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vihaan.entity.Otp;


public interface OtpRepo extends JpaRepository<Otp, Long> {

	public Optional<Otp>findByGmail(String gmail);
}
