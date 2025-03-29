package com.humber.Tasky;

import com.humber.Tasky.models.Task;
import com.humber.Tasky.models.MyUser;
import com.humber.Tasky.services.TaskService;
import com.humber.Tasky.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@SpringBootApplication
public class TaskyApplication implements CommandLineRunner {

// constructor injection - TaskService
	private final TaskService taskService;
	private final UserService userService;
	private AuthenticationManager authenticationManager;

	public TaskyApplication(TaskService taskService, UserService userService, AuthenticationManager authenticationManager) {
		this.taskService = taskService;
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
		taskService.saveTask(new Task("Dish Task", "Bolos", "Clean Dishes", "Tasky", "2025-03-30", "To Do"));
		taskService.saveTask(new Task("Dish Task", "Danyyil", "Add Dishes", "Tasky", "2025-03-30", "To Do"));
		taskService.saveTask(new Task("Dish Task", "Ilker", "Deliver Dishes", "Tasky", "2025-03-30", "To Do"));
	}
}
