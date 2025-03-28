package com.humber.Tasky.repositories;

import com.humber.Tasky.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
    //custom method to find dish records by category and price
    public List<Task> findByTitleAndAssigneeAndStatus(String title, String assignee, String status);
    public List<Task> findByTitleOrAssigneeOrStatus(Optional<String> title, Optional<String> assignee, Optional<String> status);
}
