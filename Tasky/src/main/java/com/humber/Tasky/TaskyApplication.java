package com.humber.Tasky;

import com.humber.Tasky.controllers.AuthController;
import com.humber.Tasky.models.Dish;
import com.humber.Tasky.models.MyUser;
import com.humber.Tasky.services.DishService;
import com.humber.Tasky.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskyApplication implements CommandLineRunner {

// constructor injection - Dish Service
	private final DishService dishService;
	private final UserService userService;

	public TaskyApplication(DishService dishService, UserService userService) {
		this.dishService = dishService;
        this.userService = userService;
    }

	public static void main(String[] args) {
		SpringApplication.run(TaskyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		MyUser admin = new MyUser(0, "templates/admin", "password", "ADMIN");
		userService.saveUser(admin);
		dishService.saveDish(new Dish("Pizza", "Italian", 10.0));
	}
}
