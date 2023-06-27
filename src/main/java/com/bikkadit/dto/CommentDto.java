package com.bikkadit.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
	
    private Long id;
	
    @NotBlank
	@Size(min=4, max= 50, message= "Username must be min 3 char and max 50 char!!!")
	private String content;
	
	

}
