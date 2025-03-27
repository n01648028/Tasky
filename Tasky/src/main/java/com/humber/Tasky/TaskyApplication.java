package com.humber.Tasky;

import com.humber.Tasky.controllers.AuthController;
import com.humber.Tasky.models.Dish;
import com.humber.Tasky.models.MyUser;
import com.humber.Tasky.services.DishService;
import com.humber.Tasky.services.MyUserDetailsService;
import com.humber.Tasky.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@SpringBootApplication
public class TaskyApplication implements CommandLineRunner {

// constructor injection - Dish Service
	private final DishService dishService;
	private final UserService userService;
	private AuthenticationManager authenticationManager;

	public TaskyApplication(DishService dishService, UserService userService, AuthenticationManager authenticationManager) {
		this.dishService = dishService;
        this.userService = userService;
		this.authenticationManager = authenticationManager;
    }

	public static void main(String[] args) {
		SpringApplication.run(TaskyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		MyUser admin = new MyUser(0, "templates/admin", "password", "ADMIN");
		userService.saveUser(admin);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(admin.getUsername(), "password");
		Authentication authentication = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		dishService.saveDish(new Dish("Pizza", "Italian", 10.0));
	}
}
