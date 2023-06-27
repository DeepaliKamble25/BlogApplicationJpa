package com.bikkadit.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;


import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bikkadit.config.AppConstants;
import com.bikkadit.dto.PostDto;
import com.bikkadit.payloads.ApiResponse;
import com.bikkadit.payloads.PostResponse;
import com.bikkadit.services.FileService;
import com.bikkadit.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {
	
	private static Logger logger=LoggerFactory.getLogger(PostController.class);
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	/**
	 * @author Deepali 
	 * @apiNote create post details and save it
	 * @param postDto
	 * @param userId
	 * @param categoryId
	 * @return saved  postDto 
	 */
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPostDto(@Valid
			@RequestBody PostDto postDto,
			@PathVariable Long userId,
			@PathVariable Long categoryId)
	{ logger.info("At Entering point this is the info for createPostDto level logger"+userId +"and"+categoryId);
		PostDto createPostDto = this.postService.createPostDto(postDto, userId, categoryId);
		
		  logger.info("At Exist point this is the info for createUserDto level logger"+userId +"and"+categoryId);
		return new ResponseEntity<PostDto>(createPostDto,HttpStatus.CREATED);
		}
	/**
	 * @author Deepali
	 * @apiNote get postDto by using userId
	 * @param userId
	 * @return get all postDto with userId
	 */
//	getByUser
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostDtoByuserId(@PathVariable Long userId)
	{  logger.info("At Entering point this is the info for getPostDtoByuserId level logger"+userId);
		List<PostDto> posts = this.postService.getPostDtoByuserId(userId);
		logger.info("At Exist point this is the info for getPostDtoByuserId level logger"+userId );
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);	
	}
	/**
	 * @author Deepali
	 * @apiNote get postDto by categoryId 
	 * @param categoryId
	 * @return all post on categoryId
	 */
	
//	getByCategory
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostDtoBycategoryId(@PathVariable Long categoryId)
	{
		logger.info("At Entering point this is the info for getPostDtoBycategoryId level logger"+categoryId);
		List<PostDto> posts = this.postService.getPostDtoBycategoryId(categoryId);
		logger.info("At Exist point this is the info for getPostDtoBycategoryId level logger"+categoryId);

		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);	
	}
	/**
	 * @author Deepali
	 * @apiNote get all post
	 * @return all data of post 
	 */
	
//	getAllPost
	@GetMapping("/posts")
public ResponseEntity<List<PostDto>> getAllPostDto()
	{  logger.info("At Entering point this is the info for getAllPostDto level logger");
		List<PostDto> posts = this.postService.getAllPostDto();
		logger.info("At Exist point this is the info for getAllPostDto level logger");
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);	
	}
	
	/**
	 * @author Deepali
	 * @apiNote get postDto by postId
	 * @param postId
	 * @return postDto details with postId
	 */
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostDtoById(@PathVariable Long postId)
	{ logger.info("At Entering point this is the info for getPostDtoById level logger"+postId);
		PostDto postDto = this.postService.getPostDtoById(postId);
		logger.info("At Exist point this is the info for getPostDtoById level logger"+postId);
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);	
	}
	/**
	 * @author Deepali
	 * @apiNote delete postDto by using postId
	 * @param postId
	 * @return deleted postDto with postId
	 */
	//delete postByid
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<ApiResponse>  deletePostDto(@PathVariable  Long postId){
		logger.info("At Entering point this is the info for deletePostDto level logger"+postId);
		this.postService.deletePostDto(postId);
		logger.info("At Exit point this is the info for deletePostDto level logger"+postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("POst is Deleted Successfully",true),HttpStatus.OK);
		
	}
	/**
	 * @author Deepali
	 * @apiNote update post detail using single postId
	 * @param postDto
	 * @param postId
	 * @return updated post with postId
	 */
// update post
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable Long postId)
	    {  logger.info("At Entering point this is the info for updatePost level logger"+postId);
		 PostDto updatePost = this.postService.updatePost(postDto, postId);
		 logger.info("At Exit point this is the info for updatePost level logger"+postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.CREATED);
		}
	/**
	 * @author Deepali
	 * @apiNote get all post in pagination format and sorted way
	 * @param pageNumber
	 * @param pageSize
	 * @param sortBy
	 * @param sortDir
	 * @return  data with pageNumber/size given and sort way
	 */
//	getAllPost pagenation  url:http://localhost:9090/api/posts/pages?pageNumber=0&pageSize=5&sortBy=postId&sortDir=asc
	@GetMapping("/posts/pages")
	public ResponseEntity<PostResponse> getAllPostDto//required field is not necessary to pass
	        (
			@RequestParam (value="pageNumber",defaultValue=AppConstants.PAGE_NUMBER,required= false)  Integer pageNumber,
			@RequestParam (value="pageSize",defaultValue=AppConstants.PAGE_SIZE,required= false)Integer pageSize,
			@RequestParam(value="sortId",defaultValue=AppConstants.SORT_BY,required= false)String sortBy,
	        @RequestParam(value="sortDir",defaultValue=AppConstants.SORT_DIR,required= false)String sortDir)
	{
		logger.info("At Entering point this is the info for getAllPostDto level logger");
		 PostResponse postResponse= this.postService.getAllPostDto(pageNumber, pageSize,sortBy,sortDir);
		 logger.info("At Exit point this is the info for getAllPostDto level logger");
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);	
	}
	/**
	 * @author Deepali
	 * @apiNote search post by using title
	 * @param keywords
	 * @return postDto with given title 
	 */
	
//	search
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords)
	{
		logger.info("At Entering point this is the info for searchPostByTitle level logger");
		List<PostDto> postDto = this.postService.searchPostDto(keywords);
		 logger.info("At Exit point this is the info for searchPostByTitle level logger");
		return new ResponseEntity<List<PostDto>>(postDto,HttpStatus.OK);
   }
	/**
	 * @author Deepali
	 * @apiNote upload post image by using postId
	 * @param file
	 * @param postId
	 * @return image saved in folder/database
	 * @throws IOException
	 */
//	post=imae upload
	@PostMapping("/post/file/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
		@RequestParam  MultipartFile file,
		@PathVariable Long postId) throws IOException{
		logger.info("At Entering point this is the info for uploadPostImage level logger");
		PostDto postDto = this.postService.getPostDtoById(postId);
		String fileName = this.fileService.uploadimage(path, file);
		
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		logger.info("At Exit point this is the info for uploadPostImage level logger");
		return new ResponseEntity<PostDto>( updatePost,HttpStatus.CREATED);
	
		
	}
	/**
	 * @author Deepali
	 * @apiNote download image from folder
	 * @param fileName
	 * @param response
	 * @throws IOException
	 * @return image 
	 */
//	method to serve files  imageName=="is a dynamic url which written by USER"
	
	
	@GetMapping(value="/post/file/{fileName}", produces=MediaType.IMAGE_JPEG_VALUE)
	public void  downloadImage(
			@PathVariable ("fileName") String fileName ,
	        HttpServletResponse response   
	        )throws IOException
	{
		logger.info("At Entering point this is the info for  downloadImage level logger");
		InputStream resource = this.fileService.getResource(path, fileName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(  resource,response.getOutputStream());
		logger.info("At Exit point this is the info for downloadImage level logger");
	}
	
}
