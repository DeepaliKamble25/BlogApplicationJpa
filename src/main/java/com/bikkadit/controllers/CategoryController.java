package com.bikkadit.controllers;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bikkadit.config.AppConstants;
import com.bikkadit.dto.CategoryDto;
import com.bikkadit.payloads.ApiResponse;
import com.bikkadit.services.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	private static Logger logger=LoggerFactory.getLogger(CategoryController.class);
	
	@Autowired
	private CategoryService service;
	/**
	 * @author Deepali
	 * @apiNote create Category details
	 * @param categoryDto
	 * @since 1.0
	 * @return CategoryDto
	 */
	
	@PostMapping("/")
	public ResponseEntity<CategoryDto > createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{  logger.info("At Entering point this is the info for createCategory level logger");
		CategoryDto categoryDto2 = this.service.createCategory(categoryDto);
		  logger.info("At Exist point this is the info for createCategory level logger");
		return new ResponseEntity<CategoryDto>(categoryDto2,HttpStatus.CREATED);
		
	}
	/**
	 * @author Deepali
	 * @since 1.0
	 * @apiNote update category details
	 * @param categoryDto
	 * @param categoryId
	 * @return CategoryDto updated successfully
	 */
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto  categoryDto,@PathVariable Long categoryId )
	{    logger.info("At Entering point this is the info for updateCategory level logger"+categoryId);
		CategoryDto updateCategory = this.service.updateCategory(categoryDto, categoryId);
		 logger.info("At Exist point this is the info for updateCategory level logger"+categoryId);
		
		return new ResponseEntity<CategoryDto>(updateCategory,HttpStatus.OK);
	
	}
	/**
	 * @author Deepali
	 * @apiNote delete category details using categoryId
	 * @param categoryId
	 * @return delete  single category
	 */
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId){
		logger.info("At Entering point this is the info for deleteCategory level logger"+categoryId);
		this.service.deleteCategory(categoryId);
		logger.info("At Exist point this is the info for deleteCategory level logger"+categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.DELETEMSGE_CATEGORY,true),HttpStatus.OK);
		
	}
	/**
	 * @author Deepali
	 * @apiNote get single category  details
	 * @param categoryId
	 * @return single categoryDto
	 */
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Long categoryId)
	{logger.info("At Entering point this is the info for getCategory level logger");
		CategoryDto categoryDto = this.service.getCategory(categoryId);
		logger.info("At Exist point this is the info for  getCategory level logger");
		return new ResponseEntity<CategoryDto>(categoryDto ,HttpStatus.FOUND);
		
	}
	/**
	 * @author Deepali
	 * @apiNote get all category details
	 * @return all data of categoryDto
	 */
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getCategories()
	{  logger.info("At Entering point this is the info for getCategories level logger");
		 List<CategoryDto> categories = this.service.getCategories();
		 logger.info("At Exist point this is the info for   getCategories level logger");
		return new ResponseEntity<List<CategoryDto>>(categories ,HttpStatus.FOUND);
		
	}
}