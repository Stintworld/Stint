package com.vihaan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.vihaan.dto.EnqueryDto;
import com.vihaan.service.EnqueryService;
import com.vihaan.util.ResponseStructure;



@RestController
@Validated
@CrossOrigin
public class EnqueryController {

	@Autowired
	private EnqueryService enqueryService;
	
	@PostMapping("enqueries/create")
	public ResponseEntity<ResponseStructure<EnqueryDto>> createEnquery(@RequestBody EnqueryDto enqueryDto){
		return enqueryService.createEnquery(enqueryDto);
	}
}
