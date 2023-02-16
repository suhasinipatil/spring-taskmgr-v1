package com.example.springtaskmgr;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class TaskController {
    private final List<Task> taskList;
    private AtomicInteger taskId = new AtomicInteger(0);

    public TaskController() {
        taskList = new ArrayList<>();
        taskList.add(new Task(taskId.incrementAndGet(), "task 1", "description 1", new Date()));
        taskList.add(new Task(taskId.incrementAndGet(), "task 2", "description 2", new Date()));
        taskList.add(new Task(taskId.incrementAndGet(), "task 3", "description 3", new Date()));
    }

    @GetMapping("/tasks")
    List<Task> getTaskList(){
        return taskList;
    }

    @PostMapping("/tasks")
    Task createTask(@RequestBody Task task) {
        var newTask = new Task(taskId.incrementAndGet(), task.getTitle(), task.getDescription(), task.getDueDate());
        taskList.add(newTask);
        return newTask;
    }

    @GetMapping("/tasks/{id}")
    Task getTask(@PathVariable("id") Integer id){
        for(int i = 0; i < taskList.size(); i++){
            if(taskList.get(i).id == id){
                return taskList.get(i);
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
    }

    @DeleteMapping("/tasks/{id}")
    Task deleteTask(@PathVariable("id") Integer id){
        for(int i = 0; i < taskList.size(); i++){
            if(taskList.get(i).id == id){
                Task task = taskList.get(i);
                taskList.remove(i);
                return task;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
    }

    @PatchMapping("/tasks/{id}")
    Task updateTask(@RequestBody Task task, @PathVariable("id") Integer id){
        for(int i = 0; i < taskList.size(); i++){
            if(taskList.get(i).id == id){
                Task oldTask = taskList.get(i);
                if(task.description != null)
                    oldTask.setDescription(task.description);
                if(task.dueDate != null)
                    oldTask.setDueDate(task.dueDate);
                if(task.title != null)
                    oldTask.setTitle(task.title);
                return oldTask;
            }
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
    }
}
