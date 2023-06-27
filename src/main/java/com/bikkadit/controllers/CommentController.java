package com.bikkadit.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.bikkadit.config.AppConstants;
import com.bikkadit.dto.CommentDto;
import com.bikkadit.payloads.ApiResponse;
import com.bikkadit.services.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
	
	private static Logger logger=LoggerFactory.getLogger(CommentController.class);
	@Autowired
	private CommentService commentService;
	/**
	 * @author Deepali
	 * @apiNote create comment details and save in database
	 * @param commentDto
	 * @param postId
	 * @return commentDto which is save in database
	 */
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment (@Valid @RequestBody CommentDto commentDto,
			@PathVariable Long postId)
	{ logger.info("At Entering point this is the info for createComment level logger");
		CommentDto createComment = this.commentService.createComment(commentDto, postId);
		
		logger.info("At Exist point this is the info for createComment level logger");
		return new ResponseEntity<CommentDto>(createComment,HttpStatus.CREATED);

     }
	/**
	 * @author Deepali 
	 * @apiNote delete comment by using commentId
	 * @param commentId
	 * @return comment deleted successfully
	 */
	
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Long commentId){
		
		logger.info("At Entering point this is the info for deleteComment level logger"+commentId);
		this.commentService.deleteComment(commentId);
		logger.info("At Exist point this is the info for createComment level logger"+commentId);
		return new ResponseEntity<ApiResponse>(new ApiResponse(AppConstants.DELETEMSGE_COMMENT,true),HttpStatus.OK);
		
	}
}
