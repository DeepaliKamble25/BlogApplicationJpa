package com.bikkadit.config;
import com.bikkadit.controllers.CommentController;
import com.bikkadit.security.CustomUserDetailService;
import com.bikkadit.security.JwtAuthenticationEntryPoint;
import com.bikkadit.security.JwtAuthenticationFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	public static final String[] PUBLIC_URLS= {
			"/api/v1/auth/**",
			"/v3/api-docs" ,
			"/v3/api-docs" ,
			"/swagger-resources/**"  ,
			"/swagger-ui/**",
			"/webjars/**"                 };
	
	private static Logger logger=LoggerFactory.getLogger(CommentController.class);
	@Autowired
	private CustomUserDetailService customUserDetailsService;
	
	@Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;
/*
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		http.
		csrf().disable()
		.authorizeHttpRequests()
		.anyRequest()
		.authenticated()
		.and()
		.httpBasic();
	}
*/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		logger.info("At Entering point this is the info for configure level logger"+http);
		http.
		csrf().disable()
		.authorizeHttpRequests()
		.antMatchers(PUBLIC_URLS).permitAll()
		.antMatchers(HttpMethod.GET).permitAll()
		.anyRequest()
		.authenticated()
		.and()
		.exceptionHandling().authenticationEntryPoint(this.jwtAuthenticationEntryPoint)
		.and()
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(this.jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
	
		logger.info("At Exist point this is the info for configure level logger"+http);
		}
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		logger.info("At Entering point this is the info for configure level logger"+auth);
		
		auth.userDetailsService(this.customUserDetailsService).passwordEncoder(passwordEncoder());
		logger.info("At Exist point this is the info for configure level logger"+auth);
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
		
	}
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		
		return super.authenticationManagerBean();
	}

	
	
	
}
