package com.ialonso.firstcommit;

import com.ialonso.firstcommit.repositories.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FirstcommitApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(FirstcommitApplication.class, args);
		UserRepository userRepository = context.getBean(UserRepository.class);
		RoleRepository roleRepository = context.getBean(RoleRepository.class);
		StudentRepository studentRepository = context.getBean(StudentRepository.class);
		PictureRepository pictureRepository = context.getBean(PictureRepository.class);
		ResumeRepository resumeRepository = context.getBean(ResumeRepository.class);
		TagRepository tagRepository = context.getBean(TagRepository.class);

		System.out.println("User's number in DB when API initialized: " + userRepository.count());
		System.out.println("Role's number in DB when API initialized: " + roleRepository.count());
		System.out.println("Student's number in DB when API initialized: " + studentRepository.count());
		System.out.println("Picture's number in DB when API initialized: " + pictureRepository.count());
		System.out.println("Resume's number in DB when API initialized: " + resumeRepository.count());
		System.out.println("Tag's number in DB when API initialized: " + tagRepository.count());
	}

}

