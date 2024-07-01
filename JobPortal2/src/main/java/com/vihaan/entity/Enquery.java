package com.vihaan.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Enquery {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long EnqueryId;
	private String fromName;
	private String fromEmail;
	private LocalDateTime enqueryCreatedDateTime;
	private String subject;
	private String message;
	
}
