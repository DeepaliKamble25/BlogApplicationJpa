package com.bikkadit.services;

import java.util.List;

import com.bikkadit.dto.PostDto;
import com.bikkadit.payloads.PostResponse;



public interface PostService {
	
	//create
	
	PostDto createPostDto(PostDto postDto,Long userId, Long categoryId);
	
	//update
	
	PostDto updatePost(PostDto postDto,Long postId );
	//getByUserId
	
	
	//delete
	
	void deletePostDto(Long postId);
	
	
	//getAllPost
	List <PostDto >getAllPostDto();
	
	
	//get single post
	PostDto getPostDtoById(Long postId);
	
	//get post by categoryId
	List <PostDto >getPostDtoBycategoryId(Long categoryId);
	
	//get post by userId
	List <PostDto >getPostDtoByuserId(Long userId);
	
	//search post
	List<PostDto> searchPostDto(String keyword );

	//getAllPostByPagenation
		PostResponse getAllPostDto(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
	
	
	
	

}
