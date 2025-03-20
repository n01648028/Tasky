package com.humber.Tasky;

import com.humber.Tasky.models.Dish;
import com.humber.Tasky.services.DishService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskyApplication implements CommandLineRunner {

// constructor injection - Dish Service
	private final DishService dishService;

	public TaskyApplication(DishService dishService) {
		this.dishService = dishService;
	}

	public static void main(String[] args) {
		SpringApplication.run(TaskyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		dishService.saveDish(new Dish("Pizza", "Italian", 10.0));
	}
}
