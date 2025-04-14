package com.humber.Tasky.service;

import com.humber.Tasky.model.Team;
import com.humber.Tasky.model.User;
import com.humber.Tasky.repository.TeamRepository;
import com.humber.Tasky.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    public TeamService(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    public Team createTeam(Team team) {
        return teamRepository.save(team);
    }

    public Optional<Team> getTeamById(String id) {
        return teamRepository.findById(id);
    }

    public void addMemberToTeam(String teamId, String userId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!team.getMemberIds().contains(userId)) {
            team.getMemberIds().add(userId);
            teamRepository.save(team);
        }
    }

    public void removeMemberFromTeam(String teamId, String userId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        if (team.getMemberIds().contains(userId)) {
            team.getMemberIds().remove(userId);
            teamRepository.save(team);
        }
    }
}
