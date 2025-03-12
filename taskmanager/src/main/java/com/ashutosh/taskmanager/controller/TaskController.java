package com.ashutosh.taskmanager.controller;

import com.ashutosh.taskmanager.model.Task;
import com.ashutosh.taskmanager.model.User;
import com.ashutosh.taskmanager.repository.TaskRepository;
import com.ashutosh.taskmanager.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/tasks")
public class TaskController {
    
    @Autowired
    private TaskRepository taskRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    // Display list of tasks (with optional search by title)
    @GetMapping
    public String listTasks(Model model, Principal principal, 
                            @RequestParam(required = false) String keyword) {
        User user = userRepository.findByUsername(principal.getName());
        List<Task> tasks;
        if(keyword != null && !keyword.isEmpty()){
            tasks = taskRepository.findByUserAndTitleContainingIgnoreCase(user, keyword);
        } else {
            tasks = taskRepository.findByUser(user);
        }
        model.addAttribute("tasks", tasks);
        return "tasks";
    }
    
    // Show form to add a new task
    @GetMapping("/new")
    public String showTaskForm(Model model) {
        model.addAttribute("task", new Task());
        return "task_form";
    }
    
    // Save a new or updated task
    @PostMapping("/save")
    public String saveTask(@Valid Task task, BindingResult result, Principal principal) {
        if(result.hasErrors()){
            return "task_form";
        }
        // Associate the task with the currently logged-in user
        User user = userRepository.findByUsername(principal.getName());
        task.setUser(user);
        taskRepository.save(task);
        return "redirect:/tasks";
    }
    
    // Show form to edit an existing task
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model, Principal principal) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
        // Ensure the task belongs to the logged-in user
        if(!task.getUser().getUsername().equals(principal.getName())){
            return "redirect:/tasks";
        }
        model.addAttribute("task", task);
        return "task_form";
    }
    
    // Delete a task
    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable("id") Long id, Principal principal) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid task Id:" + id));
        if(task.getUser().getUsername().equals(principal.getName())){
            taskRepository.delete(task);
        }
        return "redirect:/tasks";
    }
}
