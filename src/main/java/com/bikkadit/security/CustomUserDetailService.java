package com.bikkadit.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bikkadit.config.AppConstants;
import com.bikkadit.controllers.CommentController;
import com.bikkadit.entities.User;

import com.bikkadit.exceptions.UserDetailsNotFoundException;

import com.bikkadit.repository.UserRepo;
@Service
public class CustomUserDetailService implements UserDetailsService {

	private static Logger logger=LoggerFactory.getLogger(CommentController.class);
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		 logger.info("At Entering point this is the info for loadUserByUsername level logger"+username);
		
		User user = this.userRepo.findByEmail(username).orElseThrow(()->new UserDetailsNotFoundException(AppConstants.USERDETAIL_NOT_FOUND,username));
		
		logger.info("At Exist point this is the info for loadUserByUsername level logger"+username);
		
		return user;
	}
	

	

}
