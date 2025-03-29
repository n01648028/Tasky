package com.humber.Tasky.controllers;

import com.humber.Tasky.models.Task;
import com.humber.Tasky.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/Tasky")
public class TaskController {
    //dependency injection
    @Autowired
    private TaskService taskService;
    @Value("${tasky.name}")
    private String restaurantName;
    @Value("${page.size}")
    private int pageSize;
    //endpoint for home page
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("rName", restaurantName);
        return "home";
    }
    //endpoint for menu page (list of tasks)
    @GetMapping("/menu/{pageNo}")
    public String menu(Model model,
                       @RequestParam(required = false) String message,
                       @RequestParam(required = false) Optional<String> searchedTitle,
                       @RequestParam(required = false) Optional<String> searchedAssignee,
                       @RequestParam(required = false) Optional<String> searchedStatus,
                       @PathVariable int pageNo,
                       @RequestParam(defaultValue = "id") String sortField,
                       @RequestParam(defaultValue = "asc") String sortDirection
                       ) {
        //filter condition
        if(searchedTitle.isPresent() && searchedAssignee.isPresent() & searchedStatus.isPresent() ) {
//            List<Task> filteredTasks = taskService.getTaskByTitleAndAssigneeAndStatus(searchedTitle, searchedAssignee, searchedStatus);
            List<Task> filteredTasks = taskService.getTaskByTitleOrAssigneeOrStatus(searchedTitle, searchedAssignee, searchedStatus);
            model.addAttribute("tasks", filteredTasks);
            model.addAttribute("message", filteredTasks.isEmpty() ? "Filter failed!" : "Filter successful!");
            return "menu";
        }
        //general condition = return paginated records
        Page<Task> page = taskService.getPaginatedTasks(pageNo, pageSize, sortField, sortDirection);
        model.addAttribute("tasks", page.getContent());
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalItems", page.getTotalElements());
        //sorting info
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        //add information to display in menu like the tasks and the message if there is one
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        model.addAttribute("message", message);
        return "menu";
    }
}
