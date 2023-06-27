package com.bikkadit.exceptions;




import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ApiException  extends RuntimeException{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ApiException(String message) {
		super(message);
		
	}

	public ApiException() {
		super();
	
	}

	

	

	
	
}
