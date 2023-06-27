package com.bikkadit.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.bikkadit.config.AppConstants;
import com.bikkadit.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		
		     String message = ex.getMessage();
		     ApiResponse apiResponse=new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse>(apiResponse,HttpStatus.NOT_FOUND);
		
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handlerMethodArgumentNotValidException(MethodArgumentNotValidException ex)
	{
		Map<String,String> resp=new HashMap<String, String>();
		ex.getBindingResult()
		  .getAllErrors()
		  .forEach((error)->{
		  String fieldname = ((FieldError)error).getField();
		  String message = error.getDefaultMessage();
		  resp.put(fieldname, message);
		   });//Consumer interfaceFunction take input no output accept()
		
		return new ResponseEntity<Map<String,String>>(resp,HttpStatus.BAD_REQUEST);
		
	}
	@ExceptionHandler(UserDetailsNotFoundException.class)
	public ResponseEntity<String> userDetailsNotFoundException(UserDetailsNotFoundException ex){
		
	     String message = ex.getMessage()+ex.username;
	    
	return new ResponseEntity<String>(message,HttpStatus.NOT_FOUND);
	
}
	
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handleApiExceptionHandler(ApiException ex){
		
		     String message = AppConstants.AUTHO_INVALID;
		     ApiResponse api=new ApiResponse(message,true);
		    
		return new ResponseEntity<ApiResponse>(api,HttpStatus.BAD_REQUEST);
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
/*	@ExceptionHandler(FileNotFoundException.class)
	public ResponseEntity<ApiResponse> fileNotFoundExceptionHandler(FileNotFoundException f){
		
		String message = f.getMessage();
	     ApiResponse apiResponse=new ApiResponse(message,false);
		return new ResponseEntity<ApiResponse>(message,HttpStatus.NOT_FOUND);
		
	}*/
	
	
	
	
	
}
