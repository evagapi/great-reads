package com.ironhack.greatreads;

import com.ironhack.greatreads.model.user.Role;
import com.ironhack.greatreads.model.user.User;
import com.ironhack.greatreads.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class GreatReadsApplication {

	public static void main(String[] args) {
		SpringApplication.run(GreatReadsApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	// @Bean
	CommandLineRunner run(UserService userService) {
		return args -> {
			userService.saveRole(new Role(null, "ROLE_USER"));
			userService.saveRole(new Role(null, "ROLE_LIBRARIAN"));
			userService.saveRole(new Role(null, "ROLE_ADMIN"));

			userService.createUser(new User("Matilda", "matilda", "matilda@greatreads.com", "brucebruce"), "ROLE_USER");
			userService.createUser(new User( "Madame Pince", "mpince","madamepince@greatreads.com", "restrictedsection"), "ROLE_USER");
			userService.createUser(new User("Eva", "eva","eva@greatreads.com", "admin"), "ROLE_USER");


			userService.addRoleToUser("eva", "ROLE_ADMIN");
			userService.addRoleToUser("eva", "ROLE_LIBRARIAN");
			userService.addRoleToUser("mpince", "ROLE_LIBRARIAN");
		};
	}
}
