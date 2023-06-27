package com.bikkadit.services;

import java.util.List;

import com.bikkadit.dto.UserDto;

public interface UserService {
	
	//create user
	
	UserDto createUser(UserDto userDto);
	//update
	UserDto updateUser(UserDto userDto, Long userSid);
	//getbyId
	UserDto getUserDtoById(Long userid);
	//getAll
	List<UserDto> getAllUser();
	//deleteByid
	void deleteUser(Long userid);
	
	UserDto registerNewUser(UserDto userDto);
	
	

}
