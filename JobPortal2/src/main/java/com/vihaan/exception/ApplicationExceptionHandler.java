package com.vihaan.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vihaan.util.ErrorStructure;


/*
 * @RestControllerAdvice annotated class accepts all exceptions and Handle the same exception
 */
@RestControllerAdvice
public class ApplicationExceptionHandler {

	/*
	 * This method accepting UserWithSameEmailExist exception and Handling the same exception 
	 * by returning proper ErrorStructure 
	 */
	
	@ExceptionHandler(UserWithSameEmailExist.class)
	public ResponseEntity<ErrorStructure> emailAlreadyExist(UserWithSameEmailExist emailExist) {
		
		ErrorStructure errorStructure= new ErrorStructure();
		errorStructure.setMessage(emailExist.getMessage());
		errorStructure.setRootCause("Email already in database");
		errorStructure.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
		return new ResponseEntity<ErrorStructure>(errorStructure,HttpStatus.NOT_ACCEPTABLE);
	}
	@ExceptionHandler(EmailNotFoundException.class)
	public ResponseEntity<ErrorStructure> emailNotFound(EmailNotFoundException ex) {
		
		ErrorStructure errorStructure= new ErrorStructure();
		errorStructure.setMessage(ex.getMessage());
		errorStructure.setRootCause("Email NOT FOUND  in database");
		errorStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ErrorStructure>(errorStructure,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(PasswordMissMatchException.class)
	public ResponseEntity<ErrorStructure> passwordMismatch(PasswordMissMatchException ex) {
		
		ErrorStructure errorStructure= new ErrorStructure();
		errorStructure.setMessage(ex.getMessage());
		errorStructure.setRootCause("Password Not Matching");
		errorStructure.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
		return new ResponseEntity<ErrorStructure>(errorStructure,HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(ForbiddenOperationException.class)
	public ResponseEntity<ErrorStructure> forbiddenOperation(ForbiddenOperationException ex) {
		
		ErrorStructure errorStructure= new ErrorStructure();
		errorStructure.setMessage(ex.getMessage());
		errorStructure.setRootCause("forbiddden Operation");
		errorStructure.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
		return new ResponseEntity<ErrorStructure>(errorStructure,HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(UserNotFoundByIdException.class)
	public ResponseEntity<ErrorStructure> userNotFoundById(UserNotFoundByIdException ex) {
		
		ErrorStructure errorStructure= new ErrorStructure();
		errorStructure.setMessage(ex.getMessage());
		errorStructure.setRootCause("User not found by this Id ");
		errorStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ErrorStructure>(errorStructure,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(jobNotFoundException.class)
	public ResponseEntity<ErrorStructure> jobNotFoundException(jobNotFoundException ex) {
		
		ErrorStructure errorStructure= new ErrorStructure();
		errorStructure.setMessage(ex.getMessage());
		errorStructure.setRootCause("Job not found by this Id ");
		errorStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ErrorStructure>(errorStructure,HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(JobApplicationNotFoundException.class)
	public ResponseEntity<ErrorStructure> JobApplicationNotFoundException(JobApplicationNotFoundException ex) {
		
		ErrorStructure errorStructure= new ErrorStructure();
		errorStructure.setMessage(ex.getMessage());
		errorStructure.setRootCause("JobApplication not found by this Id ");
		errorStructure.setStatusCode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ErrorStructure>(errorStructure,HttpStatus.NOT_FOUND);
	}
}
