package com.bikkadit.services.impl;

import java.util.List;

import java.util.stream.Collectors;

import com.bikkadit.exceptions.ResourceNotFoundException;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bikkadit.repository.RoleRepository;
import com.bikkadit.repository.UserRepo;
import com.bikkadit.services.UserService;
import com.bikkadit.config.AppConstants;
import com.bikkadit.dto.UserDto;
import com.bikkadit.entities.Role;
import com.bikkadit.entities.User;
@Service
public class UserServiceImpl implements UserService{
	
	private static Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public UserDto createUser(UserDto userDto) {
		logger.info("At Entering point this is the info for createUser level logger");
		User user=this.dtoToUser(userDto);
		User save = this.userRepo.save(user);
		UserDto userDto2 = this.userToUserDto(save);
		logger.info("At Exist point this is the info for createUser level logger");
		return userDto2;
	}

	@Override
	public UserDto updateUser(UserDto userDto, Long userid) {
		logger.info("At Entering point this is the info for updateUser level logger"+userid);
		User user = this.userRepo.findById(userid)
		.orElseThrow(()->  new ResourceNotFoundException("User","Id",userid));
		
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setEmail(userDto.getEmail());
		User updatedUser = this.userRepo.save(user);
		UserDto userDto2 = this.userToUserDto(updatedUser);
		logger.info("At Exist point this is the info for updateUser level logger"+userid);
		return userDto2;
	}

	@Override
	public UserDto getUserDtoById(Long userid) {
		logger.info("At Entering point this is the info for getUserDtoById level logger"+userid);
		User user = this.userRepo.findById(userid)
				.orElseThrow(()->  new ResourceNotFoundException("User","Id",userid));
		logger.info("At Exist point this is the info for getUserDtoById level logger"+userid);
		return this.userToUserDto(user);
	}

	@Override
	public List<UserDto> getAllUser() {
		logger.info("At Entering point this is the info for getAllUser level logger");

		List<User> users = userRepo.findAll();
		List<UserDto> list = users.stream().map(user->this.userToUserDto(user)).collect(Collectors.toList());
		logger.info("At Exist point this is the info for getAllUser level logger");
		return list;
	}

	@Override
	public void deleteUser(Long userid) {
		logger.info("At Entering point this is the info for deleteUser level logger"+userid);
		User user = userRepo.findById(userid).orElseThrow(()->new ResourceNotFoundException("User","Id",userid)); 
		logger.info("At Exist point this is the info for deleteUser level logger"+userid);
		this.userRepo.delete(user);
		
	}
	
	public User dtoToUser(UserDto  userDto) {
		User user=this.modelMapper.map(userDto, User.class);
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getName());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
//		
		return user;
		
	}
	public UserDto userToUserDto(User user) {
		UserDto userDto=this.modelMapper.map(user, UserDto.class);
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getName());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		
		return userDto;
      }

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		logger.info("At Entering point this is the info for registerNewUser level logger"+userDto);
		
		User user = this.modelMapper.map(userDto, User.class);
		//encoded password
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		//
		Role role = this.roleRepository.findById(AppConstants.NORMAL_USER).get();
		
		user.getRoles().add(role);
		
		User newUser = this.userRepo.save(user);
		
		logger.info("At Exit point this is the info for registerNewUser level logger"+userDto);

		return this.modelMapper.map(newUser, UserDto.class);
	}
	
}
