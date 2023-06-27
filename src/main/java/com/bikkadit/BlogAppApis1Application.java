package com.bikkadit;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.bikkadit.config.AppConstants;
import com.bikkadit.entities.Role;
import com.bikkadit.repository.RoleRepository;


@SpringBootApplication//this annotation provide feature of configuration anntatn also,
public class BlogAppApis1Application implements CommandLineRunner {
	
	@Autowired
	private RoleRepository roleRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogAppApis1Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		System.out.println(this.passwordEncoder.encode("xyz"));
		try {
			
			Role role=new Role();
			role.setId(AppConstants.ADMIN_USER);
			role.setName("ADMIN_USER");
			
			Role role1=new Role();
			role1.setId(AppConstants.NORMAL_USER);
			role1.setName("NORMAL_USER");
			
			List<Role> roles = List.of(role,role1);
			
			List<Role> result = this.roleRepo.saveAll(roles);
			
			result.forEach(r->{
				System.out.println(r.getName());
			});
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}

	
	
}
