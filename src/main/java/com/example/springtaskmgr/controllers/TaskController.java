package com.example.springtaskmgr.controllers;

import com.example.springtaskmgr.dtos.ErrorResponse;
import com.example.springtaskmgr.entities.Task;
import com.example.springtaskmgr.service.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
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
    ResponseEntity<List<Task>> getTaskList(){
        return ResponseEntity.ok(tasksService.getTasks());
    }

    @PostMapping("/tasks")
    ResponseEntity<Task> createTask(@RequestBody Task task) {
        var newTask = tasksService.createTask(task.getTitle(), task.getDescription(), task.getDueDate());
        return ResponseEntity.created(URI.create("/tasks/" + newTask.getId())).body(newTask);
    }

    @GetMapping("/tasks/{id}")
    ResponseEntity<Task> getTask(@PathVariable("id") Integer id){
       return ResponseEntity.ok(tasksService.getTaskById(id));
    }

    @DeleteMapping("/tasks/{id}")
    ResponseEntity<Task> deleteTask(@PathVariable("id") Integer id){
        return ResponseEntity.accepted().body(tasksService.deleteTask(id));
    }

    @PatchMapping("/tasks/{id}")
    ResponseEntity<Task> updateTask(@RequestBody Task task, @PathVariable("id") Integer id){
        var updatedTask = tasksService.updateTask(id, task.getTitle(), task.getDescription(), task.getDueDate());
        return ResponseEntity.accepted().body(updatedTask);
    }

    @ExceptionHandler(TasksService.TaskNotFoundException.class)
    ResponseEntity<ErrorResponse> handleErrors(TasksService.TaskNotFoundException e){
        return new ResponseEntity<>(
                new ErrorResponse(e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }
}
