package com.bikkadit.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class MyConfiguration {

	@Bean//class is created to inject
	public ModelMapper modelMapper() {
		
		return new ModelMapper();
	}
}
