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
    @Value("${tasky.name}")
    private String taskName;
    @Value("${page.size}")
    private int pageSize;
    @GetMapping("/tasks")
    //method to get all tasks from the service
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }
    //endpoint to open the add task form
    @GetMapping("/add")
    public String addDish(Model model) {
        //add information to display in add like the tasks
        model.addAttribute("task", new Task());
        return "admin/add-task";
    }
    //endpoint to save the task
    @PostMapping("/save")
    public String saveDish(@ModelAttribute Task task, Model model) {
        //0=failure, 1=success
        int statusCode = taskService.saveTask(task);
        //error condition
        if (statusCode == 0) {
            return "admin/add-task";
        }
        //success condition
        //save the data
        model.addAttribute("tasks", task);
        model.addAttribute("message", task.getTitle() + " added successfully");
        //open the menu page with the updated data
        return "menu";
    }
    //endpoint to delete a task
    @GetMapping("/delete/{id}")
    public String deleteDish(@PathVariable int id) {
        int deleteStatusCode = taskService.deleteTaskById(id);
        if (deleteStatusCode == 1) {
            return "redirect:/Tasky/menu/1?message=Task deleted successfully.";
        }
        //does delete fail (task does not exist)
        return "redirect:/Tasky/menu/1?message=Task does not exist!";
    }
    //endpoint to open the update task form
    @GetMapping("/update/{id}")
    public String updateDish(@PathVariable int id, Model model) {
        //get the task to be updated by its id from the database
        Optional<Task> optionalTaskToUpdate = taskService.getTaskById(id);
        if (optionalTaskToUpdate.isPresent()) {
            taskService.updateTask(optionalTaskToUpdate.get());
            model.addAttribute("task", optionalTaskToUpdate.get());
            return "admin/add-task";
        }
        return "redirect:/Tasky/menu/1?message=Task to be updated does not exist!";
    }
    //endpoint to update the task
    @PostMapping("/update")
    public String updateDish(@ModelAttribute Task task, Model model) {
        //call the update method in the service layer
        taskService.updateTask(task);
        return "redirect:/Tasky/menu/1?message=Task updated successfully!";
    }

}
