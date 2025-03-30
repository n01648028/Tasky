package com.humber.Tasky.repositories;

import com.humber.Tasky.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {
   public List<Team> findByTeams(String teams);
}
