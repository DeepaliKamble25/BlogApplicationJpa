package com.bikkadit.entities;

import java.util.Date;
import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="posts")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post{
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Long postId;
	
	@Column(name="post_title")
	private String title;
	
	private String content;
	
	private String imageName;
	
	private Date addDate;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@ManyToOne
	private User user;
	
	@OneToMany(mappedBy="post",cascade= CascadeType.ALL)
	private Set<Comment> commets=new HashSet<>(); 
}

