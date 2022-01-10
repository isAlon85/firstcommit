package com.ialonso.firstcommit;

import com.ialonso.firstcommit.repositories.RoleRepository;
import com.ialonso.firstcommit.repositories.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FirstcommitApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(FirstcommitApplication.class, args);
		UserRepository userRepository = context.getBean(UserRepository.class);
		RoleRepository roleRepository = context.getBean(RoleRepository.class);

		System.out.println("User's number in DB when API initialized: " + userRepository.count());
		System.out.println("Role's number in DB when API initialized: " + roleRepository.count());
	}

}

