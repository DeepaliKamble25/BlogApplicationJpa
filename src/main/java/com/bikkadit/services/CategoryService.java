package com.bikkadit.services;

import java.util.List;

import com.bikkadit.dto.CategoryDto;

public interface CategoryService {
	
	
//	create
	CategoryDto createCategory(CategoryDto categoryDto);
	
	
//	update
	CategoryDto updateCategory(CategoryDto categoryDto,Long categoryId);
	
//	delete
	void deleteCategory(Long categoryId);
	
	//get
	CategoryDto getCategory(Long categoryId);
	
	//getAll
	List<CategoryDto> getCategories();
}
