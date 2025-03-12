package com.ashutosh.taskmanager.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String title;
    
    private String description;
    
    private LocalDate dueDate;
    
    // For simplicity, priority is stored as a string (e.g., High, Medium, Low)
    private String priority;
    
    // Many tasks belong to one user
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    public Task() {}

    public Task(String title, String description, LocalDate dueDate, String priority, User user) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
        this.user = user;
    }
    
    // Getters and setters

    public Long getId() {
         return id;
    }

    public String getTitle() {
         return title;
    }

    public void setTitle(String title) {
         this.title = title;
    }

    public String getDescription() {
         return description;
    }

    public void setDescription(String description) {
         this.description = description;
    }

    public LocalDate getDueDate() {
         return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
         this.dueDate = dueDate;
    }

    public String getPriority() {
         return priority;
    }

    public void setPriority(String priority) {
         this.priority = priority;
    }

    public User getUser() {
         return user;
    }

    public void setUser(User user) {
         this.user = user;
    }
}
