package com.humber.Tasky.controller;

import com.humber.Tasky.model.Task;
import com.humber.Tasky.model.User;
import com.humber.Tasky.service.TaskService;
import com.humber.Tasky.service.UserService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final UserService userService;

    public TaskController(TaskService taskService, UserService userService) {
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping
    public List<Task> getAllTasks(@AuthenticationPrincipal User user) {
        return taskService.getAllTasks(user);
    }

    @PostMapping
    public Task createTask(@RequestBody Task task, @AuthenticationPrincipal User user) {
        return taskService.createTask(task, user);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable String id, @RequestBody Task taskDetails) {
        return taskService.updateTask(id, taskDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
    }

    @GetMapping("/calendar")
    public List<Task> getTasksByDateRange(
            @RequestParam LocalDateTime start,
            @RequestParam LocalDateTime end,
            @AuthenticationPrincipal User user) {
        return taskService.getTasksByDateRange(user, start, end);
    }

    @GetMapping("/completed")
    public List<Task> getCompletedTasks(
            @RequestParam boolean completed,
            @AuthenticationPrincipal User user) {
        return taskService.getCompletedTasks(user, completed);
    }

    @PostMapping("/{taskId}/share")
    public Task shareTask(
            @PathVariable String taskId,
            @RequestParam String recipientEmail,
            @AuthenticationPrincipal User user) {
        return taskService.shareTask(taskId, user,
                userService.getUserByEmail(recipientEmail)
                        .orElseThrow(() -> new RuntimeException("Recipient not found")));
    }
}