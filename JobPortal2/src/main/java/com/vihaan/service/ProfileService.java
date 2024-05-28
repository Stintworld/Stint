package com.vihaan.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.vihaan.dto.ProfileDto;
import com.vihaan.util.ResponseStructure;

public interface ProfileService {

	public ResponseEntity<ResponseStructure<ProfileDto>> createProfile(long applicantId, ProfileDto dto);
	
	public ResponseEntity<ResponseStructure<ProfileDto>> getProfile(@PathVariable Long applicantId);
}
