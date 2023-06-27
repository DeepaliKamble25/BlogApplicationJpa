package com.bikkadit.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bikkadit.entities.Post;
import com.bikkadit.entities.User;
import com.bikkadit.entities.Category;
@Repository
public interface PostRepo extends JpaRepository<Post, Long>{
	
	List<Post> findByUser(User user);
	
	List<Post> findByCategory (Category category);

	List<Post> findByTitleContaining(String title);
}
