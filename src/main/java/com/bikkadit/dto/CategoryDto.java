package com.bikkadit.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

	

    private Long categoryId;
    @NotEmpty
	@Size(min=4, max= 15, message= "Username must be min 3 char and max 15 char!!!")
     private String categoryTitle;
  
    @NotEmpty
	@Size(min=4, max= 15, message= "Username must be min 3 char and max 15 char!!!")
     private String categoryDescription;



}
