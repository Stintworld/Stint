package com.vihaan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vihaan.dto.ProfileDto;
import com.vihaan.service.ProfileService;
import com.vihaan.util.ResponseStructure;
@RestController
@CrossOrigin
@Validated
public class ProfileController {

	@Autowired
	private ProfileService profileService;
	
	@PostMapping("applicants/updateprofile/{applicantId}")
	public ResponseEntity<ResponseStructure<ProfileDto>> createProfile(@PathVariable long applicantId,@RequestBody ProfileDto dto){
		return profileService.createProfile(applicantId, dto);
	}
	
	@GetMapping("applicants/getprofile/{applicantId}")
	public ResponseEntity<ResponseStructure<ProfileDto>> getProfile(@PathVariable long applicantId){
		return profileService.getProfile(applicantId);
	}
}
