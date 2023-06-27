package com.bikkadit.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.bikkadit.controllers.CommentController;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	
	private static Logger logger=LoggerFactory.getLogger(CommentController.class);

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		logger.info("At Entering point this is the info for commence level logger"+request+""+response);
		
		
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Access Denied !!!");
		
		logger.info("At Exit point this is the info for commence level logger"+request+""+response);
		
		
		
	}

}
