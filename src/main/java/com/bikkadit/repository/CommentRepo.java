package com.bikkadit.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bikkadit.entities.Comment;
@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
	
	
	

}
