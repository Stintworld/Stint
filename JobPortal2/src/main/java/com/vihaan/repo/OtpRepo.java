package com.vihaan.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vihaan.entity.Otp;


public interface OtpRepo extends JpaRepository<Otp, Long> {

	public Otp findByGmail(String gmail);
}
