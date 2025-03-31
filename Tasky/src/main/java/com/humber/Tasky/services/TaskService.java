package com.humber.Tasky.services;

import com.humber.Tasky.models.Task;
import com.humber.Tasky.models.User;
import com.humber.Tasky.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks(User owner) {
        return taskRepository.findByOwner(owner);
    }

    public Task createTask(Task task, User createdBy) {
        task.setCreatedBy(createdBy);
        task.setOwner(createdBy); // Default owner is the creator
        return taskRepository.save(task);
    }

    public Task updateTask(String id, Task taskDetails) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        task.setTitle(taskDetails.getTitle());
        task.setDescription(taskDetails.getDescription());
        task.setDueDate(taskDetails.getDueDate());
        task.setCompleted(taskDetails.isCompleted());
        task.setPriority(taskDetails.getPriority());

        return taskRepository.save(task);
    }

    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }

    public List<Task> getTasksByDateRange(User owner, LocalDateTime start, LocalDateTime end) {
        return taskRepository.findByOwnerAndDueDateBetween(owner, start, end);
    }

    public List<Task> getCompletedTasks(User owner, boolean completed) {
        return taskRepository.findByOwnerAndCompleted(owner, completed);
    }

    public Task shareTask(String taskId, User owner, User newOwner) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));

        if (!task.getOwner().equals(owner)) {
            throw new RuntimeException("You don't have permission to share this task");
        }

        Task sharedTask = new Task();
        sharedTask.setTitle(task.getTitle());
        sharedTask.setDescription(task.getDescription());
        sharedTask.setDueDate(task.getDueDate());
        sharedTask.setPriority(task.getPriority());
        sharedTask.setOwner(newOwner);
        sharedTask.setCreatedBy(owner);

        return taskRepository.save(sharedTask);
    }
}