package com.bikkadit.services;

import com.bikkadit.dto.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto,Long postId);

	
	void deleteComment(Long commentId);
}
