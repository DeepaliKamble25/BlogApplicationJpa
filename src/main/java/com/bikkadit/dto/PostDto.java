package com.bikkadit.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
	
	private Long postId;
    @NotEmpty
	@Size(min=4, max= 15, message= "Username must be min 3 char and max 15 char!!!")
	private String title;
	@NotBlank
	@Size(min=4, max= 50, message= "Username must be min 3 char and max 50 char!!!")
	private String content;
	
	private String imageName="default.png";
	
	private  Date addDate;
	
	private CategoryDto category;
	
	private UserDto user;
	
	private Set<CommentDto> commets=new HashSet<>();
	
	
	
	
	
	
	
	
	
	
}
