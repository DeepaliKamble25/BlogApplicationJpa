package com.bikkadit.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.bikkadit.dto.PostDto;
import com.bikkadit.entities.Category;
import com.bikkadit.entities.Post;
import com.bikkadit.entities.User;
import com.bikkadit.exceptions.ResourceNotFoundException;
import com.bikkadit.payloads.PostResponse;
import com.bikkadit.repository.CategoryRepo;
import com.bikkadit.repository.PostRepo;
import com.bikkadit.repository.UserRepo;
import com.bikkadit.services.PostService;
@Service
public class PostServiceImpl implements PostService {
	
	private static Logger logger =LoggerFactory.getLogger(PostServiceImpl.class);
	
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	
    @Autowired
	private UserRepo userRepo;
    
    @Autowired
    private CategoryRepo categoryRepo;
    
	@Override
	public PostDto createPostDto(PostDto postDto, Long userId, Long categoryId) {
		logger.info("At Entering point this is the info for createPostDto level logger");
		User user1 = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
Category category1 = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddDate(new Date());
		post.setUser(user1);
		post.setCategory(category1);
		Post savePost = postRepo.save(post);
		logger.info("At Exist point this is the info for createPostDto level logger");
		return this.modelMapper.map(savePost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Long postId) {
		logger.info("At Entering point this is the info for updatePost level logger"+postId);
	Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId",postId ));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post post2 = this.postRepo.save(post);
		logger.info("At Exist point this is the info for updatePost level logger"+postId);
		return this.modelMapper.map(post2, PostDto.class);
	}

	@Override
	public void deletePostDto(Long postId) {
		logger.info("At Entering point this is the info for deletePostDto level logger"+postId);
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("PostDto","postId",postId));
             this.postRepo.delete(post);
             logger.info("At Exist point this is the info for deletePostDto level logger"+postId);
	}

	@Override
	public List<PostDto> getAllPostDto() {
		logger.info("At Entering point this is the info for getAllPostDto level logger");
		List<Post> posts = this.postRepo.findAll();
		List<PostDto> postDtos = posts.stream().map((p)->this.modelMapper.map(p,PostDto.class)).collect(Collectors.toList());
		 logger.info("At Exist point this is the info for deletePostDto level logger");
		return postDtos;
	}

	@Override
	public PostDto getPostDtoById(Long postId) {
		logger.info("At Entering point this is the info for getPostDtoById level logger"+postId);
		Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("PostDto","postId",postId));
		PostDto postDto = modelMapper.map(post, PostDto.class);
		 logger.info("At Exist point this is the info for getPostDtoById level logger"+postId);
		return postDto;
	}

	@Override
	public List<PostDto> getPostDtoBycategoryId(Long categoryId) {
		logger.info("At Entering point this is the info for getPostDtoBycategoryId level logger"+categoryId);
		Category cateory = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId));
		List<Post> posts = this.postRepo.findByCategory(cateory);
		List<PostDto> postDtos = posts.stream().map((p)->this.modelMapper.map(p,PostDto.class)).collect(Collectors.toList());
		logger.info("At Exist point this is the info for getPostDtoBycategoryId level logger"+categoryId);
		return  postDtos;
	}

	@Override
	public List<PostDto> getPostDtoByuserId(Long userId) {
		logger.info("At Entering point this is the info for getPostDtoByuserId level logger"+userId);
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "userId",userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((p)->this.modelMapper.map(p,PostDto.class)).collect(Collectors.toList());
		logger.info("At Exist point this is the info for getPostDtoByuserId level logger"+userId);
		return postDtos;
	}

	@Override
	public List<PostDto> searchPostDto(String keyword) {
	/*List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");*/	
		logger.info("At Entering point this is the info for searchPostDto level logger"+keyword);
  List<Post> posts = this.postRepo.findByTitleContaining(keyword);
   List<PostDto> postDtos = posts.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
   logger.info("At Exist point this is the info for searchPostDto level logger"+keyword);	
   return postDtos;
	}

	@Override
	public PostResponse getAllPostDto(Integer pageNumber, Integer pageSize,String sortBy,String sortDir) {
        
		logger.info("At Entering point this is the info for getAllPostDto level logger :"+ pageNumber+" : "+pageSize+" : "+sortDir);
		Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		
		PageRequest pagePost = PageRequest.of(pageNumber, pageSize, sort);
		
			Page<Post> findAll = this.postRepo.findAll(pagePost);
				
				List<Post> allPOst = findAll.getContent();
				List<PostDto> postDtos = allPOst.stream().map((post)->this.modelMapper.map(post, PostDto.class) ).collect(Collectors.toList());
				PostResponse postResponse=new PostResponse();
				postResponse.setContent(postDtos);
				postResponse.setPageNumber(findAll.getNumber());
				postResponse.setPageSize(findAll.getSize());
				postResponse.setTotalElements(findAll.getTotalElements());
				postResponse.setTotalPages(findAll.getTotalPages());
				postResponse.setLastPages(findAll.isLast());
				
				logger.info("At Exit point this is the info for getAllPostDto level logger :"+ pageNumber+" : "+pageSize+" : "+sortDir);
				return postResponse;
		
		
	}

	
	



}
