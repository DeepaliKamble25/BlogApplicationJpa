package com.bikkadit.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.bikkadit.controllers.CommentController;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	private static Logger logger=LoggerFactory.getLogger(CommentController.class);
	
	@Autowired
	private UserDetailsService userDetailService;
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		logger.info("At Entering point this is the info for doFilterInternal level logger"+request+""+response+""+filterChain);
//	1.gettoken
		
		String requestToken = request.getHeader("Authorization");
		//bearer
		
		System.out.println(requestToken);
		 String username = null;
	      String token = null;
	   
	      if (requestToken != null && requestToken.startsWith("Bearer ")) 
	  {
	    	  
	    	  token = requestToken.substring(7);
	    	  try
	    	  {
	    	   username = this.jwtTokenHelper.getUsernameFromToken(token);
	    	  }
	    	  catch (IllegalArgumentException e)
	    	  {
	              System.out.println("Unable to get JWT Token");
	          } 
	    	  catch (ExpiredJwtException e) {
	             System.out.println("JWT Token has expired");
	          }
	    	  catch(MalformedJwtException e) 
	    	  {
	    		  System.out.println("invalid exception");
	    	}
	  }else 
	  {  
	            System.out.println("JWT Token has doesnot start with bearer");
	   }
//		once we get token get validate
	    
	    if(username!=null  && SecurityContextHolder.getContext().getAuthentication()==null)
	    {
	    	UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
	    	
	    	if(this.jwtTokenHelper.validateToken(token, userDetails))
	    	{
	    		
	    		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
	    		
	    	  usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	    		
	    		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	    	
	    	}else {
	    		
	    		System.out.println("Invaild jwt token");
	    		
	    	}
	    }else 
	    {
	    	System.out.println("username is null or context is not null");
	    }
	    
	
	filterChain.doFilter(request, response);
	
	logger.info("At Exit point this is the info for doFilterInternal level logger"+request+""+response+""+filterChain);

	}
}


