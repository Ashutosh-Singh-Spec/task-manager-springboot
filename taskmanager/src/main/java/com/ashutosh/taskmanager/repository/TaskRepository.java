package com.ashutosh.taskmanager.repository;

import com.ashutosh.taskmanager.model.Task;
import com.ashutosh.taskmanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUser(User user);
    
    // For search functionality based on title keyword (case-insensitive)
    List<Task> findByUserAndTitleContainingIgnoreCase(User user, String title);
}
