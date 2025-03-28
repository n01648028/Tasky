package com.humber.Tasky.controllers;

import com.humber.Tasky.models.Task;
import com.humber.Tasky.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Tasky/admin")
public class AdminController {
    //dependency injection
    @Autowired
    private TaskService taskService;
    @Value("${restaurant.name}")
    private String restaurantName;
    @Value("${page.size}")
    private int pageSize;
    @GetMapping("/tasks")
    //method to get all dishes from the service
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }
    //endpoint to open the add dish form
    @GetMapping("/add")
    public String addDish(Model model) {
        //add information to display in add like the dishes
        model.addAttribute("task", new Task());
        return "admin/add-dish";
    }
    //endpoint to save the dish
    @PostMapping("/save")
    public String saveDish(@ModelAttribute Task task, Model model) {
        //0=failure, 1=sucess
        int statusCode = taskService.saveTask(task);
        //error condition
        if (statusCode == 0) {
            return "admin/add-dish";
        }
        //success condition
        //save the data
        model.addAttribute("tasks", task);
        model.addAttribute("message", task.getId() + " added successfully");
        //open the menu page with the updated data
        return "menu";
    }
    //endpoint to delete a dish
    @GetMapping("/delete/{id}")
    public String deleteDish(@PathVariable int id) {
        int deleteStatusCode = taskService.deleteTaskById(id);
        if (deleteStatusCode == 1) {
            return "redirect:/Tasky/menu/1?message=Dish deleted successfully.";
        }
        //does delete fail (dish does not exist)
        return "redirect:/Tasky/menu/1?message=Dish does not exist!";
    }
    //endpoint to open the update dish from
    @GetMapping("/update/{id}")
    public String updateDish(@PathVariable int id, Model model) {
        //get the dish to be updated by its id from the database
        Optional<Task> optionalTaskToUpdate = taskService.getTaskById(id);
        if (optionalTaskToUpdate.isPresent()) {
            taskService.updateTask(optionalTaskToUpdate.get());
            model.addAttribute("task", optionalTaskToUpdate.get());
            return "admin/add-dish";
        }
        return "redirect:/Tasky/menu/1?message=Task to be updated does not exist!";
    }
    //endpoint to update the dish
    @PostMapping("/update")
    public String updateDish(@ModelAttribute Task task, Model model) {
        //call the update method in the service layer
        taskService.updateTask(task);
        return "redirect:/Tasky/menu/1?message=Task updated successfully!";
    }
}
