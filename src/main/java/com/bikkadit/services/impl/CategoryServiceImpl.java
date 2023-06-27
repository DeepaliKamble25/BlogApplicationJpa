package com.bikkadit.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.bikkadit.dto.CategoryDto;
import com.bikkadit.entities.Category;
import com.bikkadit.exceptions.ResourceNotFoundException;
import com.bikkadit.repository.CategoryRepo;
import com.bikkadit.services.CategoryService;
@Service
public class CategoryServiceImpl implements CategoryService {
	
	private static Logger logger=LoggerFactory.getLogger(CategoryServiceImpl.class);
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		logger.info("At Entering point this is the info for createCategory level logger");
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category save = this.categoryRepo.save(category);
		  logger.info("At Exist point this is the info for createCategory level logger");
		return this.modelMapper.map(save, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Long categoryId) {
		logger.info("At Entering point this is the info for updateCategory level logger"+categoryId);
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "categoryId",categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category category2 = this.categoryRepo.save(category);
		logger.info("At Exist point this is the info for updateCategory level logger"+categoryId);
		return this.modelMapper.map(category2, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Long categoryId) {
		logger.info("At Entering point this is the info for deleteCategory level logger"+categoryId);
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId",categoryId ));
		this.categoryRepo.delete(category);
		logger.info("At Exist point this is the info for deleteCategory level logger"+categoryId);

	}

	@Override
	public CategoryDto getCategory(Long categoryId) {
		logger.info("At Entering point this is the info for getCategory level logger"+categoryId);
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId",categoryId ));
		
		logger.info("At Exist point this is the info for getCategory level logger"+categoryId);
		return this.modelMapper.map(category,CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategories() {
		logger.info("At Entering point this is the info for getCategories level logger");
		List<Category> findAll = this.categoryRepo.findAll();
		List<CategoryDto> categoryDtos = findAll.stream().map((c)->this.modelMapper.map(c, CategoryDto.class)).collect(Collectors.toList());
		logger.info("At Exist point this is the info for getCategories level logger");
		return categoryDtos ;
	}

}
