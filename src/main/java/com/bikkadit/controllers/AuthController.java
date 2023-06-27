package com.bikkadit.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.bikkadit.config.AppConstants;
import com.bikkadit.dto.UserDto;
import com.bikkadit.exceptions.ApiException;

import com.bikkadit.payloads.JwtAuthRequest;
import com.bikkadit.payloads.JwtAuthResponse;
import com.bikkadit.security.JwtTokenHelper;
import com.bikkadit.services.UserService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
	
	private static Logger logger=LoggerFactory.getLogger(CommentController.class);
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	/**
	 * @author Deepali
	 * @apiNote create token
	 * @param request
	 * @return
	 * @throws Exception
	 */
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(
			@RequestBody JwtAuthRequest request
			) throws Exception
	{
		logger.info("At Entering point this is the info for createToken level logger");
		this.authenticate(request.getUserName(),request.getPassword());
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUserName());
				
		String token = this.jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse response=new JwtAuthResponse();
		response.setToken(token);
		logger.info("At Exist point this is the info for createToken level logger");
		return new ResponseEntity<JwtAuthResponse>(response,HttpStatus.OK);
		
		
	}
/**
 * @author HP
 * @apiNote insert user name and password for authentication
 * @param userName
 * @param password
 * @throws Exception
 */

	private void authenticate(String userName, String password) throws Exception {
		logger.info("At Entering point this is the info for createToken level logger");
  UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userName,password);
		
 try {
  this.authenticationManager.authenticate(authenticationToken);}
 catch(BadCredentialsException e) {
	 System.out.println("Invalid Details");
	 logger.info("At Exist point this is the info for authenticate level logger");
	 throw new ApiException (AppConstants.AUTHO_INVALID);
 }
  
	}

	
//	register new user api
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto ){
		
		logger.info("At Entering point this is the info for registerUser level logger"+userDto);
		
		UserDto registerNewUser = this.userService.registerNewUser(userDto);
		
		logger.info("At Exist point this is the info for registerUser level logger"+userDto);
		
		return new ResponseEntity<UserDto>(registerNewUser,HttpStatus.CREATED);
		
		
		
	}
	
	
	
}
