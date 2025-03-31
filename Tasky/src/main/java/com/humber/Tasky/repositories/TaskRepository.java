package com.humber.Tasky.repositories;

import com.humber.Tasky.models.Task;
import com.humber.Tasky.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByOwner(User owner);
    List<Task> findByOwnerAndDueDateBetween(User owner, LocalDateTime start, LocalDateTime end);
    List<Task> findByOwnerAndCompleted(User owner, boolean completed);
}