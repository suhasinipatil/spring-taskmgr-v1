package com.example.springtaskmgr.controllers;

import com.example.springtaskmgr.entities.Task;
import com.example.springtaskmgr.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TaskController {
   private TasksService tasksService;

    public TaskController(@Autowired TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @GetMapping("/tasks")
    List<Task> getTaskList(){
        return tasksService.getTasks();
    }

    @PostMapping("/tasks")
    Task createTask(@RequestBody Task task) {
        return tasksService.createTask(task.getTitle(), task.getDescription(), task.getDueDate());
    }

    @GetMapping("/tasks/{id}")
    Task getTask(@PathVariable("id") Integer id){
       return tasksService.getTaskById(id);
    }

    @DeleteMapping("/tasks/{id}")
    Task deleteTask(@PathVariable("id") Integer id){
        return tasksService.deleteTask(id);
    }

    @PatchMapping("/tasks/{id}")
    Task updateTask(@RequestBody Task task, @PathVariable("id") Integer id){
        return tasksService.updateTask(id, task.getTitle(), task.getDescription(), task.getDueDate());
    }
}
