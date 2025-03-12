package com.ashutosh.taskmanager.model;
import jakarta.persistence.*;
import com.ashutosh.taskmanager.model.Task;

import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    // One-to-many relationship with tasks
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    // Getters and setters

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
         return password;
    }

    public void setPassword(String password) {
         this.password = password;
    }

    public Set<Task> getTasks() {
         return tasks;
    }

    public void setTasks(Set<Task> tasks) {
         this.tasks = tasks;
    }
}
