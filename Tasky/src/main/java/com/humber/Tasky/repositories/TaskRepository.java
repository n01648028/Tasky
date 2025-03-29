package com.humber.Tasky.repositories;

import com.humber.Tasky.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    //custom method to find task records by tile, assignee, and status
    public List<Task> findByTitleAndAssigneeAndStatus(String title, String assignee, String status);
    //custom method to find task records by tile, assignee, or status
    public List<Task> findByTitleOrAssigneeOrStatus(Optional<String> title, Optional<String> assignee, Optional<String> status);
}
