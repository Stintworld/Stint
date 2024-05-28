package com.vihaan.serviceimpl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.vihaan.dto.ProfileDto;
import com.vihaan.entity.Applicant;
import com.vihaan.entity.Profile;
import com.vihaan.exception.UserNotFoundByIdException;
import com.vihaan.repo.ApplicantRepo;
import com.vihaan.repo.ProfileRepo;
import com.vihaan.service.ProfileService;
import com.vihaan.util.ResponseStructure;

@Service
public class ProfileServiceImpl implements ProfileService {

	@Autowired
	private ApplicantRepo applicantRepo;

	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private ProfileRepo profileRepo;
	@Override
	public ResponseEntity<ResponseStructure<ProfileDto>> createProfile(long applicantId, ProfileDto dto) {
		
		Profile profile = this.modelMapper.map(dto, Profile.class);
		
		Optional<Applicant> optional = applicantRepo.findById(applicantId);
		if (optional.isEmpty()) {
			throw new UserNotFoundByIdException("Applicant not found to get profile");
		}
		Applicant applicant = optional.get();
			profile.setProfileId(applicantId);
			profile.setEmail(applicant.getApplicantEmail());
			profile.setPhNo(applicant.getApplicantPhNo());
			profile.setFirstName(applicant.getApplicantName());
		
			
			profileRepo.save(profile);
			
			ProfileDto profileDto = this.modelMapper.map(profile, ProfileDto.class);
			
			ResponseStructure<ProfileDto>structure= new ResponseStructure<ProfileDto>();
			structure.setData(profileDto);
			structure.setMessage("Profile Updated successfully");
			structure.setStatusCode(HttpStatus.CREATED.value());
		return new ResponseEntity<ResponseStructure<ProfileDto>>(structure,HttpStatus.CREATED);
	}
	@Override
	public ResponseEntity<ResponseStructure<ProfileDto>> getProfile(Long applicantId) {
		     Profile profile = applicantRepo.findById(applicantId).get().getProfile();
		     ProfileDto profileDto = this.modelMapper.map(profile, ProfileDto.class);
		     ResponseStructure<ProfileDto>structure=new ResponseStructure<ProfileDto>();
		     structure.setData(profileDto);
		     structure.setMessage("Profile data fetched success");
		     structure.setStatusCode(HttpStatus.FOUND.value());
		return new ResponseEntity<ResponseStructure<ProfileDto>>(structure,HttpStatus.FOUND);
	}
}
