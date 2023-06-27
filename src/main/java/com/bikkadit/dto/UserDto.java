package com.bikkadit.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.bikkadit.entities.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@AllArgsConstructor
public class UserDto {
	
	private Long userId;
	//(notempty will check notnull and black both annnotation)
	@NotEmpty
	@Size(min=4, max= 15, message= "Username must be min 3 char and max 15 char!!!")
	private String name;
	
	@Email(message="email-Id must be in valid format!!!")
	private String email;
	
	//@JsonIgnore
	@NotEmpty
	@Pattern( regexp =  "^[a-zA-Z0-9]{6,12}$",message ="password must be given format only!!!")
	@Size(min=3, max= 50, message= "Password must be min 6 char and max 15 char!!!")
	private String password;
	
	@NotEmpty
	private String about;
	
	private Set<RoleDto> roles=new HashSet<>();
	
	
	
	
}
