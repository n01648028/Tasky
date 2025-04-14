package com.humber.Tasky.controller;

import com.humber.Tasky.model.Team;
import com.humber.Tasky.service.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/teams")
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public List<Team> getAllTeams() {
        return teamService.getAllTeams();
    }

    @PostMapping
    public Team createTeam(@RequestBody Team team) {
        return teamService.createTeam(team);
    }

    @GetMapping("/{id}")
    public Optional<Team> getTeamById(@PathVariable String id) {
        return teamService.getTeamById(id);
    }

    @PostMapping("/{teamId}/add-member/{userId}")
    public void addMemberToTeam(@PathVariable String teamId, @PathVariable String userId) {
        teamService.addMemberToTeam(teamId, userId);
    }

    @DeleteMapping("/{teamId}/remove-member/{userId}")
    public void removeMemberFromTeam(@PathVariable String teamId, @PathVariable String userId) {
        teamService.removeMemberFromTeam(teamId, userId);
    }
}
