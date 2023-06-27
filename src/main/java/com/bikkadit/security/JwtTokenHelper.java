package com.bikkadit.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.bikkadit.config.AppConstants;
import com.bikkadit.controllers.CommentController;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenHelper {

	private static Logger logger=LoggerFactory.getLogger(CommentController.class);
	
	
	
	private String secret ="jwtTokenKey";
	
//	retrive token name from jwt token
	public String getUsernameFromToken(String token)
	{	
		logger.info("At Entering point this is the info for getUsernameFromToken level logger"+token);
	return getClaimFromToken(token,   Claims::getSubject);	
	
	}
	
	public Date getExpirationDateFromToken(String token)
	{
		return getClaimFromToken(token,Claims::getExpiration);
			
   }
	public<T> T getClaimFromToken(String Token,Function<Claims,T> claimsResolver)
	{	logger.info("At Entering point this is the info for getUsernameFromToken level logger"+Token+""+claimsResolver);

		final Claims claims =getAllClaimsFromToken(Token);
		
		logger.info("At Exit point this is the info for getUsernameFromToken level logger"+Token+""+claimsResolver);

		return claimsResolver.apply(claims);	
	}
	//for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		logger.info("At Entering point this is the info for getUsernameFromToken level logger");
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	
//	checking the token has expired
	
	private Boolean isTokenExpired(String token) {
		logger.info("At Entering point this is the info for isTokenExpired level logger");
		final Date expiration=getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
//	generate token for user
	public String generateToken(UserDetails userDetails) {
		logger.info("At Entering point this is the info for generateToken level logger");
		Map<String,Object> claims=new HashMap<String, Object>();
		return doGenerateToken(claims,userDetails.getUsername());
	}
	
//	while creating token
//	1. define claims of token , like issuer,expiration,subject and Id
//	2. sign the JWT using the HSS12 algorithm and secret key
//	3. According to jws compact Serliazation(https://tools.iets.org/html/draft.iets.jose)
//	compaction of the jwt to a url safe string 
	private  String doGenerateToken (Map<String ,Object >claims,String subject) {
		logger.info("At Entering point this is the info for doGenerateToken level logger"+claims+""+subject);
		
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+AppConstants.JWT_TOKEN_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512,secret).compact();


	}
//	validate token 
	public Boolean validateToken(String token,UserDetails userDetails) {
		logger.info("At Entering point this is the info for  validateToken level logger"+token+""+userDetails);
		final String username= getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		
	}
}
