package com.humber.Tasky.controllers;

import com.humber.Tasky.models.Task;
import com.humber.Tasky.models.Team;
import com.humber.Tasky.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Controller
@RequestMapping("/Tasky/")
public class TeamController {
    private TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @Value("${page.size}")
    private int pageSize;


    //endpoint for menu page (list of tasks)
    @GetMapping("teams/{pageNo}")
    public String menu(Model model,
                       @RequestParam(required = false) String message,
                       @PathVariable int pageNo,
                       @RequestParam(defaultValue = "id") String sortField,
                       @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        //general condition = return paginated records
        Page<Team> page = teamService.getPaginatedTeams(pageNo, pageSize, sortField, sortDirection);
        model.addAttribute("teams", page.getContent());
        model.addAttribute("totalPagesTeams", page.getTotalPages());
        model.addAttribute("currentPageTeams", pageNo);
        model.addAttribute("totalItems", page.getTotalElements());
        //sorting info
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDirection", sortDirection);
        //add information to display in menu like the tasks and the message if there is one
        model.addAttribute("reverseSortDirection", sortDirection.equals("asc") ? "desc" : "asc");
        model.addAttribute("message", message);
        return "teams";
    }
    @GetMapping("/teams")
    public List<Team> getTeams() {
        return teamService.getTeams();
    }
    @PostMapping("admin/addTeam")
    public String addTeam(Model model, Team team) {
        model.addAttribute("teams", team);
        return "admin/teams";
    }
    @PostMapping("/saveTeam")
    public String saveTeam(@ModelAttribute Team team, Model model) {
        //0=failure, 1=success
        int statusCode = teamService.saveTeam(team);
        //error condition
        if (statusCode == 0) {
            return "admin/teams";
        }
        //success condition
        //save the data
        model.addAttribute("teams", team);
        model.addAttribute("message", team.getTeams() + " added successfully");
        //open the menu page with the updated data
        return "admin/teams";
    }
    @GetMapping("/update/{id}")
    public String updateTeam(@PathVariable int id, Model model) {
        //get the task to be updated by its id from the database
        Optional<Team> optionalTeamToUpdate = teamService.getTeamById(id);
        if (optionalTeamToUpdate.isPresent()) {
            teamService.updateTeam(optionalTeamToUpdate.get());
            model.addAttribute("teams", optionalTeamToUpdate.get());
            return "admin/teams";
        }
        return "redirect:/Tasky/teams/1?message=Team to be updated does not exist!";

    }
    @DeleteMapping("admin/delete/{id}")
    public String deleteTeam(@PathVariable int id) {
        int deleteStatusCode = teamService.deleteTeamById(id);
        if (deleteStatusCode == 1) {
            return "redirect:/Tasky/teams/1?message=Team deleted successfully.";
        }
        //does delete fail (task does not exist)
        return "redirect:/Tasky/teams/1?message=Task does not exist!";
    }
}
