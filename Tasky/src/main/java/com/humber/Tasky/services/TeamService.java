package com.humber.Tasky.services;

import com.humber.Tasky.models.Task;
import com.humber.Tasky.models.Team;
import com.humber.Tasky.repositories.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TeamService {

    private final TeamRepository teamRepository;

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<Team> getTeams(){
        return teamRepository.findAll();
    }

    public Optional<Team> getTeamById(int id){
        return teamRepository.findById(id);
    }

    public int saveTeam(Team team){
        teamRepository.save(team);
        return 0;
    }

    public void updateTeam(Team team){
        teamRepository.save(team);
    }
    public int deleteTeamById(int id){
        //check if task exists
        if(teamRepository.existsById(id)){
            //delete the task
            teamRepository.deleteById(id);
            return 1;
        }
        //task does not exist
        return 0;
    }
    //get team records by teams
    public List<Team> getTeamRecordsByTeams(String teams){
        return teamRepository.findByTeams(teams);
    }
    //pagination method
    public Page<Team> getPaginatedTeams(int pageNo, int pageSize, String sortField, String sortDirection){
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNo-1, pageSize, sort);
        return teamRepository.findAll(pageable);
    }
}
