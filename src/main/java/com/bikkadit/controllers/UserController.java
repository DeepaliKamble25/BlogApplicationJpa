package com.bikkadit.controllers;



import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bikkadit.config.AppConstants;
import com.bikkadit.dto.UserDto;
import com.bikkadit.payloads.ApiResponse;
import com.bikkadit.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private static Logger logger=LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	/**
	 * @author Deepali
	 * @apiNote create userDto detail and save in DB
	 * @param userDto
	 * @return saved userDto 
	 */
	@PostMapping("/")
	public ResponseEntity<UserDto> createUserDto(@Valid @RequestBody UserDto userDto)
	{
		logger.info("At Entering point this is the info for createUserDto level logger");
		     UserDto userDto2 = this.userService.createUser(userDto);
		     
		     logger.info("At Exist point this is the info for createUserDto level logger");
			return new ResponseEntity<UserDto>(userDto2,HttpStatus.CREATED) ;		
	}
	/**
	 * @author  Deepali
	 * @apiNote update userDto using userId
	 * @param user
	 * @param uid
	 * @return updated userDto details
	 */
	
	@PutMapping("/{userId}")                      //requestBody=new data insert with object user
	public ResponseEntity<UserDto>  updateUserDto(@Valid @RequestBody UserDto user, @PathVariable("userId") Long uid)
	{  logger.info("At Entering point this is the info for updateUserDto level logger"+uid);
		UserDto updateUser =this. userService.updateUser(user, uid);
		 logger.info("At Exist point this is the info for updateUserDto level logger"+uid);
		return new  ResponseEntity<UserDto>(updateUser,HttpStatus.CREATED);		
      }
	/**
	 * @author Deepali
	 * @apiNote delete user using userId
	 * @param userId
	 * @return deleted user successfully
	 */
	//ADMIN
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser (@PathVariable  Long userId)
	{  logger.info("At Entering point this is the info for deleteUser level logger"+userId);
		this.userService.deleteUser(userId);
		logger.info("At Exist point this is the info for updateUserDto level logger"+userId);
		return new ResponseEntity<>(new ApiResponse(AppConstants.DELETEMSGE_USER,true),HttpStatus.OK);
	}
	/**
	 * @author Deepali
	 * @apiNote get all user data
	 * @return get all details of user
	 */
    @GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser()
    {  logger.info("At Entering point this is the info for getAllUser level logger");
		List<UserDto> allUser = this.userService.getAllUser();
		logger.info("At Exist point this is the info for getAllUser level logger");
		return new ResponseEntity<List<UserDto>>(allUser,HttpStatus.OK);
		
	}
    /**
     * @author Deepali
     * @apiNote get single user by using userId
     * @param userId
     * @return user detail of userId
     */
    
    @GetMapping("/{userId}")
   	public ResponseEntity<UserDto> getSingleUser( @PathVariable Long userId)
    {     logger.info("At Entering point this is the info for getSingleUser level logger"+userId);
     	UserDto userDto = this.userService.getUserDtoById(userId);
     	logger.info("At Exist point this is the info for getSingleUser level logger"+userId);
   		return new  ResponseEntity<UserDto>(userDto,HttpStatus.OK);
   		
   }
    
}
