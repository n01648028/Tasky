package com.humber.Tasky.services;

import com.humber.Tasky.models.Task;
import com.humber.Tasky.repositories.TaskRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    //Constructor Injection
    private final TaskRepository taskRepository;
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    //get all tasks from the repo layer
    public List<Task> getAllTasks(){
        //business logic should have been included here
        return taskRepository.findAll();
    }
    //get task by id
    public Optional<Task> getTaskById(int id){
        return taskRepository.findById(id);
    }
    //save a task to the db(calling the save method on the repo)
    //0=failure, 1=success
    public int saveTask(Task task){
//        //validation
//        if(dish.getPrice() > 20){
//            return 0;
//        }
        // save the task by calling the repo
        taskRepository.save(task);
        return 1;
    }
    //delete a task from the db
    //0=failure, 1=success
    public int deleteTaskById(int id){
        //check if task exists
        if(taskRepository.existsById(id)){
            //delete the task
            taskRepository.deleteById(id);
            return 1;
        }
        //task does not exist
        return 0;
    }
    //update a task from the db
    public void updateTask(Task task){
        taskRepository.save(task);
    }

    //get task records by title, assignee, and status
    public List<Task> getTaskByTitleAndAssigneeAndStatus(String title, String assignee, String status){
        return taskRepository.findByTitleAndAssigneeAndStatus(title, assignee, status);
    }
    //get task records by title, assignee, or status
    public List<Task> getTaskByTitleOrAssigneeOrStatus(Optional<String> title, Optional<String> assignee, Optional<String> status){
        return taskRepository.findByTitleOrAssigneeOrStatus(title, assignee, status);
    }

    //pagination method
    public Page<Task> getPaginatedTasks(int pageNo, int pageSize, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return taskRepository.findAll(pageable);
    }
}
