package com.bikkadit.entities;

import javax.persistence.Entity;

import javax.persistence.Id;

import lombok.Data;
@Data
@Entity
public class Role {
	
	
	@Id
	private Integer id;
	
	private String name;
	
	
	
	

}
