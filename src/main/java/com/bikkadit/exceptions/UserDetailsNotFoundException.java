package com.bikkadit.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailsNotFoundException extends RuntimeException {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message;
	String username;
	public UserDetailsNotFoundException(String message, String username) {
		super(message);
		this.message = message;
		this.username = username;
	}
	
	
	
	
	
	
	

}
