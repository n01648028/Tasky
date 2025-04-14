package com.humber.Tasky.repository;

import com.humber.Tasky.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
