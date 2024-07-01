package com.vihaan.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.vihaan.dto.EnqueryDto;
import com.vihaan.entity.Enquery;
import com.vihaan.repo.EnqueryRepo;
import com.vihaan.service.EnqueryService;
import com.vihaan.util.ResponseStructure;

@Service
public class EnqueryServiceImpl implements EnqueryService {

	@Autowired
	private EnqueryRepo enqueryRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Override
	public ResponseEntity<ResponseStructure<EnqueryDto>> createEnquery(EnqueryDto enqueryDto) {
		System.out.println(enqueryDto);
		Enquery enquery = this.modelMapper.map(enqueryDto, Enquery.class);
		System.out.println(enquery);
		enquery.setEnqueryCreatedDateTime(LocalDateTime.now());
		Enquery enquery2 = enqueryRepo.save(enquery);
		EnqueryDto enqueryDto2 = this.modelMapper.map(enquery2, EnqueryDto.class);
		MailEnquery("mgc25999@gmail.com", "Enquery id : "+enquery2.getEnqueryId()+" "+enquery2.getSubject(), enquery2.getMessage());
		ResponseStructure<EnqueryDto>structure= new ResponseStructure<EnqueryDto>();
		structure.setData(enqueryDto2);
		structure.setMessage("Enquery submitted to admin");
		structure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<EnqueryDto>>(structure,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<String>> MailEnquery(String toMail,String subject,String message) {
		SimpleMailMessage mailMessage= new SimpleMailMessage();
		mailMessage.setTo(toMail);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);
		javaMailSender.send(mailMessage);
		ResponseStructure<String>structure= new ResponseStructure<String>();
		structure.setData("Enquery mail sent to admin");
		structure.setMessage("Enquery mail sent to admin successfully");
		structure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<String>>(structure,HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseStructure<List<EnqueryDto>>> getAllEnqueries() {
		List<Enquery> enqueries = enqueryRepo.findAll();
		List<EnqueryDto>enqueryDtos=new ArrayList<EnqueryDto>();
		for (Enquery enquery : enqueries) {
			EnqueryDto enqueryDto = this.modelMapper.map(enquery, EnqueryDto.class);
			enqueryDtos.add(enqueryDto);
		}
		ResponseStructure<List<EnqueryDto>>structure= new ResponseStructure<List<EnqueryDto>>();
		structure.setData(enqueryDtos);
		structure.setMessage("All enqueries fethed successfully");
		structure.setStatusCode(HttpStatus.OK.value());
		return new ResponseEntity<ResponseStructure<List<EnqueryDto>>>(structure,HttpStatus.OK);
	}

}
