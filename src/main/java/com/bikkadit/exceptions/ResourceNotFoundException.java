package com.bikkadit.exceptions;

import com.bikkadit.config.AppConstants;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException {

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String resourceName;
	String fieldName;
	Long fieldValue;
	public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue) {
		super(String.format(AppConstants.resourceName, resourceName,fieldName,fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	
	
}
