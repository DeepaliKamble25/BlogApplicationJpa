package com.bikkadit.services.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bikkadit.dto.CommentDto;
import com.bikkadit.entities.Comment;
import com.bikkadit.entities.Post;
import com.bikkadit.exceptions.ResourceNotFoundException;
import com.bikkadit.repository.CommentRepo;
import com.bikkadit.repository.PostRepo;
import com.bikkadit.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	
	private static Logger logger=LoggerFactory.getLogger(CommentServiceImpl.class);
	
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Long postId) {
		
		logger.info("At Entering point this is the info for createComment level logger"+postId);
		
	 Post post = this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));

		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment comment2 = this.commentRepo.save(comment);
		logger.info("At Exist point this is the info for createComment level logger");
		return this.modelMapper.map(comment2, CommentDto.class);
	}

	@Override
	public void deleteComment(Long commentId) {
		logger.info("At Entering point this is the info for deleteComment level logger"+commentId);
		
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("Comment", "commentId", commentId));
         
		this.commentRepo.delete(comment);
		logger.info("At Exist point this is the info for deleteComment level logger"+commentId);
	}

}
